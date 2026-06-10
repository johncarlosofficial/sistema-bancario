package com.banco.service;

import org.springframework.stereotype.Service;

import com.banco.dao.ContaDAO;
import com.banco.dto.ContaResponseDTO;
import com.banco.model.Conta;
import com.banco.model.ContaBonus;
import com.banco.model.ContaCorrente;
import com.banco.model.ContaPoupanca;

// Camada responsável pelas regras de negócio do sistema bancário
@Service
public class ContaService {

    // Acesso aos dados (armazenamento em memória)
    private final ContaDAO contaDAO;

    public ContaService(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }

    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    // Cadastra uma nova conta com validações básicas
    public String cadastrarConta(String numero, String tipoConta, double saldoInicial) {

        // Remove espaços extras
        numero = numero.trim();

        // Valida se está vazio
        if (numero.isEmpty()) {
            return "Número da conta não pode ser vazio.";
        }

        // Valida tamanho do número
        if (numero.length() < 3 || numero.length() > 10) {
            return "Número da conta deve ter entre 3 e 10 caracteres.";
        }

        // Verifica duplicidade
        if (contaDAO.buscarPorNumero(numero) != null) {
            return "Número de conta já existe. Escolha outro número.";
        }

        // Valida saldo inicial
        if (saldoInicial < 0){
            return "Saldo inicial inválido. Por favor digite um valor maior ou igual a zero.";
        }

        // Cria conta do tipo selecionado
        Conta conta;
        if (tipoConta.equals("1")) {
            conta = new ContaCorrente(numero, saldoInicial);
        } else if (tipoConta.equals("2")) {
            conta = new ContaPoupanca(numero, saldoInicial);
        } else if (tipoConta.equals("3")) {
            conta = new ContaBonus(numero, saldoInicial);
        } else {
            return "Tipo de conta inválido.";
        }

        contaDAO.salvar(conta);
        return "Conta cadastrada com sucesso!";
    }

    // Consulta o saldo da conta sem formatar a resposta para a API.
    public double consultarSaldo(String numero) {
        Conta conta = buscarContaOuLancarExcecao(numero);
        return conta.getSaldo();
    }

    // Realiza crédito na conta e atualiza o saldo da conta em memória.
    public Conta realizarCredito(String numero, double valor) {

        // Valida se o valor de crédito não é negativo.
        if (valor < 0) {
            throw new IllegalArgumentException("Não é permitido realizar crédito com valor negativo.");
        }

        Conta conta = buscarContaOuLancarExcecao(numero);
        conta.setSaldo(conta.getSaldo() + valor);

        if (conta instanceof ContaBonus bonus) {
            bonus.adicionarPontosPorDeposito(valor);
        }

        return conta;
    }

    // Expõe um resumo da conta no formato solicitado pela API REST.
    public ContaResponseDTO consultarContaDetalhes(String numero) {
        Conta conta = buscarContaOuLancarExcecao(numero);
        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setNumero(conta.getNumero());
        contaResponseDTO.setSaldo(conta.getSaldo());
        contaResponseDTO.setTipoConta(obterTipoConta(conta));

        if (conta instanceof ContaBonus bonus) {
            contaResponseDTO.setBonus(bonus.getPontos());
        }

        return contaResponseDTO;
    }

    // Retorna o resumo da conta após um crédito para a API.
    public ContaResponseDTO realizarCreditoDetalhado(String numero, double valor) {
        Conta conta = realizarCredito(numero, valor);
        ContaResponseDTO contaResponseDTO = new ContaResponseDTO();
        contaResponseDTO.setNumero(conta.getNumero());
        contaResponseDTO.setSaldo(conta.getSaldo());
        contaResponseDTO.setTipoConta(obterTipoConta(conta));

        if (conta instanceof ContaBonus bonus) {
            contaResponseDTO.setBonus(bonus.getPontos());
        }

        return contaResponseDTO;
    }

    // Realiza débito (subtração de saldo)
    public String realizarDebito(String numero, double valor) {

        // Valor deve ser positivo
        if (valor <= 0) {
            return "O valor do débito deve ser maior que zero.";
        }

        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        //se for conta simples ou bônus, pode ficar até -1000
        if ((conta instanceof ContaCorrente || conta instanceof ContaBonus) && (conta.getSaldo() - valor < -1000)) {
            return "Saldo insuficiente para realizar o débito. A conta pode ficar no máximo com saldo negativo de R$ -1000.";
        }

        //conta poupança não pode ficar negativa
        if (conta instanceof ContaPoupanca && (conta.getSaldo() - valor < 0)) {
            return "Saldo insuficiente para realizar o débito. A conta poupança não pode ficar com saldo negativo.";
        }

        // Subtrai valor do saldo (permitindo saldo negativo)
        conta.setSaldo(conta.getSaldo() - valor);

        return "Débito realizado com sucesso! Novo saldo: R$ " + conta.getSaldo();
    }

    public String realizarTransferencia(String origem, String destino, double valor){
      
        // Valor deve ser positivo
        if (valor <= 0) {
            return "O valor da transferência deve ser maior que zero.";
        }

        Conta contaOrigem = contaDAO.buscarPorNumero(origem);
        Conta contaDestino = contaDAO.buscarPorNumero(destino);

        // Verifica existência das contas
        if (contaOrigem == null) {
            return "Conta de origem não encontrada.";
        }
        if (contaDestino == null) {
            return "Conta de destino não encontrada.";
        }

        //verifica se é a mesma conta
        if (origem.equals(destino)) {
            return "Conta de origem e destino devem ser diferentes.";
        }

        //se for conta simples ou bônus, pode ficar até -1000
        if ((contaOrigem instanceof ContaCorrente || contaOrigem instanceof ContaBonus) && (contaOrigem.getSaldo() - valor < -1000)) {
            return "Saldo insuficiente para realizar a transferência. A conta pode ficar no máximo com saldo negativo de R$ -1000.";
        }
        //conta poupança não pode ficar negativa
        if (contaOrigem instanceof ContaPoupanca && (contaOrigem.getSaldo() - valor < 0)) {
            return "Saldo insuficiente para realizar a transferência. A conta poupança não pode ficar com saldo negativo.";
        }

        //verifica saldo suficiente na conta de origem
        if (contaOrigem.getSaldo() < valor) {
            return "Saldo insuficiente para realizar a transferência.";
        }
        // Realiza débito na conta de origem
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        // Realiza crédito na conta de destino
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        if (contaDestino instanceof ContaBonus bonus) {
            bonus.adicionarPontosPorTransferenciaRecebida(valor);
        }

        return "Transferência realizada com sucesso! Novo saldo da conta " + origem + ": R$ " + contaOrigem.getSaldo();
    }

    // Aplica juros à todas as contas-poupança cadastradas
    public String renderJuros(double taxa){
        int contasAtualizadas = 0;
        for(Conta conta : contaDAO.listar()){
            if(conta instanceof ContaPoupanca poupanca){
                poupanca.renderJuros(taxa);
                contasAtualizadas++;
            }
        }
        if (contasAtualizadas == 0) {
            return "Nenhuma conta poupança encontrada para aplicar a taxa.";
        }

        return "Taxa aplicada com sucesso em " + contasAtualizadas + " contas poupança.";
    }

    //consulta pontos de uma conta bonus
    public String consultarPontos(String numero) {
        Conta conta = contaDAO.buscarPorNumero(numero);

        // Verifica se a conta existe
        if (conta == null) {
            return "Conta não encontrada.";
        }

        if (conta instanceof ContaBonus bonus) {
            return "Pontos da conta " + numero + ": " + bonus.getPontos();
        } else {
            return "A conta " + numero + " não é do tipo Bonus.";
        }
    }

    public Conta consultarConta(String numero) {
        return buscarContaOuLancarExcecao(numero);
    }

    // Centraliza a busca de conta para evitar repetição e manter a regra em um único ponto.
    private Conta buscarContaOuLancarExcecao(String numero) {
        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        return conta;
    }

    // Mapeia a conta para um texto legível que será devolvido pela API.
    private String obterTipoConta(Conta conta) {
        if (conta instanceof ContaBonus) {
            return "Conta Bônus";
        }

        if (conta instanceof ContaPoupanca) {
            return "Conta Poupança";
        }

        if (conta instanceof ContaCorrente) {
            return "Conta Corrente";
        }

        return "Conta";
    }
}
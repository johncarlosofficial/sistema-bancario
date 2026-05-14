package com.banco.service;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;
import com.banco.model.ContaBonus;
import com.banco.model.ContaCorrente;
import com.banco.model.ContaPoupanca;

// Camada responsável pelas regras de negócio do sistema bancário
public class ContaService {

    // Acesso aos dados (armazenamento em memória)
    private ContaDAO contaDAO = new ContaDAO();

    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    // Cadastra uma nova conta com validações básicas
    public String cadastrarConta(String numero, String tipoConta) {

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

        // Cria conta do tipo selecionado, com saldo 0
        Conta conta;
        if (tipoConta.equals("1")) {
            conta = new ContaCorrente(numero, 0);
        } else if (tipoConta.equals("2")) {
            conta = new ContaPoupanca(numero, 0);
        } else if (tipoConta.equals("3")) {
            conta = new ContaBonus(numero, 0);
        } else {
            return "Tipo de conta inválido.";
        }

        contaDAO.salvar(conta);
        return "Conta cadastrada com sucesso!";
    }

    // Consulta o saldo de uma conta pelo número
    public String consultarSaldo(String numero) {
        Conta conta = contaDAO.buscarPorNumero(numero);

        // Verifica se a conta existe
        if (conta == null) {
            return "Conta não encontrada.";
        }

        return "Saldo da conta " + numero + ": R$ " + conta.getSaldo();
    }

    // Realiza crédito (adição de saldo)
    public String realizarCredito(String numero, double valor) {

        // Valor deve ser positivo
        if (valor <= 0) {
            return "O valor do crédito deve ser maior que zero.";
        }

        Conta conta = contaDAO.buscarPorNumero(numero);

        // Verifica existência da conta
        if (conta == null) {
            return "Conta não encontrada.";
        }

        // Soma valor ao saldo atual
        conta.setSaldo(conta.getSaldo() + valor);

        if (conta instanceof ContaBonus bonus) {
            bonus.adicionarPontosPorDeposito(valor);
        }

        return "Crédito realizado com sucesso! Novo saldo: R$ " + conta.getSaldo();
    }

    // Realiza débito (subtração de saldo)
    public String realizarDebito(String numero, double valor) {

        // Valor deve ser positivo
        if (valor <= 0) {
            return "O valor do débito deve ser maior que zero.";
        }



        Conta conta = contaDAO.buscarPorNumero(numero);
        //se for conta simples ou bônus, pode ficar até -1000
        if ((conta instanceof ContaCorrente || conta instanceof ContaBonus) && (conta.getSaldo() - valor < -1000)) {
            return "Saldo insuficiente para realizar o débito. A conta pode ficar no máximo com saldo negativo de R$ -1000.";
        }

        //conta poupança não pode ficar negativa
        if (conta instanceof ContaPoupanca && (conta.getSaldo() - valor < 0)) {
            return "Saldo insuficiente para realizar o débito. A conta poupança não pode ficar com saldo negativo.";
        }  
               
        // Verifica existência da conta
        if (conta == null) {
            return "Conta não encontrada.";
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

        //verifica se é a mesma conta
        if (origem.equals(destino)) {
            return "Conta de origem e destino devem ser diferentes.";
        }
        //verifica se o saldo atual da conta de origem

        //se for conta simples ou bônus, pode ficar até -1000
        if ((contaOrigem instanceof ContaCorrente || contaOrigem instanceof ContaBonus) && (contaOrigem.getSaldo() - valor < -1000)) {
            return "Saldo insuficiente para realizar a transferência. A conta pode ficar no máximo com saldo negativo de R$ -1000.";
        }
        //conta poupança não pode ficar negativa
        if (contaOrigem instanceof ContaPoupanca && (contaOrigem.getSaldo() - valor < 0)) {
            return "Saldo insuficiente para realizar a transferência. A conta poupança não pode ficar com saldo negativo.";
        }
        
        // Verifica existência das contas
        if (contaOrigem == null) {
            return "Conta de origem não encontrada.";
        }
        if (contaDestino == null) {
            return "Conta de destino não encontrada.";
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
}
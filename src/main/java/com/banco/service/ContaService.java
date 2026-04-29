package com.banco.service;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;

// Camada responsável pelas regras de negócio do sistema bancário
public class ContaService {

    // Acesso aos dados (armazenamento em memória)
    private ContaDAO contaDAO = new ContaDAO();

    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    // Cadastra uma nova conta com validações básicas
    public String cadastrarConta(String numero) {

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

        // Cria conta com saldo inicial zero
        Conta conta = new Conta(numero, 0);
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

        return "Crédito realizado com sucesso! Novo saldo: R$ " + conta.getSaldo();
    }

    // Realiza débito (subtração de saldo)
    public String realizarDebito(String numero, double valor) {

        // Valor deve ser positivo
        if (valor <= 0) {
            return "O valor do débito deve ser maior que zero.";
        }

        Conta conta = contaDAO.buscarPorNumero(numero);

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

        return "Transferência realizada com sucesso! Novo saldo da conta " + origem + ": R$ " + contaOrigem.getSaldo();
    }
}
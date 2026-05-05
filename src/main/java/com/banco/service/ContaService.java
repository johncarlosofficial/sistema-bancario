package com.banco.service;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;

// Camada de regras de negócio
public class ContaService {

    private ContaDAO contaDAO = new ContaDAO(); // acesso aos dados

    // Inicializa serviço
    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    // Cadastra nova conta
    public String cadastrarConta(String numero) {

        numero = numero.trim(); // remove espaços

        // validações básicas
        if (numero.isEmpty()) {
            return "Número da conta não pode ser vazio.";
        }

        if (numero.length() < 3 || numero.length() > 10) {
            return "Número da conta deve ter entre 3 e 10 caracteres.";
        }

        // verifica duplicidade
        if (contaDAO.buscarPorNumero(numero) != null) {
            return "Número de conta já existe.";
        }

        // cria conta com saldo zero
        Conta conta = new Conta(numero, 0);
        contaDAO.salvar(conta);

        return "Conta cadastrada com sucesso!";
    }

    // Consulta saldo
    public String consultarSaldo(String numero) {
        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        return "Saldo da conta " + numero + ": R$ " + conta.getSaldo();
    }

    // Realiza crédito
    public String realizarCredito(String numero, double valor) {

        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        try {
            conta.creditar(valor); // usa regra da entidade
            return "Crédito realizado! Saldo: R$ " + conta.getSaldo();
        } catch (IllegalArgumentException e) {
            return e.getMessage(); // trata erro
        }
    }

    // Realiza débito
    public String realizarDebito(String numero, double valor) {

        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        try {
            conta.debitar(valor); // impede saldo negativo
            return "Débito realizado! Saldo: R$ " + conta.getSaldo();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    // Realiza transferência
    public String realizarTransferencia(String origem, String destino, double valor){

        // impede mesma conta
        if (origem.equals(destino)) {
            return "Contas devem ser diferentes.";
        }

        Conta contaOrigem = contaDAO.buscarPorNumero(origem);
        Conta contaDestino = contaDAO.buscarPorNumero(destino);

        // valida existência
        if (contaOrigem == null) return "Conta de origem não encontrada.";
        if (contaDestino == null) return "Conta de destino não encontrada.";

        try {
            // debita de uma e credita na outra
            contaOrigem.debitar(valor);
            contaDestino.creditar(valor);

            return "Transferência OK! Saldo origem: R$ " + contaOrigem.getSaldo();

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
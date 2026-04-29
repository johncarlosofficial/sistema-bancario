package com.banco.service;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;

public class ContaService {

    private ContaDAO contaDAO = new ContaDAO();

    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    public String cadastrarConta(String numero) {
        // remove espaços
        numero = numero.trim();

        // valida vazio
        if (numero.isEmpty()) {
            return "Número da conta não pode ser vazio.";
        }

        // valida tamanho
        if (numero.length() < 3 || numero.length() > 10) {
            return "Número da conta deve ter entre 3 e 10 caracteres.";
        }

        // valida duplicidade
        if (contaDAO.buscarPorNumero(numero) != null) {
            return "Número de conta já existe. Escolha outro número.";
        }

        Conta conta = new Conta(numero, 0);
        contaDAO.salvar(conta);

        return "Conta cadastrada com sucesso!";
    }

    public String consultarSaldo(String numero) {
        Conta conta = contaDAO.buscarPorNumero(numero);
        if (conta == null) {
            return "Conta não encontrada.";
        }
        return "Saldo da conta " + numero + ": R$ " + conta.getSaldo();
    }

    public String realizarCredito(String numero, double valor) {

        // valida valor
        if (valor <= 0) {
            return "O valor do crédito deve ser maior que zero.";
        }

        Conta conta = contaDAO.buscarPorNumero(numero);

        if (conta == null) {
            return "Conta não encontrada.";
        }

        conta.setSaldo(conta.getSaldo() + valor);

        return "Crédito realizado com sucesso! Novo saldo: R$ " + conta.getSaldo();
    }
}
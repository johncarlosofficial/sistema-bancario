package com.banco.service;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;

import java.util.Scanner;

public class ContaService {

    private ContaDAO contaDAO = new ContaDAO();
    Scanner sc = new Scanner(System.in);

    public void iniciarServico() {
        System.out.println("[SERVICE] Serviço iniciado.");
    }

    public void selecionarOperacao(String operacao) {
        switch (operacao) {
            case "1" :
                System.out.println("Você escolheu a opção 1: Cadastrar Conta");
                CadastrarConta();
                break;
            case "2" :
                System.out.println("Você escolheu a opção 2. Consultar Saldo");
                System.out.println("Infelizmente essa operação está indisponível no momento.\n"
                        + "Por favor tente novamente mais tarde.");
                // TODO: Implementar funcionalidade de consulta de saldo.
                break;
            case "3" :
                System.out.println("Você escolheu a opção 3. Crédito");
                System.out.println("Infelizmente essa operação está indisponível no momento.\n"
                        + "Por favor tente novamente mais tarde.");
                // TODO: Implementar operação de crédito em conta.
                break;
            case "4" :
                System.out.println("Você escolheu a opção 4. Débito");
                System.out.println("Infelizmente essa operação está indisponível no momento.\n"
                        + "Por favor tente novamente mais tarde.");
                // TODO: Implementar operação de débito em conta.
                break;
            case "5" :
                System.out.println("Você escolheu a opção 5. Transferência");
                System.out.println("Infelizmente essa operação está indisponível no momento.\n"
                        + "Por favor tente novamente mais tarde.");
                // TODO: Implementar transferência entre contas.
                break;

            // PARA TESTES: Lista contas cadastradas.
            /*
            case "6":
                System.out.println("Você escolheu a opção 6. Listar Contas");
                System.out.println("Contas cadastradas:");
                for (Conta conta : contaDAO.getContas()) {
                    System.out.println("Número: " + conta.getNumero() + " | Saldo: " + conta.getSaldo());
                }
                break;
            */

        }
    }

    public void CadastrarConta() {
        int numero;
        double saldoInicial = 0;
        System.out.println("[SERVICE] Cadastrar Conta");
        System.out.println("Digite o numero da conta:");
        numero = Integer.parseInt(sc.nextLine());
        for (Conta conta : contaDAO.getContas()) {
            if (conta.getNumero() == numero) {
                System.out.println("Número de conta já existe. Por favor escolha outro número.");
                return;
            }
        }
        try {
            Conta conta = new Conta(numero, saldoInicial);
            contaDAO.save(conta);
            System.out.println("Conta Cadastrada com sucesso!");
        }catch (Exception e){
            System.out.println("Erro ao criar conta.");
        }
    }
}

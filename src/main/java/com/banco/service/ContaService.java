package com.banco.service;

import com.banco.model.Conta;

import java.util.Scanner;

public class ContaService {

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


        }
    }

    public void CadastrarConta() {
        String nome;
        String cpf;
        double saldoInicial = 0;
        System.out.println("[SERVICE] Cadastrar Conta");
        System.out.println("Digite o nome do conta:");
        nome = sc.nextLine();
        System.out.println("Digite o CPF do conta:");
        cpf = sc.nextLine();
        try {
            Conta conta = new Conta(nome, cpf, saldoInicial);
        }catch (Exception e){
            System.out.println("Erro ao criar conta.");
        }
    }
}

package com.banco.ui;

import com.banco.service.ContaService;

import java.util.Scanner;

public class TerminalUI {

    private ContaService contaService;
    private Scanner sc = new Scanner(System.in);

    public TerminalUI() {
        this.contaService = new ContaService();
    }

    public void iniciar() {
        System.out.println("[UI] Interface iniciada.");
        contaService.iniciarServico();

        boolean continuar = true;
        while (continuar) {
            continuar = opcoes();
        }

        System.out.println("[UI] Programa encerrado.");
    }

    public boolean opcoes() {
        System.out.println("""
                1. Cadastrar Conta
                2. Consultar Saldo
                3. Realizar Crédito
                0. Sair
                """);

        String resposta = sc.nextLine();

        if (resposta.equals("0")) return false;

        if (resposta.equals("1")) {
            System.out.println("Digite o número da nova conta:");
            String numero = sc.nextLine();

            String resultado = contaService.cadastrarConta(numero);
            System.out.println(resultado);

        } else if (resposta.equals("2")) {
            System.out.println("Digite o número da sua conta:");
            String numero = sc.nextLine();

            String resultado = contaService.consultarSaldo(numero);
            System.out.println(resultado);

        } else if (resposta.equals("3")) {
            System.out.println("Digite o número da conta:");
            String numero = sc.nextLine();

            System.out.println("Digite o valor do crédito:");
            double valor = lerValor();

            String resultado = contaService.realizarCredito(numero, valor);
            System.out.println(resultado);

        } else {
            System.out.println("Opção inválida. Tente novamente.");
        }

        return true;
    }

    private double lerValor() {
        while (true) {
            try {
                String entrada = sc.nextLine();
                double valor = Double.parseDouble(entrada);

                if (valor <= 0) {
                    System.out.println("Digite um valor maior que zero:");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número válido:");
            }
        }
    }
}
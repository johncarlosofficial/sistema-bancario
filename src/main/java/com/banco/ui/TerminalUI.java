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
                0. Sair
                """);

        String resposta = sc.nextLine();

        if (resposta.equals("0")) return false;

        if (resposta.equals("1")) {
            System.out.println("Digite o número da conta:");
            String numero = sc.nextLine();

            String resultado = contaService.cadastrarConta(numero);
            System.out.println(resultado);
        }

        return true;
    }
}
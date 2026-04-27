package com.banco.ui;

import com.banco.service.ContaService;

import java.util.Scanner;

public class TerminalUI {

    private ContaService contaService;
    Scanner sc = new Scanner(System.in);

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
        System.out.println("[UI] Digite um número de 1 à 5 para selecionar uma das opções abaixo:");
        System.out.println(
                """
                    1. Cadastrar Conta
                    2. Consultar Saldo [WIP]
                    3. Crédito [WIP]
                    4. Débito [WIP]
                    5. Transferência [WIP]
                    0. Sair
                """
        );
        String resposta = sc.nextLine();

        if (resposta.equals("0")) {
            return false;
        }

        contaService.selecionarOperacao(resposta);
        return true;
    }
}

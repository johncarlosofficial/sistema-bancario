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
    }

    public void opcoes() {
        System.out.println("[UI] Digite um número de 1 à 5 para selecionar uma das opções abaixo:");
        System.out.println(
                """
                    1. Cadastrar Conta\s
                    2. Consultar Saldo [WIP]\s
                    3. Crédito [WIP]\s
                    4. Débito [WIP]\s
                    5. Transferência [WIP]\s
                """
        );
        String resposta = sc.nextLine();
        contaService.selecionarOperacao(resposta);
    }
}

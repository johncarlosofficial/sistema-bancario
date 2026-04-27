package com.banco.ui;
import com.banco.service.ContaService;

public class TerminalUI {

    private ContaService contaService;

    public TerminalUI() {
        this.contaService = new ContaService();
    }

    public void iniciar() {
        System.out.println("[UI] Interface iniciada.");
        contaService.iniciarServico();
    }
}

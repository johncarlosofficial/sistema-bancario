package com.banco;

import com.banco.ui.TerminalUI;

// Classe de entrada da aplicação
public class Main {

    public static void main(String[] args) {

        // Inicializa a interface de usuário (terminal)
        TerminalUI ui = new TerminalUI();

        // Inicia o fluxo principal do sistema
        ui.iniciar();
    }
}
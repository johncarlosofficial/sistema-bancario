package com.banco.ui;

import com.banco.service.ContaService;

import java.util.Scanner;

// Camada responsável pela interação com o usuário via terminal
public class TerminalUI {

    private ContaService contaService;

    // Scanner para leitura de entrada do usuário
    private Scanner sc = new Scanner(System.in);

    public TerminalUI() {
        this.contaService = new ContaService();
    }

    // Inicia a interface e mantém o programa em execução até o usuário sair
    public void iniciar() {
        System.out.println("[UI] Interface iniciada.");
        contaService.iniciarServico();

        boolean continuar = true;

        // Loop principal do sistema
        while (continuar) {
            continuar = opcoes();
        }

        System.out.println("[UI] Programa encerrado.");
    }

    // Exibe menu e direciona para a operação escolhida
    public boolean opcoes() {
        System.out.println("""
                1. Cadastrar Conta
                2. Consultar Saldo
                3. Realizar Crédito
                4. Realizar Débito
                5. realizar transferência
                0. Sair
                """);

        String resposta = sc.nextLine();

        // Encerra o programa
        if (resposta.equals("0")) return false;

        // Cadastro de conta
        if (resposta.equals("1")) {
            System.out.println("Digite o número da nova conta:");
            String numero = sc.nextLine();

            String resultado = contaService.cadastrarConta(numero);
            System.out.println(resultado);

        // Consulta de saldo
        } else if (resposta.equals("2")) {
            System.out.println("Digite o número da sua conta:");
            String numero = sc.nextLine();

            String resultado = contaService.consultarSaldo(numero);
            System.out.println(resultado);

        // Operação de crédito
        } else if (resposta.equals("3")) {
            System.out.println("Digite o número da conta:");
            String numero = sc.nextLine();

            System.out.println("Digite o valor do crédito:");
            double valor = lerValor(); // leitura segura

            String resultado = contaService.realizarCredito(numero, valor);
            System.out.println(resultado);

        // Operação de débito
        } else if (resposta.equals("4")) {
            System.out.println("Digite o número da conta:");
            String numero = sc.nextLine();

            System.out.println("Digite o valor do débito:");
            double valor = lerValor(); // reutiliza validação

            String resultado = contaService.realizarDebito(numero, valor);
            System.out.println(resultado);

        // Operação de transferência
        } else if (resposta.equals("5")) {
            System.out.println("Digite o número da conta de origem:");
            String origem = sc.nextLine();

            System.out.println("Digite o número da conta de destino:");
            String destino = sc.nextLine();

            System.out.println("Digite o valor da transferência:");
            double valor = lerValor(); // reutiliza validação

            String resultado = contaService.realizarTransferencia(origem, destino, valor);
            System.out.println(resultado);

        } else {
            // Trata opção inválida
            System.out.println("Opção inválida. Tente novamente.");
        }

        return true;
    }

    // Lê um valor numérico com validação
    private double lerValor() {
        while (true) {
            try {
                String entrada = sc.nextLine();
                double valor = Double.parseDouble(entrada);

                // Garante valor positivo
                if (valor <= 0) {
                    System.out.println("Digite um valor maior que zero:");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                // Trata entrada inválida
                System.out.println("Valor inválido. Digite um número válido:");
            }
        }
    }
}
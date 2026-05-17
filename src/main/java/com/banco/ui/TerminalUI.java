package com.banco.ui;

import java.util.Scanner;

import com.banco.service.ContaService;

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
                5. Realizar transferência
                6. Render Juros
                7. Consultar Pontos (Conta Bonus)
                0. Sair
                """);

        String resposta = sc.nextLine();

        // Encerra o programa
        if (resposta.equals("0")) return false;

        // Cadastro de conta
        if (resposta.equals("1")) {
            String tipoConta = lerTipoConta();
            if (tipoConta == null) return true;

            System.out.println("Digite o número da nova conta:");
            String numero = sc.nextLine();

            double saldoInicial = 0;

            // Contas que exigem saldo inicial
            if (tipoConta.equals("1") || tipoConta.equals("2")) {
                System.out.println("Digite o saldo inicial da conta:");
                saldoInicial = lerValor();
            }

            String resultado = contaService.cadastrarConta(
                numero,
                tipoConta,
                saldoInicial
            );

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

        } else if (resposta.equals("6")) {
            System.out.println("Digite a taxa a ser aplicada:");
            double taxa = lerValor(); // reutiliza validação

            String resultado = contaService.renderJuros(taxa);
            System.out.println(resultado);
        } else if (resposta.equals("7")) {
            System.out.println("Digite o número da conta:");
            String numero = sc.nextLine();

            String resultado = contaService.consultarPontos(numero);
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

    // Lê um tipo de conta com validação
    private String lerTipoConta() {
        while (true) {
            System.out.println("""
                    Escolha o tipo de conta:
                        1 - Conta Corrente
                        2 - Conta Poupança
                        3 - Conta Bonus
                        0 - Cancelar
                    """);
            String tipo = sc.nextLine().trim();

            if (tipo.equals("1") || tipo.equals("2") || tipo.equals("3")) {
                return tipo;
            } else if (tipo.equals("0")) {
                return null; // Cancela a operação
            } else {
                System.out.println("Opção inválida. Escolha 1, 2, 3 ou 0.");
            }
        }
    }
}
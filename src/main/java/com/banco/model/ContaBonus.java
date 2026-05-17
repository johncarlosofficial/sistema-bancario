package com.banco.model;

public class ContaBonus extends Conta {

    // Para esse tipo de conta, a pontuacao inicial e de 10 pontos.
    private static final int PONTUACAO_INICIAL = 10;
    private int pontos;

    public ContaBonus(String numero, double saldoInicial) {
        super(numero, saldoInicial);
        this.pontos = PONTUACAO_INICIAL;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPontosPorDeposito(double valor) {
        pontos += calcularPontuacao(valor, "deposito");
    }

    public void adicionarPontosPorTransferenciaRecebida(double valor) {
        pontos += calcularPontuacao(valor, "transferencia_recebida");
    }

    public int calcularPontuacao(double valor, String tipoTransacao) {
        if (tipoTransacao.equalsIgnoreCase("deposito")) {
            return (int) (valor / 100); // 1 ponto para cada R$ 100,00 de deposito
        } else if (tipoTransacao.equalsIgnoreCase("transferencia_recebida")) {
            return (int) (valor / 150); // 1 ponto para cada R$ 150,00 recebidos
        }

        return 0;
    }
}
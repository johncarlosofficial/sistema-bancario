package com.banco.model;

// Representa uma conta poupança
public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numero, double saldoInicial) {
        super(numero, saldoInicial);
    }

    // Aplica taxa de juros ao saldo da conta
    public void renderJuros(double taxaJuros) {
        double rendimento = getSaldo() * taxaJuros;
        setSaldo(getSaldo() + rendimento);
    }
}
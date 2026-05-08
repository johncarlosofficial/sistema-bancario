package com.banco.model;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numero, double saldoInicial) {
        super(numero, saldoInicial);
    }

    public void renderJuros(double taxaJuros) {
        double rendimento = getSaldo() * taxaJuros;
        setSaldo(getSaldo() + rendimento);
    }
}
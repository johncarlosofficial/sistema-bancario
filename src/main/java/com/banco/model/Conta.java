package com.banco.model;

// Representa uma conta bancária simples
public class Conta {

    // Identificador único da conta
    private String numero;

    // Saldo atual da conta (pode ser negativo)
    private double saldo;

    // Cria uma conta com número e saldo inicial
    public Conta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    // Retorna o número da conta
    public String getNumero() {
        return numero;
    }

    // Retorna o saldo atual
    public double getSaldo() {
        return saldo;
    }

    // Atualiza o saldo da conta
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
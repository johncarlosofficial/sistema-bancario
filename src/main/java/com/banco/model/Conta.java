package com.banco.model;

// Representa uma conta bancária
public class Conta {

    private String numero; // número da conta
    private double saldo; // saldo atual

    // Construtor
    public Conta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    // Retorna número da conta
    public String getNumero() {
        return numero;
    }

    // Retorna saldo atual
    public double getSaldo() {
        return saldo;
    }

    // Adiciona valor ao saldo
    public void creditar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        this.saldo += valor;
    }

    // Subtrai valor do saldo (sem permitir negativo)
    public void debitar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        if (this.saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.saldo -= valor;
    }
}
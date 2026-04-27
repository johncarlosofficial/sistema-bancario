package com.banco.model;

public class Conta {
    private String nome;
    private String cpf;
    private double saldo;

    public Conta(String nome, String cpf, double saldoInicial) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldo = saldoInicial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}


package com.banco.model;

// Representa uma conta-corrente
public class ContaCorrente extends Conta {
    public ContaCorrente(String numero, double saldoInicial) {
        super(numero, saldoInicial);
    }
    // No momento é idêntica à conta abstrata padrão, mas no futuro podem ser adicionadas regras de negócio específicas aqui
}
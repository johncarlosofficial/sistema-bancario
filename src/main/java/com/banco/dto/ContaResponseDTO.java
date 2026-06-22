package com.banco.dto;

// Representa o resumo de uma conta para a resposta da API REST.
public class ContaResponseDTO {

    private String tipoConta;
    private String numero;
    private double saldo;
    private Integer bonus;

    public ContaResponseDTO() {
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
}

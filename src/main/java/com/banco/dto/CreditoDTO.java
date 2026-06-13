package com.banco.dto;

// Recebe o valor do crédito no corpo da requisição da API.
public class CreditoDTO {

    private double valor;

    public CreditoDTO() {
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

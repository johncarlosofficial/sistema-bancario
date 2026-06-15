package com.banco.dto;

// Recebe o valor do crédito no corpo da requisição da API.
public class ValorDTO {

    private double valor;

    public ValorDTO() {
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

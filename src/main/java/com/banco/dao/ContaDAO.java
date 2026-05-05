package com.banco.dao;

import com.banco.model.Conta;
import java.util.ArrayList;
import java.util.List;

// Simula banco de dados em memória
public class ContaDAO {

    private List<Conta> contas = new ArrayList<>(); // lista de contas

    // Salva conta
    public void salvar(Conta conta) {
        contas.add(conta);
    }

    // Busca conta pelo número
    public Conta buscarPorNumero(String numero) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null; // não encontrada
    }

    // Lista todas contas
    public List<Conta> listar() {
        return contas;
    }
}
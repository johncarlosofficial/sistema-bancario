package com.banco.dao;

import com.banco.model.Conta;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

// Simula banco de dados em memória
@Repository
public class ContaDAO {

    private final List<Conta> contas = new ArrayList<>(); // lista de contas

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
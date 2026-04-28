package com.banco.dao;

import com.banco.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
    private List<Conta> contas = new ArrayList<>();

    public void salvar(Conta conta) {
        contas.add(conta);
    }

    public Conta buscarPorNumero(String numero) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public List<Conta> listar() {
        return contas;
    }
}
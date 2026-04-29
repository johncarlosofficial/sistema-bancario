package com.banco.dao;

import com.banco.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    // Lista em memória que armazena as contas (sem persistência em banco)
    private List<Conta> contas = new ArrayList<>();

    // Salva uma nova conta na lista
    public void salvar(Conta conta) {
        contas.add(conta);
    }

    // Busca uma conta pelo número
    public Conta buscarPorNumero(String numero) {
        for (Conta conta : contas) {
            // Compara o número da conta
            if (conta.getNumero().equals(numero)) {
                return conta; // retorna a conta se encontrar
            }
        }
        return null; // retorna null se não existir
    }

    // Retorna todas as contas cadastradas
    public List<Conta> listar() {
        return contas;
    }
}
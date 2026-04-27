package com.banco.dao;

import com.banco.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
    private List<Conta> contas = new ArrayList<>();

    public void save(Conta conta) {
        contas.add(conta);
    }
}

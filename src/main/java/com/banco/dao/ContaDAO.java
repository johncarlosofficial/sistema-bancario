package com.banco.dao;

import com.banco.model.Conta;

import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
    private List<Conta> contas = new ArrayList<>();

    public void save(Conta conta) {
        contas.add(conta);
    }

// FOR TESTING:
//    public void listContas() {
//        for (int i = 0; i <= contas.size(); i++) {
//            System.out.println("Nome = " + contas.get(i).getNome());
//            System.out.println("CPF = " + contas.get(i).getCpf());
//            System.out.println("Saldo = " + contas.get(i).getSaldo());
//        }
//    }
}

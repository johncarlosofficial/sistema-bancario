package com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.banco.dao.ContaDAO;
import com.banco.model.Conta;
import com.banco.model.ContaBonus;
import com.banco.model.ContaCorrente;
import com.banco.model.ContaPoupanca;

class ContaServiceTest {

    private ContaService contaService;

    @BeforeEach
    void setup() {
        ContaDAO contaDAO = new ContaDAO();
        contaService = new ContaService(contaDAO);
    }

    @Test
    void deveCadastrarContaCorrente() {

        String resultado =
                contaService.cadastrarConta("123", "1", 1000.0);

        assertEquals(
                "Conta cadastrada com sucesso!",
                resultado
        );

        Conta conta = contaService.consultarConta("123");

        assertNotNull(conta);
        assertInstanceOf(ContaCorrente.class, conta);
        assertEquals(1000.0, conta.getSaldo());
    }

    @Test
    void deveCadastrarContaPoupanca() {

        String resultado =
                contaService.cadastrarConta("456", "2", 500.0);

        assertEquals(
                "Conta cadastrada com sucesso!",
                resultado
        );

        Conta conta = contaService.consultarConta("456");

        assertNotNull(conta);
        assertInstanceOf(ContaPoupanca.class, conta);
        assertEquals(500.0, conta.getSaldo());
    }

    @Test
    void deveCadastrarContaBonus() {

        String resultado =
                contaService.cadastrarConta("789", "3", 2000.0);

        assertEquals(
                "Conta cadastrada com sucesso!",
                resultado
        );

        Conta conta = contaService.consultarConta("789");

        assertNotNull(conta);
        assertInstanceOf(ContaBonus.class, conta);
        assertEquals(2000.0, conta.getSaldo());

        ContaBonus bonus = (ContaBonus) conta;
        assertEquals(10, bonus.getPontos());
    }
}

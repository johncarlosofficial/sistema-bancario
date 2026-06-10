package com.banco.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void deveConsultarContaParaDiferentesTipos() {
        contaService.cadastrarConta("100", "1", 100.0);
        contaService.cadastrarConta("200", "2", 200.0);
        contaService.cadastrarConta("300", "3", 300.0);

        Conta contaCorrente = contaService.consultarConta("100");
        Conta contaPoupanca = contaService.consultarConta("200");
        Conta contaBonus = contaService.consultarConta("300");

        assertInstanceOf(ContaCorrente.class, contaCorrente);
        assertInstanceOf(ContaPoupanca.class, contaPoupanca);
        assertInstanceOf(ContaBonus.class, contaBonus);
    }

    @Test
    void deveConsultarSaldoCorretamente() {
        contaService.cadastrarConta("400", "1", 1500.0);

        double saldo = contaService.consultarSaldo("400");

        assertEquals(1500.0, saldo);
    }

    @Test
    void deveRealizarCredito() {
        contaService.cadastrarConta("500", "1", 1000.0);

        Conta conta = contaService.realizarCredito("500", 250.0);

        assertNotNull(conta);
        assertEquals(1250.0, conta.getSaldo());
    }

    @Test
    void deveFalharAoRealizarCreditoComValorNegativo() {
        contaService.cadastrarConta("600", "1", 1000.0);

        assertThrows(IllegalArgumentException.class, () -> contaService.realizarCredito("600", -50.0));
    }

    @Test
    void deveAplicarBonificacaoParaContaBonus() {
        contaService.cadastrarConta("700", "3", 1000.0);

        Conta conta = contaService.realizarCredito("700", 200.0);

        assertNotNull(conta);
        assertEquals(1200.0, conta.getSaldo());
        assertInstanceOf(ContaBonus.class, conta);
        assertEquals(12, ((ContaBonus) conta).getPontos());
    }
}

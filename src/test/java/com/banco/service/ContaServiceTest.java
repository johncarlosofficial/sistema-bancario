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

    @Test
    void deveRealizarDebitoNormal() {
        contaService.cadastrarConta("800", "1", 1000.0);
        
        String resultado = contaService.realizarDebito("800", 250.0);
        
        assertEquals("Débito realizado com sucesso! Novo saldo: R$ 750.0", resultado);
        assertEquals(750.0, contaService.consultarSaldo("800"));
    }

    @Test
    void deveFalharAoRealizarDebitoComValorNegativo() {
        contaService.cadastrarConta("801", "1", 1000.0);
        
        String resultado = contaService.realizarDebito("801", -50.0);
        
        assertEquals("O valor do débito deve ser maior que zero.", resultado);
        assertEquals(1000.0, contaService.consultarSaldo("801")); // Garante que o saldo não mudou
    }

    @Test
    void naoDevePermitirSaldoNegativoEmContaPoupancaNoDebito() {
        contaService.cadastrarConta("802", "2", 500.0); // Tipo 2 = Poupança
        
        String resultado = contaService.realizarDebito("802", 600.0);
        
        assertEquals("Saldo insuficiente para realizar o débito. A conta poupança não pode ficar com saldo negativo.", resultado);
        assertEquals(500.0, contaService.consultarSaldo("802")); // Garante que o saldo não mudou
    }

    @Test
    void deveFalharAoRealizarTransferenciaComValorNegativo() {
        contaService.cadastrarConta("900", "1", 1000.0);
        contaService.cadastrarConta("901", "1", 1000.0);
        String resultado = contaService.realizarTransferencia("900", "901", -100.0);
        assertEquals("O valor da transferência deve ser maior que zero.", resultado);
    }

    @Test
    void naoDevePermitirSaldoNegativoEmContaPoupancaNaTransferencia() {
        contaService.cadastrarConta("902", "2", 500.0); // Origem Poupança
        contaService.cadastrarConta("903", "1", 1000.0); // Destino Corrente
        String resultado = contaService.realizarTransferencia("902", "903", 600.0);
        assertEquals("Saldo insuficiente para realizar a transferência. A conta poupança não pode ficar com saldo negativo.", resultado);
    }

    @Test
    void deveAplicarBonificacaoParaContaBonusAoReceberTransferencia() {
        contaService.cadastrarConta("904", "1", 1000.0); // Origem
        contaService.cadastrarConta("905", "3", 1000.0); // Destino Bônus (Inicia com 10 pontos)
        
        contaService.realizarTransferencia("904", "905", 300.0);
        
        // 300 / 150 = 2 pontos adicionais (regra da classe ContaBonus)
        ContaBonus contaDestino = (ContaBonus) contaService.consultarConta("905");
        assertEquals(12, contaDestino.getPontos());
        assertEquals(1300.0, contaDestino.getSaldo());
    }

}

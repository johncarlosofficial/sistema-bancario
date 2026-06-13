package com.banco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.ContaDTO;
import com.banco.dto.ValorDTO;
import com.banco.dto.TransferenciaDTO;
import com.banco.service.ContaService;

@RestController
@RequestMapping("/banco/conta")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public String cadastrarConta(@RequestBody ContaDTO contaDTO) {

        return contaService.cadastrarConta(
                contaDTO.getNumero(),
                contaDTO.getTipoConta(),
                contaDTO.getSaldoInicial()
        );
    }

    // Consulta os dados completos da conta para a API REST.
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarConta(@PathVariable String id) {
        try {
            return ResponseEntity.ok(contaService.consultarContaDetalhes(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    // Consulta apenas o saldo da conta para a API REST.
    @GetMapping("/{id}/saldo")
    public ResponseEntity<?> consultarSaldo(@PathVariable String id) {
        try {
            return ResponseEntity.ok(contaService.consultarSaldo(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    // Aplica um crédito à conta através do serviço de negócio.
    @PutMapping("/{id}/credito")
    public ResponseEntity<?> realizarCredito(@PathVariable String id, @RequestBody ValorDTO valorDTO) {
        try {
            return ResponseEntity.ok(contaService.realizarCreditoDetalhado(id, valorDTO.getValor()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    // Aplica um débito à conta através do serviço de negócio.
    @PutMapping("/{id}/debito")
    public ResponseEntity<?> realizarDebito(@PathVariable String id, @RequestBody ValorDTO valorDTO) {
        String resultado = contaService.realizarDebito(id, valorDTO.getValor());
        
        if (resultado.contains("sucesso")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
    }

    // Realiza transferência entre contas
    @PutMapping("/transferencia")
    public ResponseEntity<?> realizarTransferencia(@RequestBody TransferenciaDTO transferenciaDTO) {
        String resultado = contaService.realizarTransferencia(
                transferenciaDTO.getFrom(),
                transferenciaDTO.getTo(),
                transferenciaDTO.getAmount()
        );
        if (resultado.contains("sucesso")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultado);
        }
    }

}
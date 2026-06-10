package com.banco.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.dto.ContaDTO;
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
}
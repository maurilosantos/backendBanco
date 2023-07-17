package br.com.banco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.banco.entities.Transferencia;
import br.com.banco.services.ExtratoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/extrato")
public class ExtratoController {
    private final ExtratoService extratoService;

    @Autowired
    public ExtratoController(ExtratoService extratoService) {
        this.extratoService = extratoService;
    }

    @GetMapping
    public ResponseEntity<List<Transferencia>> obterExtratoBancario(
            @RequestParam(value = "numeroConta", required = false) Long numeroConta,
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(value = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(value = "nomeResponsavel", required = false) String nomeResponsavel) {
        List<Transferencia> extrato = extratoService.obterExtratoBancario(numeroConta, dataInicio, dataFim, nomeResponsavel);
        return ResponseEntity.ok(extrato);
    }

    @GetMapping("/saldo-total")
    public ResponseEntity<BigDecimal> calcularSaldoTotal(
            @RequestParam(value = "numeroConta", required = false) Long numeroConta) {
        List<Transferencia> extrato = extratoService.obterExtratoBancario(numeroConta, null, null, null);
        BigDecimal saldoTotal = extratoService.calcularSaldoTotal(extrato);
        return ResponseEntity.ok(saldoTotal);
    }

    @GetMapping("/saldo-total-no-periodo")
    public ResponseEntity<BigDecimal> calcularSaldoTotalNoPeriodo(
            @RequestParam(value = "numeroConta", required = false) Long numeroConta,
            @RequestParam(value = "dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(value = "dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Transferencia> extrato = extratoService.obterExtratoBancario(numeroConta, dataInicio, dataFim, null);
        BigDecimal saldoTotalNoPeriodo = extratoService.calcularSaldoTotalNoPeriodo(extrato, dataInicio, dataFim);
        return ResponseEntity.ok(saldoTotalNoPeriodo);
    }
}

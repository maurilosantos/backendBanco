package br.com.banco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.banco.entities.Transferencia;
import br.com.banco.repository.TransferenciaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExtratoService {
    private final TransferenciaRepository transferenciaRepository;

    @Autowired
    public ExtratoService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    public List<Transferencia> obterExtratoBancario(Long numeroConta, LocalDateTime dataInicio, LocalDateTime dataFim, String nomeOperador) {
        if (numeroConta != null && (dataInicio != null || dataFim != null || nomeOperador != null)) {
            // Caso todos os filtros sejam informados
            return transferenciaRepository.findByContaIdAndDataTransferenciaBetweenAndNomeOperadorTransacao(
                    numeroConta, dataInicio, dataFim, nomeOperador
            );
        } else if (numeroConta != null) {
            // Caso seja informado o número da conta bancária como filtro
            return transferenciaRepository.findByContaId(numeroConta);
        } else if (dataInicio != null || dataFim != null || nomeOperador != null) {
            // Caso seja informado o período de tempo e/ou nome do operador como filtro
            return transferenciaRepository.findByDataTransferenciaBetweenAndNomeOperadorTransacao(dataInicio, dataFim, nomeOperador);
        } else {
            // Caso não seja informado nenhum filtro, retornar todas as transferências
            return transferenciaRepository.findAll();
        }
    }

    public BigDecimal calcularSaldoTotal(List<Transferencia> transferencias) {
        BigDecimal saldoTotal = BigDecimal.ZERO;

        for (Transferencia transferencia : transferencias) {
            if ("SAIDA".equals(transferencia.getTipo())) {
                saldoTotal = saldoTotal.subtract(transferencia.getValor());
            } else {
                saldoTotal = saldoTotal.add(transferencia.getValor());
            }
        }

        return saldoTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal calcularSaldoTotalNoPeriodo(List<Transferencia> transferencias, LocalDateTime dataInicio, LocalDateTime dataFim) {
        BigDecimal saldoTotalNoPeriodo = BigDecimal.ZERO;

        for (Transferencia transferencia : transferencias) {
            if (transferencia.getDataTransferencia().isAfter(dataInicio) && transferencia.getDataTransferencia().isBefore(dataFim)) {
                if ("SAIDA".equals(transferencia.getTipo())) {
                    saldoTotalNoPeriodo = saldoTotalNoPeriodo.subtract(transferencia.getValor());
                } else {
                    saldoTotalNoPeriodo = saldoTotalNoPeriodo.add(transferencia.getValor());
                }
            }
        }

        return saldoTotalNoPeriodo.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}

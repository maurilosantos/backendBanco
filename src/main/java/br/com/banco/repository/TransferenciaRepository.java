package br.com.banco.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.banco.entities.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
	
	List<Transferencia> findByContaIdAndDataTransferenciaBetweenAndNomeOperadorTransacao(
       Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim, String nomeOperadorTransacao);
	
	List<Transferencia> findByDataTransferenciaBetweenAndNomeOperadorTransacao(
            LocalDateTime dataInicio, LocalDateTime dataFim, String nomeOperadorTransacao);
	
	List<Transferencia> findByContaId(Long contaId);
    
}

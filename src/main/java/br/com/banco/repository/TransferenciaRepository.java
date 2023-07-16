package br.com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.banco.entities.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    
}

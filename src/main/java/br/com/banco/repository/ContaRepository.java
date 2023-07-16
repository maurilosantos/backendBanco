package br.com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.banco.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    
}


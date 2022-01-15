package br.com.pubfuture.controlefinanceiro.repository;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query(value = "SELECT sum(saldo) FROM conta", nativeQuery = true)
    BigDecimal saldoTotal();

}

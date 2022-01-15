package br.com.pubfuture.controlefinanceiro.repository;

import br.com.pubfuture.controlefinanceiro.domain.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query(value = "SELECT * FROM despesa WHERE tipo_despesa LIKE %?%", nativeQuery = true)
    Page<Despesa> findByTipoDespesa(String tipo, Pageable pageable);

    @Query(value = "SELECT * FROM despesa WHERE data_pagamento BETWEEN ?1 AND ?2", nativeQuery = true)
    Page<Despesa> findByDatas(String inicio, String fim, Pageable pageable);

    @Query(value = "SELECT SUM(valor) FROM receita", nativeQuery = true)
    BigDecimal somarTotal();

}

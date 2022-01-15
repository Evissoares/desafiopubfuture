package br.com.pubfuture.controlefinanceiro.repository;

import br.com.pubfuture.controlefinanceiro.domain.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query(value = "SELECT * FROM receita WHERE tipo_receita LIKE %?%", nativeQuery = true)
    Page<Receita> findByTipoReceita(String tipo, Pageable pageable);

    @Query(value = "SELECT * FROM receita WHERE data_recebimento BETWEEN ?1 AND ?2", nativeQuery = true)
    Page<Receita> findByDatas(String inicio, String fim, Pageable pageable);

    @Query(value = "SELECT SUM(valor) FROM receita", nativeQuery = true)
    BigDecimal somarTotal();

}

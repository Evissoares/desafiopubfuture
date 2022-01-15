package br.com.pubfuture.controlefinanceiro.dto.receita;

import br.com.pubfuture.controlefinanceiro.dto.conta.ContaResponseBody;
import br.com.pubfuture.controlefinanceiro.enums.TipoReceita;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReceitaResponseBody implements Serializable{

    private Long id;
    private BigDecimal valor;
    private LocalDate dataRecebimento;
    private LocalDate dataRecebimentoEsperado;
    private String descricao;
    private TipoReceita tipoReceita;
    private ContaResponseBody conta;

}

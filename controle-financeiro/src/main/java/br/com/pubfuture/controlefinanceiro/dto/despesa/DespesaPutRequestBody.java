package br.com.pubfuture.controlefinanceiro.dto.despesa;

import br.com.pubfuture.controlefinanceiro.enums.TipoDespesa;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DespesaPutRequestBody implements Serializable {

    private Long id;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private LocalDate dataPagamentoEsperado;
    private TipoDespesa tipoDespesa;
    private Long contaId;
}

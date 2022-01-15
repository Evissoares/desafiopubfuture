package br.com.pubfuture.controlefinanceiro.dto.conta;

import br.com.pubfuture.controlefinanceiro.enums.TipoConta;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ContaResponseBody implements Serializable {

    private Long id;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private String instituicaoFinanceira;
}

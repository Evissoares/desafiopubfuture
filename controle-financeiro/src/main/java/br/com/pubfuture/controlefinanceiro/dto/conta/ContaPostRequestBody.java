package br.com.pubfuture.controlefinanceiro.dto.conta;

import br.com.pubfuture.controlefinanceiro.enums.TipoConta;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class ContaPostRequestBody implements Serializable {

    private BigDecimal saldo;
    private TipoConta tipoConta;
    private String instituicaoFinanceira;

}

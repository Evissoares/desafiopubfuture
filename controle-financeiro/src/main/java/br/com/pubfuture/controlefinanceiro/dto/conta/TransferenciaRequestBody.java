package br.com.pubfuture.controlefinanceiro.dto.conta;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class TransferenciaRequestBody implements Serializable {

    private Long idContaOrigem;
    private Long idContaDestino;
    private BigDecimal valorTransferencia;

}

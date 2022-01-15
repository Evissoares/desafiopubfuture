package br.com.pubfuture.controlefinanceiro.exception;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaldoInsuficienteException extends RuntimeException{

    public static final String SALDO_INSUFICIENTE_MSG =
            "A conta %s no banco %s não possui saldo para esta operação";

    public SaldoInsuficienteException(Conta contaOrigem){
        super(String.format(SALDO_INSUFICIENTE_MSG,
                contaOrigem.getTipoConta().toString().toLowerCase(Locale.ROOT), contaOrigem.getInstituicaoFinanceira()));

    }
}

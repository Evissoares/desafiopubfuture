package br.com.pubfuture.controlefinanceiro.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ConsultaEntreDatas implements Serializable {

    private LocalDate inicio;
    private LocalDate fim;
}

package br.com.pubfuture.controlefinanceiro.domain;

import br.com.pubfuture.controlefinanceiro.enums.TipoDespesa;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private LocalDate dataPagamentoEsperado;
    @Enumerated(EnumType.STRING)
    private TipoDespesa tipoDespesa;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conta conta;

}

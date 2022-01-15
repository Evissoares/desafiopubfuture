package br.com.pubfuture.controlefinanceiro.domain;

import br.com.pubfuture.controlefinanceiro.enums.TipoReceita;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Receita implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    private LocalDate dataRecebimento;
    private LocalDate dataRecebimentoEsperado;
    private String descricao;
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Conta conta;
    @Enumerated(EnumType.STRING)
    private TipoReceita tipoReceita;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receita receita = (Receita) o;

        if (!Objects.equals(id, receita.id)) return false;
        if (!Objects.equals(valor, receita.valor)) return false;
        if (!Objects.equals(dataRecebimento, receita.dataRecebimento))
            return false;
        if (!Objects.equals(dataRecebimentoEsperado, receita.dataRecebimentoEsperado))
            return false;
        if (!Objects.equals(descricao, receita.descricao)) return false;
        if (!Objects.equals(conta, receita.conta)) return false;
        return Objects.equals(tipoReceita, receita.tipoReceita);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + (dataRecebimento != null ? dataRecebimento.hashCode() : 0);
        result = 31 * result + (dataRecebimentoEsperado != null ? dataRecebimentoEsperado.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (conta != null ? conta.hashCode() : 0);
        result = 31 * result + (tipoReceita != null ? tipoReceita.hashCode() : 0);
        return result;
    }
}

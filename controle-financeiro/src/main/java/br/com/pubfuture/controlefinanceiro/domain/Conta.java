package br.com.pubfuture.controlefinanceiro.domain;

import br.com.pubfuture.controlefinanceiro.enums.TipoConta;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal saldo;
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;
    private String instituicaoFinanceira;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conta conta = (Conta) o;

        if (!Objects.equals(id, conta.id)) return false;
        if (!Objects.equals(saldo, conta.saldo)) return false;
        if (tipoConta != conta.tipoConta) return false;
        return Objects.equals(instituicaoFinanceira, conta.instituicaoFinanceira);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (saldo != null ? saldo.hashCode() : 0);
        result = 31 * result + (tipoConta != null ? tipoConta.hashCode() : 0);
        result = 31 * result + (instituicaoFinanceira != null ? instituicaoFinanceira.hashCode() : 0);
        return result;
    }
}

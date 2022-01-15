package br.com.pubfuture.controlefinanceiro.mapper;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {

    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract Conta paraConta(ContaPutRequestBody contaPutRequestBody);

    public abstract Conta paraConta(ContaPostRequestBody contaPostRequestBody);


}

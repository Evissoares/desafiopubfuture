package br.com.pubfuture.controlefinanceiro.mapper;

import br.com.pubfuture.controlefinanceiro.domain.Receita;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaResponseBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ReceitaMapper {


    public static final ReceitaMapper INSTANCE = Mappers.getMapper(ReceitaMapper.class);

    public abstract Receita paraReceita(ReceitaPutRequestBody receitaPutRequestBody);

    public abstract Receita paraReceita(ReceitaPostRequestBody receitaPostRequestBody);

    public abstract ReceitaResponseBody paraResponse(Receita receita);

}

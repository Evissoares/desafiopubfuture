package br.com.pubfuture.controlefinanceiro.mapper;

import br.com.pubfuture.controlefinanceiro.domain.Despesa;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaResponseBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DespesaMapper {

    public static final DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);

    public abstract Despesa paraReceita(DespesaPostRequestBody despesaPostRequestBody);

    public abstract Despesa paraDespesa(DespesaPutRequestBody despesaPutRequestBody);

    public abstract DespesaResponseBody paraResponse(Despesa despesa);


}


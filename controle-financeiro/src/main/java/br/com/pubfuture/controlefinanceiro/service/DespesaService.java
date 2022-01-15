package br.com.pubfuture.controlefinanceiro.service;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import br.com.pubfuture.controlefinanceiro.domain.Despesa;
import br.com.pubfuture.controlefinanceiro.dto.ConsultaEntreDatas;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaResponseBody;
import br.com.pubfuture.controlefinanceiro.exception.BadRequestException;
import br.com.pubfuture.controlefinanceiro.mapper.DespesaMapper;
import br.com.pubfuture.controlefinanceiro.repository.ContaRepository;
import br.com.pubfuture.controlefinanceiro.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final ContaRepository contaRepository;

    public DespesaResponseBody cadastrar(DespesaPostRequestBody despesaPost) {

        Conta conta = contaRepository.findById(despesaPost.getContaId())
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));
        Despesa despesa = DespesaMapper.INSTANCE.paraReceita(despesaPost);
        despesa.setConta(conta);
        return DespesaMapper.INSTANCE.paraResponse(despesaRepository.save(despesa));
    }

    public DespesaResponseBody editar(DespesaPutRequestBody despesaPutRequestBody) {
        Conta conta = contaRepository.findById(despesaPutRequestBody.getContaId())
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));
        buscarPorIdOuLancaException(despesaPutRequestBody.getId());
        Despesa despesaAtualizada = DespesaMapper.INSTANCE.paraDespesa(despesaPutRequestBody);
        despesaAtualizada.setConta(conta);
        despesaRepository.save(despesaAtualizada);
        return DespesaMapper.INSTANCE.paraResponse(despesaAtualizada);
    }

    public Page<Despesa> listarPorPeriodo(ConsultaEntreDatas consulta, Pageable pageable) {
        String inicio = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(consulta.getInicio());
        String fim = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(consulta.getFim());
        return despesaRepository.findByDatas(inicio, fim, pageable);

    }

    public Page<Despesa> listarPorTipoDespesa(String tipo, Pageable pageable) {
        tipo = tipo.toUpperCase();
        return despesaRepository.findByTipoDespesa(tipo, pageable);

    }

    public void remover(long id) {
        despesaRepository.delete(buscarPorIdOuLancaException(id));
    }

    public BigDecimal somarTotal() {
        return despesaRepository.somarTotal();
    }

    protected Despesa buscarPorIdOuLancaException(long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Despesa não encontrada"));
    }
}

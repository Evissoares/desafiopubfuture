package br.com.pubfuture.controlefinanceiro.service;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import br.com.pubfuture.controlefinanceiro.domain.Receita;
import br.com.pubfuture.controlefinanceiro.dto.ConsultaEntreDatas;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaResponseBody;
import br.com.pubfuture.controlefinanceiro.exception.BadRequestException;
import br.com.pubfuture.controlefinanceiro.mapper.ReceitaMapper;
import br.com.pubfuture.controlefinanceiro.repository.ContaRepository;
import br.com.pubfuture.controlefinanceiro.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ContaRepository contaRepository;

    public ReceitaResponseBody cadastrar(ReceitaPostRequestBody receitaPost) {

        Conta conta = contaRepository.findById(receitaPost.getContaId())
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));
        Receita receita = ReceitaMapper.INSTANCE.paraReceita(receitaPost);
        receita.setConta(conta);
        return ReceitaMapper.INSTANCE.paraResponse(receitaRepository.save(receita));
    }

    public ReceitaResponseBody editar(ReceitaPutRequestBody receitaPutRequestBody) {
        Conta conta = contaRepository.findById(receitaPutRequestBody.getContaId())
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));
        buscarPorIdOuLancaException(receitaPutRequestBody.getId());
        Receita receitaAtualizada = ReceitaMapper.INSTANCE.paraReceita(receitaPutRequestBody);
        receitaAtualizada.setConta(conta);
        receitaRepository.save(receitaAtualizada);
        return ReceitaMapper.INSTANCE.paraResponse(receitaAtualizada);

    }

    public Page<Receita> listarPorPeriodo(ConsultaEntreDatas consulta, Pageable pageable) {
        String inicio = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(consulta.getInicio());
        String fim = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(consulta.getFim());
        return receitaRepository.findByDatas(inicio, fim, pageable);

    }

    public Page<Receita> listarPorTipoReceita(String tipo, Pageable pageable) {
        tipo = tipo.toUpperCase();
        return receitaRepository.findByTipoReceita(tipo, pageable);

    }

    public void remover(long id) {
        receitaRepository.delete(buscarPorIdOuLancaException(id));
    }

    public BigDecimal somarTotal() {
        return receitaRepository.somarTotal();
    }

    protected Receita buscarPorIdOuLancaException(long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Receita não encontrada"));
    }
}

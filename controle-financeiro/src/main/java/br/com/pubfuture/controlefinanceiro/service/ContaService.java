package br.com.pubfuture.controlefinanceiro.service;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.conta.TransferenciaRequestBody;
import br.com.pubfuture.controlefinanceiro.exception.BadRequestException;
import br.com.pubfuture.controlefinanceiro.exception.SaldoInsuficienteException;
import br.com.pubfuture.controlefinanceiro.mapper.ContaMapper;
import br.com.pubfuture.controlefinanceiro.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public Conta cadastrar(ContaPostRequestBody contaPostRequestBody) {
        return contaRepository.save(ContaMapper.INSTANCE.paraConta(contaPostRequestBody));
    }

    public Conta editar(ContaPutRequestBody contaPutRequestBody) {
        buscarPorId(contaPutRequestBody.getId());
        Conta conta = ContaMapper.INSTANCE.paraConta(contaPutRequestBody);
        return contaRepository.save(conta);
    }

    public void remover(long id) {
        contaRepository.delete(buscarPorId(id));
    }


    public Page<Conta> listar(Pageable pageable) {
        return contaRepository.findAll(pageable);
    }

    public void transferir(TransferenciaRequestBody transferenciaRequestBody) {

        Conta contaOrigem = buscarPorId(transferenciaRequestBody.getIdContaOrigem());

        Conta contaDestino = buscarPorId(transferenciaRequestBody.getIdContaDestino());

        BigDecimal valorTransferencia = transferenciaRequestBody.getValorTransferencia();

        validarSaldoParaTransferencia(contaOrigem, valorTransferencia);

        contaDestino.setSaldo(contaDestino.getSaldo().add(valorTransferencia));
        contaRepository.save(contaDestino);

        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valorTransferencia));
        contaRepository.save(contaOrigem);

    }

    public BigDecimal saldoTotal() {
        return contaRepository.saldoTotal();
    }

    public Conta buscarPorId(long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));
    }

    private void validarSaldoParaTransferencia(Conta contaOrigem, BigDecimal valorTransferencia) {

        if (contaOrigem.getSaldo().compareTo(valorTransferencia) < 0) {

            throw new SaldoInsuficienteException(contaOrigem);
        }
    }


}

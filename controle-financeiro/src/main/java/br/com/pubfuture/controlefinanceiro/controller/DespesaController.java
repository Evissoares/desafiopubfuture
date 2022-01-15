package br.com.pubfuture.controlefinanceiro.controller;

import br.com.pubfuture.controlefinanceiro.domain.Despesa;
import br.com.pubfuture.controlefinanceiro.domain.Receita;
import br.com.pubfuture.controlefinanceiro.dto.ConsultaEntreDatas;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.despesa.DespesaResponseBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.service.DespesaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    @ApiOperation(value = "Cria uma nova despesa no banco de dados",
            notes = "A despesa deve informar um ID de conta previamente criada<hr><br>" +
                    "Os campos de data devem seguir o padrão ISO8601 aaaa-MM-dd<hr><br>" +
                    "O separador do campo data deve obrigatóriamente ser o hífen (-)<hr><br>" +
                    "Caso algum requisito não seja atendido será lançado status 400 Bad Request.")

    @PostMapping()
    public ResponseEntity<DespesaResponseBody> cadastrar(@RequestBody DespesaPostRequestBody despesa) {

        return new ResponseEntity<>(despesaService.cadastrar(despesa), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Edita uma despesa no banco de dados",
            notes = "Deve ser informado o ID da despesa que será atualizada<hr><br>" +
                    "Deve ser informado um ID de conta previamente criada<br>" +
                    "Caso o ID de conta não exista será retornado status 400 Bad Request<hr><br>" +
                    "Os campos de data devem seguir o padrão ISO-8601 aaaa-MM-dd<hr><br>" +
                    "Os separadores do campo data devem obrigatoriamente ser o hífen (-)<hr><br>" +
                    "Caso algum requisito não seja atendido será retornado status 400 Bad Request.")

    @PutMapping
    public ResponseEntity<DespesaResponseBody> editar(@RequestBody DespesaPutRequestBody despesaPutRequestBody) {
        return new ResponseEntity<>(despesaService.editar(despesaPutRequestBody), HttpStatus.OK);
    }


    @ApiOperation(value = "Exclui uma despesa do banco de dados",
            notes = "A requisição deve informar um ID válido<hr><br>" +
                    "Caso o ID da despesa não seja encontrado será retornado status 400 Bad Request.")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        despesaService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Consulta despesas por data no banco de dados.",
            notes = "Deve ser informado o periodo de consulta desejada.<hr><br>" +
                    "As datas informadas devem seguir o padrão ISO-8601 aaaa-MM-dd" +
                    "Caso não existam despesas cadastradas no período informado será retornada uma lista vazia.")
    @PostMapping("/consultar")
    public ResponseEntity<Page<Despesa>> listarPorPeriodo(@RequestBody ConsultaEntreDatas consulta, Pageable pageable) {
        return new ResponseEntity<>(despesaService.listarPorPeriodo(consulta, pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Método para listar todas as despesas pelo tipo de despesa cadastrada.",
            notes = "O parâmetro de busca deverá conter parte ou toda a descrição do tipo cadastrado nas despesas.<hr><br>" +
                    "Não é considerada a capitalização das letras.</hr><br>" +
                    "Caso não existam registros que combinem com a busca será retornada uma lista vazia.")
    @GetMapping("/consultar/{tipo}")
    public ResponseEntity<Page<Despesa>> listarPorTipoReceita(@PathVariable String tipo, Pageable pageable) {
        return new ResponseEntity<>(despesaService.listarPorTipoDespesa(tipo, pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Método para somar todos os valores das despesas cadastradas.",
            notes = "Este método não recebe parâmetros e retorna um valor real da soma dos valores das despesas.<hr><br>" +
                    "Caso não existam despesas cadastradas será retornado o valor zero.")
    @GetMapping("/consultar")
    public ResponseEntity<BigDecimal> somarTotal() {
        return new ResponseEntity<>(despesaService.somarTotal(), HttpStatus.OK);
    }
}

package br.com.pubfuture.controlefinanceiro.controller;


import br.com.pubfuture.controlefinanceiro.domain.Receita;
import br.com.pubfuture.controlefinanceiro.dto.ConsultaEntreDatas;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaResponseBody;
import br.com.pubfuture.controlefinanceiro.dto.receita.ReceitaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.service.ReceitaService;
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
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    @ApiOperation(value = "Cria uma nova receita no banco de dados",
            notes = "A requisição deve informar um ID de conta previamente criada<hr><br>" +
                    "Os campos de data devem seguir o padrão ISO8601 aaaa-MM-dd<hr><br>" +
                    "O separador do campo data deve obrigatoriamente ser o hífen (-)<hr><br>" +
                    "Caso algum requisito não seja atendido será lançado status 400 Bad Request.")

    @PostMapping()
    public ResponseEntity<ReceitaResponseBody> cadastrar(@RequestBody ReceitaPostRequestBody receita) {

        return new ResponseEntity<>(receitaService.cadastrar(receita), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Edita uma receita no banco de dados",
            notes = "Deve ser informado o ID da receita que será atualizada<hr><br>" +
                    "Deve ser informado um ID de conta previamente criada<br>" +
                    "Caso o ID de conta não exista será retornado status 400 Bad Request<hr><br>" +
                    "Os campos de data devem seguir o padrão ISO-8601 aaaa-MM-dd<hr><br>" +
                    "Os separadores do campo data devem obrigatoriamente ser o hífen (-)<hr><br>" +
                    "Caso algum requisito não seja atendido será retornado status 400 Bad Request.")

    @PutMapping
    public ResponseEntity<ReceitaResponseBody> editar(@RequestBody ReceitaPutRequestBody receitaPutRequestBody) {
        return new ResponseEntity<>(receitaService.editar(receitaPutRequestBody), HttpStatus.OK);
    }


    @ApiOperation(value = "Exclui uma receita do banco de dados",
            notes = "A requisição deve informar um ID válido<hr><br>" +
                    "Caso o ID da receita não seja encontrado será retornado status 400 Bad Request.")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        receitaService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Consulta receitas por data no banco de dados.",
            notes = "Deve ser informado o periodo de consulta desejada.<hr><br>" +
                    "As datas informadas devem seguir o padrão ISO-8601 aaaa-MM-dd" +
                    "Caso não existam receitas cadastradas no período informado será retornada uma lista vazia.")
    @PostMapping("/consultar")
    public ResponseEntity<Page<Receita>> listarPorPeriodo(@RequestBody ConsultaEntreDatas consulta, Pageable pageable) {
        return new ResponseEntity<>(receitaService.listarPorPeriodo(consulta, pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Método para listar todas as receitas pelo tipo de receita cadastrada.",
            notes = "O parâmetro de busca deverá conter parte ou toda a descrição do tipo cadastrado nas receitas.<hr><br>" +
                    "Não é considerada a capitalização das letras.</hr><br>" +
                    "Caso não existam registros que combinem com a busca será retornada uma lista vazia.")
    @GetMapping("/consultar/{tipo}")
    public ResponseEntity<Page<Receita>> listarPorTipoReceita(@PathVariable String tipo, Pageable pageable) {
        return new ResponseEntity<>(receitaService.listarPorTipoReceita(tipo, pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Método para somar todos os valores das receitas cadastradas.",
            notes = "Este método não recebe parâmetros e retorna um valor real da soma dos valores das receitas.<hr><br>" +
                    "Caso não existam receitas cadastradas será retornado o valor zero.")
    @GetMapping("/consultar")
    public ResponseEntity<BigDecimal> somarTotal() {
        return new ResponseEntity<>(receitaService.somarTotal(), HttpStatus.OK);
    }

}

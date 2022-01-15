package br.com.pubfuture.controlefinanceiro.controller;

import br.com.pubfuture.controlefinanceiro.domain.Conta;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPostRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.conta.ContaPutRequestBody;
import br.com.pubfuture.controlefinanceiro.dto.conta.TransferenciaRequestBody;
import br.com.pubfuture.controlefinanceiro.service.ContaService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;


    @ApiOperation(value = "Este método é responsável por criar uma nova conta no banco de dados.",
            notes = "Não é permitido o uso de virgula como separador de casa decimal no campo saldo.<br>" +
                    "Exemplo válido para saldo: 1500.35<br>" +
                    "Exemplo inválido para saldo: 1500,35<hr><br>" +
                    "O atributo tipoConta deve conter um valor pré estabelecido nas enumerações da API.<br>" +
                    "Valores aceitos: CARTEIRA, CORRENTE, POUPANCA<hr><br>" +
                    "Caso a requisição tenha valores inválidos " +
                    "será lançado um status code 400 Bad Request e a conta não será persistida.<hr><br>")
    @PostMapping
    public ResponseEntity<Conta> cadastrar(@RequestBody ContaPostRequestBody contaPostRequestBody) {

        return new ResponseEntity<>(contaService.cadastrar(contaPostRequestBody), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Este método é responsável por atualizar o cadastro de uma conta no banco de dados.",
            notes = "É obrigatório passar o ID da conta que deseja atualizar e os campos a serem atualizados<hr><br>" +
                    "O atributo tipoConta deve conter um valor pré estabelecido nas enumerações da API.<br>" +
                    "Valores aceitos: CARTEIRA, CORRENTE, POUPANCA<hr><br>" +
                    "Caso o ID passado não exista no banco de dados, será retornado um status code 400 Bad Request.")
    @PutMapping
    public ResponseEntity<Conta> editar(@RequestBody ContaPutRequestBody contaPutRequestBody) {

        return ResponseEntity.ok(contaService.editar(contaPutRequestBody));
    }


    @ApiOperation(value = "Este método é responsável por deletar o cadastro de uma conta no banco de dados.",
            notes = "Para deletar uma conta, é obrigatório passar o ID da conta que deseja deletar<br>" +
                    "Caso contrário será retornado um status code 400 Bad Request ")

    @DeleteMapping("{id}")
    public ResponseEntity<Conta> remover(@PathVariable long id) {
        contaService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Este método é responsável por listar todas as contas cadastradas no banco de dados.",
            notes = "Não é necessário passar nenhum parâmetro para este método</br><hr>" +
                    "Caso não existam contas salvas no banco de dados, será retornada uma lista vazia.</br><hr>" +
                    "O retorno padrão de paginação contém um total de cinco elementos por página.</br><hr>" +
                    "Para alterar os valores, devem ser passados na URL na forma server:port/contas?size=valor&page=valor</br>" +
                    "Onde o valor de size é o tamanho da lista retornada e o valor de page o número da página.")
    @GetMapping
    public ResponseEntity<Page<Conta>> listar(Pageable pageable) {
        return new ResponseEntity<>(contaService.listar(pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Este método é responsável por transferir valores entre as contas cadastradas.",
            notes = "É necessário passar o ID da conta origem, o ID da conta destino e o valor da tansferência<hr><br>" +
                    "Não é permitido o uso de virgula como separador de casa decimal no campo valorTransferencia.<hr><br>" +
                    "Exemplo válido para valorTransferencia: 1500.35<br>" +
                    "Exemplo inválido para valorTransferencia: 1500,35<hr><br>" +
                    "Caso não exista saldo suficiente na conta origem será retornado um status code 400 Bad Request")
    @PutMapping("/transferir")
    public ResponseEntity<Conta> transferir(@RequestBody TransferenciaRequestBody transferenciaRequestBody) {
        contaService.transferir(transferenciaRequestBody);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @ApiOperation(value = "Este método é responsável por calcular o total de saldo nas contas cadastradas.",
            notes = "Não é necessário passar parâmetros para este método.<hr><br>" +
                    "Caso não hajam contas cadastradas será retornado o valor zero.")

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> saldoTotal() {
        return new ResponseEntity<>(contaService.saldoTotal(), HttpStatus.OK);
    }

}

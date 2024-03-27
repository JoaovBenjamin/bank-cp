package com.example.bankcp.Controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcp.Model.Conta;
import com.example.bankcp.movimentacao.MovimentacaoRequest;
import com.example.bankcp.service.ContaService;

@RestController
@RequestMapping("conta")
public class ContaController {
    

    @Autowired
    ContaService service;

    @GetMapping()
    public List<Conta> listarContas(){
        return service.listarContas();
    }

   
    @PostMapping
    @ResponseStatus(CREATED)
    public Conta criarConta(@RequestBody Conta conta){
       return service.criarConta(conta);
    }

     @GetMapping("{id}")
    public ResponseEntity<Conta> listarContasId(@PathVariable Long id){
        return service.listarContasId(id);
    }

    @GetMapping("cpf/{cpf}")
    public List<Conta> index(@PathVariable String cpf) {
        return service.listarCpf(cpf);
    }

    @DeleteMapping("{id}") 
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.apagarConta(id);
    }
   
    @PostMapping("{id}/deposito")
    public Conta depositar(@PathVariable Long id, @RequestBody MovimentacaoRequest movimentacao){
        return service.depositar(id, movimentacao.valor());
    }
   
    @PostMapping("{id}/saque")
    public Conta sqConta(@PathVariable Long id, @RequestBody MovimentacaoRequest movimentacao){
        return service.saque(id, movimentacao.valor());
    }

    @PostMapping("{idOrigem}/pix")
    public Conta pix(@PathVariable Long idOrigem, @RequestBody MovimentacaoRequest movimentacao){
        return service.pix(idOrigem, movimentacao.contaDestino(), movimentacao.valor());
    }

}

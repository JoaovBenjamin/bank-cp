package com.example.bankcp.Controller;

import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.bankcp.Model.Conta;
import com.example.bankcp.Repository.ContaRepository;
import com.example.bankcp.movimentacao.MovimentacaoRequest;
import com.example.bankcp.service.ContaService;

@RestController
@RequestMapping("conta")
public class ContaController {
    
    Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    ContaService service;

    @Autowired
    ContaRepository repository;

    //Retornando as contas do banco, testando h2
    @GetMapping()
    public List<Conta> listarContas(){
        return repository.findAll();
    }

    //Criando conta  no banco de dados 
    @PostMapping
    @ResponseStatus(CREATED)
    public Conta criarConta(@RequestBody Conta conta){
        log.info("Criando conta " + conta);
        return repository.save(conta);
    }

     @GetMapping("{id}")
    public ResponseEntity<Conta> listarContasId(@PathVariable Long id){
        log.info("buscar conta por id {} ", id);
        return repository
                        .findById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("cpf/{cpf}")
    public List<Conta> index(@PathVariable String cpf) {
        log.info("buscar por cpf", cpf);
        return repository.findByCpf(cpf);
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

package com.example.bankcp.service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.example.bankcp.Model.Conta;
import com.example.bankcp.Repository.ContaRepository;



@Service
public class ContaService {

    @Autowired
    ContaRepository repository;

     public void apagarConta(Long id){
        Conta conta = repository.findById(id)
                                            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        conta.setAtiva(false);
        repository.save(conta);
    }

    public Conta depositar(Long id, BigDecimal valor){
        Conta conta = repository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        conta.setSaldo(conta.getSaldo().add(valor));
        return repository.save(conta);
    }

    
    public Conta saque(Long id, BigDecimal valor){
        Conta conta = repository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        return repository.save(conta);
    }

    public Conta pix(long idOrigem, long idDestido, BigDecimal valor){
        Conta contaOrigem = repository.findById(idOrigem)
                                                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(BAD_REQUEST, "Saldo insuficiente");
        }
        Conta contaDestino = repository.findById(idOrigem)
                                                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
        throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        repository.save(contaOrigem);
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
        repository.save(contaDestino);
        return contaDestino;
    }

       public Conta criarConta(@RequestBody Conta conta){
        return repository.save(conta);
    }

      public List<Conta> listarContas(){
        return repository.findAll();
    }

      public ResponseEntity<Conta> listarContasId(@PathVariable Long id){
        return repository
                        .findById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    public List<Conta> listarCpf(@PathVariable String cpf) {
        return repository.findByCpf(cpf);
    }


  
}


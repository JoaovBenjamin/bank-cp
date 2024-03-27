package com.example.bankcp.service;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.bankcp.Model.Conta;
import com.example.bankcp.Repository.ContaRepository;

import lombok.val;

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
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        repository.save(contaOrigem);
        
        Conta contaDestino = repository.findById(idOrigem)
                                                    .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
        throw new ResponseStatusException(BAD_REQUEST, "Valor deve ser maior que zero");
        }
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));
        return repository.save(contaDestino);
    }

  
}


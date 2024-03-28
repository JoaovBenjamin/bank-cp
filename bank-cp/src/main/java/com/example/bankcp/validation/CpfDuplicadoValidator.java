package com.example.bankcp.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.bankcp.service.ContaService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfDuplicadoValidator implements ConstraintValidator<CpfDuplicado, String> {
    @Autowired
    ContaService service;

  
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        var cpfExistente = service.listarCpf(cpf);
        if (cpfExistente.equals(cpf)) {
            return false;
        }
            return true;
    }

    
}

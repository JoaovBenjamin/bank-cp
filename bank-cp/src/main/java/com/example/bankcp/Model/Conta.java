package com.example.bankcp.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.example.bankcp.validation.CpfDuplicado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Conta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{conta.numeroagencia.notblank}")
    private String numeroAgencia;
    @NotBlank(message = "{conta.nome.notblank}")
    @Size(message = "{conta.nome.size}", min = 5, max = 50)
    private String nome;
    @NotBlank(message = "{conta.cpf.notblank}")
    @CPF(message = "{conta.cpf}")
    @CpfDuplicado(message = "{conta.cpf.CpfDuplicado}")
    private String cpf;
    @PastOrPresent(message = "{conta.datacriacao.pastorpresent}")
    private LocalDate datacriacao;
    @PositiveOrZero(message = "{conta.saldo.positivoupperzero}")
    private BigDecimal saldo;
    private boolean ativa = true;
     @Pattern(
        regexp = "^(CORRENTE|POUPANCA|SALARIO)$",
        message = "{conta.tipo.pattern}"
    )    
    private String tipo;

}

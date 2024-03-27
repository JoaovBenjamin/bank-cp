package com.example.bankcp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankcp.Model.Conta;

public interface ContaRepository extends JpaRepository<Conta,Long> {
     List<Conta> findByCpf(String cpf);
}

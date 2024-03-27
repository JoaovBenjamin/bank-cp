package com.example.bankcp.movimentacao;

import java.math.BigDecimal;

public record MovimentacaoRequest(BigDecimal valor, Long contaDestino){}    


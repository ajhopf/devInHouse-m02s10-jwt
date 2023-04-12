package com.devinhouse.securityjwt.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProdutoDto {
    private String nome;
    private String descricao;
    private Double valor;
}

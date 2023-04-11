package com.devinhouse.securityjwt.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenDto {
    private String token;
    private String tipo;
}

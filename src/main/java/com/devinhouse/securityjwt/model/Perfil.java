package com.devinhouse.securityjwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "perfis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Perfil implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @Override
    public String getAuthority() {//retorna o nome do perfil
        return nome;
    }
}

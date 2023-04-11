package com.devinhouse.securityjwt.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;

    //fetchtype Eager -> ao iniciar o programa irá fazer a procura de todos perfis
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis;
    }

    @Override
    public boolean isAccountNonExpired() {//define se a conta expira ou não
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {//define se a conta é travada(false) ou não(true)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {//define se a credencial expira(false) ou não(true)
        return true;
    }

    @Override
    public boolean isEnabled() {//define se a conta está ativa ou não
        return true;
    }
}

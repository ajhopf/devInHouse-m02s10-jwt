package com.devinhouse.securityjwt.controller;

import com.devinhouse.securityjwt.controller.dtos.LoginDto;
import com.devinhouse.securityjwt.controller.dtos.TokenDto;
import com.devinhouse.securityjwt.controller.dtos.UsuarioDto;
import com.devinhouse.securityjwt.model.Usuario;
import com.devinhouse.securityjwt.repository.PerfilRepository;
import com.devinhouse.securityjwt.repository.UsuarioRepository;
import com.devinhouse.securityjwt.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AutenticadorController {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final TokenService tokenService; //cuidar para importar o correto (o meu tokenService), nao a interface
    private final PasswordEncoder encoder;

    public AutenticadorController(
            AuthenticationManager authenticationManager,//está vindo do WebSecurityConfiguration
            TokenService tokenService,
            UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository,
            PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Validated LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = loginDto.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer")); //Bearer é o tipo de token que acompanha os tokens JWT
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioDto usuarioDto){
        perfilRepository.save(usuarioDto.getPerfil());

        Usuario usuario = Usuario.builder()
                .username(usuarioDto.getUsername())
                .password(encoder.encode(usuarioDto.getPassword()))
                .ativo(usuarioDto.getAtivo())
                .perfis(List.of(usuarioDto.getPerfil()))
                .build();
        usuarioRepository.save(usuario);

        return ResponseEntity.created(URI.create("/auth/cadastrar")).build();
    }
}

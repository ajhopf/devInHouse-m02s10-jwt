package com.devinhouse.securityjwt.config;

import com.devinhouse.securityjwt.model.Usuario;
import com.devinhouse.securityjwt.repository.UsuarioRepository;
import com.devinhouse.securityjwt.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//é um filtro que acontece uma vez por endpoint
public class AutenticacaoTokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public AutenticacaoTokenFilter(
            TokenService tokenService,
            UsuarioRepository repository) {
        this.repository = repository;
        this.tokenService = tokenService;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        boolean valido = tokenService.isTokenValid(token);
        if(valido) autenticarCliente(token);

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = repository.findById(idUsuario).get();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token.isBlank() || !token.startsWith("Bearer")) return null;

        return token.substring(7);
    }

}

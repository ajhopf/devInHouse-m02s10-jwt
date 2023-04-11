package com.devinhouse.securityjwt.config;

import com.devinhouse.securityjwt.repository.UsuarioRepository;
import com.devinhouse.securityjwt.service.AutenticacaoService;
import com.devinhouse.securityjwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;
    private final AutenticacaoService autenticacaoService;

    @Autowired
    public WebSecurityConfiguration(
            TokenService tokenService,
            UsuarioRepository repository,
            AutenticacaoService autenticacaoService) {
        this.tokenService = tokenService;
        this.repository = repository;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    @Bean
    //traz o gerenciador de autenticação para o programa
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    //cria o formato padrão de encriptação das senhas
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    //Alterando o serviço de autenticação para usar o banco de dados que criamos
    //usa o usuário
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(encoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll() // permite o acesso ao endpoint de autenticação
                .antMatchers(HttpMethod.POST, "/auth/cadastrar").permitAll() // permite o acesso ao endpoint de autenticação
                .anyRequest().authenticated()
                // desabilita o csrf (necessário para o uso do token)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // aceita apenas chamadas com o token
                .and()
                .addFilterBefore( // adicionar o filtro do token JWT
                        new AutenticacaoTokenFilter(tokenService, repository),
                        UsernamePasswordAuthenticationFilter.class
                );
    }

    // removendo a configuração do WebSecurity padrão
    @Override
    public void configure(WebSecurity web) throws  Exception{
        web.ignoring().antMatchers("/auth");
    }

}

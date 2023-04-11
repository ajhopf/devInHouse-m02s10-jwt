package com.devinhouse.securityjwt.repository;

import com.devinhouse.securityjwt.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //    @Query(
//            "select u from Usuario u where u.username=:username"
//    )
    UserDetails findByUsername(String username);
}

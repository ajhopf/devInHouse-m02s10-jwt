package com.devinhouse.securityjwt.repository;

import com.devinhouse.securityjwt.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    public Produto findByNome(String name);
}

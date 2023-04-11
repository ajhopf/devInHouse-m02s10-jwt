package com.devinhouse.securityjwt.service;

import com.devinhouse.securityjwt.model.Produto;
import com.devinhouse.securityjwt.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private ProdutoRepository repository;

    @Autowired
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    public Produto salvarProduto(Produto produto) {
        return repository.save(produto);
    }

}

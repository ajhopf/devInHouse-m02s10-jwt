package com.devinhouse.securityjwt.service;

import com.devinhouse.securityjwt.controller.dtos.ProdutoDto;
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

    public Produto salvarProduto(ProdutoDto produtoDto) {
        Produto produto = new Produto();

        produto.setNome(produtoDto.getNome());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setValor(produtoDto.getValor());

        return repository.save(produto);

    }

    public Produto atualizarProduto(Long id, ProdutoDto produtoDto) {
        Produto produto = repository.findById(id).get();

        produto.setNome(produtoDto.getNome());
        produto.setValor(produtoDto.getValor());
        produto.setDescricao(produtoDto.getDescricao());

        return repository.save(produto);
    }

    public void deletarProduto(Long id) {
        repository.deleteById(id);
    }
}

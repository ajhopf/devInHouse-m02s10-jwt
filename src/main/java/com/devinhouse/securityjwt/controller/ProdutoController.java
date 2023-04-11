package com.devinhouse.securityjwt.controller;

import com.devinhouse.securityjwt.model.Produto;
import com.devinhouse.securityjwt.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService service;

    @Autowired
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public List<Produto> listarProdutos() {
        return service.listarProdutos();
    }

    @PostMapping()
    public Produto salvarProduto(@RequestBody Produto produto) {
        return service.salvarProduto(produto);
    }
}

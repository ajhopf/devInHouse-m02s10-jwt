package com.devinhouse.securityjwt.controller;

import com.devinhouse.securityjwt.controller.dtos.ProdutoDto;
import com.devinhouse.securityjwt.model.Produto;
import com.devinhouse.securityjwt.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public Produto salvarProduto(@Validated @RequestBody ProdutoDto produtoDto) {
        return service.salvarProduto(produtoDto);
    }

    @PutMapping("/{id}")
    public Produto editarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoDto produtoDto) {
        return service.atualizarProduto(id, produtoDto);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
    }
}

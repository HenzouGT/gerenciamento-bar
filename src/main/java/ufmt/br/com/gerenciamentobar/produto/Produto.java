/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto;

import ufmt.br.com.gerenciamentobar.exception.EstoqueInsuficienteException;
import java.math.BigDecimal;

/**
 *
 * @author HenzouGT
 */
public class Produto {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEstoque;
    private boolean ativo;
        
    public Produto(String nome, BigDecimal preco, int quantidadeEstoque, boolean ativo) {
        this.nome = validarNome(nome);
        this.preco = validarPreco(preco);
        this.quantidadeEstoque = validarQuantidadeEstoque(quantidadeEstoque);
        this.ativo = ativo;
    }
    
    public void setId(Long id) {
        this.id = validarId(id);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }
    
    public String getNome() {
        return nome;
    }
        
    public BigDecimal getPreco() {
        return preco;
    }
    
    
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void ativar() {
        ativo = true;
    }
    
    public void desativar() {
        ativo = false;
    }
    
    public void setEstoque(int quantidade) {
        quantidadeEstoque = validarQuantidadeEstoque(quantidade);
    }
    
    public void adicionarEstoque(int quantidade) {
        quantidadeEstoque += validarQuantidadeMovimentacao(quantidade);
    }
        
    public void removerEstoque(int quantidade) { 
        int quantidadeValidada = validarQuantidadeMovimentacao(quantidade);
        if (quantidadeValidada > this.quantidadeEstoque) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente. Disponível: " + quantidadeEstoque
            );
        }
 
        quantidadeEstoque -= quantidadeValidada;        
    }
    
    public boolean possuiEstoque() {
        return quantidadeEstoque > 0;
    }
    
    public void alterarPreco(BigDecimal novoPreco) {
        this.preco = validarPreco(novoPreco);
    }    
    
    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do produto é obrigatório."
            );
        }
        
        String nomeNormalizado = nome.trim();

        if (nomeNormalizado.length() > 150) {
            throw new IllegalArgumentException(
                "O nome deve possuir no máximo 150 caracteres."
            );
        }
        
        return nomeNormalizado;
    }
    
    private Long validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID do produto é inválido."
            );
        }
        
        return id;
    }
    
    private BigDecimal validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Preço deve ser maior que zero."
            );
        }
        return preco;
    }
    
    private int validarQuantidadeEstoque(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                "A quantidade deve ser maior ou igual a zero."
            );
        }
        
        return quantidade;        
    }
    
    private int validarQuantidadeMovimentacao(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                "A quantidade deve ser maior que zero."
            );
        }
        
        return quantidade;        
    }
    
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Produto outroProduto = (Produto) objeto;

        return id != null && id.equals(outroProduto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

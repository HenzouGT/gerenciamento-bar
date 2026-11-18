/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda;

import ufmt.br.com.gerenciamentobar.comanda.Comanda;
import ufmt.br.com.gerenciamentobar.produto.Produto;
import java.math.BigDecimal;

/**
 *
 * @author HenzouGT
 */
public class ItemComanda {
    private Long id;
    private Produto produto;
    private int quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
    private Comanda comanda;

    public ItemComanda(Produto produto, int quantidade, Comanda comanda) {
        if (comanda == null) {
            throw new IllegalArgumentException("A comanda não pode ser nula.");
        }
        
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }

        if (produto.getPreco() == null
                || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O preço do produto deve ser maior que zero."
            );
        }

        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
        this.subtotal = calcularSubtotal();
        this.comanda = comanda;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void adicionarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");                 
        }
        
        this.quantidade += quantidade;
        this.subtotal = calcularSubtotal();
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}

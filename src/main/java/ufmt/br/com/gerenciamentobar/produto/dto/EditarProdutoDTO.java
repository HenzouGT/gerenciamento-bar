/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto.dto;

import java.math.BigDecimal;
/**
 *
 * @author HenzouGT
 */
public record EditarProdutoDTO(
    String nome,
    BigDecimal preco,
    int quantidadeEstoque
) {    
}

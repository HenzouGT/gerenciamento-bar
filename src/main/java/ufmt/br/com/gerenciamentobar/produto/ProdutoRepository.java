/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto;

import java.util.Optional;
import java.util.List;
/**
 *
 * @author HenzouGT
 */
public interface ProdutoRepository {
    Produto salvar(Produto produto);
    
    List<Produto> buscarTodos();
    
    Optional<Produto> buscarPorId(Long id);
    
    Produto editar(Long id, Produto produto);
    
    void desativar(Long id);
    
    void ativar(Long id);
}

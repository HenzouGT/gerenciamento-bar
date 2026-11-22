/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto;

import ufmt.br.com.gerenciamentobar.produto.dto.CriarProdutoDTO;
import ufmt.br.com.gerenciamentobar.produto.dto.EditarProdutoDTO;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.produto.dto.ResumoProdutoDTO;
/**
 *
 * @author HenzouGT
 */
public interface ProdutoService {        
    public void criar(CriarProdutoDTO dados);
    
    public List<ResumoProdutoDTO> buscarTodos();
    
    public Optional<ResumoProdutoDTO> buscarPorId(Long id);
    
    public void editar(Long id, EditarProdutoDTO dados);
    
    public void desativar(Long id);
    
    public void ativar(Long id);
}
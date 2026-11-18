/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto;

import java.util.ArrayList;
import ufmt.br.com.gerenciamentobar.exception.NotFoundException;
import ufmt.br.com.gerenciamentobar.produto.dto.CriarProdutoDTO;
import ufmt.br.com.gerenciamentobar.produto.dto.EditarProdutoDTO;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.produto.dto.ResumoProdutoDTO;
/**
 *
 * @author HenzouGT
 */
public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;    
    
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    @Override
    public Produto criar(CriarProdutoDTO dados) {
        if (dados == null) {
            throw new IllegalArgumentException(
                "Os dados do produto são obrigatórios."
            );
        }       

        Produto produto = new Produto(
            dados.nome(),
            dados.preco(),
            dados.quantidadeEstoque(),
            dados.ativo()
        );
        
        return produtoRepository.salvar(produto);
    }        
    
    @Override
    public List<ResumoProdutoDTO> buscarTodos() {
        List<Produto> produtos = produtoRepository.buscarTodos();
        List<ResumoProdutoDTO> resumoProdutos = new ArrayList<>();
        
        for (Produto produto : produtos) {
            resumoProdutos.add(converterParaResumo(produto));
        }
        return resumoProdutos;
    }
    
    @Override
    public Optional<ResumoProdutoDTO> buscarPorId(Long id) {
        validarId(id);
        Optional<Produto> resultado = produtoRepository.buscarPorId(id);
        
        if (resultado.isEmpty()) {
            return Optional.empty();
        }
        
        Produto produto = resultado.get();
        
        ResumoProdutoDTO dto = converterParaResumo(produto);
        return Optional.of(dto);
    }
    
    @Override
    public Produto editar(Long id, EditarProdutoDTO dados) {
        validarId(id);
        
        if (dados == null) {
            throw new IllegalArgumentException("Os dados do produto são obrigatórios.");
        }
        
        Produto produtoAtual = produtoRepository.buscarPorId(id)
            .orElseThrow(() ->
                new NotFoundException("Produto não encontrado: " + id)
            );
        
        produtoAtual.setNome(dados.nome());
        produtoAtual.alterarPreco(dados.preco());
        produtoAtual.setEstoque(dados.quantidadeEstoque());
        
        return produtoRepository.editar(id, produtoAtual);        
    }
    
    @Override
    public void desativar(Long id) {
        validarId(id);
        produtoRepository.desativar(id);
    }
    
    @Override
    public void ativar(Long id) {
        validarId(id);
        produtoRepository.ativar(id);
    }
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID do produto é inválido."
            );
        }
    }
    
    private ResumoProdutoDTO converterParaResumo(Produto produto) {
        return new ResumoProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getPreco(),
            produto.getQuantidadeEstoque(),
            produto.isAtivo()
        );
    }
}

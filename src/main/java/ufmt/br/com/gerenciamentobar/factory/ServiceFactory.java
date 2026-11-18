/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.factory;

import ufmt.br.com.gerenciamentobar.funcionario.FuncionarioService;
import ufmt.br.com.gerenciamentobar.funcionario.FuncionarioServiceImpl;
import ufmt.br.com.gerenciamentobar.funcionario.FuncionarioRepository;
import ufmt.br.com.gerenciamentobar.funcionario.PostgresFuncionarioRepository;
import ufmt.br.com.gerenciamentobar.seguranca.BCryptSenhaHasher;
import ufmt.br.com.gerenciamentobar.seguranca.SenhaHasher;

import ufmt.br.com.gerenciamentobar.produto.ProdutoService;
import ufmt.br.com.gerenciamentobar.produto.ProdutoServiceImpl;
import ufmt.br.com.gerenciamentobar.produto.ProdutoRepository;
import ufmt.br.com.gerenciamentobar.produto.PostgresProdutoRepository;

import ufmt.br.com.gerenciamentobar.sinuca.MesaSinucaRepository;
import ufmt.br.com.gerenciamentobar.sinuca.MesaSinucaService;
import ufmt.br.com.gerenciamentobar.sinuca.MesaSinucaServiceImpl;
import ufmt.br.com.gerenciamentobar.sinuca.PostgresMesaSinucaRepository;
/**
 *
 * @author HenzouGT
 */
public final class ServiceFactory {
    public FuncionarioService criarFuncionarioService() {  
        FuncionarioRepository repository = new PostgresFuncionarioRepository();
        SenhaHasher senhaHasher = new BCryptSenhaHasher();
        return new FuncionarioServiceImpl(repository, senhaHasher);
    }
    
    public ProdutoService criarProdutoService() {
        ProdutoRepository repository = new PostgresProdutoRepository();
        return new ProdutoServiceImpl(repository);
    }  
    
    public MesaSinucaService criarMesaSinucaService() {
        MesaSinucaRepository repository = new PostgresMesaSinucaRepository();
        return new MesaSinucaServiceImpl(repository);
    }
}

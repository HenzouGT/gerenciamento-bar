/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ufmt.br.com.gerenciamentobar;

import javax.swing.SwingUtilities;
import ufmt.br.com.gerenciamentobar.funcionario.TelaFuncionario;
import ufmt.br.com.gerenciamentobar.produto.TelaProduto;
import ufmt.br.com.gerenciamentobar.funcionario.FuncionarioService;
import ufmt.br.com.gerenciamentobar.produto.ProdutoService;

import ufmt.br.com.gerenciamentobar.factory.ServiceFactory;
import ufmt.br.com.gerenciamentobar.sinuca.MesaSinucaService;
import ufmt.br.com.gerenciamentobar.sinuca.TelaMesaSinuca;

/**
 *
 * @author HenzouGT
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServiceFactory factory = new ServiceFactory();
            
            FuncionarioService funcionarioService = factory.criarFuncionarioService();
            ProdutoService produtoService = factory.criarProdutoService();
            MesaSinucaService mesaSinucaService = factory.criarMesaSinucaService();
            
            new TelaFuncionario(funcionarioService).setVisible(true);
            new TelaProduto(produtoService).setVisible(true);
            new TelaMesaSinuca(mesaSinucaService).setVisible(true);
        });
    }
    
}

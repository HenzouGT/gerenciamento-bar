/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda;

import ufmt.br.com.gerenciamentobar.produto.Produto;
import ufmt.br.com.gerenciamentobar.funcionario.Funcionario;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.sinuca.MesaSinuca;
import ufmt.br.com.gerenciamentobar.sinuca.UsoMesaSinuca;

/**
 *
 * @author HenzouGT
 */
public class Comanda {
    private Long id;
    private LocalDateTime dataHoraAbertura;
    private LocalDateTime dataHoraFechamento;
    private StatusComanda status;
    private String nomeCliente;
    private Funcionario funcionarioResponsavel;
    private List<ItemComanda> itens = new ArrayList<>();
    private List<UsoMesaSinuca> usoMesasSinuca = new ArrayList<>();
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public void iniciarComanda(LocalDateTime dataHoraAbertura, String nomeCliente) {
        this.dataHoraAbertura = dataHoraAbertura;
        this.status = StatusComanda.ABERTA;
        this.nomeCliente = nomeCliente;
    }
    
    public void fecharComanda(LocalDateTime dataHoraFechamento) {
        LocalDateTime horaFim = LocalDateTime.now();
        
        usoMesasSinuca.forEach(uso -> uso.encerrarUso(horaFim));
        this.dataHoraFechamento = dataHoraFechamento;
        this.status = StatusComanda.FECHADA;
    }

    public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
    
    
    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public void adicionarUsoMesaSinuca(MesaSinuca mesaSinuca) {
        UsoMesaSinuca usoMesaSinuca = new UsoMesaSinuca(mesaSinuca);
        usoMesasSinuca.add(usoMesaSinuca);
    }
    
    public void adicionarItem(Produto produto, int quantidade) {
        Optional<ItemComanda> itemExistente = itens
                .stream()
                .filter(i -> i.getProduto().equals(produto))
                .findFirst();
        
        produto.removerEstoque(quantidade);
        
        if (itemExistente.isPresent()) {
            itemExistente.get().adicionarQuantidade(quantidade);
            return;
        }        
        
        ItemComanda novoItem = new ItemComanda(produto, quantidade, this);
        itens.add(novoItem);        
    }
    
    public void removerItem(Produto produto) {        
        ItemComanda item = itens
                .stream()
                .filter(i -> i.getProduto().equals(produto))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado na comanda."));
                
        produto.adicionarEstoque(item.getQuantidade());
        
        itens.remove(item);        
    }
    
    public BigDecimal calcularTotalProdutos() {
        return itens.stream()
                .map(ItemComanda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add); 
    }
    
    public BigDecimal calcularTotalSinuca() {
        return usoMesasSinuca.stream()
                .map(UsoMesaSinuca::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add); 
    }
    
    public BigDecimal calcularTotal() {
        return calcularTotalProdutos().add(calcularTotalSinuca());
    }
    
    public List<ItemComanda> getItens() {
        return List.copyOf(itens);
    }
}

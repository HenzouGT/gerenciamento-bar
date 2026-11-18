/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author HenzouGT
 */
public interface ComandaRepository {
    public Comanda criar(Comanda comanda);
    
    public List<Comanda> buscarTodos();
    
    public Optional<Comanda> buscarPorId(Long id);
    
    public Comanda editar(Long id, Comanda comanda);        
}

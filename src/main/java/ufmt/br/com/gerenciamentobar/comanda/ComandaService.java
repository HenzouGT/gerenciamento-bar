/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda;

import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.comanda.dto.CriarComandaDTO;
import ufmt.br.com.gerenciamentobar.comanda.dto.ResumoComandaDTO;

/**
 *
 * @author HenzouGT
 */
public interface ComandaService {
    public Comanda iniciarComanda(CriarComandaDTO dados);
    
    public List<ResumoComandaDTO> buscarTodos();
    
    public Optional<ResumoComandaDTO> buscarPorId(Long id);
}

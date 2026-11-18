/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.sinuca.dto.CriarMesaSinucaDTO;
import ufmt.br.com.gerenciamentobar.sinuca.dto.EditarMesaSinucaDTO;
import ufmt.br.com.gerenciamentobar.sinuca.dto.ResumoMesaSinucaDTO;
/**
 *
 * @author HenzouGT
 */
public interface MesaSinucaService {
    public MesaSinuca criar(CriarMesaSinucaDTO dados);
    
    public List<ResumoMesaSinucaDTO> buscarTodos();
    
    public Optional<ResumoMesaSinucaDTO> buscarPorId(Long id);
    
    public MesaSinuca editar(Long id, EditarMesaSinucaDTO dados);
    
    public void ocupar(Long id);
    
    public void liberar(Long id);
    
    public void colocarEmManutencao(Long id);
}

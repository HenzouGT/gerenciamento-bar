/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author HenzouGT
 */
public interface MesaSinucaRepository {
    public MesaSinuca salvar(MesaSinuca mesaSinuca);
    
    public List<MesaSinuca> buscarTodos();
    
    public Optional<MesaSinuca> buscarPorId(Long id);
    
    public MesaSinuca editar(Long id, MesaSinuca mesaSinuca);

    public void excluir(Long id);
    
    public void atualizarStatus(MesaSinuca mesa);
}

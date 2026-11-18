/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.funcionario.dto.CriarFuncionarioDTO;
import ufmt.br.com.gerenciamentobar.funcionario.dto.EditarFuncionarioDTO;
import ufmt.br.com.gerenciamentobar.funcionario.dto.ResumoFuncionarioDTO;
/**
 *
 * @author HenzouGT
 */
public interface FuncionarioService {
    public Funcionario criar(CriarFuncionarioDTO dados);
    
    public List<ResumoFuncionarioDTO> buscarTodos();
    
    public Optional<ResumoFuncionarioDTO> buscarPorId(Long id);
    
    public Funcionario editar(Long id, EditarFuncionarioDTO dados);
    
    public void desativar(Long id);
    
    public void ativar(Long id);
}

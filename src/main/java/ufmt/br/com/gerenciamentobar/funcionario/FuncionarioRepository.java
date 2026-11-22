/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

import java.util.Optional;
import java.util.List;
/**
 *
 * @author HenzouGT
 */
public interface FuncionarioRepository {
    Funcionario salvar(Funcionario funcionario);   

    boolean existePorCpf(String cpf);
    
    List<Funcionario> buscarTodos();
    
    Optional<Funcionario> buscarPorId(Long id);        
    
    Funcionario editar(Long id, Funcionario funcionario);

    void excluir(Long id);
    
    void desativar(Long id);
    
    void ativar(Long id);
    
}

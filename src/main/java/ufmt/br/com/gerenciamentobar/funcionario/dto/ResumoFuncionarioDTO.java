/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario.dto;

import ufmt.br.com.gerenciamentobar.funcionario.Cargo;

/**
 *
 * @author HenzouGT
 */
public record ResumoFuncionarioDTO(
    Long id,
    String nome,
    String cpf,
    String telefone,
    Cargo cargo,
    boolean ativo
) {
}

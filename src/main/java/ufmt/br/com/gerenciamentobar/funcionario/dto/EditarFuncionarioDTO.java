package ufmt.br.com.gerenciamentobar.funcionario.dto;

import ufmt.br.com.gerenciamentobar.funcionario.Cargo;

public record EditarFuncionarioDTO(
    String nome,
    String cpf,
    String telefone,
    Cargo cargo
) {
}

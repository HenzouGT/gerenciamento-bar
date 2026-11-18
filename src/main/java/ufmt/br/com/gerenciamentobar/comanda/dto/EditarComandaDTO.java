/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda.dto;

import java.util.List;
import ufmt.br.com.gerenciamentobar.funcionario.Funcionario;
import ufmt.br.com.gerenciamentobar.sinuca.dto.ResumoUsoMesaSinucaDTO;

/**
 *
 * @author HenzouGT
 */
public record EditarComandaDTO(
    Long id,
    String nomeCliente,
    Funcionario funcionarioResponsavel,
    List<ResumoItemComandaDTO> itens,
    List<ResumoUsoMesaSinucaDTO> usoMesasSinuca
) {
}

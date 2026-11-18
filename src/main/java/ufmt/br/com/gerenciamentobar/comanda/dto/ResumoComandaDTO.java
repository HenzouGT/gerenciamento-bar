/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda.dto;

import java.time.LocalDateTime;
import java.util.List;
import ufmt.br.com.gerenciamentobar.comanda.StatusComanda;
import ufmt.br.com.gerenciamentobar.sinuca.dto.ResumoUsoMesaSinucaDTO;

/**
 *
 * @author HenzouGT
 */
public record ResumoComandaDTO(
    Long id,
    LocalDateTime dataHoraAbertura,
    LocalDateTime dataHoraFechamento,
    StatusComanda status,
    String nomeCliente,
    Long funcionarioResponsavelId,
    List<ResumoItemComandaDTO> itens,
    List<ResumoUsoMesaSinucaDTO> usoMesasSinuca
) {
}

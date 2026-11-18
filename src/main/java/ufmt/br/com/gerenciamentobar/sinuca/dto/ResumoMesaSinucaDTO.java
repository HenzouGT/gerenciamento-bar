/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca.dto;

import java.math.BigDecimal;
import ufmt.br.com.gerenciamentobar.sinuca.StatusMesaSinuca;

/**
 *
 * @author HenzouGT
 */
public record ResumoMesaSinucaDTO(
    Long id,
    int numero,
    BigDecimal valorHora,
    StatusMesaSinuca status    
) {
}

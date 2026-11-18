/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import ufmt.br.com.gerenciamentobar.sinuca.MesaSinuca;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author HenzouGT
 */
public class UsoMesaSinuca {
    private Long id;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    private BigDecimal valorHoraAplicado;
    private BigDecimal valorTotal;
    private MesaSinuca mesaSinuca;

    public UsoMesaSinuca(MesaSinuca mesaSinuca) {
        if (mesaSinuca == null) {
            throw new IllegalArgumentException("A mesa de sinuca não pode ser nula.");
        }
        
        BigDecimal valorHora = mesaSinuca.getValorHora();
        
        if (valorHora == null
                || valorHora.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O valor da hora deve ser maior que zero."
            );
        }

        this.horaInicio = LocalDateTime.now();
        this.valorHoraAplicado = valorHora;
        this.mesaSinuca = mesaSinuca;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public BigDecimal getValorHoraAplicado() {
        return valorHoraAplicado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal calcularValorTotal() {
        if (horaFim == null) {
            throw new IllegalStateException(
                    "O uso deve ser encerrado antes do cálculo do valor total."
            );
        }

        long segundosUtilizados = Duration.between(horaInicio, horaFim).getSeconds();
        
        BigDecimal horasUtilizadas = BigDecimal.valueOf(segundosUtilizados)
                .divide(BigDecimal.valueOf(3600), 10, RoundingMode.HALF_UP);

        valorTotal = valorHoraAplicado
                .multiply(horasUtilizadas)
                .setScale(2, RoundingMode.HALF_UP);

        return valorTotal;
    }

    public void encerrarUso(LocalDateTime horaFim) {
        if (horaFim == null) {
            throw new IllegalArgumentException("A hora de fim não pode ser nula.");
        }

        if (horaFim.isBefore(horaInicio)) {
            throw new IllegalArgumentException(
                    "A hora de fim não pode ser anterior à hora de início."
            );
        }

        if (this.horaFim != null) {
            throw new IllegalStateException("O uso da mesa já foi encerrado.");
        }

        this.horaFim = horaFim;
        calcularValorTotal();
    }
    
    public void setMesaSinuca(MesaSinuca mesaSinuca) {
        this.mesaSinuca = mesaSinuca;
    }
    
    public MesaSinuca getMesaSinuca() {
        return mesaSinuca;
    } 
}

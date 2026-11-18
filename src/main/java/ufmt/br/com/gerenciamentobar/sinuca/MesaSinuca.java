/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import java.math.BigDecimal;

/**
 *
 * @author HenzouGT
 */
public class MesaSinuca {
    private Long id;
    private int numero;
    private BigDecimal valorHora;
    private StatusMesaSinuca status = StatusMesaSinuca.LIVRE;    
    
    public MesaSinuca(int numero, BigDecimal valorHora, StatusMesaSinuca status) {
        this.numero = numero;
        this.valorHora = valorHora;
        this.status = status;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public StatusMesaSinuca getStatus() {
        return status;
    }
    
    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }
    
    public BigDecimal getValorHora() {
        return valorHora;
    }
    
    public void ocupar() {
        this.status = StatusMesaSinuca.OCUPADA;
    }
    
    public void liberar() {
        this.status = StatusMesaSinuca.LIVRE;
    }
    
    public void colocarEmManutencao() {
        this.status = StatusMesaSinuca.MANUTENCAO;
    }
    
    public boolean isDisponivel() {
        return status == StatusMesaSinuca.LIVRE;
    }
}


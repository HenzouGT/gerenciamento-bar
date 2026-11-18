/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.comanda;

/**
 *
 * @author HenzouGT
 */
public enum StatusComanda {
    ABERTA("Aberta"),
    FECHADA("Fechada"),
    CANCELADA("Cancelada");
    
    private final String nomeFormatado;
    
    StatusComanda(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }
    
    public String getNomeFormatado() {
        return nomeFormatado;
    }

    @Override
    public String toString() {
        return this.nomeFormatado;
    }
}

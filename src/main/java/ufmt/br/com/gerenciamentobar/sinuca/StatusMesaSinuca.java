/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

/**
 *
 * @author HenzouGT
 */
public enum StatusMesaSinuca {
    LIVRE("Livre"),
    OCUPADA("Ocupada"),
    MANUTENCAO("Manutenção");
    
    private final String nomeFormatado;
    
    StatusMesaSinuca(String nomeFormatado) {
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

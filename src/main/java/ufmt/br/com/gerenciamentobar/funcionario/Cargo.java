/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

/**
 *
 * @author HenzouGT
 */
public enum Cargo {
    ATENDENTE("Atendente"),
    GARCOM("Garçom"),
    GERENTE("Gerente");

    private final String nomeFormatado;

    Cargo(String nomeFormatado) {
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

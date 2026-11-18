/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

import ufmt.br.com.gerenciamentobar.funcionario.Cargo;

/**
 *
 * @author HenzouGT
 */
public class Funcionario {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private Cargo cargo;
    private String senhaHash;
    private boolean ativo;   

    public Funcionario(
            String nome,
            String cpf,
            String telefone,
            Cargo cargo,
            String senhaHash
    ) {
        this.nome = validarNome(nome);
        this.cpf = validarCpf(cpf);
        this.telefone = validarTelefone(telefone);
        this.cargo = validarCargo(cargo);
        this.senhaHash = validarSenhaHash(senhaHash);
        this.ativo = true;
    }
    
    public void setId(Long id) {
        this.id = validarId(id);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }
    
    public String getNome() {
        return nome;
    }
        
    public void setCpf(String cpf) {
        this.cpf = validarCpf(cpf);
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = validarTelefone(telefone);
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void alterarCargo(Cargo cargo) {
        this.cargo = validarCargo(cargo);
    }
    
    public Cargo getCargo() {
        return cargo;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void ativar() {
        ativo = true;
    }
    
    public void desativar() {
        ativo = false;
    }    

    public String getSenhaHash() {
        return senhaHash;
    }
    
    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do funcionário é obrigatório."
            );
        }
        
        String nomeNormalizado = nome.trim();

        if (nomeNormalizado.length() > 150) {
            throw new IllegalArgumentException(
                "O nome deve possuir no máximo 150 caracteres."
            );
        }
        
        return nomeNormalizado;
    }

    private String validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException(
                "O CPF é obrigatório."
            );
        }

        if (!cpf.matches("[0-9]{11}")) {
            throw new IllegalArgumentException(
                "O CPF deve conter exatamente 11 números."
            );
        }
        
        return cpf;
    }

    private Long validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID do funcionário é inválido."
            );
        }
        
        return id;
    }

    private String validarTelefone(String telefone) {
        if (telefone == null) {
            return null;
        }
        
        String telefoneNormalizado = telefone.trim();
        
        if (telefoneNormalizado.length() > 20) {
            throw new IllegalArgumentException(
                "O telefone deve possuir no máximo 20 caracteres."
            );
        }
        
        return telefoneNormalizado;
    }
    
    private String validarSenhaHash(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException(
                "O hash da senha é obrigatório."
            );
        }
        
        return senhaHash;
    }

    private Cargo validarCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException(
                "O cargo é obrigatório."
            );
        }
        
        return cargo;
    }    
}

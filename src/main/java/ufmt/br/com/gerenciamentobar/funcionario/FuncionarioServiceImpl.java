/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.exception.NotFoundException;
import ufmt.br.com.gerenciamentobar.funcionario.dto.EditarFuncionarioDTO;
import ufmt.br.com.gerenciamentobar.funcionario.dto.CriarFuncionarioDTO;
import ufmt.br.com.gerenciamentobar.funcionario.dto.ResumoFuncionarioDTO;
import ufmt.br.com.gerenciamentobar.seguranca.SenhaHasher;

/**
 *
 * @author HenzouGT
 */
public class FuncionarioServiceImpl implements FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final SenhaHasher senhaHasher;

    public FuncionarioServiceImpl(
        FuncionarioRepository funcionarioRepository,
        SenhaHasher senhaHasher
    ) {
        this.funcionarioRepository = funcionarioRepository;
        this.senhaHasher = senhaHasher;
    }

    @Override
    public void criar(CriarFuncionarioDTO dados) {
        if (dados == null) {
            throw new IllegalArgumentException(
                "Os dados do funcionário são obrigatórios."
            );
        }
        
        String senhaHash = senhaHasher.gerarHash(dados.senha());

        Funcionario funcionario = new Funcionario(
            dados.nome(),
            dados.cpf(),
            dados.telefone(),
            dados.cargo(),
            senhaHash
        );

        if (funcionarioRepository.existePorCpf(funcionario.getCpf())) {
            throw new IllegalArgumentException(
                "Já existe um funcionário cadastrado com esse CPF."
            );
        }        
        
        funcionarioRepository.salvar(funcionario);
    }
    
    @Override
    public List<ResumoFuncionarioDTO> buscarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.buscarTodos();
        List<ResumoFuncionarioDTO> resumos = new ArrayList<>();

        for (Funcionario funcionario : funcionarios) {
            resumos.add(converterParaResumo(funcionario));
        }

        return resumos;
    }
    
    @Override
    public Optional<ResumoFuncionarioDTO> buscarPorId(Long id) {
        validarId(id);
        Optional<Funcionario> resultado = funcionarioRepository.buscarPorId(id);
        
        if (resultado.isEmpty()) {
            return Optional.empty();
        }
        Funcionario funcionario = resultado.get();
        
        ResumoFuncionarioDTO dto = converterParaResumo(funcionario);
        
        return Optional.of(dto);
    }
    
    @Override
    public void editar(Long id, EditarFuncionarioDTO dados) {
        validarId(id);
        
        if (dados == null) {
            throw new IllegalArgumentException("Os dados do funcionário são obrigatórios.");
        }

        Funcionario funcionarioAtual = funcionarioRepository.buscarPorId(id)
            .orElseThrow(() ->
                new NotFoundException("Funcionário não encontrado: " + id)
            );
        
        String cpfAnterior = funcionarioAtual.getCpf();
            
        funcionarioAtual.setNome(dados.nome());
        funcionarioAtual.setCpf(dados.cpf());
        funcionarioAtual.setTelefone(dados.telefone());
        funcionarioAtual.alterarCargo(dados.cargo());
        
        String cpfNovo = funcionarioAtual.getCpf();
        boolean cpfFoiAlterado = !cpfAnterior.equals(cpfNovo);

        if (cpfFoiAlterado && funcionarioRepository.existePorCpf(cpfNovo)) {
            throw new IllegalArgumentException(
                "Já existe um funcionário cadastrado com esse CPF."
            );
        }
        
        funcionarioRepository.editar(id, funcionarioAtual);
    }
    
    @Override
    public void desativar(Long id) {
        validarId(id);  
        funcionarioRepository.desativar(id);
    }
    
    @Override
    public void ativar(Long id) {
        validarId(id);      
        funcionarioRepository.ativar(id);
    }     

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID do funcionário é inválido."
            );
        }
    }

    private ResumoFuncionarioDTO converterParaResumo(
        Funcionario funcionario
    ) {
        return new ResumoFuncionarioDTO(
            funcionario.getId(),
            funcionario.getNome(),
            funcionario.getCpf(),
            funcionario.getTelefone(),
            funcionario.getCargo(),
            funcionario.isAtivo()
        );
    }
}

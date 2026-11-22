/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.exception.NotFoundException;
import ufmt.br.com.gerenciamentobar.sinuca.dto.CriarMesaSinucaDTO;
import ufmt.br.com.gerenciamentobar.sinuca.dto.EditarMesaSinucaDTO;
import ufmt.br.com.gerenciamentobar.sinuca.dto.ResumoMesaSinucaDTO;

/**
 *
 * @author HenzouGT
 */
public class MesaSinucaServiceImpl implements MesaSinucaService {
    private final MesaSinucaRepository mesaSinucaRepository;
    
    public MesaSinucaServiceImpl(MesaSinucaRepository mesaSinucaRepository) {
        this.mesaSinucaRepository = mesaSinucaRepository;
    }
    
    @Override
    public MesaSinuca criar(CriarMesaSinucaDTO dados) {
        if (dados == null) {
            throw new IllegalArgumentException(
                "Os dados da mesa de sinuca são obrigatórios."
            );
        }
        
        MesaSinuca mesaSinuca = new MesaSinuca(
            dados.numero(),
            dados.valorHora(),
            StatusMesaSinuca.LIVRE
        );
        
        return mesaSinucaRepository.salvar(mesaSinuca);
    }
    
@Override
    public List<ResumoMesaSinucaDTO> buscarTodos() {
        List<MesaSinuca> mesaSinucas = mesaSinucaRepository.buscarTodos();
        List<ResumoMesaSinucaDTO> resumoMesas = new ArrayList<>();
        
        for (MesaSinuca mesaSinuca : mesaSinucas) {
            resumoMesas.add(converterParaResumo(mesaSinuca));
        }
        return resumoMesas;
    }
    
    @Override
    public Optional<ResumoMesaSinucaDTO> buscarPorId(Long id) {
        validarId(id);
        
        Optional<MesaSinuca> resultado = mesaSinucaRepository.buscarPorId(id);
        
        if (resultado.isEmpty()) {
            return Optional.empty();
        }        
        
        MesaSinuca mesaSinuca = resultado.get();
        
        ResumoMesaSinucaDTO dto = converterParaResumo(mesaSinuca);        
        return Optional.of(dto);
    }
    
    @Override
    public MesaSinuca editar(Long id, EditarMesaSinucaDTO dados) {
        validarId(id);
        
        if (dados == null) {
            throw new IllegalArgumentException("Os dados da mesa de sinuca são obrigatórios.");
        }
        
        MesaSinuca mesaSinucaAtual = mesaSinucaRepository.buscarPorId(id)
            .orElseThrow(() ->
                new NotFoundException("Mesa de sinuca não encontrado: " + id)
            );
        
        mesaSinucaAtual.setNumero(dados.numero());
        mesaSinucaAtual.setValorHora(dados.valorHora());      
        
        return mesaSinucaRepository.editar(id, mesaSinucaAtual);        
    }

    @Override
    public void excluir(Long id) {
        validarId(id);
        mesaSinucaRepository.excluir(id);
    }
    
    @Override
    public void ocupar(Long id) {
        validarId(id);
        
        MesaSinuca mesa = mesaSinucaRepository.buscarPorId(id)
        .orElseThrow(() ->
            new NotFoundException("Mesa de sinuca não encontrada: " + id)
        );
        
        mesa.ocupar();        
        mesaSinucaRepository.atualizarStatus(mesa);
    }
    
    @Override
    public void liberar(Long id) {
        validarId(id);
        
        MesaSinuca mesa = mesaSinucaRepository.buscarPorId(id)
        .orElseThrow(() ->
            new NotFoundException("Mesa de sinuca não encontrada: " + id)
        );
        
        mesa.liberar();
        mesaSinucaRepository.atualizarStatus(mesa);

    }
    
    @Override
    public void colocarEmManutencao(Long id) {
        validarId(id);
        
        MesaSinuca mesa = mesaSinucaRepository.buscarPorId(id)
        .orElseThrow(() ->
            new NotFoundException("Mesa de sinuca não encontrada: " + id)
        );
        
        mesa.colocarEmManutencao();
        mesaSinucaRepository.atualizarStatus(mesa);
    }    
    
    
    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                "O ID da mesa de sinuca é inválido."
            );
        }
    }
    
    private ResumoMesaSinucaDTO converterParaResumo(MesaSinuca mesaSinuca) {
        return new ResumoMesaSinucaDTO(
            mesaSinuca.getId(),
            mesaSinuca.getNumero(),
            mesaSinuca.getValorHora(),
            mesaSinuca.getStatus()
        );
    }
 }

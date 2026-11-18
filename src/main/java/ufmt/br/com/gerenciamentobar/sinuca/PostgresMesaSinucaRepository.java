/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.sinuca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.config.DatabaseConnection;
import ufmt.br.com.gerenciamentobar.exception.NotFoundException;
/**
 *
 * @author HenzouGT
 */
public class PostgresMesaSinucaRepository implements MesaSinucaRepository {
    @Override 
    public MesaSinuca salvar(MesaSinuca mesaSinuca) {
        String sql = """
            INSERT INTO mesa_sinuca     
                (numero, valor_hora) 
            VALUES (?,?)             
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setInt(1, mesaSinuca.getNumero());
            statement.setBigDecimal(2, mesaSinuca.getValorHora());
            
            statement.executeUpdate();
            
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    mesaSinuca.setId(keys.getLong(1));
                } 
            }
            return mesaSinuca;
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao salvar mesa de sinuca.", e);
        }
    }
    
    @Override 
    public List<MesaSinuca> buscarTodos() {
        String sql = """
            SELECT * FROM mesa_sinuca ORDER BY numero ASC
        """;
        
        List<MesaSinuca> mesasSinuca = new ArrayList<>();
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
        ) { 
            while (resultSet.next()) {
                MesaSinuca mesaSinuca = new MesaSinuca(
                    resultSet.getInt("numero"),
                    resultSet.getBigDecimal("valor_hora"),
                    StatusMesaSinuca.valueOf(resultSet.getString("status"))
                );
                mesaSinuca.setId(resultSet.getLong("id"));
                
                mesasSinuca.add(mesaSinuca);                
            }
            return mesasSinuca;
        
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao buscar mesas de sinuca.", e);
        }
    }
    
    @Override
    public Optional<MesaSinuca> buscarPorId(Long id) {
        String sql = """
            SELECT * FROM mesa_sinuca WHERE id = ?         
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    MesaSinuca mesaSinuca = new MesaSinuca(
                        resultSet.getInt("numero"),
                        resultSet.getBigDecimal("valor_hora"),
                        StatusMesaSinuca.valueOf(resultSet.getString("status"))
                    );
                
                    mesaSinuca.setId(resultSet.getLong("id"));
                    
                    return Optional.of(mesaSinuca);
                }
                return Optional.empty();
                
            } 
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao buscar mesa de sinuca.", e);
        }                                                   
    }
    
    @Override
    public MesaSinuca editar(Long id, MesaSinuca mesaSinuca) {
        String sql = """
            UPDATE mesa_sinuca SET numero = ?, valor_hora = ? WHERE id = ?
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, mesaSinuca.getNumero());
            statement.setBigDecimal(2, mesaSinuca.getValorHora());
            statement.setLong(3, id);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new NotFoundException("Mesa de sinuca não encontrada: " + id);
            }
            mesaSinuca.setId(id);
            return mesaSinuca;
                        
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao editar mesa de sinuca.", e);
        }
    }
    
    @Override 
    public void atualizarStatus(MesaSinuca mesa) {
        String sql = """
            UPDATE mesa_sinuca SET status = ? WHERE id = ?
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, mesa.getStatus().name(), Types.OTHER);
            statement.setLong(2, mesa.getId());

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Mesa de sinuca não encontrada: " + mesa.getId());
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao atualizar status da mesa de sinuca.", e);
        } 
    }        
}

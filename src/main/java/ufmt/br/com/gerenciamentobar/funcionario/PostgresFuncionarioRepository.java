/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.funcionario;

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
public class PostgresFuncionarioRepository implements FuncionarioRepository {
    @Override
    public Funcionario salvar(Funcionario funcionario) {
        String sql = """
            INSERT INTO funcionario
                (nome, cpf, telefone, cargo, senha_hash, ativo)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getCpf());
            statement.setString(3, funcionario.getTelefone());
            statement.setObject(4, funcionario.getCargo().name(), Types.OTHER);
            statement.setString(5, funcionario.getSenhaHash());
            statement.setBoolean(6, funcionario.isAtivo());

            statement.executeUpdate();

            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    funcionario.setId(keys.getLong(1));
                }
            }
            return funcionario;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário.", e);
        }
    }

    @Override
    public boolean existePorCpf(String cpf) {
        String sql = """
            SELECT EXISTS (SELECT 1 FROM funcionario WHERE cpf = ?) AS cpf_existe
        """;

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, cpf);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean("cpf_existe");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar CPF do funcionário.", e);
        }
    }
    
    @Override
    public List<Funcionario> buscarTodos() {
        String sql = """
            SELECT id, nome, cpf, telefone, cargo, senha_hash, ativo
            FROM funcionario
            ORDER BY nome ASC
        """;

        List<Funcionario> funcionarios = new ArrayList<>();

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario(
                    resultSet.getString("nome"),
                    resultSet.getString("cpf"),
                    resultSet.getString("telefone"),
                    Cargo.valueOf(resultSet.getString("cargo")),
                    resultSet.getString("senha_hash")
                );

                funcionario.setId(resultSet.getLong("id"));

                if (!resultSet.getBoolean("ativo")) {
                    funcionario.desativar();
                }

                funcionarios.add(funcionario);
            }
            return funcionarios;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionários.", e);
        }
    }
    
    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        String sql = """
            SELECT * FROM funcionario WHERE id = ?
        """;
                
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
                
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Funcionario funcionario = new Funcionario(
                        resultSet.getString("nome"),
                        resultSet.getString("cpf"),
                        resultSet.getString("telefone"),
                        Cargo.valueOf(resultSet.getString("cargo")),
                        resultSet.getString("senha_hash")
                    );
                
                    funcionario.setId(resultSet.getLong("id"));
                
                    if (!resultSet.getBoolean("ativo")) {
                        funcionario.desativar();
                    }               
                    return Optional.of(funcionario);
                }                
                return Optional.empty();
            }            
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário.", e);
        }
    }
    
    @Override
    public Funcionario editar(Long id, Funcionario funcionario) {
        String sql = """
            UPDATE funcionario SET nome = ?, cpf = ?, telefone = ?, cargo = ? WHERE id = ?             
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, funcionario.getNome());
            statement.setString(2, funcionario.getCpf());
            statement.setString(3, funcionario.getTelefone());
            statement.setObject(4, funcionario.getCargo().name(), Types.OTHER);
            statement.setLong(5, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Funcionário não encontrado: " + id);
            }
            
            funcionario.setId(id);
            return funcionario;
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao editar funcionário.", e);
        }
    }
    
    @Override
    public void desativar(Long id) {
        String sql = """
            UPDATE funcionario SET ativo = false WHERE id = ? 
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Funcionário não encontrado: " + id);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao desativar funcionário.", e);
        }
    }
    
    @Override
    public void ativar(Long id) {
        String sql = """
            UPDATE funcionario SET ativo = true WHERE id = ?             
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            
            int linhasAfetadas = statement.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new NotFoundException("Funcionário não encontrado: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao ativar funcionário.", e);
        }
    }
}

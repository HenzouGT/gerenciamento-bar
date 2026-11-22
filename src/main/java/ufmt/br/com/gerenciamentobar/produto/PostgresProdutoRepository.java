/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ufmt.br.com.gerenciamentobar.config.DatabaseConnection;
import ufmt.br.com.gerenciamentobar.exception.NotFoundException;
/**
 *
 * @author HenzouGT
 */
public class PostgresProdutoRepository implements ProdutoRepository {
    @Override 
    public Produto salvar(Produto produto) {
        String sql = """
            INSERT INTO produto 
                (nome, preco, quantidade_estoque, ativo)
            VALUES (?, ?, ?, ?)            
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, produto.getNome());
            statement.setBigDecimal(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidadeEstoque());
            statement.setBoolean(4, produto.isAtivo());
            
            statement.executeUpdate();
            
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    produto.setId(keys.getLong(1));
                }            
            }
            return produto;
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao salvar produto.", e);
        }
    }
    
    @Override
    public List<Produto> buscarTodos() {
        String sql = """
            SELECT * FROM produto ORDER BY nome ASC;         
        """;
        
        List<Produto> produtos = new ArrayList<>();
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                Produto produto = new Produto(
                    resultSet.getString("nome"),
                    resultSet.getBigDecimal("preco"),
                    resultSet.getInt("quantidade_estoque"),
                    resultSet.getBoolean("ativo")
                );
                
                produto.setId(resultSet.getLong("id"));
                
                produtos.add(produto);
            }
            return produtos;
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao buscar produtos.", e);
        }
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        String sql = """
            SELECT * FROM produto WHERE id = ?
        """;
                
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
                
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Produto produto = new Produto(
                        resultSet.getString("nome"),
                        resultSet.getBigDecimal("preco"),
                        resultSet.getInt("quantidade_estoque"),
                        resultSet.getBoolean("ativo")
                    );
                
                    produto.setId(resultSet.getLong("id"));
           
                    return Optional.of(produto);
                }                
                return Optional.empty();
            }            
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto.", e);
        }
    }    
    
    @Override
    public Produto editar(Long id, Produto produto) {
        String sql = """
            UPDATE produto SET nome = ?, preco = ?, quantidade_estoque = ? WHERE id = ?             
        """;
        
        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, produto.getNome());
            statement.setBigDecimal(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidadeEstoque());            
            statement.setLong(4, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Produto não encontrado: " + id);
            }
            
            produto.setId(id);
            return produto;
            
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao editar produto", e);
        }
    }

    @Override
    public void excluir(Long id) {
        String sql = """
            DELETE FROM produto WHERE id = ?
        """;

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Produto não encontrado: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao excluir produto.", e);
        }
    }
    
    @Override
    public void desativar(Long id) {
        String sql = """
            UPDATE produto SET ativo = false WHERE id = ? 
        """;

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Produto não encontrado: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao desativar.", e);
        }
    }

    @Override
    public void ativar(Long id) {
        String sql = """
            UPDATE produto SET ativo = true WHERE id = ?             
        """;

        try (
            Connection connection = DatabaseConnection.open();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            int linhasAfetadas = statement.executeUpdate();

            if (linhasAfetadas == 0) {
                throw new NotFoundException("Produto não encontrado: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao ativar produto.", e);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ufmt.br.com.gerenciamentobar.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author HenzouGT
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/gerenciamento_bar";

    private static final String USER = "bar_user";

    private static final String PASSWORD = "bar_password";

    private DatabaseConnection() {
    }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

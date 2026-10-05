
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Altere o nome do banco, usuário e senha se o seu MySQL Workbench utilizar dados diferentes
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_auto_pecas?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";     // Seu usuário do MySQL
    private static final String PASSWORD = "ero3803"; // Sua senha do MySQL

    /**
     * Estabelece e retorna uma conexão ativa com o banco MySQL
     */
    public static Connection getConexao() throws SQLException {
        try {
            // Garante o carregamento do driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do MySQL não encontrado: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}

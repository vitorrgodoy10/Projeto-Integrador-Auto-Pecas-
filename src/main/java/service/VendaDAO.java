
package service;

import model.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {
    /**
     * Registra a venda, reduz a quantidade em estoque e grava no banco.
     */
    public boolean registrarVenda(int idPeca, int quantidade, double precoVenda) {
        String sqlVenda = "INSERT INTO venda (id_peca, quantidade, preco_venda, data_venda) VALUES (?, ?, ?, NOW())";
        String sqlEstoque = "UPDATE peca SET quantidade = quantidade - ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            // Inicia transação para garantir que ambas as operações funcionem juntos
            conn.setAutoCommit(false);

            // 1. Inserir a Venda
            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {
                stmtVenda.setInt(1, idPeca);
                stmtVenda.setInt(2, quantidade);
                stmtVenda.setDouble(3, precoVenda);
                stmtVenda.executeUpdate();
            }

            // 2. Dar baixa no Estoque
            try (PreparedStatement stmtEstoque = conn.prepareStatement(sqlEstoque)) {
                stmtEstoque.setInt(1, quantidade);
                stmtEstoque.setInt(2, idPeca);
                stmtEstoque.executeUpdate();
            }

            conn.commit(); // Confirma a transação
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Cancela a transação se der erro
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao registrar venda: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Erro ao restaurar auto-commit: " + e.getMessage());
                }
            }
        }
    }
}

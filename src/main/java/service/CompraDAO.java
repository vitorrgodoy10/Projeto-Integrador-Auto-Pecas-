package service;

import model.CompraEstoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    // 1. CADASTRAR COMPRA E ATUALIZAR ESTOQUE DA PEÇA
    public boolean registrarCompra(int idPeca, int idFornecedor, int quantidade, double precoCusto) {
        String sqlCompra = "INSERT INTO compras (id_peca, id_fornecedor, quantidade, preco_custo, data_compra) VALUES (?, ?, ?, ?, NOW())";
        String sqlAtualizaEstoque = "UPDATE peca SET quantidade = quantidade + ?, preco_custo = ? WHERE id = ?";

        Connection conn = null;

        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false); // Transação manual

            // 1. Insere o registro da compra
            try (PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra)) {
                stmtCompra.setInt(1, idPeca);
                stmtCompra.setInt(2, idFornecedor);
                stmtCompra.setInt(3, quantidade);
                stmtCompra.setDouble(4, precoCusto);
                stmtCompra.executeUpdate();
            }

            // 2. Aumenta o estoque e atualiza o preço de custo na tabela peca
            try (PreparedStatement stmtEstoque = conn.prepareStatement(sqlAtualizaEstoque)) {
                stmtEstoque.setInt(1, quantidade);
                stmtEstoque.setDouble(2, precoCusto);
                stmtEstoque.setInt(3, idPeca);
                stmtEstoque.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro ao registrar compra: " + ex.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    System.err.println("Erro no Rollback: " + e.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    // 2. LISTAR HISTÓRICO DE COMPRAS
    public List<CompraEstoque> listarTodas() {
        List<CompraEstoque> lista = new ArrayList<>();
        String sql = "SELECT c.id, p.nome AS peca_nome, f.razao_social AS fornecedor_nome, " +
                     "c.quantidade, c.preco_custo, (c.quantidade * c.preco_custo) AS total, c.data_compra " +
                     "FROM compras c " +
                     "INNER JOIN peca p ON c.id_peca = p.id " +
                     "INNER JOIN fornecedores f ON c.id_fornecedor = f.id " +
                     "ORDER BY c.id DESC";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CompraEstoque c = new CompraEstoque();
                c.setId(rs.getInt("id"));
                c.setNomePeca(rs.getString("peca_nome"));
                c.setNomeFornecedor(rs.getString("fornecedor_nome"));
                c.setQuantidade(rs.getInt("quantidade"));
                c.setPrecoCustoUnitario(rs.getDouble("preco_custo"));
                c.setTotal(rs.getDouble("total"));
                c.setDataCompra(rs.getTimestamp("data_compra"));

                lista.add(c);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar compras: " + ex.getMessage());
        }

        return lista;
    }
}
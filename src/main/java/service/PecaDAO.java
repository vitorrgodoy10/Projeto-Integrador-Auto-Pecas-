
package service;

import model.Peca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    /**
     * Retorna a lista completa de peças salvas no MySQL
     */
    public List<Peca> listarTodas() {
        List<Peca> lista = new ArrayList<>();
        String sql = "SELECT * FROM peca";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Peca p = new Peca();
                p.setId(rs.getInt("id"));
                p.setSku(rs.getString("sku"));
                p.setNome(rs.getString("nome"));
                p.setQuantidade(rs.getInt("quantidade"));
                p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                p.setPrecoCusto(rs.getDouble("preco_custo"));
                p.setPrecoVenda(rs.getDouble("preco_venda"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar peças: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca uma peça pelo seu código SKU no banco de dados
     */
    public Peca buscarPorSKU(String sku) {
        String sql = "SELECT * FROM peca WHERE sku = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sku);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Peca p = new Peca();
                    p.setId(rs.getInt("id"));
                    p.setSku(rs.getString("sku"));
                    p.setNome(rs.getString("nome"));
                    p.setQuantidade(rs.getInt("quantidade"));
                    p.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                    p.setPrecoCusto(rs.getDouble("preco_custo"));
                    p.setPrecoVenda(rs.getDouble("preco_venda"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar peça por SKU: " + e.getMessage());
        }
        return null;
    }

    /**
     * Cadastra uma nova peça no banco de dados e retorna o ID gerado
     */
    public int cadastrar(Peca peca) {
        String sql = "INSERT INTO peca (sku, nome, quantidade, estoque_minimo, preco_custo, preco_venda) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, peca.getSku());
            stmt.setString(2, peca.getNome());
            stmt.setInt(3, peca.getQuantidade());
            stmt.setInt(4, peca.getEstoqueMinimo());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Retorna o ID auto-incremento do MySQL
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar peça: " + e.getMessage());
        }
        return -1; // Retorna -1 em caso de erro
    }

    /**
     * Atualiza os dados de uma peça existente pelo ID
     */
    public boolean atualizar(Peca peca) {
        String sql = "UPDATE peca SET sku = ?, nome = ?, quantidade = ?, estoque_minimo = ?, preco_custo = ?, preco_venda = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getSku());
            stmt.setString(2, peca.getNome());
            stmt.setInt(3, peca.getQuantidade());
            stmt.setInt(4, peca.getEstoqueMinimo());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());
            stmt.setInt(7, peca.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar peça: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove uma peça do banco de dados pelo ID
     */
    public boolean excluir(int id) {
        String sql = "DELETE FROM peca WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir peça: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES PARA O DASHBOARD (MENU PRINCIPAL)
    // =========================================================================

    public int contarTotalSKUs() {
        String sql = "SELECT COUNT(*) FROM peca";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar SKUs: " + e.getMessage());
        }
        return 0;
    }

    public int contarEstoqueBaixo() {
        String sql = "SELECT COUNT(*) FROM peca WHERE quantidade <= estoque_minimo";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar estoque baixo: " + e.getMessage());
        }
        return 0;
    }
}
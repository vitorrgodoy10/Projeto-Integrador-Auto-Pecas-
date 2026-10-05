package service;

import model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    // 1. CADASTRAR NOVO FORNECEDOR
    public boolean inserir(Fornecedor f) {
        String sql = "INSERT INTO fornecedores (razao_social, cnpj, telefone) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getRazaoSocial());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getTelefone());

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir fornecedor: " + ex.getMessage());
            return false;
        }
    }

    // 2. LISTAR TODOS OS FORNECEDORES
    public List<Fornecedor> listarTodos() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores ORDER BY id DESC";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id"));
                f.setRazaoSocial(rs.getString("razao_social"));
                f.setCnpj(rs.getString("cnpj"));
                f.setTelefone(rs.getString("telefone"));

                lista.add(f);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar fornecedores: " + ex.getMessage());
        }

        return lista;
    }

    // 3. BUSCAR POR ID (USADO NA EDIÇÃO)
    public Fornecedor buscarPorId(int id) {
        String sql = "SELECT * FROM fornecedores WHERE id = ?";
        Fornecedor f = null;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    f = new Fornecedor();
                    f.setId(rs.getInt("id"));
                    f.setRazaoSocial(rs.getString("razao_social"));
                    f.setCnpj(rs.getString("cnpj"));
                    f.setTelefone(rs.getString("telefone"));
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar fornecedor por ID: " + ex.getMessage());
        }

        return f;
    }

    // 4. ATUALIZAR FORNECEDOR EXISTENTE
    public boolean atualizar(Fornecedor f) {
        String sql = "UPDATE fornecedores SET razao_social = ?, cnpj = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getRazaoSocial());
            stmt.setString(2, f.getCnpj());
            stmt.setString(3, f.getTelefone());
            stmt.setInt(4, f.getId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar fornecedor: " + ex.getMessage());
            return false;
        }
    }

    // 5. EXCLUIR FORNECEDOR
    public boolean excluir(int id) {
        String sql = "DELETE FROM fornecedores WHERE id = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.err.println("Erro ao excluir fornecedor: " + ex.getMessage());
            return false;
        }
    }
}
package service;

import java.util.ArrayList;
import java.util.List;
import model.CompraEstoque;
import model.Fornecedor;
import model.Peca;
import model.VendaML;

public class LojaAutoPecas {
    private final List<Peca> pecas = new ArrayList<>();
    private final List<Fornecedor> fornecedores = new ArrayList<>();
    private final List<CompraEstoque> compras = new ArrayList<>();
    private final List<VendaML> vendas = new ArrayList<>();

    public void cadastrarPeca(String sku, String nome, double precoCusto, double precoVenda, int qtd, int min) {
        int id = pecas.size() + 1;
        Peca p = new Peca(id, sku, nome, precoCusto, precoVenda, qtd, min);
        pecas.add(p);
    }

    public List<Peca> listarPecas() { return pecas; }

   public Peca buscarPecaPorSku(String sku) {
    for (Peca p : pecas) {
        if (String.valueOf(p.getSku()).equalsIgnoreCase(sku.trim())) return p;
    }
    return null;
        
}
    public List<Peca> listarEstoqueBaixo() {
        List<Peca> baixos = new ArrayList<>();
        for (Peca p : pecas) {
            if (p.isEstoqueBaixo()) baixos.add(p);
        }
        return baixos;
    }

    public void cadastrarFornecedor(String razaoSocial, String cnpj, String telefone) {
        int id = fornecedores.size() + 1;
        fornecedores.add(new Fornecedor(id, razaoSocial, cnpj, telefone));
    }

    public List<Fornecedor> listarFornecedores() { return fornecedores; }

    public Fornecedor buscarFornecedorPorId(int id) {
        for (Fornecedor f : fornecedores) {
            if (f.getId() == id) return f;
        }
        return null;
    }

    public boolean registrarCompra(int idFornecedor, String sku, int qtd, double custoUnitario) {
        Fornecedor f = buscarFornecedorPorId(idFornecedor);
        Peca p = buscarPecaPorSku(sku);
        if (f != null && p != null) {
            CompraEstoque compra = new CompraEstoque(compras.size() + 1, f, p, qtd, custoUnitario);
            compra.processarEntrada();
            compras.add(compra);
            return true;
        }
        return false;
    }

    public boolean registrarVendaML(String pedidoML, String sku, int qtd) {
        Peca p = buscarPecaPorSku(sku);
        if (p != null) {
            VendaML venda = new VendaML(vendas.size() + 1, pedidoML, p, qtd);
            if (venda.processarVenda()) {
                vendas.add(venda);
                return true;
            }
        }
        return false;
    }
}
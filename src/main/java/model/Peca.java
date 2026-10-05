package model;

public class Peca {
    private int id;
    private String sku;
    private String nome;
    private int quantidade;
    private int estoqueMinimo;
    private double precoCusto;
    private double precoVenda;

    // Construtor vazio
    public Peca() {
    }

    // Construtor completo (Ordem padrão)
    public Peca(int id, String sku, String nome, int quantidade, int estoqueMinimo, double precoCusto, double precoVenda) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
    }

    // Construtor alternativo (usado na LojaAutoPecas)
    public Peca(int id, String sku, String nome, double precoCusto, double precoVenda, int quantidade, int estoqueMinimo) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Método auxiliar para verificar se o estoque está baixo
    public boolean isEstoqueBaixo() {
        return this.quantidade <= this.estoqueMinimo;
    }

    // Métodos para movimentação de estoque
    public void darEntradaEstoque(int qtd) {
        this.quantidade += qtd;
    }

    public boolean darBaixaEstoque(int qtd) {
        if (this.quantidade >= qtd) {
            this.quantidade -= qtd;
            return true;
        }
        return false;
    }

    // toString para o JComboBox exibir bonita a opção (Ex: "SKU123 - Correia Dentada")
    @Override
    public String toString() {
        if (this.sku != null && !this.sku.isEmpty()) {
            return this.sku + " - " + this.nome;
        }
        return this.nome;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public int getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    public double getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(double precoCusto) { this.precoCusto = precoCusto; }

    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }
}
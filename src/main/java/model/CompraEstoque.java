package model;

import java.util.Date;

public class CompraEstoque {
    private int id;
    private Fornecedor fornecedor;
    private Peca peca;
    private int quantidade;
    private double precoCustoUnitario;
    
    // Campos auxiliares para consulta e exibição na tabela
    private String nomePeca;
    private String nomeFornecedor;
    private double total;
    private Date dataCompra;

    // Construtor vazio
    public CompraEstoque() {
    }

    // Construtor completo
    public CompraEstoque(int id, Fornecedor fornecedor, Peca peca, int quantidade, double precoCustoUnitario) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.peca = peca;
        this.quantidade = quantidade;
        this.precoCustoUnitario = precoCustoUnitario;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoCustoUnitario() {
        return precoCustoUnitario;
    }

    public void setPrecoCustoUnitario(double precoCustoUnitario) {
        this.precoCustoUnitario = precoCustoUnitario;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void processarEntrada() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
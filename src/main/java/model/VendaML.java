package model;

public class VendaML {
    private int id;
    private String codigoPedidoML;
    private Peca peca;
    private int quantidade;
    private double precoVendaUnitario;

    public VendaML(int id, String codigoPedidoML, Peca peca, int quantidade) {
        this.id = id;
        this.codigoPedidoML = codigoPedidoML;
        this.peca = peca;
        this.quantidade = quantidade;
        this.precoVendaUnitario = peca.getPrecoVenda();
    }

    public boolean processarVenda() {
        return peca.darBaixaEstoque(quantidade);
    }
}
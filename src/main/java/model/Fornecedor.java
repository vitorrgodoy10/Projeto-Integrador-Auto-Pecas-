package model;

public class Fornecedor {
    private int id;
    private String razaoSocial;
    private String cnpj;
    private String telefone;

    public Fornecedor() {
    }

    public Fornecedor(String razaoSocial, String cnpj, String telefone) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public Fornecedor(int id, String razaoSocial, String cnpj, String telefone) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // Retorna razaoSocial caso algum método busque por getNome()
    public String getNome() {
        return this.razaoSocial;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + razaoSocial + " (CNPJ: " + cnpj + ")";
    }
}
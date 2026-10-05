package view;

import java.util.Scanner;
import model.Fornecedor;
import model.Peca;
import service.LojaAutoPecas;

public class Menu {
    private LojaAutoPecas loja = new LojaAutoPecas();
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        // Dados de teste pré-carregados
        loja.cadastrarFornecedor("Distribuidora Brasil", "11.222.333/0001-44", "(11) 9999-8888");
        loja.cadastrarPeca("FAR-GOL-G5", "Farol Gol G5 Lado Esquerdo", 120.0, 189.9, 5, 3);
        loja.cadastrarPeca("PAL-CORSA-18", "Par de Palhetas Corsa 18", 15.0, 39.9, 2, 5);

        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            System.out.print("Digite a opção: ");
            
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> cadastrarPeca();
                case 2 -> consultarEstoque();
                case 3 -> relatorioEstoqueBaixo();
                case 4 -> cadastrarFornecedor();
                case 5 -> registrarCompra();
                case 6 -> registrarVendaML();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
            System.out.println();
        }
    }

    private void exibirMenu() {
        System.out.println("===== SISTEMA DE AUTO PEÇAS =====");
        System.out.println("1 - Cadastrar Peça (SKU)");
        System.out.println("2 - Consultar Todo o Estoque");
        System.out.println("3 - Relatório de Estoque Baixo");
        System.out.println("4 - Cadastrar Fornecedor");
        System.out.println("5 - Registrar Compra (Entrada)");
        System.out.println("6 - Registrar Venda Mercado Livre (Saída)");
        System.out.println("0 - Sair");
        System.out.println("================================");
    }

    private void cadastrarPeca() {
        System.out.print("SKU: "); String sku = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Preço Custo: "); double custo = Double.parseDouble(scanner.nextLine());
        System.out.print("Preço Venda: "); double venda = Double.parseDouble(scanner.nextLine());
        System.out.print("Qtd Inicial: "); int qtd = Integer.parseInt(scanner.nextLine());
        System.out.print("Estoque Mínimo: "); int min = Integer.parseInt(scanner.nextLine());
        loja.cadastrarPeca(sku, nome, custo, venda, qtd, min);
        System.out.println("Peça cadastrada com sucesso!");
    }

    private void consultarEstoque() {
        System.out.println("\n--- ESTOQUE ATUAL ---");
        if (loja.listarPecas().isEmpty()) {
            System.out.println("Nenhuma peça cadastrada.");
        } else {
            for (Peca p : loja.listarPecas()) System.out.println(p);
        }
    }

    private void relatorioEstoqueBaixo() {
        System.out.println("\n--- ITENS COM ESTOQUE BAIXO ---");
        if (loja.listarEstoqueBaixo().isEmpty()) {
            System.out.println("Nenhum item com estoque baixo no momento.");
        } else {
            for (Peca p : loja.listarEstoqueBaixo()) System.out.println(p);
        }
    }

    private void cadastrarFornecedor() {
        System.out.print("Razão Social: "); String rz = scanner.nextLine();
        System.out.print("CNPJ: "); String cnpj = scanner.nextLine();
        System.out.print("Telefone: "); String tel = scanner.nextLine();
        loja.cadastrarFornecedor(rz, cnpj, tel);
        System.out.println("Fornecedor cadastrado!");
    }

    private void registrarCompra() {
        System.out.println("Fornecedores disponíveis:");
        for (Fornecedor f : loja.listarFornecedores()) System.out.println(f);
        System.out.print("ID do Fornecedor: "); int idForn = Integer.parseInt(scanner.nextLine());
        System.out.print("SKU da Peça: "); String sku = scanner.nextLine();
        System.out.print("Quantidade Comprada: "); int qtd = Integer.parseInt(scanner.nextLine());
        System.out.print("Preço Custo Unitário: "); double custo = Double.parseDouble(scanner.nextLine());

        if (loja.registrarCompra(idForn, sku, qtd, custo)) {
            System.out.println("Entrada de estoque registrada com sucesso!");
        } else {
            System.out.println("Erro: Fornecedor ou SKU não encontrado.");
        }
    }

    private void registrarVendaML() {
        System.out.print("Código do Pedido ML: "); String pedido = scanner.nextLine();
        System.out.print("SKU da Peça Vendida: "); String sku = scanner.nextLine();
        System.out.print("Quantidade Vendida: "); int qtd = Integer.parseInt(scanner.nextLine());

        if (loja.registrarVendaML(pedido, sku, qtd)) {
            System.out.println("Venda efetuada e baixa de estoque realizada!");
        } else {
            System.out.println("Erro: Saldo insuficiente ou SKU não encontrado.");
        }
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
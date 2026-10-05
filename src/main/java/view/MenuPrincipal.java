
package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.awt.Font;
import service.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Vitor Godoy
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuPrincipal.class.getName());

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
       initComponents();
        this.setLocationRelativeTo(null);
        
        java.awt.Color fundoEscuro = new java.awt.Color(30, 30, 30);
        
        // 1. Pinta a janela principal
        this.getContentPane().setBackground(fundoEscuro);
        
        // 2. Pinta o jPanel1 e força opacidade
        if (jPanel1 != null) {
            jPanel1.setBackground(fundoEscuro);
            jPanel1.setOpaque(true);
        }

        // 3. Estilização dos Cards de Métricas (jLabel1, jLabel2, jLabel3)
        javax.swing.border.Border bordaCard = javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2),
            javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        if (jLabel1 != null) {
            jLabel1.setForeground(java.awt.Color.WHITE);
            jLabel1.setOpaque(true);
            jLabel1.setBackground(new java.awt.Color(45, 45, 45));
            jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel1.setBorder(bordaCard);
            jLabel1.setText("<html><center>Total de SKUs<br/><b style='font-size:20px; color:#FF3333;'>15</b></center></html>");
        }

        if (jLabel2 != null) {
            jLabel2.setForeground(java.awt.Color.WHITE);
            jLabel2.setOpaque(true);
            jLabel2.setBackground(new java.awt.Color(45, 45, 45));
            jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel2.setBorder(bordaCard);
            jLabel2.setText("<html><center>Estoque Baixo<br/><b style='font-size:20px; color:#FF3333;'>20</b></center></html>");
        }

        if (jLabel3 != null) {
            jLabel3.setForeground(java.awt.Color.WHITE);
            jLabel3.setOpaque(true);
            jLabel3.setBackground(new java.awt.Color(45, 45, 45));
            jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel3.setBorder(bordaCard);
            jLabel3.setText("<html><center>Vendas do Mês<br/><b style='font-size:18px; color:#FF3333;'>R$ 1.000,00</b></center></html>");
        }

        // 4. Desenha o gráfico
        criarGraficoDashboard();
        // 5. Carrega as métricas reais do Banco de Dados
        carregarMetricasDoBanco();
        }
    
        private void criarGraficoDashboard() {
        // Dados de exemplo para o gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(12, "Vendas", "Seg");
        dataset.addValue(19, "Vendas", "Ter");
        dataset.addValue(15, "Vendas", "Qua");
        dataset.addValue(27, "Vendas", "Qui");
        dataset.addValue(34, "Vendas", "Sex");
        dataset.addValue(20, "Vendas", "Sáb");

        // Criar gráfico de barras
        JFreeChart barChart = ChartFactory.createBarChart(
                "Desempenho de Vendas Semanal",
                "Dia da Semana",
                "Quantidade Vendida",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        // Estilização com cores do tema escuro da CR Auto Peças
        Color fundoEscuro = new Color(30, 30, 30);
        Color cinzaCard = new Color(45, 45, 45);

        barChart.setBackgroundPaint(fundoEscuro);
        if (barChart.getTitle() != null) {
            barChart.getTitle().setPaint(Color.WHITE);
            barChart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        }

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(cinzaCard);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);

        // Define a cor vermelha para as barras do gráfico
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 51, 51));

        // Letras dos eixos em branco
        plot.getDomainAxis().setLabelPaint(Color.WHITE);
        plot.getDomainAxis().setTickLabelPaint(Color.WHITE);
        plot.getRangeAxis().setLabelPaint(Color.WHITE);
        plot.getRangeAxis().setTickLabelPaint(Color.WHITE);

        // Criar o painel do gráfico e adicionar à tela
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBounds(250, 180, 480, 270);
        
        jPanel1.setLayout(null);
        jPanel1.add(chartPanel);
        jPanel1.revalidate();
        jPanel1.repaint();
    
    }
        /**
     * Consulta o Banco de Dados e atualiza o texto dos 3 cards superiores
     */
    private void carregarMetricasDoBanco() {
       // Consultas ajustadas para a estrutura real do teu banco de dados
        String sqlSkus = "SELECT COUNT(*) AS total FROM peca";
        String sqlEstoqueBaixo = "SELECT COUNT(*) AS total FROM peca WHERE quantidade <= estoque_minimo";
        String sqlVendasMes = "SELECT COALESCE(SUM(quantidade * preco_venda), 0) AS total FROM venda";

        try (Connection conn = Conexao.getConexao()) {

            // 1. Total de SKUs
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlSkus);
                 ResultSet rs1 = stmt1.executeQuery()) {
                if (rs1.next()) {
                    int totalSkus = rs1.getInt("total");
                    jLabel1.setText("<html><center>Total de SKUs<br/><b style='font-size:20px; color:#FF3333;'>" + totalSkus + "</b></center></html>");
                }
            }

            // 2. Estoque Baixo (Alertas)
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlEstoqueBaixo);
                 ResultSet rs2 = stmt2.executeQuery()) {
                if (rs2.next()) {
                    int totalBaixo = rs2.getInt("total");
                    jLabel2.setText("<html><center>Estoque Baixo<br/><b style='font-size:20px; color:#FF3333;'>" + totalBaixo + "</b></center></html>");
                }
            }

            // 3. Vendas do Mês (Calcula Quantidade x Preço de Venda)
            try (PreparedStatement stmt3 = conn.prepareStatement(sqlVendasMes);
                 ResultSet rs3 = stmt3.executeQuery()) {
                if (rs3.next()) {
                    double totalVendas = rs3.getDouble("total");
                    jLabel3.setText(String.format("<html><center>Vendas do Mês<br/><b style='font-size:18px; color:#FF3333;'>R$ %.2f</b></center></html>", totalVendas));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao carregar métricas do banco: " + e.getMessage());
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnEstoque = new javax.swing.JButton();
        btnFornecedores = new javax.swing.JButton();
        btnVendas = new javax.swing.JButton();
        btnMenuPrincipal = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        btnEstoque.setText("Peças em estoque");
        btnEstoque.addActionListener(this::btnEstoqueActionPerformed);

        btnFornecedores.setText("Fornecedores");
        btnFornecedores.addActionListener(this::btnFornecedoresActionPerformed);

        btnVendas.setText("Cadastro de vendas");
        btnVendas.addActionListener(this::btnVendasActionPerformed);

        btnMenuPrincipal.setText("Menu Principal");
        btnMenuPrincipal.addActionListener(this::btnMenuPrincipalActionPerformed);

        btnCompras.setText("Cadastro de compras ");
        btnCompras.addActionListener(this::btnComprasActionPerformed);

        btnSair.setBackground(new java.awt.Color(0, 0, 0));
        btnSair.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSair.setForeground(new java.awt.Color(255, 51, 51));
        btnSair.setText("Sair");
        btnSair.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSair.addActionListener(this::btnSairActionPerformed);

        jLabel1.setText("Total de SKU cadastrados 15");

        jLabel2.setText("Alerta de estoque baixo 20 ");

        jLabel3.setText("Vendas do Mes R$1000");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCompras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFornecedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMenuPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addGap(63, 63, 63)
                .addComponent(jLabel3)
                .addContainerGap(95, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(675, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenuPrincipal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEstoque)
                .addGap(18, 18, 18)
                .addComponent(btnFornecedores)
                .addGap(18, 18, 18)
                .addComponent(btnCompras)
                .addGap(18, 18, 18)
                .addComponent(btnVendas)
                .addGap(160, 160, 160))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(422, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        // Retorna para a tela de Login ao clicar em Sair
    Login login = new Login();
    login.setLocationRelativeTo(null);
    login.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnMenuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuPrincipalActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menu = new MenuPrincipal();
    menu.setVisible(true);
    this.dispose(); // Fecha a tela atual (ex: Estoque) e volta ao Menu
    }//GEN-LAST:event_btnMenuPrincipalActionPerformed

    private void btnEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstoqueActionPerformed
        // TODO add your handling code here:
        TelaEstoque estoque = new TelaEstoque();
    estoque.setLocationRelativeTo(null);
    estoque.setVisible(true);
    this.dispose(); // Fecha o Menu e abre o Estoque
    }//GEN-LAST:event_btnEstoqueActionPerformed

    private void btnFornecedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedoresActionPerformed
        // TODO add your handling code here:
            TelaFornecedores fornecedores = new TelaFornecedores();
    fornecedores.setLocationRelativeTo(null);
    fornecedores.setVisible(true);
    this.dispose();
        
    }//GEN-LAST:event_btnFornecedoresActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        // TODO add your handling code here:
        TelaCompras compras = new TelaCompras();
    compras.setLocationRelativeTo(null);
    compras.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVendasActionPerformed
        // TODO add your handling code here:
        TelaVendas vendas = new TelaVendas();
    vendas.setLocationRelativeTo(null);
    vendas.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnVendasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnEstoque;
    private javax.swing.JButton btnFornecedores;
    private javax.swing.JButton btnMenuPrincipal;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVendas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

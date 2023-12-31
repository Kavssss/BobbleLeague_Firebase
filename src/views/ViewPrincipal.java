package views;

import controller.ServiceController;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author Flavio Morais
 */
public class ViewPrincipal extends javax.swing.JFrame {

    ServiceController controller = new  ServiceController();
    
    public ViewPrincipal() {
        initComponents();
        fillCbPlayers();
        cbPlayer2.setSelectedIndex(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnInicio = new javax.swing.JButton();
        cbBestOf = new javax.swing.JComboBox<>();
        cbPlayer1 = new javax.swing.JComboBox<>();
        cbPlayer2 = new javax.swing.JComboBox<>();
        btnStatusMatch = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(478, 390));
        setResizable(false);
        getContentPane().setLayout(null);

        btnInicio.setBorderPainted(false);
        btnInicio.setContentAreaFilled(false);
        btnInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        getContentPane().add(btnInicio);
        btnInicio.setBounds(180, 307, 120, 40);

        cbBestOf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Scrimmage - 1", "Qualifier - 3", "Championship - 5"}));
        getContentPane().add(cbBestOf);
        cbBestOf.setBounds(230, 210, 160, 20);
        getContentPane().add(cbPlayer1);
        cbPlayer1.setBounds(230, 240, 160, 20);
        getContentPane().add(cbPlayer2);
        cbPlayer2.setBounds(230, 270, 160, 20);

        btnStatusMatch.setText("Status da Partida");
        btnStatusMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusMatchActionPerformed(evt);
            }
        });
        getContentPane().add(btnStatusMatch);
        btnStatusMatch.setBounds(330, 310, 130, 30);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/index.png"))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 0, 480, 362);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        int typeMatch = cbBestOf.getSelectedIndex() == 0 ? 1 : cbBestOf.getSelectedIndex() == 1 ? 3 : 5;
        int idPlayer1;
        int idPlayer2;
        
        idPlayer1 = controller.findIdByPlayer((String) cbPlayer1.getSelectedItem());
        idPlayer2 = controller.findIdByPlayer((String) cbPlayer2.getSelectedItem());
        
        if (idPlayer1 != idPlayer2) {
            new ViewPowers(controller.insertStartMatch(typeMatch, idPlayer1, idPlayer2),
                    typeMatch, idPlayer1, idPlayer2, LocalDateTime.now()).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Mesmos jogadores!");
        }
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnStatusMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusMatchActionPerformed
        new ViewBuscaMatches().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnStatusMatchActionPerformed

    private void fillCbPlayers() {
        for (String player : controller.findAllPlayers()) {
            cbPlayer1.addItem(player);
            cbPlayer2.addItem(player);
        }
    }
    
    
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ViewPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnStatusMatch;
    private javax.swing.JComboBox<String> cbBestOf;
    private javax.swing.JComboBox<String> cbPlayer1;
    private javax.swing.JComboBox<String> cbPlayer2;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}

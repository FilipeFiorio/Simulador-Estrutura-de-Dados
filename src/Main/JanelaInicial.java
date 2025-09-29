package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author filipe
 */
public class JanelaInicial extends javax.swing.JPanel {
  
    private Color padrao = new Color(0, 128, 0);
    private Color selecionado = new Color(144, 238, 144);
 
    public JanelaInicial() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JanelaInicial = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        botaoPilha = new javax.swing.JButton();
        botaoFila = new javax.swing.JButton();
        botaoDeque = new javax.swing.JButton();
        botaoLista = new javax.swing.JButton();

        JanelaInicial.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        JanelaInicial.setMaximumSize(new java.awt.Dimension(800, 600));
        JanelaInicial.setMinimumSize(new java.awt.Dimension(800, 600));
        JanelaInicial.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Simulador Estrutura de Dados");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel1.setText("Versão 1.0 - Desenvolvido por Filipe Roma Fiorio");

        botaoPilha.setBackground(new java.awt.Color(0, 128, 0));
        botaoPilha.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        botaoPilha.setForeground(new java.awt.Color(255, 255, 255));
        botaoPilha.setText("PILHA");
        botaoPilha.setBorderPainted(false);
        botaoPilha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoPilha.setFocusPainted(false);
        botaoPilha.setMaximumSize(new java.awt.Dimension(200, 60));
        botaoPilha.setMinimumSize(new java.awt.Dimension(200, 60));
        botaoPilha.setPreferredSize(new java.awt.Dimension(200, 60));
        botaoPilha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoPilhaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoPilhaMouseExited(evt);
            }
        });
        botaoPilha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPilhaActionPerformed(evt);
            }
        });

        botaoFila.setBackground(new java.awt.Color(0, 128, 0));
        botaoFila.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        botaoFila.setForeground(new java.awt.Color(255, 255, 255));
        botaoFila.setText("FILA");
        botaoFila.setBorderPainted(false);
        botaoFila.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFila.setFocusPainted(false);
        botaoFila.setMaximumSize(new java.awt.Dimension(200, 60));
        botaoFila.setMinimumSize(new java.awt.Dimension(200, 60));
        botaoFila.setPreferredSize(new java.awt.Dimension(200, 60));
        botaoFila.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoFilaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoFilaMouseExited(evt);
            }
        });
        botaoFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFilaActionPerformed(evt);
            }
        });

        botaoDeque.setBackground(new java.awt.Color(0, 128, 0));
        botaoDeque.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        botaoDeque.setForeground(new java.awt.Color(255, 255, 255));
        botaoDeque.setText("DEQUE");
        botaoDeque.setBorderPainted(false);
        botaoDeque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoDeque.setFocusPainted(false);
        botaoDeque.setMaximumSize(new java.awt.Dimension(200, 60));
        botaoDeque.setMinimumSize(new java.awt.Dimension(200, 60));
        botaoDeque.setPreferredSize(new java.awt.Dimension(200, 60));
        botaoDeque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoDequeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoDequeMouseExited(evt);
            }
        });
        botaoDeque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDequeActionPerformed(evt);
            }
        });

        botaoLista.setBackground(new java.awt.Color(0, 128, 0));
        botaoLista.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        botaoLista.setForeground(new java.awt.Color(255, 255, 255));
        botaoLista.setText("LISTA");
        botaoLista.setBorderPainted(false);
        botaoLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoLista.setFocusPainted(false);
        botaoLista.setMaximumSize(new java.awt.Dimension(200, 60));
        botaoLista.setMinimumSize(new java.awt.Dimension(200, 60));
        botaoLista.setPreferredSize(new java.awt.Dimension(200, 60));
        botaoLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoListaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoListaMouseExited(evt);
            }
        });
        botaoLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoListaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JanelaInicialLayout = new javax.swing.GroupLayout(JanelaInicial);
        JanelaInicial.setLayout(JanelaInicialLayout);
        JanelaInicialLayout.setHorizontalGroup(
            JanelaInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JanelaInicialLayout.createSequentialGroup()
                .addGroup(JanelaInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jLabel3))
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(botaoPilha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(botaoFila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(botaoDeque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JanelaInicialLayout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(botaoLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        JanelaInicialLayout.setVerticalGroup(
            JanelaInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JanelaInicialLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel3)
                .addGap(120, 120, 120)
                .addComponent(botaoPilha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoFila, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoDeque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JanelaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JanelaInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoPilhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPilhaActionPerformed
        inicializarSimualcao(new Pilha());
    }//GEN-LAST:event_botaoPilhaActionPerformed

    private void botaoPilhaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoPilhaMouseEntered
        botaoPilha.setBackground(selecionado);
    }//GEN-LAST:event_botaoPilhaMouseEntered

    private void botaoPilhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoPilhaMouseExited
        botaoPilha.setBackground(padrao);
    }//GEN-LAST:event_botaoPilhaMouseExited

    private void botaoFilaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoFilaMouseEntered
        botaoFila.setBackground(selecionado);
    }//GEN-LAST:event_botaoFilaMouseEntered

    private void botaoFilaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoFilaMouseExited
        botaoFila.setBackground(padrao);
    }//GEN-LAST:event_botaoFilaMouseExited

    private void botaoFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFilaActionPerformed
        inicializarSimualcao(new Fila());
    }//GEN-LAST:event_botaoFilaActionPerformed

    private void botaoDequeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoDequeMouseEntered
        botaoDeque.setBackground(selecionado);
    }//GEN-LAST:event_botaoDequeMouseEntered

    private void botaoDequeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoDequeMouseExited
        botaoDeque.setBackground(padrao);
    }//GEN-LAST:event_botaoDequeMouseExited

    private void botaoDequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDequeActionPerformed
        inicializarSimualcao(new Deque());
    }//GEN-LAST:event_botaoDequeActionPerformed

    private void botaoListaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoListaMouseEntered
        botaoLista.setBackground(selecionado);
    }//GEN-LAST:event_botaoListaMouseEntered

    private void botaoListaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botaoListaMouseExited
        botaoLista.setBackground(padrao);
    }//GEN-LAST:event_botaoListaMouseExited

    private void botaoListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoListaActionPerformed
        inicializarSimualcao(new Lista());
    }//GEN-LAST:event_botaoListaActionPerformed


    private void inicializarSimualcao(EngineFrame simulacao) {
        simulacao.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
           
        FlatMacLightLaf.setup();
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simulador Estrutura de Dados");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 620);

            frame.setContentPane(new JanelaInicial());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JanelaInicial;
    private javax.swing.JButton botaoDeque;
    private javax.swing.JButton botaoFila;
    private javax.swing.JButton botaoLista;
    private javax.swing.JButton botaoPilha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}

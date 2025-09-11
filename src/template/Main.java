package template;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author filipe
 */
public class Main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simulador Estrutura de Dados");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            frame.setContentPane(new JanelaInicial());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

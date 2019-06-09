import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame {

    public static void main(String[] args) {

        int width = 5;
        int height = 5;
        int bombs = 5;

        JFrame frame = new JFrame("Шестиугольный сапер");
        JPanel mainPanel = new JPanel();
        JLabel label;
        Game game = new Game(width, height, bombs);
        label = game.label;
        mainPanel.setLayout( new BorderLayout());
        mainPanel.add(BorderLayout.CENTER, game);
        mainPanel.add(BorderLayout.SOUTH, label);

        frame.setContentPane(mainPanel);

        frame.setSize(width * 30 + 30, (1 + 2*  height) * 20 + 60);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

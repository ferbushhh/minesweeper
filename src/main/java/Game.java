import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JPanel {
    private final int widthCell;
    private final int heightCell;

    private GameLogic gameLogic;
    JLabel label;

    public Game (final int widthCell, final int heightCell, int bombs) {
        this.widthCell = widthCell;
        this.heightCell = heightCell;
        gameLogic = new GameLogic(widthCell, heightCell, bombs);
        label = new JLabel();
        changeLabel();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int xAtMouse = e.getX();
                int yAtMouse = e.getY();
                Cell cell = null;
                for (int x = 0; x < widthCell; x++) {
                    for (int y = 0; y < heightCell; y++) {
                        if (gameLogic.cells[x][y].containsCell(xAtMouse, yAtMouse)) {
                            cell = gameLogic.cells[x][y];
                        }
                    }
                }
                if (cell != null) {
                    if (e.getButton() == MouseEvent.BUTTON1) //левая кнопка мыши
                    {
                        gameLogic.leftMouse(cell);
                        repaint();
                        changeLabel();
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3) //правая кнопка мыши
                    {
                        gameLogic.rightMouse(cell);
                        repaint();
                        changeLabel();
                    }
                }
            }
        });
    }

    public void changeLabel() {
        if (gameLogic.gameWin)
            label.setText("Вы выиграли!!");
        else if (gameLogic.endGame)
            label.setText("Вы проиграли :(");
        else
            label.setText("Осталось мин " + gameLogic.numBomb);
    }


    @Override
    public void paintComponent(Graphics g) {
        Font font = new Font ("SansSerif", Font.PLAIN, 48);
        g.setFont (font);
        for (int x = 0; x < widthCell; x++) {
            for (int y = 0; y < heightCell; y++) {
                try {
                    gameLogic.cells[x][y].paint(g);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

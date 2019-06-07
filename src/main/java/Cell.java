import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Cell {
    int cellX, cellY, middleX, middleY;
    int closeCell; // 1 - открыта, 0 - с флажочком, -1 - закрыта
    int bombCell; //-1 - бомба, 0-6 - кол-во рядом бомб
    Polygon field;

    public Cell (int cellX, int cellY, int middleX, int middleY, int radius) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.middleX = middleX;
        this.middleY = middleY;
        closeCell = -1;
        bombCell = 0;
        field = new Polygon();
        field.addPoint(middleX - radius, middleY); //левая точка
        field.addPoint(middleX - radius / 2, middleY - radius); // левая верхняя точка
        field.addPoint(middleX + radius / 2, middleY - radius); //правая верхняя точка
        field.addPoint(middleX + radius, middleY); //правая точка
        field.addPoint(middleX + radius / 2, middleY + radius); //правая нижняя точка
        field.addPoint(middleX - radius / 2, middleY + radius); //левая нижняя точка

    }

    public void paint(Graphics p) throws IOException {

        Color colorCell = Color.GRAY;
        if (closeCell == 1) {
            if (bombCell == -1)
                colorCell = Color.RED;
            else {
                colorCell = Color.magenta;
            }
        }
        else if (closeCell == 0) {
            colorCell = Color.CYAN;
        }
        p.setColor(colorCell);
        p.fillPolygon(field);
        p.setColor (Color.BLACK);
        p.drawPolygon (field);
        if (closeCell == 1 && bombCell >= 0)
            p.drawChars (new char[]{(char) ('0' + bombCell)}, 0, 1, middleX -10, middleY + 20);
        else if (closeCell == 1 && bombCell == -1)
            p.drawChars (new char[]{'*'}, 0, 1, middleX -10, middleY + 20);
        else if (closeCell == 0)
            p.drawChars (new char[]{'F'}, 0, 1, middleX -10, middleY + 20);
    }

    public boolean containsCell(int x, int y) {
        return field.contains(x, y);
    }
}

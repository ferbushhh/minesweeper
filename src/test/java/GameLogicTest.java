import org.junit.Assert;
import org.junit.Test;

public class GameLogicTest {

    @Test
    public void openZeroCell() {
        int cellX = 5;
        int cellY = 5;
        GameLogic gameTest = new GameLogic(cellX, cellY, 5);
        for (int y = 0; y < 5; y++) {
            gameTest.cells[3][y].bombCell = 1;
        }

        gameTest.openZeroCell(2, 2);

        int count = 0;
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++) {
                if (gameTest.cells[x][y].closeCell == 1) {
                    count++;
                }
            }

        Assert.assertEquals(20, count);

        GameLogic gameTestSecond = new GameLogic(cellX, cellY, 5);

        gameTestSecond.cells[1][1].bombCell = 1;
        gameTestSecond.cells[2][1].bombCell = 1;
        gameTestSecond.cells[3][1].bombCell = 1;
        gameTestSecond.cells[1][2].bombCell = 1;
        gameTestSecond.cells[2][3].bombCell = 1;
        gameTestSecond.cells[3][2].bombCell = 1;

        gameTestSecond.openZeroCell(0,0);

        int countSecond = 0;
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++) {
                if (gameTestSecond.cells[x][y].closeCell == 1) {
                    countSecond++;
                }
            }

        Assert.assertEquals(24, countSecond);
    }

    @Test
    public void placementOfMinesAndNumbers() {
        GameLogic game = new GameLogic(5, 5, 5);

        game.placementOfMinesAndNumbers(2, 2);

        boolean flag = true;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (game.cells[x][y].bombCell != -1) {
                    int count = 0;
                    if (y - 1 >= 0) { //клетка сверху
                        if (game.cells[x][y-1].bombCell == -1)
                            count++;
                    }

                    if (y + 1 < 5) { //клетка снизу
                        if (game.cells[x][y+1].bombCell == -1)
                            count++;
                    }

                    if (x % 2 == 0) { //если x - четный (расстановка по-разному)
                        if (x - 1 >= 0) {
                            if (y - 1 >= 0) {
                                if (game.cells[x-1][y-1].bombCell == -1)
                                    count++;
                            }
                            if (game.cells[x-1][y].bombCell == -1)
                                count++;
                        }
                        if (x + 1 < 5) {
                            if (y - 1 >= 0) {
                                if(game.cells[x+1][y-1].bombCell == -1)
                                    count++;
                            }
                            if (game.cells[x+1][y].bombCell == -1)
                                count++;
                        }
                    } else { //если x - нечетный
                        if (x - 1 >= 0) {
                            if (game.cells[x-1][y].bombCell == -1)
                                count++;
                            if (y + 1 < 5) {
                                if (game.cells[x-1][y+1].bombCell == -1)
                                    count++;
                            }
                        }
                        if (x + 1 < 5) {
                            if (game.cells[x+1][y].bombCell == -1)
                                count++;
                            if (y + 1 < 5) {
                                if (game.cells[x+1][y+1].bombCell == -1)
                                    count++;
                            }
                        }
                    }

                    if (count != game.cells[x][y].bombCell) {
                        flag = false;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(flag);
    }
}

public class GameLogic {

    private final int width;
    private final int height;
    private final int max_bomb;
    private final int allCells;
    private final int radius;
    private final int widthCell;
    private final int heightCell;
    public int numBomb;
    Cell cells[][];
    boolean endGame = false;
    boolean gameOn = false;
    boolean gameWin = false;
    int freeCell;

    public GameLogic (final int widthCell, final int heightCell, int bombs) {
        this.widthCell = widthCell;
        this.heightCell = heightCell;
        freeCell = widthCell * heightCell;
        cells = new Cell[widthCell][heightCell];
        width = widthCell * 10;
        height = heightCell * 10;
        max_bomb = bombs;
        numBomb = bombs;
        allCells = width * height;
        radius = 20;
        for (int x = 0; x < widthCell; x++) {
            for (int y = 0; y < heightCell; y++) {
                int middleX = radius + 3 * x * radius / 2;
                int middleY;
                if (x % 2 == 0) {
                    middleY = radius + 2 * radius * y;
                } else {
                    middleY = 2 * radius + y * 2 * radius;
                }
                cells[x][y] = new Cell(x, y, middleX, middleY, radius);
            }
        }
    }

    public void openZeroCell(int thisX, int thisY) {
        if (cells[thisX][thisY].closeCell == -1) {
            cells[thisX][thisY].closeCell = 1;
            if (cells[thisX][thisY].bombCell == 0) {
                if (thisY - 1 >= 0)
                    openZeroCell(thisX, thisY - 1);
                if (thisY + 1 < heightCell)
                    openZeroCell(thisX, thisY + 1);
                if (thisX % 2 == 0) {
                    if (thisX - 1 >= 0) {
                        if (thisY - 1 >= 0) {
                            openZeroCell(thisX-1,thisY-1);
                        }
                        openZeroCell(thisX - 1, thisY);
                    }
                    if (thisX + 1 < widthCell) {
                        if (thisY - 1 >= 0) {
                            openZeroCell(thisX+1, thisY - 1);
                        }
                        openZeroCell(thisX+1, thisY);
                    }
                } else { //если x - нечетный
                    if (thisX - 1 >= 0) {
                        openZeroCell(thisX - 1, thisY);
                        if (thisY + 1 < heightCell) {
                            openZeroCell(thisX - 1, thisY + 1);
                        }
                    }
                    if (thisX + 1 < widthCell) {
                        openZeroCell(thisX + 1, thisY);
                        if (thisY + 1 < heightCell) {
                            openZeroCell(thisX + 1, thisY + 1);
                        }
                    }
                }
            }
        }
    }

    public void placementOfMinesAndNumbers (int cellX, int cellY) {
        for (int i = 0; i < max_bomb;) {
            while (1==1) {
                int x = (int)(Math.random() * widthCell);
                int y = (int) (Math.random() * heightCell);
                if ((cellX != x || cellY != y) && cells[x][y].bombCell != -1) {
                    cells[x][y].bombCell = -1;
                    i++;
                    if (y - 1 >= 0) { //клетка сверху
                        if (cells[x][y-1].bombCell != -1)
                            cells[x][y-1].bombCell++;
                    }
                    if (y + 1 < heightCell) { //клетка снизу
                        if (cells[x][y+1].bombCell != -1)
                            cells[x][y+1].bombCell++;
                    }
                    if (x % 2 == 0) { //если x - четный (расстановка по-разному)
                        if (x - 1 >= 0) {
                            if (y - 1 >= 0) {
                                if (cells[x-1][y-1].bombCell != -1)
                                    cells[x-1][y-1].bombCell++;
                            }
                            if (cells[x-1][y].bombCell != -1)
                                cells[x-1][y].bombCell++;
                        }
                        if (x + 1 < widthCell) {
                            if (y - 1 >= 0) {
                                if(cells[x+1][y-1].bombCell != -1)
                                    cells[x+1][y-1].bombCell++;
                            }
                            if (cells[x+1][y].bombCell != -1)
                                cells[x+1][y].bombCell++;
                        }
                    } else { //если x - нечетный
                        if (x - 1 >= 0) {
                            if (cells[x-1][y].bombCell != -1)
                                cells[x-1][y].bombCell++;
                            if (y + 1 < heightCell) {
                                if (cells[x-1][y+1].bombCell != -1)
                                    cells[x-1][y+1].bombCell++;
                            }
                        }
                        if (x + 1 < widthCell) {
                            if (cells[x+1][y].bombCell != -1)
                                cells[x+1][y].bombCell++;
                            if (y + 1 < heightCell) {
                                if (cells[x+1][y+1].bombCell != -1)
                                    cells[x+1][y+1].bombCell++;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public void leftMouse(Cell cell) {
        if (!endGame && !gameWin) {
            if (!gameOn) {
                begginingOfGame(cell.cellX, cell.cellY);
            }
            if (cell.closeCell == -1 && cell.bombCell != -1) {
                freeCell--;
                openZeroCell(cell.cellX, cell.cellY);
            } else if (cell.bombCell == -1) {
                //System.exit(0);
                cell.closeCell = 1;
                endGame = true;
            }
        }
    }

    public void rightMouse(Cell cell) {
        if (!endGame && !gameWin) {
            if (!gameOn) {
                begginingOfGame(cell.cellX, cell.cellY);
            }
            if (cell.closeCell == -1) {
                cell.closeCell = 0;
                putFlag(cell);
            }
        }
    }

    public void putFlag(Cell cell) {
        if (cell.bombCell != -1) { //поставлен флажок не на бомбу - конец игры
            endGame = true;
        }
        else {
            numBomb--;
            if (numBomb == 0) {
                gameWin = true;
            }
        }
    }

    public void begginingOfGame(int x, int y) {
        gameOn = true;
        placementOfMinesAndNumbers(x, y);
    }
}

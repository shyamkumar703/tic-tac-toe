import edu.princeton.cs.introcs.StdDraw;
import java.util.Random;
import java.awt.Font;
public class GameEngine {
    Tile[][] tileArr;
    boolean turn = true;
    boolean gameOver = false;
    int winner = 3;
    Group horTop;
    Group horMid;
    Group horBot;
    Group vertLeft;
    Group vertMid;
    Group vertRight;
    Group diagLeft;
    Group diagRight;
    Group [] groups;
    boolean firstTurn = true;
    public GameEngine(int mode) {
        tileArr = new Tile[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tileArr[i][j] = new Tile(0.33333 * i, 0.33333 * (i + 1), 0.33333 * (j + 1),
                        0.33333 * j);
            }
        }
        this.createGroups();
        if (mode == 0) {
            this.gameLoop1v1();
        }
        if (mode == 1) {
            this.pVC();
        }
    }

    public Tile findTile(double x, double y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = tileArr[i][j];
                if (x > tile.xLeft && x < tile.xRight && y > tile.yBottom && y < tile.yTop) {
                    return tile;
                }
            }
        }
        return null;
    }

    public void createGroups() {
        horTop = new Group(new Tile[] {tileArr[0][2], tileArr[1][2], tileArr[2][2]});
        horMid = new Group(new Tile[] {tileArr[0][1], tileArr[1][1], tileArr[2][1]});
        horBot = new Group(new Tile[] {tileArr[0][0], tileArr[1][0], tileArr[2][0]});
        vertLeft = new Group(new Tile[] {tileArr[0][2], tileArr[0][1], tileArr[0][0]});
        vertMid = new Group(new Tile[] {tileArr[1][2], tileArr[1][1], tileArr[1][0]});
        vertRight = new Group(new Tile[] {tileArr[2][2], tileArr[2][1], tileArr[2][0]});
        diagLeft = new Group(new Tile[] {tileArr[0][2], tileArr[1][1], tileArr[2][0]});
        diagRight = new Group(new Tile[] {tileArr[2][2], tileArr[1][1], tileArr[0][0]});
        groups = new Group[] {horTop, horMid, horBot, vertLeft, vertMid, vertRight, diagLeft, diagRight};
    }

    public void gameLoop1v1() {
        while(true) {
            if (StdDraw.isMousePressed()) {
                double tileX = StdDraw.mouseX();
                double tileY = StdDraw.mouseY();
                Tile t = findTile(tileX, tileY);
                if (t.marked == false) {
                    t.markTile(turn);
                    t.marked = true;
                    if (turn) {
                        t.tileMark = 1;
                    } else {
                        t.tileMark = -1;
                    }
                    turn = !turn;
                }
                if (this.checkOver()) {
                    System.out.println(winner);
                    switch(winner) {
                        case 0: this.drawScreen();
                                break;
                        case 1: this.xWinScreen();
                                break;
                        case 2: this.oWinScreen();
                                break;
                    }
                    return;
                }
            }
        }
    }

    public void userTurn() {
        while (true) {
            if (gameOver) {
                return;
            }
            if (StdDraw.isMousePressed()) {
                double tileX = StdDraw.mouseX();
                double tileY = StdDraw.mouseY();
                Tile t = findTile(tileX, tileY);
                if (t.marked == false) {
                    t.markTile(turn);
                    t.marked = true;
                    t.tileMark = -1;
                    turn = !turn;
                }
                gameOver = checkOver();
                gameOverSequence();
                return;
            }
        }
    }

    public void pVC() {
        if (firstTurn) {
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
            Tile t = tileArr[1][1];
            t.markTile(turn);
            t.marked = true;
            t.tileMark = 1;
            firstTurn = !firstTurn;
            turn = !turn;
            this.userTurn();
            this.pVC();
        }
        while (!gameOver) {
            try{
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
            for (Group g: groups) {
                if (g.checkTwoSameOneEmpty()) {
                    g.emptyTile().markTile(turn);
                    g.emptyTile().marked = true;
                    g.emptyTile().tileMark = 1;
                    gameOver = checkOver();
                    gameOverSequence();
                }
            }
            for (Group g: groups) {
                if (g.oppCheckTwoSameOneEmpty()) {
                    g.emptyTile().markTile(turn);
                    g.emptyTile().marked = true;
                    g.emptyTile().tileMark = 1;
                    gameOver = checkOver();
                    turn = !turn;
                    gameOverSequence();
                    if (!gameOver) {
                        this.userTurn();
                        this.pVC();
                    }
                }
            }
            Random rand = new Random();
            int x = rand.nextInt(3);
            int y = rand.nextInt(3);
            Tile t = tileArr[x][y];
            while (t.marked) {
                x = rand.nextInt(3);
                y = rand.nextInt(3);
                t = tileArr[x][y];
            }
            t.markTile(turn);
            t.marked = true;
            t.tileMark = 1;
            turn = !turn;
            gameOver = checkOver();
            gameOverSequence();
            this.userTurn();
        }
    }

    public void gameOverSequence() {
        if (gameOver) {
            try{
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
            switch(winner) {
                case 0: this.drawScreen();
                    break;
                case 1: this.xWinScreen();
                    break;
                case 2: this.oWinScreen();
                    break;
                default: System.out.println("Error");
                    break;
            }
            try{
                Thread.sleep(100000);
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
            System.exit(0);
        }
    }

    public void xWinScreen() {
        StdDraw.setCanvasSize(400, 400);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 100, 100);
        StdDraw.setPenColor(StdDraw.BLUE);
        Font font = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "X WINS");
    }

    public void oWinScreen() {
        StdDraw.setCanvasSize(400, 400);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 100, 100);
        StdDraw.setPenColor(StdDraw.RED);
        Font font = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "O WINS");
    }

    public void drawScreen() {
        StdDraw.setCanvasSize(400, 400);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(0.5, 0.5, 100, 100);
        StdDraw.setPenColor(StdDraw.YELLOW);
        Font font = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.5, "DRAW");
    }

    public boolean checkOver() {
        Tile tile0 = tileArr[0][0];
        Tile tile1 = tileArr[0][1];
        Tile tile2 = tileArr[0][2];
        Tile tile3 = tileArr[1][0];
        Tile tile4 = tileArr[1][1];
        Tile tile5 = tileArr[1][2];
        Tile tile6 = tileArr[2][0];
        Tile tile7 = tileArr[2][1];
        Tile tile8 = tileArr[2][2];
        if (Math.abs(tile0.tileMark + tile1.tileMark + tile2.tileMark) == 3) {
            if (tile0.tileMark + tile1.tileMark + tile2.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile3.tileMark + tile4.tileMark + tile5.tileMark) == 3) {
            if (tile3.tileMark + tile4.tileMark + tile5.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile6.tileMark + tile7.tileMark + tile8.tileMark) == 3) {
            if (tile6.tileMark + tile7.tileMark + tile8.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile0.tileMark + tile3.tileMark + tile6.tileMark) == 3) {
            if (tile0.tileMark + tile3.tileMark + tile6.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile1.tileMark + tile4.tileMark + tile7.tileMark) == 3) {
            if (tile1.tileMark + tile4.tileMark + tile7.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile2.tileMark + tile5.tileMark + tile8.tileMark) == 3) {
            if (tile2.tileMark + tile5.tileMark + tile8.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile0.tileMark + tile4.tileMark + tile8.tileMark) == 3) {
            if (tile0.tileMark + tile4.tileMark + tile8.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (Math.abs(tile2.tileMark + tile4.tileMark + tile6.tileMark) == 3) {
            if (tile2.tileMark + tile4.tileMark + tile6.tileMark > 0) {
                winner = 1;
            } else {winner = 2;}
            return true;
        }
        if (tile0.marked && tile1.marked && tile2.marked && tile3.marked && tile4.marked && tile5.marked && tile6.marked
                && tile7.marked && tile8.marked) {
            winner = 0;
            return true;
        }
        return false;
    }
}

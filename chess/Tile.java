import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
public class Tile {
    double xLeft, xRight, yTop, yBottom;
    boolean marked;
    int tileMark;
    public Tile(double xLeft, double xRight, double yTop, double yBottom) {
        this.xLeft = xLeft;
        this.xRight = xRight;
        this.yTop = yTop;
        this.yBottom = yBottom;
        marked = false;
        tileMark = 0;
    }

    public void markTile(boolean turn) {
        double xCoor = xLeft + (0.33333 / 2);
        double yCoor = yBottom + (0.33333 / 2);
        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        if (turn) {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.text(xCoor, yCoor, "X");
        } else {
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(xCoor, yCoor, "O");
        }
    }
}

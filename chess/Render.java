import edu.princeton.cs.introcs.StdDraw;
public class Render {
    public static void initialize(int w, int h) {
        StdDraw.setCanvasSize(w, h);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0.33333, 0, 0.33333, 1);
        StdDraw.line(0.66666, 0, 0.66666, 1);
        StdDraw.line(0, 0.66666, 1, 0.66666);
        StdDraw.line(0, 0.33333, 1, 0.33333);
    }

    public static double[] findCoordinates(int x, int y){
        double yCoor = (0.33333 * y) + (0.33333 / 2);
        double xCoor = (0.33333 * x) + (0.33333 / 2);
        return new double[] {xCoor, yCoor};
    }

    public static void main(String[] args) {
        Render.initialize(1000, 1000);
        GameEngine ge = new GameEngine(1);
    }
}
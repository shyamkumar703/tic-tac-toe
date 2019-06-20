public class Group {
    Tile[] tileArr;
    boolean twoSameOneEmpty;
    boolean oppTwoSameOneEmpty;
    public Group(Tile[] tileArr) {
        this.tileArr = tileArr;
        twoSameOneEmpty = this.checkTwoSameOneEmpty();
        oppTwoSameOneEmpty = this.oppCheckTwoSameOneEmpty();
    }

    public boolean checkEmptySquare() {
        for (Tile t : tileArr) {
            if (t.tileMark == 0) {
                return true;
            }
        }
        return false;
    }

    public Tile emptyTile() {
        for (Tile t: tileArr) {
            if (t.tileMark == 0) {
                return t;
            }
        }
        return null;
    }

    public int tileSum() {
        int sum = 0;
        for (Tile t : tileArr) {
            sum += t.tileMark;
        }
        return sum;
    }

    public boolean checkTwoSameOneEmpty() {
        if (this.checkEmptySquare() && this.tileSum() == 2) {
            return true;
        }
        return false;
    }

    public boolean oppCheckTwoSameOneEmpty() {
        if (this.checkEmptySquare() && this.tileSum() == -2) {
            return true;
        }
        return false;
    }
}

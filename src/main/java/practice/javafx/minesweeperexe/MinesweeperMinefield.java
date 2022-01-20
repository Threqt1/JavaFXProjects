package practice.javafx.minesweeperexe;

public class MinesweeperMinefield {
    final MinesweeperTile[] minefield;
    int spanX;
    int spanY;
    int amountFlipped;
    final int amountOfFlags;

    MinesweeperMinefield(int x, int y, int numberOfFlags) {
        minefield = new MinesweeperTile[x * y];
        spanX = x;
        spanY = y;
        amountFlipped = 0;
        amountOfFlags = numberOfFlags;
    }

    public boolean isInGrid(int x, int y) {
        return (x >= 0 && x < spanX) || (y >= 0 && y < spanY);
    }

    public boolean isGameWon(int amountFlagged) {
        return amountFlipped == (spanX * spanY) - amountOfFlags && amountFlagged <= 0;
    }

    public int flag(int x, int y, int currentFlags) {
        MinesweeperTile tile = get(x, y);
        if (tile.isFlipped()) return 0;
        if (tile.isFlagged()) {
            tile.flag();
            return -1;
        } else if (currentFlags > 0) {
            tile.flag();
            return 1;
        }
        return 0;
    }

    public boolean flip(int x, int y) {
        MinesweeperTile tile = get(x, y);
        if (tile.isFlipped() || tile.isFlagged()) return true;
        tile.flip();
        if (tile.isBomb()) {
            return false;
        }
        amountFlipped++;
        if (tile.getAmountOfBombsNearby() == 0) {
            if (x - 1 >= 0) {
                flip(x - 1, y);
            }
            if (y - 1 >= 0) {
                flip(x, y - 1);
            }
            if (y + 1 < spanY) {
                flip(x, y + 1);
            }
            if (x + 1 < spanX) {
                flip(x + 1, y);
            }
        }
        return true;
    }

    public MinesweeperTile get(int x, int y) {
        return minefield[(x * spanY) + y];
    }

    public void set(int x, int y, MinesweeperTile tile) {
        minefield[(x * spanY) + y] = tile;
    }
}

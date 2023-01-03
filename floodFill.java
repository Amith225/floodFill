package floodFill;

public class floodFill {
    static final int FREE = 0;
    static final int WALL = 1;
    static final int START = 2;
    static final int END = 3;
    private int[][] grid;
    private final int width, height;
    private int xStart, yStart, xEnd, yEnd;

    public floodFill(int x, int y) {
        assert x >= 3 && y >= 3;
        this.width = x;
        this.height = y;
        reset();
    }

    public void reset() {
        this.xStart = this.xEnd = width / 2;
        this.yStart =  this.yEnd = height / 2;
        this.grid = new int[height][width];
        this.grid[yStart][xStart] = START;
        this.grid[yEnd][xEnd] = END;
        for (int i = 0; i < width; i++) grid[0][i] = grid[height - 1][i] = WALL;
        for (int i = 0; i < height; i++) grid[i][0] = grid[i][width - 1] = WALL;
    }

    public void display() {
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                if(grid[j][i] == WALL) System.out.print('#');
                else if(grid[j][i] == FREE) System.out.print('*');
                else if(grid[j][i] == START) System.out.print('S');
                else if(grid[j][i] == END) System.out.print('E');
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void setWall(int x, int y, char dir, int len) {
        assert (0 <= x && x < width) && (0 <= y && y < height);
        assert dir == 'x' || dir == 'y';
        if (dir == 'x') assert 0 <= x + len && x + len < width;
        else assert 0 <= y + len && y + len < height;
        if (dir == 'x') {
            for (int i = x; i < x + len; i += len > 0 ? 1 : 0) grid[y][i] = WALL;
        } else {
            for (int i = y; i < x + len; i += len > 0 ? 1 : 0) grid[y][i] = WALL;
        }
    }

    private void setPoints(int xStart, int yStart, int xEnd, int yEnd) {
        assert grid[yStart][xStart] != WALL;
        assert grid[yEnd][yStart] != WALL;

        grid[this.yStart][this.xStart] = FREE;
        grid[this.yEnd][this.xEnd] = FREE;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        grid[yStart][xStart] = START;
        grid[yEnd][xEnd] = END;
    }

    public void shortest(int xStart, int yStart, int xEnd, int yEnd) {
        setPoints(xStart, yStart, xEnd, yEnd);
        // todo: flood fill algo
    }
}

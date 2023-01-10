package floodFill.src;

public class FloodFill {
    static final int FREE = 0;
    static final int WALL = 1;
    static final int START = 2;
    static final int END = 3;
    private int[][] grid;
    private final int width, height;
    public int xStart, yStart, xEnd, yEnd;
    private String path;

    public FloodFill(int x, int y) {
        assert x >= 2 && y >= 2;
        this.width = x + 2;
        this.height = y + 2;
        reset();
    }

    public void reset() {
        this.grid = new int[height][width];
        for (int i = 0; i < width; i++) grid[0][i] = grid[height - 1][i] = WALL;
        for (int i = 0; i < height; i++) grid[i][0] = grid[i][width - 1] = WALL;
        this.path = "";
    }

    public void display() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (grid[j][i] == WALL) System.out.print('#');
                else if (grid[j][i] == FREE) System.out.print('*');
                else if (grid[j][i] == START) System.out.print('S');
                else if (grid[j][i] == END) System.out.print('E');
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public void set(int x, int y, int a) {
        x += 1;
        y += 1;
        assert (1 <= x && x < width - 1) && (1 <= y && y < height - 1);
        grid[y][x] = a;
    }

    public int get(int x, int y) {
        return grid[++y][++x];
    }

    public void setWall(int x, int y, char dir, int len) {
        x += 1;
        y += 1;
        assert (1 <= x && x < width - 1) && (1 <= y && y < height - 1);
        assert dir == 'x' || dir == 'y';
        if (dir == 'x') assert 1 <= x + len && x + len < width - 1;
        else assert 1 <= y + len && y + len < height - 1;
        if (dir == 'x') {
            for (int i = x; i < x + len; i += len > 0 ? 1 : 0) grid[y][i] = WALL;
        } else {
            for (int i = y; i < x + len; i += len > 0 ? 1 : 0) grid[y][i] = WALL;
        }
    }

    private void setPoints(int xStart, int yStart, int xEnd, int yEnd) {
        xStart += 1;
        yStart += 1;
        xEnd += 1;
        yEnd += 1;
        assert (1 <= xStart && xStart < width - 1) && (1 <= yStart && yStart < height - 1);
        assert (1 <= xEnd && xEnd < width - 1) && (1 <= yEnd && yEnd < height - 1);
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

    private void ffAlgo(int x, int y, String psf, boolean[][] visited) {
        if (visited[y][x] || grid[y][x] == WALL) return;
        else if (grid[y][x] == END) {
            this.path = psf;
            return;
        }
        int len = path.length();
        if (len != 0 && psf.length() >= len) return;
        visited[y][x] = true;
        ffAlgo(x - 1, y, psf + "l", visited);
        ffAlgo(x, y - 1, psf + "t", visited);
        ffAlgo(x, y + 1, psf + "d", visited);
        ffAlgo(x + 1, y, psf + "r", visited);
        visited[y][x] = false;
    }

    public int getX() {
        return xStart;
    }

    public int getY() {
        return yStart;
    }

    public String shortest() {
        this.path = "";
        boolean[][] visited = new boolean[height][width];
        ffAlgo(xStart, yStart, "", visited);
        System.out.println(":" + path);
        return this.path;
    }
}

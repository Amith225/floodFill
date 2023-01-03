import floodFill.floodFill;

public class Main {
    public static void main(String[] args) {
        floodFill ff = new floodFill(10, 10);
        ff.setWall(3, 3, 'x', 3);
        ff.display();
    }
}

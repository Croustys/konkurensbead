import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Room {
    private final int width;
    private final int height;
    private final Object[][] matrix;
    private final AtomicInteger playerCount = new AtomicInteger(0);

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new Object[width][height];
        initializeMatrix();
    }

    private void initializeMatrix() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Empty();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void printRoom() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("\u001B[0;0H");

        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        for (int i = 0; i < width; i++) {
            System.out.print("|");
            for (int j = 0; j < height; j++) {
                System.out.print(matrix[i][j].toString());
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

    public Object getObjectAtPosition(int x, int y) {
        return matrix[x][y];
    }

    public int getPlayerCount() {
        return playerCount.get();
    }

    public void placeObject(Object obj, int row, int col) {
        matrix[row][col] = obj;
        if (obj instanceof Player) {
            playerCount.incrementAndGet();
        }
    }

    public void moveObject(int fromX, int fromY, int toX, int toY) {
        matrix[toX][toY] = matrix[fromX][fromY];
        matrix[fromX][fromY] = new Empty();
    }

    public void removeObject(int row, int col) {
        Object obj = matrix[row][col];
        matrix[row][col] = new Empty();
        if (obj instanceof Player) {
            playerCount.decrementAndGet();
            ((Player) obj).isActive = false;
        }
    }

    public String getLastPlayerStanding() {
        String name = "";
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (matrix[i][j] instanceof Player) {
                    name = matrix[i][j].toString();
                    ((Player) matrix[i][j]).gameOver();
                }
            }
        }
        return name;
    }
}
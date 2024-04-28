public class Room {
    private int width;
    private int height;
    private final Object[][] matrix;
    private int playerCount = 0;

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
        //System.out.print("\u001B[0;0H");

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
        synchronized (matrix) {
            return playerCount;
        }
    }

    public void placeObject(Object obj, int row, int col) {
        synchronized (matrix) {
            matrix[row][col] = obj;
            if (obj instanceof Player) {
                playerCount++;
            }
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
            playerCount--;
        }
    }
}
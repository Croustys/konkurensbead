import java.util.Random;
class Player extends Thread {
    private static final int TIMEOUT = 500;

    private final Room room;
    private boolean isActive = true;
    private int X;
    private int Y;

    private final String name;

    public Player(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    @Override
    public void run() {
        Random random = new Random();
        this.X = random.nextInt(this.room.getWidth()-1);
        this.Y = random.nextInt(this.room.getHeight()-1);

        this.room.placeObject(this, this.X, this.Y);
        while(this.isActive) {
            try {
                sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move();
        }
        this.room.removeObject(this.X, this.Y);
    }

    private void move() {
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();
        Random random = new Random();

        int[][] directions = { {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1} };

        int[] direction = directions[random.nextInt(directions.length)];

        int newPlayerX = this.X + direction[0];
        int newPlayerY = this.Y + direction[1];

        boolean moved = false;

        if (newPlayerX >= 0 && newPlayerX < roomWidth && newPlayerY >= 0 && newPlayerY < roomHeight) {
            if (room.getObjectAtPosition(newPlayerX, newPlayerY) instanceof Empty) {
                room.moveObject(this.X, this.Y, newPlayerX, newPlayerY);
                this.X = newPlayerX;
                this.Y = newPlayerY;
                moved = true;
            }
        }
        if(moved && this.isBallNearby()) {
            this.kick();
        }
    }

    private boolean isBallNearby() {
        return false;
        /*
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();

        Object top = (this.Y > 0) ? this.room.getObjectAtPosition(this.X, this.Y - 1) : null;
        Object right = (this.X < roomWidth - 1) ? this.room.getObjectAtPosition(this.X + 1, this.Y) : null;
        Object bottom = (this.Y < roomHeight - 1) ? this.room.getObjectAtPosition(this.X, this.Y + 1) : null;
        Object left = (this.X > 0) ? this.room.getObjectAtPosition(this.X - 1, this.Y) : null;

        return (top instanceof Ball) || (right instanceof Ball) || (bottom instanceof Ball) || (left instanceof Ball);
         */
    }

    private void kick() {

    }

    @Override
    public String toString() {
        return name;
    }
}
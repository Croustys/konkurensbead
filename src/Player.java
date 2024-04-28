import java.util.Random;

class Player extends Thread {
    private static final int TIMEOUT = 100;

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
        this.X = random.nextInt(this.room.getWidth() - 1);
        this.Y = random.nextInt(this.room.getHeight() - 1);

        if (this.room.getObjectAtPosition(this.X, this.Y) instanceof Ball) {
            return;
        }
        this.room.placeObject(this, this.X, this.Y);
        while (this.isActive) {
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

        int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

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
        if (moved) {
            Object ball = this.getBallNearby();
            if (ball instanceof Ball && !((Ball) ball).isMoving()) {
                this.kick((Ball) ball);
            }
        }
    }

    private Object getBallNearby() {
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();

        Object top = (this.Y > 0) ? this.room.getObjectAtPosition(this.X, this.Y - 1) : null;
        if (top instanceof Ball) {
            return top;
        }
        Object right = (this.X < roomWidth - 1) ? this.room.getObjectAtPosition(this.X + 1, this.Y) : null;
        if (right instanceof Ball) {
            return right;
        }
        Object bottom = (this.Y < roomHeight - 1) ? this.room.getObjectAtPosition(this.X, this.Y + 1) : null;
        if (bottom instanceof Ball) {
            return bottom;
        }
        Object left = (this.X > 0) ? this.room.getObjectAtPosition(this.X - 1, this.Y) : null;
        if (left instanceof Ball) {
            return left;
        }

        return null;
    }

    private void kick(Ball b) {
        Random random = new Random();
        int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

        int[] direction = directions[random.nextInt(directions.length)];
        int directionX = direction[0];
        int directionY = direction[1];

        b.throwBall(directionX, directionY);
    }

    @Override
    public String toString() {
        return name;
    }
}
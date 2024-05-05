import java.util.Random;

class Player extends Thread {
    private static final int TIMEOUT = 100;
    private final Room room;
    public boolean isActive = true;
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
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();

        synchronized (room) {
            do {
                this.X = random.nextInt(roomWidth);
                this.Y = random.nextInt(roomHeight);
            } while (this.room.getObjectAtPosition(this.X, this.Y) instanceof Player || this.room.getObjectAtPosition(this.X, this.Y) instanceof Ball);

            this.room.placeObject(this, this.X, this.Y);
        }

        while (this.isActive) {
            try {
                sleep(TIMEOUT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.move();
        }
    }

    private void move() {
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();
        Random random = new Random();

        int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

        int[] direction = directions[random.nextInt(directions.length)];

        int newPlayerX = this.X + direction[0];
        int newPlayerY = this.Y + direction[1];

        if (newPlayerX >= 0 && newPlayerX < roomWidth && newPlayerY >= 0 && newPlayerY < roomHeight) {
            synchronized (room) {
                if (room.getObjectAtPosition(newPlayerX, newPlayerY) instanceof Empty) {
                    room.moveObject(this.X, this.Y, newPlayerX, newPlayerY);
                    this.X = newPlayerX;
                    this.Y = newPlayerY;
                    Object ball = this.getBallNearby();
                    if (ball instanceof Ball && !((Ball) ball).isMoving()) {
                        this.kick((Ball) ball);
                    }
                }
            }
        }
    }

    private Object getBallNearby() {
        synchronized (room) {
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
    }

    private void kick(Ball b) {
        synchronized (room) {
            int ballX = b.getX();
            int ballY = b.getY();

            Random random = new Random();
            int[][] directions = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};

            int randomIndex = random.nextInt(directions.length);
            int[] direction = directions[randomIndex];
            int newX = direction[0];
            int newY = direction[1];

            if (ballX + newX == this.X && ballY + newY == this.Y) {
                int oldRandom = randomIndex;
                do {
                    randomIndex = random.nextInt(directions.length);
                } while (randomIndex == oldRandom);
            }

            direction = directions[randomIndex];

            b.throwBall(direction[0], direction[1]);
        }
    }

    public void gameOver() {
        synchronized (room) {
            this.room.removeObject(this.X, this.Y);
            this.isActive = false;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
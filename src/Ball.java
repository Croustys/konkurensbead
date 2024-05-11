import java.util.concurrent.atomic.AtomicBoolean;

class Ball extends Thread {
    private static final int TIMEOUT = 50;
    private final Room room;
    private int X;
    private int Y;
    private int directionX;
    private int directionY;
    private static final AtomicBoolean isMoving = new AtomicBoolean(false);

    public Ball(Room room, int x, int y) {
        this.room = room;
        this.X = x;
        this.Y = y;
        this.directionX = 0;
        this.directionY = 0;
        this.room.placeObject(this, x, y);
    }

    @Override
    public void run() {
        while (this.room.getPlayerCount() > 1) {
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            if (isMoving.get()) {
                synchronized (room) {
                    int nextX = this.X + this.directionX;
                    int nextY = this.Y + this.directionY;
                    if (this.hasReachedWall() && invalidNextStep(nextX, nextY)) isMoving.set(false);
                    else {
                        Object nextPlace = this.room.getObjectAtPosition(nextX, nextY);
                        if (nextPlace instanceof Player) {
                            ((Player) nextPlace).gameOver();
                            isMoving.set(false);
                        } else {
                            this.room.moveObject(this.X, this.Y, nextX, nextY);
                            this.X = nextX;
                            this.Y = nextY;
                        }
                    }
                }
            }
        }
    }

    public boolean hasReachedWall() {
        int width = this.room.getWidth();
        int height = this.room.getHeight();
        return this.X == 0 || this.X == width - 1 || this.Y == 0 || this.Y == height - 1;
    }

    public boolean invalidNextStep(int nx, int ny) {
        int width = this.room.getWidth();
        int height = this.room.getHeight();
        return nx < 0 || ny < 0 || nx >= width || ny >= height;
    }

    public synchronized void throwBall(int directionX, int directionY) {
        if (isMoving.get()) return;
        this.directionX = directionX;
        this.directionY = directionY;
        isMoving.set(true);
    }

    public boolean isMoving() {
        return isMoving.get();
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }


    @Override
    public String toString() {
        return "o";
    }
}
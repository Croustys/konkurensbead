class Ball extends Thread {
    private static final int TIMEOUT = 50;

    private final Room room;
    private int X;
    private int Y;
    private int directionX;
    private int directionY;
    private boolean isMoving = false;

    public Ball(Room room, int x, int y) {
        this.room = room;
        this.X = x;
        this.Y = y;
        this.directionX = x;
        this.directionY = y;
        this.room.placeObject(this, x, y);
    }

    @Override
    public void run() {
        while (this.room.getPlayerCount() > 1) {
            try {
                sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isMoving) {
                synchronized (this.room) {
                    int nextX = this.X + this.directionX;
                    int nextY = this.Y + this.directionY;

                    int width = this.room.getWidth();
                    int height = this.room.getHeight();

                    if (nextX < 0 || nextX >= width || nextY < 0 || nextY >= height) {
                        this.isMoving = false;
                    } else {
                        if (this.room.getObjectAtPosition(nextX, nextY) instanceof Player) {
                            ((Player) this.room.getObjectAtPosition(nextX, nextY)).gameOver();
                            this.room.removeObject(nextX, nextY);
                            this.isMoving = false;
                        }
                        this.room.moveObject(this.X, this.Y, nextX, nextY);
                        this.X = nextX;
                        this.Y = nextY;
                    }
                }
            }

        }
    }

    public synchronized void throwBall(int directionX, int directionY) {
        synchronized (this.room) {
            this.directionX = directionX;
            this.directionY = directionY;
            isMoving = true;
        }
    }

    public boolean isMoving() {
        return isMoving;
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
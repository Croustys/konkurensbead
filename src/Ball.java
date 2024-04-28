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
                int width = this.room.getWidth();
                int height = this.room.getHeight();
                if (this.X == 0 || this.X == width - 1 || this.Y == 0 || this.Y == height - 1) {
                    this.isMoving = false;
                } else {
                    int nextX = this.X + this.directionX;
                    int nextY = this.Y + this.directionY;

                    if (this.room.getObjectAtPosition(nextX, nextY) instanceof Player) {
                        this.room.removeObject(nextX, nextY);
                        this.room.moveObject(this.X, this.Y, nextX, nextY);
                        this.X = nextX;
                        this.Y = nextY;
                    }
                }

            }

        }
    }

    public synchronized void throwBall(int directionX, int directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
        isMoving = true;
    }

    public boolean isMoving() {
        return isMoving;
    }


    @Override
    public String toString() {
        return "o";
    }
}
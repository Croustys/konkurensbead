class Ball extends Thread {
    private static final int TIMEOUT = 50;

    private final Room room;
    private int X;
    private int Y;
    private int directionX;
    private int directionY;
    private boolean isMoving;

    public Ball(Room room, int x, int y) {
        this.room = room;
        this.X = x;
        this.Y = y;
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

            if(isMoving) {
                // if touching wall, stop moving
            }

        }
    }

    public void throwBall(int directionX, int directionY) {
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
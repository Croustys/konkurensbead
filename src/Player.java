class Player extends Thread {
    private static final int ROOM_SIZE = 5;
    private static final int NUM_PLAYERS = 10;
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

    public void run() {
        this.X = 0;
        this.Y = 0;
        this.room.placeObject(this, this.X, this.Y);
        while(this.isActive) {
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move();
            if(this.isBallNearby()) {
                this.kick();
            }
        }
        this.room.removeObject(this.X, this.Y);
    }

    private void move() {

    }

    private boolean isBallNearby() {
        Object top = this.room.getObjectAtPosition(this.X, this.Y-1);
        Object right = this.room.getObjectAtPosition(this.X+1, this.Y);
        Object bottom = this.room.getObjectAtPosition(this.X, this.Y+1);
        Object left = this.room.getObjectAtPosition(this.X-1, this.Y);

        return (top instanceof Ball) || (right instanceof Ball) || (bottom instanceof Ball) || (left instanceof Ball);
    }

    private void kick() {

    }

    @Override
    public String toString() {
        return name;
    }
}
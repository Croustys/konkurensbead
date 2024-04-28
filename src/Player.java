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

    public void run() {
        Thread thread = new Thread(() -> {
            Random random = new Random();
            this.X = random.nextInt(this.room.getWidth());
            this.Y = random.nextInt(this.room.getHeight());

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
        });
        thread.start();
    }

    private void move() {

    }

    private boolean isBallNearby() {
        int roomWidth = this.room.getWidth();
        int roomHeight = this.room.getHeight();

        Object top = (this.Y > 0) ? this.room.getObjectAtPosition(this.X, this.Y - 1) : null;
        Object right = (this.X < roomWidth - 1) ? this.room.getObjectAtPosition(this.X + 1, this.Y) : null;
        Object bottom = (this.Y < roomHeight - 1) ? this.room.getObjectAtPosition(this.X, this.Y + 1) : null;
        Object left = (this.X > 0) ? this.room.getObjectAtPosition(this.X - 1, this.Y) : null;

        return (top instanceof Ball) || (right instanceof Ball) || (bottom instanceof Ball) || (left instanceof Ball);
    }

    private void kick() {

    }

    @Override
    public String toString() {
        return name;
    }
}
import java.util.ArrayList;

public class Game {

    private static final int TIMEOUT = 50;
    private static final int ROOM_WIDTH = 5;
    private static final int ROOM_HEIGHT = 5;
    private static final int INITIAL_PLAYER_COUNT = 10;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Room room;
    private Ball ball;
    public void main() {
        this.room = new Room(ROOM_WIDTH, ROOM_HEIGHT);
        this.ball = new Ball(room, 2, 2);
        for (int i = 0; i < INITIAL_PLAYER_COUNT; i++) {
            new Player(ALPHABET.substring(i, i + 1), room).start();
        }
        this.ball.start();

        while (room.getPlayerCount() > 1) {
            room.printRoom();
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        room.printRoom();
        System.out.println("Winner: " + this.room.getLastPlayerStanding());
    }
}

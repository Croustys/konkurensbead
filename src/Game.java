public class Game {
    private static final int TIMEOUT = 50;
    private static final int ROOM_WIDTH = 5;
    private static final int ROOM_HEIGHT = 5;
    private static final int INITIAL_PLAYER_COUNT = 10;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        Room room = new Room(ROOM_WIDTH, ROOM_HEIGHT);
        Ball ball = new Ball(room, ROOM_WIDTH / 2, ROOM_HEIGHT / 2);
        for (int i = 0; i < INITIAL_PLAYER_COUNT; i++) {
            new Player(ALPHABET.substring(i, i + 1), room).start();
        }
        ball.start();

        try {
            Thread.sleep(2 * TIMEOUT);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (room.getPlayerCount() > 1) {
            try {
                room.printRoom();
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        room.printRoom();
        System.out.println("Winner: " + room.getLastPlayerStanding());
    }
}

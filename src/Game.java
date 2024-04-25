import java.util.ArrayList;

public class Game {

    private static final int TIMEOUT = 50;
    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Room room;
    private Ball ball;
    private ArrayList<Player> players = new ArrayList<>();

    public void main() {
        this.room = new Room(10 ,10);
        this.ball = new Ball(room, 0, 0);
        for(int i = 0; i < 10; i++) {
            this.players.add(new Player(alphabet.substring(i, i+1),room));
        }

        while(room.getPlayerCount() > 1) {
            room.printRoom();
            try {
                Thread.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // print winner player's name
    }
}

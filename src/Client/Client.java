//Student: Litcan Nicolae-Gabirel
//Registration Number: 1903165

package Client;

import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;


//In our case, Player is the client
public class Client implements AutoCloseable {
    private final Scanner scanner;
    private final PrintWriter writer;

//  In this case, Client will be the Player
    public Client(int playerId) throws Exception {
//        Initialize connection
        int port = 8888;
        Socket socket = new Socket("localhost", port);

//       Creating a new PrintWriter and new Scanner in order
//       to write and display the requirements
        writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(playerId);

        scanner = new Scanner(socket.getInputStream());
        String line = scanner.nextLine();

        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
        alert(playerId);
    }

    public int alert(int playerId){
        return playerId;
    }

//    Method for getting all the Players' IDs.
    public int[] getIds() {
        writer.println("LIST_OF_IDS");

        int playersNumber = Integer.parseInt( scanner.nextLine());
        int[] players = new int[playersNumber];

        int i = 0;
        while (i < playersNumber) {
            players[i] = (Integer.parseInt(scanner.nextLine()));
            i++;
        }
        return players;
    }

    //    Method for checking if the current user has the ball or not
//        SEE: ClientProgram.java
//        Row: 23
    public int hasBall(int playerId){
        writer.println("HAS_BALL " + playerId);
        return Integer.parseInt(scanner.nextLine());
    }

    public int wait(int playerId){
        writer.println("WAIT " + playerId);
        return Integer.parseInt(scanner.nextLine());
    }

    public int removePlayer(int playerId){
        writer.println("REMOVE_PLAYER " + playerId);
        return Integer.parseInt(scanner.nextLine());
    }

// Method for displaying the player who has the ball
    public int ballPosition(){
        writer.println("BALL_POSITION");
        return Integer.parseInt(scanner.nextLine());
    }

//    Method for passing the ball around the players, we will take 2 parameters
//    Player 1 - thrower
//    Player 2 - receiver
    public void passAction(int thrower, int receiver) throws Exception {
        writer.println("PASS_ACTION " + thrower + " " + receiver);
        if (!(scanner.nextLine().trim().compareToIgnoreCase("success") == 0))
            throw new Exception(scanner.nextLine());
    }

//    Closing the scanner and writer
    @Override
    public void close() throws Exception {
        scanner.close();
        writer.close();
    }
}

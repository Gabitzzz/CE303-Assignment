//Student: Litcan Nicolae-Gabirel
//Registration Number: 1903165

package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Game game;

    public ClientHandler(Socket socket, Game game) {
        //        Instantiate the game and the socket
        this.socket = socket;
        this.game = game;


    }

    @Override
    public void run() {
//        We will use an integer variable for player ID
//        and a new List called idsList
        int playerId = 0;
        List<Integer> idsList;




        try (var scanner = new Scanner(socket.getInputStream());
             var writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {
//                we will initialise playerId with the user input
                playerId = Integer.parseInt(scanner.nextLine());
                System.out.println("Player " + playerId + " joined the game!");
//                New player will be added to the game and 
//                the list of IDs will be updated.
                game.addNewPlayer(playerId);
                idsList = game.idsList();
                System.out.println("Active players:" + idsList);
                writer.println("SUCCESS");
                do {
//                  I have worked in a similar way with
//                  the ClientHandle.java file from Lab 4.
//                  using a switch statement for each possible case
//                   this cases use the methods provided in Server/Game.java
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "list_of_ids":
                            idsList = game.idsList();
                            writer.println(idsList.size());
                            for (int id : idsList) {
                                writer.println(id);
                            }
                            break;

                        case "has_ball":
                            int id = Integer.parseInt(substrings[1]);
                            writer.println(game.hasBall(id) ? 1 : 0);
                            break;

                        case "new_player_connected":
                            writer.println("SUCCESS");
                            break;

                        case "ball_position":
                            writer.println(game.ballPosition());
                            break;

                        case "pass_action":
                            int playerA = Integer.parseInt(substrings[1]);
                            int playerB = Integer.parseInt(substrings[2]);
                            game.passAction(playerA, playerB);
                            writer.println("SUCCESS");
                            System.out.println("Ball passed from player " + playerA + " to player " + playerB);
                            break;

                        case "wait":
                            System.out.println("Wait for the ball");

                            break;

                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                } while (true);
            } catch (Exception e) {
                writer.println("Error: " + e.getMessage());
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                Create a boolean to check if the player has the Ball
                boolean had_ball = game.hasBall(playerId);
//                Player exits program
                game.removePlayer(playerId);
//                If the player that left the game had the ball
//                a different player will receive the ball.
                if (had_ball) {
                    System.out.println("Player " + playerId + " left the game.");
                    try {
                        int receiving_playerId = game.players.firstKey();
                        game.giveBall(receiving_playerId);
                        System.out.printf("The ball has been passed to Player %d after Player %d left the game.\n", receiving_playerId, playerId);
                    } catch (NoSuchElementException e) {
                        // no players left so ball is not given
                        System.out.println(e);
                    }
                } else {
                    System.out.printf("Player %d got removed (dind't have a ball)\n", playerId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            Active Players' IDs
            idsList = game.idsList();
            System.out.println("Active players: " + idsList);

            if (game.ballPosition() != 0)
                System.out.println("Ball is in possession of Player " + game.ballPosition());
        }
    }
}

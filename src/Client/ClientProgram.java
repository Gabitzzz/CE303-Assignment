//Student: Litcan Nicolae-Gabirel
//Registration Number: 1903165

package Client;


import Server.Game;

import java.util.Arrays;
import java.util.Scanner;

public class ClientProgram {

    public static void main(String[] args) {
        System.out.println("Enter your Player ID: ");
        try {
            Scanner in = new Scanner(System.in);
            int playerId = Integer.parseInt(in.nextLine());

            try (Client player = new Client(playerId)) {
                int[] playersIds;
                System.out.println("Your Player ID is " + playerId);
                playersIds = player.getIds();
                System.out.println("Active players in the game: " + Arrays.toString(playersIds));
                System.out.println("Player " + player.ballPosition() + " has the ball");

                do {

                    if (player.hasBall(playerId) == 1) {
                        System.out.println("You have the ball\nEnter player ID to pass ball to: ");
                        int receiver = Integer.parseInt(in.nextLine());
                        player.passAction(playerId, receiver);
                        System.out.println("Ball passed to player: " + receiver);
                    }
                } while (true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


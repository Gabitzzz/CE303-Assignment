//Student: Litcan Nicolae-Gabirel
//Registration Number: 1903165

package Server;

import java.util.*;

public class Game {
    public final TreeMap<Integer, Boolean> players = new TreeMap();

    //    Method for adding a new player
//    If the ID is taken, an exception will be thrown
    public void addNewPlayer(int playerId) throws Exception {
        for (Map.Entry<Integer, Boolean> entry : players.entrySet()) {
            if (entry.getKey().equals(playerId))
                throw new Exception("Player ID " + playerId + " is taken. Choose a different one!");
        }
        Boolean ok = (players.size() == 0);
        players.put(playerId, ok);
    }

    //  Method used when player exits and the ball is give to
//    the previous holder
    public void removePlayer(int playerId) throws Exception {
        players.remove(playerId);
    }

    public boolean hasBall(int playerId) {
        return Boolean.TRUE.equals( players.get(playerId) );
    }

    public void giveBall(int playerId) {
        if (!players.containsKey(playerId)) {
            //raise exception or something (because player doesn't exist)
        }
        if (!players.containsValue(true)) {
            // raise exception or something (because someone already has the ball
            // or
            // get player with the ball, set it to false
            // and don't raise exception (just give new player the ball)
        }
        players.put(playerId, true);
    }


    public int wait(int playerId) {
        if (players.get(playerId)) {
            return 1;
        }
        return 0;
    }

    public Integer ballPosition() {
        return players.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    public List<Integer> idsList() {
        return new ArrayList<>(players.keySet());
    }

    public void passAction(int thrower, int receiver) throws Exception {
        synchronized (players) {
            if (!players.containsKey(receiver)) {
                throw new Exception("Player " + receiver + " isn't in the game.");
            }
            players.put(thrower, false);
            players.put(receiver, true);
        }
    }

}

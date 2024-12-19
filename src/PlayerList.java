import java.util.ArrayList;
import java.util.Scanner;

public class PlayerList {
    private static ArrayList<Player> players ;//= new ArrayList<>(); // ensures only one copy of players

    public static void setPlayers(ArrayList<Player> playerList) // only call once in main
    {
        players = playerList;
    }

    public static ArrayList<Player> getPlayers() // use in any class that needs the database
    {
        return players;
    }

    public static boolean containsPlayer(String name)
    {
        name = name.toLowerCase().trim();
        for(int i=0;i<players.size();i++)
        {
            String s = players.get(i).getName().toLowerCase().trim();
            if(name.equals(s))
                return true;
        }

        return false;
    }

    public static void addPlayer(Player p)
    {
        players.add(p);
    }

    public static void printPlayers(ArrayList<Player> players)
    {
        for(int i=0;i<players.size();i++)
            System.out.println(players.get(i));
    }



}

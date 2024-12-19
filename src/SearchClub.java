import java.util.ArrayList;


public class SearchClub {
    // MAKE SEARCH BY MAX ... MORE GENERALISED ?

    public static ArrayList<Player> createClub(String clubName) // makes a list where all players are from the club named "clubName"
    {
        System.out.println(PlayerList.getPlayers());
        ArrayList<Player> players = new ArrayList<>(PlayerList.getPlayers()); // create a copy instead of assigning to avoid changing players array while searching

        //ArrayList<Player> searched = new ArrayList<>();
        clubName = clubName.toLowerCase().trim(); // MAKE IT PRIVATE?

        ArrayList<Player> club = new ArrayList<>();
        for(int i=0;i<players.size();i++)
        {
            String s = players.get(i).getClub().toLowerCase().trim();

            if(clubName.equals(s))
            {
                club.add(players.get(i));
            }
        }

        return club; // now we can work with a list that contains only a single club's players
    }


    public static ArrayList<Player> byMaxSalary(String clubName)
    {
        ArrayList<Player> players = new ArrayList<>(PlayerList.getPlayers()); // create a copy instead of assigning to avoid changing players array while searching
        ArrayList<Player> searched = new ArrayList<>();
        ArrayList<Player> club = createClub(clubName);

        if(club.isEmpty())
        {
            return searched;
        }

        else
        {
            int maxSalary = -1;
            for(int i=0;i<club.size();i++) // finding the max salary within the club
            {
                if(club.get(i).getWeeklySalary() > maxSalary)
                    maxSalary = club.get(i).getWeeklySalary();
            }

            for(int i=0;i<club.size();i++) // return the players with the max salary
            {
                if(club.get(i).getWeeklySalary() == maxSalary)
                    searched.add(club.get(i));
            }

            return searched;
        }

    }

    public static long totalClubSalary(String clubName)
    {
        ArrayList<Player> club = createClub(clubName);
        if(club.isEmpty())
        {
            return -1;
        }

        else
        {
            long total = 0;
            for(int i=0;i<club.size();i++)
            {
                total += club.get(i).getWeeklySalary();
            }

            return total*52;
        }

    }


    public static ArrayList<Player> byMaxAge(String clubName)
    {
        ArrayList<Player> searched = new ArrayList<>();
        ArrayList<Player> club = createClub(clubName);

        if(club.isEmpty())
        {
            return searched;
        }

        else
        {
            int maxAge = -1;
            for(int i=0;i<club.size();i++) // finding the max age within the club
            {
                if(club.get(i).getAge()> maxAge)
                    maxAge = club.get(i).getAge();
            }

            for(int i=0;i<club.size();i++) // return the players with the max age
            {
                if(club.get(i).getAge() == maxAge)
                    searched.add(club.get(i));
            }
            return searched;
        }

    }


    public static ArrayList<Player> byMaxHeight(String clubName)
    {
        ArrayList<Player> searched = new ArrayList<>();
        ArrayList<Player> club = createClub(clubName);

        if(club.isEmpty())
        {
            return searched;
        }

        else
        {
            double maxHeight = -1;
            for(int i=0;i<club.size();i++) // finding the max height within the club
            {
                if(club.get(i).getHeight() > maxHeight)
                    maxHeight = club.get(i).getHeight();
            }

            for(int i=0;i<club.size();i++) // print the players with the max height
            {
                if(club.get(i).getHeight() == maxHeight)
                    searched.add(club.get(i));
            }
            return searched;
        }

    }


}

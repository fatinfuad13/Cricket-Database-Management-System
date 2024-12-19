import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileOperations {

    private static final String INPUT_FILE_NAME = "src/players.txt";


    // Load players from the file and return them as a list
    public static ArrayList<Player> loadPlayersFromFile() {

        ArrayList<Player> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty())
                {  // Ensure the line is not empty
                    String[] details = line.split(",");
                    // System.out.println(details[7]);

                    if (details.length == 8 )
                    { // Ensure that there are 8 details (fields)
                        String name = details[0].trim();
                        String country = details[1].trim();
                        int age = Integer.parseInt(details[2].trim());
                        double height = Double.parseDouble(details[3].trim());
                        String club = details[4].trim();
                        String position = details[5].trim();

                        int number = 0;
                        if(details[6].trim().isEmpty())
                        {
                            number = 0;
                        }
                        else
                        {
                            number = Integer.parseInt(details[6].trim());
                        }
                        int weeklySalary =  Integer.parseInt(details[7].trim());


                        Player player = new Player(name, country, age, height, club, position, number, weeklySalary);
                        players.add(player);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return players;
    }

    public static void savePlayersToFile(ArrayList<Player> players) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE_NAME)))
        {
            // Loop through the list of players and write each player to the file
            for (Player player : players)
            {
                // Construct a string representation of each player (e.g., CSV format)
                String playerData = player.getName() + "," +
                        player.getCountry() + "," +
                        player.getAge() + "," +
                        player.getHeight() + "," +
                        player.getClub() + "," +
                        player.getPosition() + "," +
                        player.getNumber() + "," +
                        player.getWeeklySalary();

                // Write the player data followed by a newline
                writer.write(playerData);
                writer.newLine();
            }
            System.out.println("Players data has been successfully written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to the file.");
        }
    }



}

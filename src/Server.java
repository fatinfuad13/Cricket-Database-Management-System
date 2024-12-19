import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private static final int PORT = 1234; // Port to listen on
    private static  HashMap<String, String> userCredentials = new HashMap<>();
    // Map manager id to password
    private static ArrayList<Player> players = FileOperations.loadPlayersFromFile();// load players from input file
    private static HashMap<String, ArrayList<Player>> clubPlayers = new HashMap<>(); // Club to Players
    private static ArrayList<Player> transferMarket = new ArrayList<>();

    static
    {
        PlayerList.setPlayers(players);
        userCredentials.put("KolkataKnightRiders", "KKR");
        userCredentials.put("RoyalChallengersBangalore", "RCB");
        userCredentials.put("MumbaiIndians","MI");
        userCredentials.put("ChennaiSuperKings","CSK");
        userCredentials.put("DelhiCapitals","DC");
        userCredentials.put("RajasthanRoyals","RR");
        userCredentials.put("LucknowSuperGiants","LSG");
        userCredentials.put("GujaratTitans","GT");
        userCredentials.put("PunjabKings","PK");
        userCredentials.put("SunrisersHyderabad","SRH");

        /*ArrayList<Player> club = new ArrayList<>(SearchClub.createClub("Kolkata Knight Riders"));
        //System.out.println(SearchClub.createClub("Kolkata Knight Riders"));
        clubPlayers.put("Kolkata Knight Riders",club);

        club = SearchClub.createClub("Rajasthan Royals");
        clubPlayers.put("Rajasthan Royals",club);*/

        ArrayList<Player> club;

// Kolkata Knight Riders
        club = new ArrayList<>(SearchClub.createClub("Kolkata Knight Riders"));
        clubPlayers.put("Kolkata Knight Riders", club);

// Rajasthan Royals
        club = new ArrayList<>(SearchClub.createClub("Rajasthan Royals"));
        clubPlayers.put("Rajasthan Royals", club);

// Royal Challengers Bangalore
        club = new ArrayList<>(SearchClub.createClub("Royal Challengers Bangalore"));
        clubPlayers.put("Royal Challengers Bangalore", club);

// Mumbai Indians
        club = new ArrayList<>(SearchClub.createClub("Mumbai Indians"));
        clubPlayers.put("Mumbai Indians", club);

// Chennai Super Kings
        club = new ArrayList<>(SearchClub.createClub("Chennai Super Kings"));
        clubPlayers.put("Chennai Super Kings", club);

// Delhi Capitals
        club = new ArrayList<>(SearchClub.createClub("Delhi Capitals"));
        clubPlayers.put("Delhi Capitals", club);

// Lucknow Super Giants
        club = new ArrayList<>(SearchClub.createClub("Lucknow Super Giants"));
        clubPlayers.put("Lucknow Super Giants", club);

// Gujarat Titans
        club = new ArrayList<>(SearchClub.createClub("Gujarat Titans"));
        clubPlayers.put("Gujarat Titans", club);

// Punjab Kings
        club = new ArrayList<>(SearchClub.createClub("Punjab Kings"));
        clubPlayers.put("Punjab Kings", club);

// Sunrisers Hyderabad
        club = new ArrayList<>(SearchClub.createClub("Sunrisers Hyderabad"));
        clubPlayers.put("Sunrisers Hyderabad", club);


    }






    public static void main(String[] args) {
       // System.out.println(Server.clubPlayers);
       // System.out.println("Hello");
       /* ArrayList<Player> players = FileOperations.loadPlayersFromFile(); // load players from input file
        PlayerList.setPlayers(players); // load players into database from file*/
        System.out.println(Server.userCredentials);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accept client connection
                System.out.println("Connected to client: " + clientSocket.getInetAddress());

                // Create a thread to handle the client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ) {
                String request = in.readLine(); // Read the client request

                if (request.startsWith("LOGIN")) {
                    handleLogin(request, out);
                } else if (request.startsWith("GET_PLAYERS")) {
                    String[] parts = request.split(" ", 2);  // Split only on the first space
                    if (parts.length == 2) {
                        String club = parts[1].trim();  // Get the club name after the first space
                        System.out.println(club);  // Debugging line
                        handleGetPlayers(club, out);
                    } else {
                        out.println("Invalid request format. Club name missing.");
                    }



                } else if (request.startsWith("SELL_PLAYER")) {
                    String[] parts = request.split(" ", 3); // SELL_PLAYER club playerName
                    handleSellPlayer(parts[1], parts[2], out);
                } else if (request.equals("VIEW_TRANSFER_MARKET")) {
                    handleViewTransferMarket(out);
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleLogin(String request, PrintWriter out) {
            if (request.startsWith("LOGIN")) {
                String[] parts = request.split(" ");
                String username = parts[1].trim();
                String password = parts[2].trim();
                if (Server.userCredentials.containsKey(username) && Server.userCredentials.get(username).equals(password)) {
                    out.println("SUCCESS"); // Send response to client
                } else {
                    out.println("FAILURE");
                }
            }
        }

        private void handleGetPlayers(String club, PrintWriter out) {
            ArrayList<Player> players = Server.clubPlayers.get(club);
            if (players != null) {
                for (Player player : players) {
                    // Format player data as a single CSV line
                    String playerData = String.format("%s,%s,%d,%.2f,%s,%s,%d,%d",
                            player.getName(),
                            player.getCountry(),
                            player.getAge(),
                            player.getHeight(),
                            player.getClub(),
                            player.getPosition(),
                            player.getNumber(),
                            player.getWeeklySalary()
                    );
                    out.println(playerData); // Send each player's data as a single line
                }
            } else {
                out.println("No players found for this club.");
            }
            out.flush(); // Ensure the data is sent immediately
        }

        private void handleSellPlayer(String club, String playerName, PrintWriter out) {
            ArrayList<Player> players = Server.clubPlayers.get(club);
            if (players != null) {
                for (Player player : players) {
                    if (player.getName().equals(playerName)) {
                        Server.transferMarket.add(player);
                        players.remove(player);
                        out.println("Player " + playerName + " added to transfer market.");
                        return;
                    }
                }
            }
            out.println("Player not found.");
        }

        private void handleViewTransferMarket(PrintWriter out) {
            out.println("Transfer Market:");
            for (Player player : Server.transferMarket) {
                out.println(player);
            }
        }
    }






}


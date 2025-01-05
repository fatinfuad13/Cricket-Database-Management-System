import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private static final int PORT = 1234; // Port to listen on
    private static  HashMap<String, String> userCredentials = new HashMap<>();
    public static ArrayList<Player> players = FileOperations.loadPlayersFromFile();// load players from input file //// matobbori kortesi
    private static HashMap<String, ArrayList<Player>> clubPlayers = new HashMap<>(); // Club to Players
    private static ArrayList<Player> transferMarket = new ArrayList<>();
    private static HashMap<String, String> usernameToClubName = new HashMap<>();
    private static final String TRANSFER_MARKET_FILE = "src/TransferMarket.txt";



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

        usernameToClubName.put("KolkataKnightRiders", "Kolkata Knight Riders");
        usernameToClubName.put("RajasthanRoyals", "Rajasthan Royals");
        usernameToClubName.put("RoyalChallengersBangalore", "Royal Challengers Bangalore");
        usernameToClubName.put("MumbaiIndians", "Mumbai Indians");
        usernameToClubName.put("ChennaiSuperKings", "Chennai Super Kings");
        usernameToClubName.put("DelhiCapitals", "Delhi Capitals");
        usernameToClubName.put("LucknowSuperGiants", "Lucknow Super Giants");
        usernameToClubName.put("GujaratTitans", "Gujarat Titans");
        usernameToClubName.put("PunjabKings", "Punjab Kings");
        usernameToClubName.put("SunrisersHyderabad", "Sunrisers Hyderabad");

// Retrieve the club name
       // userName = usernameToClubName.getOrDefault(username, null);


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

        loadTransferMarket();
    }

    /*public static void updatePlayerList() {

        //players = FileOperations.loadPlayersFromFile();
        PlayerList.setPlayers(FileOperations.loadPlayersFromFile());

        ArrayList<Player> club;
        clubPlayers = new HashMap<>(); //dfshgherherherh

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


    }*/



    private static synchronized void saveTransferMarket() {
        System.out.println("Dhuksek");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSFER_MARKET_FILE))) {
            for (Player player : transferMarket)
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
        }
    }

    private static synchronized void loadTransferMarket() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSFER_MARKET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = Player.fromText(line);
                transferMarket.add(player);
            }
            System.out.println("Transfer market loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("Transfer market file not found. Starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown hook triggered. Saving transfer market...");
            saveTransferMarket(); // Ensure transfer market is saved
        })); /// asdghsdl;hgpsdogih

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
        } finally {
            System.out.println("Entering finally block...");
            saveTransferMarket(); // Save transfer market when server stops
            System.out.println("Saved!!!");
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
                }else if (request.startsWith("BUY_PLAYER")) {
                    String[] parts = request.split(" ", 3); // SELL_PLAYER club playerName
                    handleBuyPlayer(parts[1], parts[2], out);
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
            System.out.println("sending from server"+ players);
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
            System.out.println("Handling sales " + club);
            club = usernameToClubName.getOrDefault(club,null);
            ArrayList<Player> players = Server.clubPlayers.get(club);
            System.out.println("Handling sales" + players);
            System.out.println(playerName);
            if (players != null) {
                for (Player player : players) {
                    if (player.getName().equals(playerName)) {
                        System.out.println("hellobaby");
                        Server.transferMarket.add(player);
                        players.remove(player);
                        out.println("Player " + playerName + " added to transfer market.");
                        System.out.println(transferMarket);
                        return;
                    }
                }
            }
            out.println("Player not found.");
        }

        private void handleViewTransferMarket(PrintWriter out) {
            if (Server.transferMarket.isEmpty()) {
                out.println("No players available in the transfer market.");
            } else {
                for (Player player : Server.transferMarket) {
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
            }
            out.flush(); // Ensure the data is sent immediately
        }

        private static void handleBuyPlayer(String userName, String playerName, PrintWriter out) {
            Player playerToBuy = null;
            String club = usernameToClubName.getOrDefault(userName,null);
            System.out.println(club);
            // Check if the player exists in the transfer market
            String buyerClub = usernameToClubName.getOrDefault(userName, null);
            String sellerClub = null;
            System.out.println("BUYER "+ buyerClub);
            for (Player player : transferMarket) {
                if (player.getName().equals(playerName)) {
                    playerToBuy = player;
                    sellerClub = playerToBuy.getClub();
                    break;
                }
            }

            System.out.println(playerToBuy);

            if (playerToBuy != null) {

                transferMarket.remove(playerToBuy);  // remove from transfer market
                ArrayList<Player> players = PlayerList.getPlayers(); // get reference of OG list and modify there
                for(Player player: players)
                {

                    if(player.getName().equals(playerToBuy.getName()))
                    {
                        player.setClub(club); // change club of this player
                        System.out.println(player);

                        ArrayList<Player> buyerClubPlayers = Server.clubPlayers.getOrDefault(buyerClub, new ArrayList<>());
                        buyerClubPlayers.add(player); // Add player to buyer's club
                        Server.clubPlayers.put(buyerClub, buyerClubPlayers);

                        System.out.println("Buyer Club: "+Server.clubPlayers.getOrDefault(buyerClub,null));

                        if (sellerClub != null && Server.clubPlayers.containsKey(sellerClub)) {
                            ArrayList<Player> sellerClubPlayers = Server.clubPlayers.get(sellerClub);
                            sellerClubPlayers.remove(player); // Remove player from seller's club

                            for(int i=0;i<sellerClubPlayers.size();i++)
                            {
                                if(sellerClubPlayers.get(i).getName().equals(player.getName()))
                                    sellerClubPlayers.remove(sellerClubPlayers.get(i));
                            }



                            System.out.println("Seller players: dasfg"+sellerClubPlayers);
                            Server.clubPlayers.put(sellerClub, sellerClubPlayers);
                        }

                        System.out.println("SELLER: "+sellerClub);
                       // System.out.println("Seller Club:"+ Server.clubPlayers.getOrDefault(sellerClub,null));

                        break;
                    }
                    else
                    {
                        System.out.println("oopss");
                    }

                }

                System.out.println("Bought!!");
                //out.println("SUCCESS: You have successfully bought " + playerName + "!");
                String serializedPlayers = serializePlayerList(players);
                System.out.println("Ei je afet\n"+serializedPlayers);
                out.println("UPDATED_LIST: " + serializedPlayers);  // Send updated list to the client
                out.flush();

               PlayerList.setPlayers(players); /////dgfsgadfsg
                FileOperations.savePlayersToFile(PlayerList.getPlayers()); // update players.txt using the list of server *****
                /////gsadghjaopsdghsdtgh
            } else {
                // If the player doesn't exist in the transfer market
                out.println("FAILURE: Player " + playerName + " not found in the transfer market.");
            }
        }

        public static String serializePlayerList(ArrayList<Player> players) {
            StringBuilder serializedData = new StringBuilder();

            for (Player player : players) {
                System.out.println("Serializing Player: " + player); // Debug each player
                serializedData.append(player.getName()).append(",")
                        .append(player.getCountry()).append(",")
                        .append(player.getAge()).append(",")
                        .append(player.getHeight()).append(",")
                        .append(player.getClub()).append(",")
                        .append(player.getPosition()).append(",")
                        .append(player.getNumber()).append(",")
                        .append(player.getWeeklySalary()).append("|END|");
            }
            System.out.println("Serialized Data:\n" + serializedData);
            System.out.println(serializedData.toString());
            return serializedData.toString();
        }



    }






}


import com.sun.media.jfxmedia.events.PlayerEvent;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.print.event.PrintJobListener;


public class Menu {


    //ArrayList<Player> players = PlayerList.getPlayers(); // we assign here instead of making a copy
    private static String clubName = null;
    private static String userName = null;

    public static void loginMenu(Stage primaryStage) {

        VBox layout = new VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.getStyleClass().add("vbox-menu"); // CSS class for overall VBox

        // Title
        Label titleLabel = new Label("Login");
        titleLabel.getStyleClass().add("title-label"); // CSS class for title

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(10); // Set preferred width
        usernameField.getStyleClass().add("input-field"); // CSS class for input field

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(10); // Set preferred width
        passwordField.getStyleClass().add("input-field"); // CSS class for input field

        // Login button
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button"); // CSS class for button

        // Message label
        Label messageLabel = new Label();
        messageLabel.getStyleClass().add("message-label"); // CSS class for messages

        // Button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            System.out.println(username);
            System.out.println(password);
            ;

            boolean success = sendLoginRequest(username, password);

            if (success) {
                messageLabel.setText("Login Successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                userName = username;

                if (username.equals("KolkataKnightRiders"))
                    clubName = "Kolkata Knight Riders";
                else if (username.equals("RajasthanRoyals"))
                    clubName = "Rajasthan Royals";
                else if (username.equals("RoyalChallengersBangalore"))
                    clubName = "Royal Challengers Bangalore";
                else if (username.equals("MumbaiIndians"))
                    clubName = "Mumbai Indians";
                else if (username.equals("ChennaiSuperKings"))
                    clubName = "Chennai Super Kings";
                else if (username.equals("DelhiCapitals"))
                    clubName = "Delhi Capitals";
                else if (username.equals("LucknowSuperGiants"))
                    clubName = "Lucknow Super Giants";
                else if (username.equals("GujaratTitans"))
                    clubName = "Gujarat Titans";
                else if (username.equals("PunjabKings"))
                    clubName = "Punjab Kings";
                else if (username.equals("SunrisersHyderabad"))
                    clubName = "Sunrisers Hyderabad";
                else
                    clubName = null; // Optional: Handle unknown usernames


                mainMenu(primaryStage); // here
            } else {
                messageLabel.setText("Invalid Login!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Adding components to layout
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, messageLabel);

        // Set up the scene
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Link to CSS file
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client Login");
        primaryStage.show();
    }


    private static boolean sendLoginRequest(String username, String password) {
        try (Socket socket = new Socket("192.168.56.1", 1234);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send request to server
            out.println("LOGIN " + username + " " + password);
            String response = in.readLine();

            return response.equals("SUCCESS");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void mainMenu(Stage primaryStage) // here
    {

        VBox layout = new VBox(20); // Spacing between elements
        layout.setAlignment(Pos.CENTER); // Center align all children
        layout.getStyleClass().add("vbox-menu"); // Apply CSS styling

        // Title
        Label titleLabel = new Label("Main Menu");
        titleLabel.getStyleClass().add("title-label");

        // Buttons
        Button searchPlayersButton = new Button("Search Players");
        Button searchClubsButton = new Button("Search Clubs");
        Button addPlayerButton = new Button("Add Player");
        Button exitButton = new Button("Exit");

        Button viewPlayersButton = new Button("View My Players");
        Button sellPlayerButton = new Button("Sell Player");
        Button viewMarketButton = new Button("View Transfer Market");
        Button buyPlayerButton = new Button("Buy Player");

        // Add button CSS classes
        searchPlayersButton.getStyleClass().add("button");
        searchClubsButton.getStyleClass().add("button");
        addPlayerButton.getStyleClass().add("button");
        exitButton.getStyleClass().add("button");
        viewPlayersButton.getStyleClass().add("button");
        sellPlayerButton.getStyleClass().add("button");
        viewMarketButton.getStyleClass().add("button");
        buyPlayerButton.getStyleClass().add("button");

        // Button actions
        searchPlayersButton.setOnAction(e -> playerSearchMenu(primaryStage));
        searchClubsButton.setOnAction(e -> clubSearchMenu(primaryStage));
        addPlayerButton.setOnAction(e -> addPlayerMenu(primaryStage));
        exitButton.setOnAction(e -> {
            FileOperations.savePlayersToFile(PlayerList.getPlayers());
            primaryStage.close();
        });

        // Actions
        viewPlayersButton.setOnAction(e -> viewMyPlayers(primaryStage));
        sellPlayerButton.setOnAction(e -> sellPlayer(primaryStage));
        viewMarketButton.setOnAction(e -> viewTransferMarket(primaryStage));
        buyPlayerButton.setOnAction(e -> buyPlayer(primaryStage));

        // Add components to layout
        layout.getChildren().addAll(titleLabel, searchPlayersButton, searchClubsButton, addPlayerButton, viewPlayersButton, sellPlayerButton, viewMarketButton, buyPlayerButton, exitButton);

        // Create scene
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Add CSS file
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void viewMyPlayers(Stage primaryStage) {
       /*try (Socket socket = new Socket("192.168.56.1", 1234);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
           System.out.println("Menu user"+clubName);
           out.println("GET_PLAYERS " + clubName);
          // System.out.println("Entered Menu of viewMyPlayers");
           String response;
           ArrayList<Player> playerList = new ArrayList<>();
           while ((response = in.readLine()) != null) {
               System.out.println("Received response: " + response);  // Debugging line
               // Assuming each response contains player data
               // Parse the response into Player objects
               String[] details = response.split(",");
               if (details.length == 8) {
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
                   System.out.println("hehehafgd"+player);
                   playerList.add(player);
               }
           }*/
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>(PlayerList.getPlayers());
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getClub().equals(clubName))
                playerList.add(players.get(i));
        }

        System.out.println(playerList);
        // Create the TableView using the helper method
        TableView<Player> tableView = TableViewHelper.createPlayerTableView(playerList);

        // Create a VBox to hold the TableView
        VBox layout = new VBox(10);
        layout.getChildren().add(tableView);

        // Create a scene and stage to show the TableView
        Scene scene = new Scene(layout, 600, 400);  // Set width and height as needed
        Stage tableStage = new Stage();
        tableStage.setTitle("My Players");
        tableStage.setScene(scene);
        tableStage.show();

//    } catch(
//    IOException e)
//
//    {
//        e.printStackTrace();
//    }
}
    public static void sellPlayer(Stage primaryStage)
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter player name to sell:");
        dialog.showAndWait().ifPresent(playerName -> {
            try (Socket socket = new Socket("192.168.56.1", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("SELL_PLAYER "+userName+" "+ playerName);
                String response = in.readLine();
                // showAlert("Sell Player", response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void buyPlayer(Stage primaryStage)
    {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter player name to buy:");
        dialog.showAndWait().ifPresent(playerName -> {
            try (Socket socket = new Socket("192.168.56.1", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Send request to server to buy the player
                out.println("BUY_PLAYER " + userName + " " + playerName);

                // Wait for response from server
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    responseBuilder.append(line);
                }
                String response = responseBuilder.toString();
                System.out.println("Full response: " + response);



                if (response.startsWith("UPDATED_LIST:")) {
                    String serializedData = response.substring("UPDATED_LIST:".length());
                    String[] playerEntries = serializedData.split("\\|END\\|");
                    ArrayList<Player> deserializedPlayers = new ArrayList<>();

                    for (String playerEntry : playerEntries) {
                        if (!playerEntry.isBlank()) {
                            String[] fields = playerEntry.split(",");
                            Player player = new Player(
                                    fields[0],        // Name
                                    fields[1],        // Country
                                    Integer.parseInt(fields[2]),  // Age
                                    Double.parseDouble(fields[3]), // Height
                                    fields[4],        // Club
                                    fields[5],        // Position
                                    Integer.parseInt(fields[6]),  // Number
                                    Integer.parseInt(fields[7]) // Weekly Salary
                            );
                            deserializedPlayers.add(player);
                        }
                    }

                    // Replace the client-side player list
                    PlayerList.setPlayers(deserializedPlayers);

                    System.out.println("Updated Player List Received by Client:\n" + deserializedPlayers);
                }

            } catch (IOException e) {
                e.printStackTrace();
                //showAlert("Error", "Unable to connect to the server.");
            }
        });

        System.out.println("After buying in client side\n"+PlayerList.getPlayers());
        FileOperations.savePlayersToFile(PlayerList.getPlayers()); // SAVE AFTER BUYING IMMEDIATELY
    }

    public static ArrayList<Player> deserializePlayerList(String serializedData) {
        ArrayList<Player> players = new ArrayList<>();

        // Split the data by lines
        String[] lines = serializedData.split("\n");
       // String[] playerLines = serializedData.split("\n");
        for (String playerline : lines) {
            System.out.println("Parsing Line: " + playerline); // Debug each line
        }

        for (String line : lines) {
            String[] parts = line.split(",");  // Split by commas

            if (parts.length == 8) {
                String name = parts[0].trim();
                String country = parts[1].trim();
                int age = Integer.parseInt(parts[2].trim());
                double height = Double.parseDouble(parts[3].trim());
                String club = parts[4].trim();
                String position = parts[5].trim();
                int number = Integer.parseInt(parts[6].trim());
                int weeklySalary = Integer.parseInt(parts[7].trim());

                // Create a new player
                Player player = new Player(name, country, age, height, club, position, number, weeklySalary);
                players.add(player);
            }
        }

        return players;
    }


    public static void viewTransferMarket(Stage primaryStage)
    {
        try (Socket socket = new Socket("192.168.56.1", 1234);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("VIEW_TRANSFER_MARKET");
            String response;
            StringBuilder market = new StringBuilder();

            // Read the transfer market data from the server
            while ((response = in.readLine()) != null) {
                market.append(response).append("\n");
            }

            // Show the market data in a dialog box
            Alert marketAlert = new Alert(Alert.AlertType.INFORMATION);
            marketAlert.setTitle("Transfer Market");
            marketAlert.setHeaderText("Available Players in Transfer Market");
            marketAlert.setContentText(market.toString());
            marketAlert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            // Show an error dialog if there is an issue with the connection
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Unable to connect to the server");
            errorAlert.setContentText("Please try again later.");
            errorAlert.showAndWait();
        }

    }

    public static void playerSearchMenu(Stage primaryStage) {
        VBox layout = new VBox(20); // Spacing of 20 between elements
        layout.setAlignment(Pos.CENTER); // Center align all elements
        layout.getStyleClass().add("vbox-menu"); // Apply the "vbox-menu" CSS styling for consistent menu design

        // Title Label
        Label instructions = new Label("Select a Search Option:");
        instructions.getStyleClass().add("title-label"); // Use CSS class for title labels

        // Buttons for search options
        Button searchByNameButton = new Button("Search by Player Name");
        searchByNameButton.getStyleClass().add("menu-button");

        Button searchByClubCountryButton = new Button("Search by Club and Country");
        searchByClubCountryButton.getStyleClass().add("menu-button");

        Button searchByPositionButton = new Button("Search by Position");
        searchByPositionButton.getStyleClass().add("menu-button");

        Button searchBySalaryRangeButton = new Button("Search by Salary Range");
        searchBySalaryRangeButton.getStyleClass().add("menu-button");

        Button countryWiseCountButton = new Button("Country-wise Player Count");
        countryWiseCountButton.getStyleClass().add("menu-button");

        Button backButton = new Button("Back to Main Menu");
        backButton.getStyleClass().add("menu-button");

        // Add event handlers for each button
        searchByNameButton.setOnAction(e -> searchByName(primaryStage));
        searchByClubCountryButton.setOnAction(e -> searchByClubAndCountry(primaryStage));
        searchByPositionButton.setOnAction(e -> searchByPosition(primaryStage));
        searchBySalaryRangeButton.setOnAction(e -> searchBySalaryRange(primaryStage));
        countryWiseCountButton.setOnAction(e -> showCountryWiseCount(primaryStage));
        backButton.setOnAction(e -> mainMenu(primaryStage));

        // Add all components to the layout
        layout.getChildren().addAll(
                instructions,
                searchByNameButton,
                searchByClubCountryButton,
                searchByPositionButton,
                searchBySalaryRangeButton,
                countryWiseCountButton,
                backButton
        );

        // Create and set the scene
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Link the stylesheet
        primaryStage.setTitle("Player Search Menu");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private static void searchByName(Stage primaryStage) {

            VBox layout = new VBox(20); // Increased spacing for better appearance
            layout.setAlignment(Pos.CENTER); // Center align all elements
            layout.getStyleClass().add("vbox-menu"); // Apply CSS styling for uniform layout

            // Label for player name prompt
            Label prompt = new Label("Enter Player Name:");
            prompt.getStyleClass().add("title-label"); // Styled as a title or prompt

            // TextField for input
            TextField nameField = new TextField();
            nameField.setPromptText("Type player name here...");
            nameField.getStyleClass().add("text-field");

            // Buttons
            Button searchButton = new Button("Search");
            searchButton.getStyleClass().add("menu-button");

            Button backButton = new Button("Back");
            backButton.getStyleClass().add("menu-button");

            // Create a placeholder TableView using TableViewHelper
            TableView<Player> tableView = TableViewHelper.createPlayerTableView(new ArrayList<>()); // Empty initially

            // Event handling for the search button
            searchButton.setOnAction(e -> {
                String name = nameField.getText().trim();
                ArrayList<Player> searchResult = SearchPlayer.byName(name); // Get search results

                // If search result is empty, we clear the table. Otherwise, we populate it.
                if (searchResult.isEmpty()) {
                    tableView.setItems(FXCollections.observableArrayList());
                } else {
                    tableView.setItems(FXCollections.observableArrayList(searchResult)); // Add results to table
                }
            });

            // Event handling for the back button
            backButton.setOnAction(e -> playerSearchMenu(primaryStage));

            // Add all elements to the layout
            layout.getChildren().addAll(prompt, nameField, searchButton, tableView, backButton);

            // Create and set the scene
            Scene scene = new Scene(layout, 1000, 700);
            scene.getStylesheets().add("styles.css"); // Attach stylesheet
            primaryStage.setTitle("Search by Player Name");
            primaryStage.setScene(scene);
            primaryStage.show();
        }



    private static void searchByClubAndCountry(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox-menu");
        layout.setPadding(new Insets(10));

        Label clubPrompt = new Label("Enter club name:");
        TextField clubField = new TextField();

        Label countryPrompt = new Label("Enter country name:");
        TextField countryField = new TextField();

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button"); // Adding CSS class

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button"); // Adding CSS class

        TableView<Player> tableView = TableViewHelper.createPlayerTableView(new ArrayList<>());

        searchButton.setOnAction(e -> {
            String club = clubField.getText().trim();
            String country = countryField.getText().trim();
            ArrayList<Player> searchResult = SearchPlayer.byClubCountry(club, country);
            if (searchResult.isEmpty()) {
                tableView.setItems(FXCollections.observableArrayList());
            } else {
                tableView.setItems(FXCollections.observableArrayList(searchResult));
            }
        });

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(clubPrompt, clubField, countryPrompt, countryField, searchButton, tableView, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Add the stylesheet
        primaryStage.setTitle("Search by Club and Country");
        primaryStage.setScene(scene);
    }

    private static void showCountryWiseCount(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox-menu");
        layout.setPadding(new Insets(10));

        Label resultLabel = new Label("Country-wise Player Count:");
        resultLabel.getStyleClass().add("label");  // Adding CSS class

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.getStyleClass().add("text-area");  // Adding CSS class

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");  // Adding CSS class

        HashMap<String, Integer> map = SearchPlayer.countryWiseCount();
        StringBuilder resultText = new StringBuilder();
        map.forEach((key, value) -> resultText.append("Country: ").append(key).append(", Players: ").append(value).append("\n"));

        resultArea.setText(resultText.toString());

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(resultLabel, resultArea, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css");  // Add the stylesheet
        primaryStage.setTitle("Country-wise Player Count");
        primaryStage.setScene(scene);
    }

    private static void searchByPosition(Stage primaryStage){

        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox-menu");
        layout.setPadding(new Insets(10));

        Label label = new Label("Search player by position:");
        label.getStyleClass().add("label");  // Adding CSS class

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button");  // Adding CSS class

        Button backButton = new Button("Back to Player Search Menu:");
        backButton.getStyleClass().add("button");  // Adding CSS class

        Label positionPrompt = new Label("Enter position of Player: ");
        positionPrompt.getStyleClass().add("label");  // Adding CSS class
        TextField positionField = new TextField();
        positionField.getStyleClass().add("text-field");  // Adding CSS class

        TableView<Player> tableView = TableViewHelper.createPlayerTableView(new ArrayList<>());

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        searchButton.setOnAction(e -> {
            String position = positionField.getText().trim();
            ArrayList<Player> searchResult = SearchPlayer.byPosition(position);
            if (searchResult.isEmpty()) {
                tableView.setItems(FXCollections.observableArrayList());
            } else {
                tableView.setItems(FXCollections.observableArrayList(searchResult));
            }
        });

        layout.getChildren().addAll(positionPrompt, positionField, searchButton, tableView, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css");  // Add the stylesheet
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static void searchBySalaryRange(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox-menu");
        layout.setPadding(new Insets(10));

        Label lowerRangePrompt = new Label("Enter lower salary range:");
        lowerRangePrompt.getStyleClass().add("label");  // Adding CSS class
        TextField lowerRangeField = new TextField();
        lowerRangeField.getStyleClass().add("text-field");  // Adding CSS class

        Label upperRangePrompt = new Label("Enter upper salary range:");
        upperRangePrompt.getStyleClass().add("label");  // Adding CSS class
        TextField upperRangeField = new TextField();
        upperRangeField.getStyleClass().add("text-field");  // Adding CSS class

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("button");  // Adding CSS class

        Button backButton = new Button("Back");
        backButton.getStyleClass().add("button");  // Adding CSS class

        TableView<Player> tableView = TableViewHelper.createPlayerTableView(new ArrayList<>());

        searchButton.setOnAction(e -> {
            try {
                int low = Integer.parseInt(lowerRangeField.getText().trim());
                int high = Integer.parseInt(upperRangeField.getText().trim());

                ArrayList<Player> searchResult = SearchPlayer.bySalaryRange(low, high);
                if (searchResult.isEmpty()) {
                    tableView.setItems(FXCollections.observableArrayList());
                } else {
                    tableView.setItems(FXCollections.observableArrayList(searchResult));
                }
            } catch (NumberFormatException ex) {
                tableView.setItems(FXCollections.observableArrayList());
            }
        });

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(lowerRangePrompt, lowerRangeField, upperRangePrompt, upperRangeField, searchButton, tableView, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css");  // Add the stylesheet
        primaryStage.setTitle("Search by Salary Range");
        primaryStage.setScene(scene);
    }





    /// ////////// /////
    public static void clubSearchMenu(Stage primaryStage)
    {


       VBox layout = new VBox(20);
       layout.setPadding(new Insets(20));
       layout.setAlignment(Pos.CENTER);
       layout.getStyleClass().add("vbox-menu");


       Button byMaxSalaryButton = new Button("By Max Salary");
       Button byMaxAgeButton = new Button("By Max Age");
       Button byMaxHeightButton = new Button("By Max Height");
       Button totalYearlySalaryButton = new Button("Total Salary (Yearly)");
       Button backButton = new Button("Back");

       byMaxSalaryButton.getStyleClass().add("button");
       byMaxAgeButton.getStyleClass().add("button");
       byMaxHeightButton.getStyleClass().add("button");
       totalYearlySalaryButton.getStyleClass().add("button");
       backButton.getStyleClass().add("button");



        Label prompt = new Label("Enter name of the club: ");
        TextField textField = new TextField();
        TableView<Player> tableView = TableViewHelper.createPlayerTableView(new ArrayList<>()); // initially empty

        prompt.getStyleClass().add("label");

        TextArea totalSalary = new TextArea();
        totalSalary.setEditable(false);


        byMaxSalaryButton.setOnAction(e -> {
           totalSalary.setText("");   // clear total salary screen from before
        String club = textField.getText().trim();
        ArrayList<Player> searchResult = SearchClub.byMaxSalary(club);
        if(searchResult.isEmpty()){
            tableView.setItems(FXCollections.observableArrayList());
        }
        else{
            tableView.setItems(FXCollections.observableArrayList(searchResult));
        }

       });

       byMaxAgeButton.setOnAction(e -> {
           totalSalary.setText("");
           String club = textField.getText().trim();
           ArrayList<Player> searchResult = SearchClub.byMaxAge(club);
           if(searchResult.isEmpty()){
               tableView.setItems(FXCollections.observableArrayList());
           }
           else{
               tableView.setItems(FXCollections.observableArrayList(searchResult));
           }
       });

       byMaxHeightButton.setOnAction(e -> {
           totalSalary.setText("");
           String club = textField.getText().trim();
           ArrayList<Player> searchResult = SearchClub.byMaxHeight(club);
           if(searchResult.isEmpty()){
               tableView.setItems(FXCollections.observableArrayList());
           }
           else{
               tableView.setItems(FXCollections.observableArrayList(searchResult));
           }
       });

       totalYearlySalaryButton.setOnAction(e -> {
           tableView.setItems(FXCollections.observableArrayList());    // clear table from before
        String club = textField.getText().trim();
        long total = SearchClub.totalClubSalary(club);
        totalSalary.setText("Total Salary of the club is "+total);

       });

       backButton.setOnAction(e -> mainMenu(primaryStage));

       layout.getChildren().addAll(byMaxSalaryButton,byMaxAgeButton,byMaxHeightButton,totalYearlySalaryButton,prompt,textField,tableView,totalSalary,backButton);

       Scene scene = new Scene(layout,1000,700);
       scene.getStylesheets().add("styles.css");
       primaryStage.setTitle("Club Search Menu");
       primaryStage.setScene(scene);
       primaryStage.show();

    }

    public static void addPlayerMenu(Stage primaryStage) {


            VBox layout = new VBox(20); // Increased spacing for better appearance
            layout.getStyleClass().add("vbox-menu");
            layout.setPadding(new Insets(20));

            // Labels and input fields with consistent styling
            Label nameLabel = new Label("Enter name of the player:");
            TextField nameField = new TextField();
            nameLabel.getStyleClass().add("label");

            Label countryLabel = new Label("Enter country of the player:");
            TextField countryField = new TextField();
            countryLabel.getStyleClass().add("label");

            Label clubLabel = new Label("Enter club of the player:");
            TextField clubField = new TextField();
            clubLabel.getStyleClass().add("label");

            Label positionLabel = new Label("Enter position of the player:");
            TextField positionField = new TextField();
            positionLabel.getStyleClass().add("label");

            Label ageLabel = new Label("Enter age of the player (in years):");
            TextField ageField = new TextField();
            ageLabel.getStyleClass().add("label");

            Label heightLabel = new Label("Enter height of the player (in m):");
            TextField heightField = new TextField();
            heightLabel.getStyleClass().add("label");

            Label numberLabel = new Label("Enter jersey number of the player (press Enter to skip):");
            TextField numberField = new TextField();
            numberLabel.getStyleClass().add("label");

            Label salaryLabel = new Label("Enter weekly salary of the player (in USD):");
            TextField salaryField = new TextField();
            salaryLabel.getStyleClass().add("label");

            // Buttons
            Button addButton = new Button("Add Player");
            Button backButton = new Button("Back");

            addButton.getStyleClass().add("menu-button");
            backButton.getStyleClass().add("menu-button");

            // Text area to display messages
            TextArea messageArea = new TextArea();
            messageArea.setEditable(false);
            messageArea.setWrapText(true);
            messageArea.setPrefHeight(100);
            messageArea.getStyleClass().add("message-area");

            // Event handling for Add Player button
            addButton.setOnAction(e -> {
                try {
                    // Collect and validate input
                    String name = nameField.getText().trim();
                    String country = countryField.getText().trim();
                    String club = clubField.getText().trim();
                    String position = positionField.getText().trim();

                    if (name.isEmpty() || country.isEmpty() || club.isEmpty() || position.isEmpty()) {
                        throw new IllegalArgumentException("All fields except jersey number must be filled.");
                    }

                    int age = Integer.parseInt(ageField.getText().trim());
                    if (age <= 0) throw new NumberFormatException("Age must be positive.");

                    double height = Double.parseDouble(heightField.getText().trim());
                    if (height <= 0) throw new NumberFormatException("Height must be positive.");

                    int number = 0; // Default jersey number if skipped
                    if (!numberField.getText().trim().isEmpty()) {
                        number = Integer.parseInt(numberField.getText().trim());
                        if (number < 0) throw new NumberFormatException("Jersey number cannot be negative.");
                    }

                    int weeklySalary = Integer.parseInt(salaryField.getText().trim());
                    if (weeklySalary < 0) throw new NumberFormatException("Salary cannot be negative.");

                    // Create and add the player
                    Player newPlayer = new Player(name, country, age, height, club, position, number, weeklySalary);

                    if (PlayerList.containsPlayer(newPlayer.getName())) {
                        messageArea.setText("Player of this name is already present in the database.");
                    } else {
                        PlayerList.addPlayer(newPlayer); // Assuming you have an `addPlayer` method in `PlayerList`
                        messageArea.setText("Player added successfully.");
                    }

                } catch (NumberFormatException ex) {
                    messageArea.setText("Invalid input: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    messageArea.setText(ex.getMessage());
                }
            });

            // Event handling for Back button
            backButton.setOnAction(e -> mainMenu(primaryStage)); // Assuming you have a `showMainMenu` method

            // Add components to the layout
            layout.getChildren().addAll(
                    nameLabel, nameField,
                    countryLabel, countryField,
                    clubLabel, clubField,
                    positionLabel, positionField,
                    ageLabel, ageField,
                    heightLabel, heightField,
                    numberLabel, numberField,
                    salaryLabel, salaryField,
                    addButton, messageArea, backButton
            );

            // Create and set the scene
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true); // Ensures the layout fits the width of the screen
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar if content overflows

        // Create and set the scene
        Scene scene = new Scene(scrollPane, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Attach stylesheet
        primaryStage.setTitle("Add Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        }


    }







import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.print.event.PrintJobListener;


public class Menu {


    //ArrayList<Player> players = PlayerList.getPlayers(); // we assign here instead of making a copy


    public static void mainMenu(Stage primaryStage)
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

        // Add button CSS classes
        searchPlayersButton.getStyleClass().add("button");
        searchClubsButton.getStyleClass().add("button");
        addPlayerButton.getStyleClass().add("button");
        exitButton.getStyleClass().add("button");

        // Button actions
        searchPlayersButton.setOnAction(e -> playerSearchMenu(primaryStage));
        addPlayerButton.setOnAction(e -> addPlayerMenu(primaryStage));
        exitButton.setOnAction(e -> {
            FileOperations.savePlayersToFile(PlayerList.getPlayers());
            primaryStage.close();
        });

        // Add components to layout
        layout.getChildren().addAll(titleLabel, searchPlayersButton, searchClubsButton, addPlayerButton, exitButton);

        // Create scene
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Add CSS file
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
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
    public static void clubSearchMenu()
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> searchResult = new ArrayList<>();


        int choice = -1;
        while(choice != 5)
        {
            System.out.println( "Club Searching Options:\n(1) Player(s) with the maximum salary of a club\n(2) Player(s) with the maximum age of a club\n(3) Player(s) with the maximum height of a club\n(4) Total yearly salary of a club\n(5) Back to Main Menu");
            choice = scanner.nextInt();
            scanner.nextLine();

            String clubName;

            switch(choice)
            {
                case 1:
                    System.out.println("Enter name of the club: ");
                    clubName = scanner.nextLine();
                    searchResult = SearchClub.byMaxSalary(clubName);
                    if(searchResult.isEmpty())
                        System.out.println("No club of such name exists.");
                    else
                        PlayerList.printPlayers(searchResult);

                    break;

                case 2:
                    System.out.println("Enter name of the club: ");
                    clubName = scanner.nextLine();
                    searchResult = SearchClub.byMaxAge(clubName);
                    if(searchResult.isEmpty())
                        System.out.println("No club of such name exists.");
                    else
                        PlayerList.printPlayers(searchResult);

                    break;

                case 3:
                    System.out.println("Enter name of the club: ");
                    clubName = scanner.nextLine();
                    searchResult = SearchClub.byMaxHeight(clubName);
                    if(searchResult.isEmpty())
                        System.out.println("No club of such name exists.");
                    else
                        PlayerList.printPlayers(searchResult);

                    break;

                case 4:
                    System.out.println("Enter name of the club: ");
                    clubName = scanner.nextLine();
                    long total = SearchClub.totalClubSalary(clubName);
                    if(total == -1)
                        System.out.println("No club of such name exists.");
                    else
                        System.out.println("Total yearly salary of the club is "+total);
                    break;

                case 5:
                    break;

                default:
                    System.out.println("Invalid option");
                    break;

            }



        }

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







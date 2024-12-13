import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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




    public static void playerSearchMenu(Stage primaryStage)
    {
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

        // TextArea for results
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false); // Make the TextArea read-only
        resultArea.getStyleClass().add("result-area"); // Styled for results
        resultArea.setPrefHeight(300); // Adjust height to make results more readable
        resultArea.setPrefWidth(500); // Adjust width to align with other elements

        // Event handling
        searchButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            ArrayList<Player> searchResult = SearchPlayer.byName(name);
            if (searchResult.isEmpty()) {
                resultArea.setText("No player found with this name.");
            } else {
                resultArea.setText(/*PlayerList.getFormattedPlayers(searchResult)*/"Found"); // Display formatted results
            }
        });

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        // Add all elements to the layout
        layout.getChildren().addAll(prompt, nameField, searchButton, resultArea, backButton);

        // Create and set the scene
        Scene scene = new Scene(layout, 1000, 700);
        scene.getStylesheets().add("styles.css"); // Attach stylesheet
        primaryStage.setTitle("Search by Player Name");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static void searchByClubAndCountry(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label clubPrompt = new Label("Enter club name:");
        TextField clubField = new TextField();

        Label countryPrompt = new Label("Enter country name:");
        TextField countryField = new TextField();

        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        searchButton.setOnAction(e -> {
            String club = clubField.getText().trim();
            String country = countryField.getText().trim();
            ArrayList<Player> searchResult = SearchPlayer.byClubCountry(club, country);
            if (searchResult.isEmpty()) {
                resultArea.setText("No player found with this club and country.");
            } else {
                resultArea.setText("Found");
            }
        });

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(clubPrompt, clubField, countryPrompt, countryField, searchButton, resultArea, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setTitle("Search by Club and Country");
        primaryStage.setScene(scene);
    }

    private static void showCountryWiseCount(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label resultLabel = new Label("Country-wise Player Count:");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        Button backButton = new Button("Back");

        HashMap<String, Integer> map = SearchPlayer.countryWiseCount();
        StringBuilder resultText = new StringBuilder();
        map.forEach((key, value) -> resultText.append("Country: ").append(key).append(", Players: ").append(value).append("\n"));

        resultArea.setText(resultText.toString()); //

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(resultLabel, resultArea, backButton);

        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setTitle("Country-wise Player Count");
        primaryStage.setScene(scene);
    }

    private static void searchByPosition(Stage primaryStage){

        VBox layout = new VBox(10);
        Label label = new Label("Search player by position:");

        Button searchButton = new Button("Search");
        Button backButton = new Button("Back to Player Search Menu:");

        Label positionPrompt = new Label("Enter position of Player: ");
        TextField positionField = new TextField();

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);


        backButton.setOnAction(e ->
        { playerSearchMenu(primaryStage);
        });

        searchButton.setOnAction(e -> {

         String position = positionField.getText().trim();
         ArrayList<Player> searchResult = SearchPlayer.byPosition(position);
            if (searchResult.isEmpty()) {
                resultArea.setText("No player found with position.");
            } else {
                resultArea.setText("Found");
            }


        });

        layout.getChildren().addAll(positionPrompt,positionField,searchButton,resultArea,backButton);
        Scene scene = new Scene(layout,1000,700);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static void searchBySalaryRange(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label lowerRangePrompt = new Label("Enter lower salary range:");
        TextField lowerRangeField = new TextField();

        Label upperRangePrompt = new Label("Enter upper salary range:");
        TextField upperRangeField = new TextField();

        Button searchButton = new Button("Search");
        Button backButton = new Button("Back");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        searchButton.setOnAction(e -> {
            try {
                int low = Integer.parseInt(lowerRangeField.getText().trim());
                int high = Integer.parseInt(upperRangeField.getText().trim());

                ArrayList<Player> searchResult = SearchPlayer.bySalaryRange(low, high);
                if (searchResult.isEmpty()) {
                    resultArea.setText("No player found within this salary range.");
                } else {
                    resultArea.setText("Found");
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter valid numeric values for the salary range.");
            }
        });

        backButton.setOnAction(e -> playerSearchMenu(primaryStage));

        layout.getChildren().addAll(lowerRangePrompt, lowerRangeField, upperRangePrompt, upperRangeField, searchButton, resultArea, backButton);

        Scene scene = new Scene(layout, 1000, 700);
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

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Labels and input fields
        Label nameLabel = new Label("Enter name of the player:");
        TextField nameField = new TextField();

        Label countryLabel = new Label("Enter country of the player:");
        TextField countryField = new TextField();

        Label clubLabel = new Label("Enter club of the player:");
        TextField clubField = new TextField();

        Label positionLabel = new Label("Enter position of the player:");
        TextField positionField = new TextField();

        Label ageLabel = new Label("Enter age of the player (in years):");
        TextField ageField = new TextField();

        Label heightLabel = new Label("Enter height of the player (in m):");
        TextField heightField = new TextField();

        Label numberLabel = new Label("Enter jersey number of the player (press Enter to skip):");
        TextField numberField = new TextField();

        Label salaryLabel = new Label("Enter weekly salary of the player (in USD):");
        TextField salaryField = new TextField();

        // Buttons
        Button addButton = new Button("Add Player");
        Button backButton = new Button("Back");

        // Text area to display messages
        TextArea messageArea = new TextArea();
        messageArea.setEditable(false);
        messageArea.setWrapText(true);

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

        // Set the scene
        Scene scene = new Scene(layout, 1000, 700);
        primaryStage.setTitle("Add Player");
        primaryStage.setScene(scene);
    }

    }







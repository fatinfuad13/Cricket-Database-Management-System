import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override

        public void start(Stage primaryStage) {
        ArrayList<Player> players = FileOperations.loadPlayersFromFile(); // load players from input file
        PlayerList.setPlayers(players); // load players into database from file
       // System.out.println(players);
       // Menu.mainMenu(primaryStage); // display main menu
        Menu.loginMenu(primaryStage);
        //System.out.println(PlayerList.getPlayers());
        // FileOperations.savePlayersToFile(players); // save changes back into file before exiting
        primaryStage.setOnCloseRequest(event -> {
            FileOperations.savePlayersToFile(players);
        });

        }

        public static void main(String[] args) {
            launch(args); // Launch the JavaFX application
        }
    }



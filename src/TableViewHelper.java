import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TableViewHelper {

    public static TableView<Player> createPlayerTableView(ArrayList<Player> players) {
        // Create a TableView
        TableView<Player> tableView = new TableView<>();

        // Define columns for Player properties
        TableColumn<Player, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<Player, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Player, Double> heightColumn = new TableColumn<>("Height");
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        TableColumn<Player, String> clubColumn = new TableColumn<>("Club");
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));

        TableColumn<Player, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Player, Integer> numberColumn = new TableColumn<>("Number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<Player, Integer> salaryColumn = new TableColumn<>("Weekly Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("weeklySalary"));

        // Add columns to the TableView
        tableView.getColumns().addAll(nameColumn, countryColumn, ageColumn, heightColumn,
                clubColumn, positionColumn, numberColumn, salaryColumn);

        // Set the data for the table
        ObservableList<Player> observableList = FXCollections.observableArrayList(players);
        tableView.setItems(observableList);

        // Optionally add column resizing behavior
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tableView;
    }
}

package application;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

	private Martyr[] martyrArray = new Martyr[16]; // Initial size of 16
	private int martyrCount = 0;

	private TableView<Martyr> tableView = new TableView<>();
	private TextField nameField;
	private TextField ageField;
	private TextField eventLocationField;
	private TextField dateOfDeathField;
	private TextField genderField;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("my Project");

		BorderPane borderPane = new BorderPane();
		VBox vbox = new VBox();
		HBox hbox = new HBox();
		nameField = new TextField();
		ageField = new TextField();
		eventLocationField = new TextField();
		dateOfDeathField = new TextField();
		genderField = new TextField();
		Button insertButton = new Button("Insert");
		Button deleteButton = new Button("Delete");
		TextField searchField = new TextField();
		Button searchButton = new Button("Search");
		Button loadButton = new Button("Load Data");

		insertButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold;");
		deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
		loadButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
		searchButton.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold;");

		Label nameLabel = new Label("Name: ");
		Label ageLabel = new Label("Age: ");
		Label eventLocationLabel = new Label("Event Location: ");
		Label dateOfDeathLabel = new Label("Date of Death: ");
		Label genderLabel = new Label("Gender: ");

		vbox.getChildren().addAll(new HBox(nameLabel, nameField), new HBox(ageLabel, ageField),
				new HBox(eventLocationLabel, eventLocationField), new HBox(dateOfDeathLabel, dateOfDeathField),
				new HBox(genderLabel, genderField), hbox);
		vbox.setSpacing(10);

		hbox.getChildren().addAll(insertButton, deleteButton, searchField, searchButton);
		hbox.setSpacing(10);
		borderPane.setBottom(vbox);
		borderPane.setCenter(tableView);

		TableColumn<Martyr, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Martyr, Integer> ageColumn = new TableColumn<>("Age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

		TableColumn<Martyr, String> eventLocationColumn = new TableColumn<>("Event Location");
		eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("Elocation"));

		TableColumn<Martyr, String> dateOfDeathColumn = new TableColumn<>("Date of Death");
		dateOfDeathColumn.setCellValueFactory(new PropertyValueFactory<>("dateofdeath"));

		TableColumn<Martyr, String> genderColumn = new TableColumn<>("Gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

		tableView.getColumns().addAll(nameColumn, ageColumn, eventLocationColumn, dateOfDeathColumn, genderColumn);

		insertButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = nameField.getText();
				int age = Integer.parseInt(ageField.getText());
				String eventLocation = eventLocationField.getText();
				String dateOfDeath = dateOfDeathField.getText();
				String gender = genderField.getText();

				// Check if the date format is valid (dd/mm/yyyy)
				if (!isValidDateFormat(dateOfDeath)) {
					showAlert("Error", "Invalid date format. Please use the format dd/mm/yyyy.");
					return;
				}

				// Check if the array is full and resize if necessary
				if (martyrCount == martyrArray.length) {
					resizeArray();
				}

				Martyr newMartyr = new Martyr(name, age, eventLocation, dateOfDeath, gender);
				martyrArray[martyrCount++] = newMartyr;
				tableView.getItems().add(newMartyr);

				showAlert("Success :)", "Inserted: " + newMartyr);

				nameField.clear();
				ageField.clear();
				eventLocationField.clear();
				dateOfDeathField.clear();
				genderField.clear();
			}

		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = nameField.getText();

				for (int i = 0; i < martyrCount; i++) {
					if (martyrArray[i].getName().equals(name)) {
						tableView.getItems().remove(martyrArray[i]);
						for (int j = i; j < martyrCount - 1; j++) {
							martyrArray[j] = martyrArray[j + 1];
						}
						martyrCount--;
						showAlert("Success :)", "Deleted: " + name);

						// Clear text fields after deletion
						nameField.clear();
						ageField.clear();
						eventLocationField.clear();
						dateOfDeathField.clear();
						genderField.clear();

						return;
					}
				}

				showAlert("Error :(", "Martyr not found.");
			}
		});

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = searchField.getText();

				for (int i = 0; i < martyrCount; i++) {
					if (martyrArray[i].getName().equals(name)) {
						showAlert("Search Result", "Martyr found: " + martyrArray[i]);

						// Clear text field after search
						searchField.clear();

						return;
					}
				}

				showAlert("Search Result", "Martyr not found.");
				searchField.clear();

			}
		});

		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					readFile(file);
				}
			}
		});
		Button displayStatsButton = new Button("Display Stats");

		displayStatsButton.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold;");
		hbox.getChildren().add(displayStatsButton);

		displayStatsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayMartyrsStats();
			}
		});

		hbox.getChildren().add(loadButton);
		borderPane.setBackground(
				new Background(new BackgroundFill(javafx.scene.paint.Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
		Scene scene = new Scene(borderPane, 700, 700);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void displayMartyrsStats() {
		// Calculate the number of martyrs by age
		int[] ageCount = new int[100];
		int maleCount = 0;
		int femaleCount = 0;
		int unspecifiedGenderCount = 0;

		for (int i = 0; i < martyrCount; i++) {
			int age = martyrArray[i].getAge();
			if (age >= 0 && age < 100) {
				ageCount[age]++;
			}

			String gender = martyrArray[i].getGender();
			if ("M".equalsIgnoreCase(gender)) {
				maleCount++;
			} else if ("F".equalsIgnoreCase(gender)) {
				femaleCount++;
			} else {
				unspecifiedGenderCount++;
			}
		}

		// Display the stats
		StringBuilder stats = new StringBuilder();
		for (int i = 0; i < ageCount.length; i++) {
			if (ageCount[i] > 0) {
				stats.append("Age ").append(i).append(": ").append(ageCount[i]).append("\n");
			}
		}
		stats.append("\nMale Martyrs: ").append(maleCount);
		stats.append("\nFemale Martyrs: ").append(femaleCount);
		stats.append("\nMartyrs with unspecified gender: ").append(unspecifiedGenderCount);

		showAlert("Martyrs Statistics", stats.toString());
	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// method to read data from the file
	private void readFile(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			boolean isFirstLine = true;
			while ((line = br.readLine()) != null) {
				if (isFirstLine) {
					isFirstLine = false;
					continue;
				}
				String[] parts = line.split(",");
				if (parts.length == 5) {
					String name = parts[0].trim();
					String ageStr = parts[1].trim();
					String eventLocation = parts[2].trim();
					String dateOfDeath = parts[3].trim();
					String gender = parts[4].trim();

					if (ageStr.isEmpty()) {
						continue;
					}

					int age;
					try {
						age = Integer.parseInt(ageStr);
					} catch (NumberFormatException e) {
						continue;
					}

					// Check if the array is full and resize if necessary
					if (martyrCount == martyrArray.length) {
						resizeArray();
					}

					Martyr newMartyr = new Martyr(name, age, eventLocation, dateOfDeath, gender);
					martyrArray[martyrCount++] = newMartyr;
					tableView.getItems().add(newMartyr);
				} else {
					System.err.println("Invalid data format in line: " + line);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + file.getPath());
			e.printStackTrace();
			showAlert("Error", "File not found: " + file.getPath());
		} catch (IOException e) {
			System.err.println("Failed to read data from file: " + file.getPath());
			e.printStackTrace();
			showAlert("Error", "Failed to read data from file: " + file.getPath());
		}
	}

	private void resizeArray() {
		int newSize = martyrArray.length * 2; // Double the size
		Martyr[] newArray = new Martyr[newSize];
		System.arraycopy(martyrArray, 0, newArray, 0, martyrArray.length);
		martyrArray = newArray;
	}

	// Method to check if the date format is valid
	private boolean isValidDateFormat(String date) {
		String regex = "\\d{2}/\\d{2}/\\d{4}";
		return date.matches(regex);
	}
}

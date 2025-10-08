package com.quiz_geek.controllers.teacher;

import com.quiz_geek.services.core.UserService;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DashboardController {

	@FXML private ScrollPane rootScroll;
	@FXML private VBox rootContainer;
	@FXML private StackPane greetingCard;
	@FXML private PieChart typesPie;
	@FXML private PieChart participationPie;
	@FXML private VBox mostEnrolledBox;
	@FXML private TableView<RecentItem> recentTable;
	@FXML private TableColumn<RecentItem, String> colSubject;
	@FXML private TableColumn<RecentItem, String> colType;
	@FXML private TableColumn<RecentItem, String> colAccess;
	@FXML private TableColumn<RecentItem, String> colDifficulty;
	@FXML private LineChart<String, Number> lineChart;
	@FXML private ComboBox<String> periodCombo;
	@FXML private Label greetingText;

	@FXML
	private void initialize() {
		// Load stylesheet from classpath to avoid URL parsing issues in FXML
		String css = getClass().getResource("/com/quiz_geek/styles/style.css").toExternalForm();
		rootScroll.getStylesheets().add(css);
		setupPies();
		setupMostEnrolled();
		setupRecentTable();
		setupLineChart();
		applyEnterAnimations();
		greetingText.setText("Hello " + UserService.getCurrentUser().getFullName());
	}

	private void setupPies() {
		// Assessment types pie with specific colors mapping legend chips
		ObservableList<PieChart.Data> typeData = FXCollections.observableArrayList(
				new PieChart.Data("Practice Questions", 45),
				new PieChart.Data("Worksheet", 30),
				new PieChart.Data("Exams", 25)
		);
		typesPie.setData(typeData);
		// Colors according to legend chips
		// Practice: #22C55E, Worksheet: #F59E0B, Exams: #EF4444
		typesPie.applyCss();
		typesPie.getData().forEach(d -> {
			String color = switch (d.getName()) {
				case "Practice Questions" -> "#22C55E";
				case "Worksheet" -> "#F59E0B";
				default -> "#EF4444";
			};
			d.getNode().setStyle("-fx-pie-color: " + color + ";");
		});

		ObservableList<PieChart.Data> participationData = FXCollections.observableArrayList(
				new PieChart.Data("Completed", 50),
				new PieChart.Data("In progress", 35),
				new PieChart.Data("Not started", 15)
		);
		participationPie.setData(participationData);
		participationPie.applyCss();
		participationPie.getData().forEach(d -> {
			String color = switch (d.getName()) {
				case "Completed" -> "#22C55E";
				case "In progress" -> "#F59E0B";
				default -> "#EF4444";
			};
			d.getNode().setStyle("-fx-pie-color: " + color + ";");
		});
	}

private void setupMostEnrolled() {
	// Rows are defined in FXML; add subtle hover scaling to each row
	mostEnrolledBox.getChildren().forEach(this::wireHoverScale);
}

	private void setupRecentTable() {
		// Ensure constrained resize is applied programmatically for Scene Builder compatibility
		recentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colAccess.setCellValueFactory(new PropertyValueFactory<>("access"));
		colDifficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        ObservableList<RecentItem> data = FXCollections.observableArrayList(
                new RecentItem("Maths", "Exam", "Private", "Easy"),
                new RecentItem("Biology", "Quiz", "Private", "Medium"),
                new RecentItem("Chemistry", "Exam", "Public", "Hard"),
                new RecentItem("Physics", "Worksheet", "Private", "Medium"),
                new RecentItem("English", "Practice", "Public", "Easy")
        );
		recentTable.setItems(data);
		// Force layout/refresh so rows appear even if table was initially not visible
		recentTable.layout();
		recentTable.refresh();

        // Row styling and hover effect; use style class only (no inline background-color strings)
        recentTable.setRowFactory(tv -> {
            TableRow<RecentItem> row = new TableRow<>();
            row.getStyleClass().add("table-row");
            row.hoverProperty().addListener((obs, was, isNow) -> {
                if (isNow) {
                    row.getStyleClass().add("row-hover");
                } else {
                    row.getStyleClass().remove("row-hover");
                }
            });
            return row;
        });
	}

	private void setupLineChart() {
		periodCombo.setItems(FXCollections.observableArrayList("Weekly", "Monthly", "Quarterly"));
		periodCombo.getSelectionModel().selectFirst();
		plotLineSeries("Weekly");
		periodCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> plotLineSeries(n));
	}

	private void plotLineSeries(String mode) {
		lineChart.getData().clear();
		XYChart.Series<String, Number> series = new XYChart.Series<>();
		if ("Monthly".equals(mode)) {
			series.getData().add(new XYChart.Data<>("Jan", 1));
			series.getData().add(new XYChart.Data<>("Feb", 3));
			series.getData().add(new XYChart.Data<>("Mar", 2));
			series.getData().add(new XYChart.Data<>("Apr", 2));
			series.getData().add(new XYChart.Data<>("May", 1));
			series.getData().add(new XYChart.Data<>("Jun", 4));
		} else if ("Quarterly".equals(mode)) {
			series.getData().add(new XYChart.Data<>("Q1", 6));
			series.getData().add(new XYChart.Data<>("Q2", 5));
			series.getData().add(new XYChart.Data<>("Q3", 7));
			series.getData().add(new XYChart.Data<>("Q4", 8));
		} else {
			series.getData().add(new XYChart.Data<>("Mon", 1));
			series.getData().add(new XYChart.Data<>("Tue", 0));
			series.getData().add(new XYChart.Data<>("Wed", 3));
			series.getData().add(new XYChart.Data<>("Thu", 2));
			series.getData().add(new XYChart.Data<>("Fri", 2));
			series.getData().add(new XYChart.Data<>("Sat", 0));
			series.getData().add(new XYChart.Data<>("Sun", 4));
		}
		lineChart.getData().add(series);
	}

	private void applyEnterAnimations() {
		// Animate each direct child of the root container
		int index = 0;
		for (javafx.scene.Node node : rootContainer.getChildren()) {
			playPopIn(node, index * 80);
			index++;
		}
        // Hover scale for all cards and specifically stat cards
        rootContainer.lookupAll(".card").forEach(this::wireHoverScale);
        wireHoverScale(greetingCard);
	}

	private void playPopIn(javafx.scene.Node node, int delayMs) {
		FadeTransition fade = new FadeTransition(Duration.millis(450), node);
		fade.setFromValue(0);
		fade.setToValue(1);
		ScaleTransition scale = new ScaleTransition(Duration.millis(450), node);
		scale.setFromX(0.98);
		scale.setFromY(0.98);
		scale.setToX(1);
		scale.setToY(1);
		SequentialTransition seq = new SequentialTransition();
		seq.getChildren().addAll(new javafx.animation.PauseTransition(Duration.millis(delayMs)), fade, scale);
		seq.setInterpolator(Interpolator.EASE_OUT);
		seq.play();
	}

	private void wireHoverScale(javafx.scene.Node node) {
		node.setOnMouseEntered(e -> { node.setScaleX(1.01); node.setScaleY(1.01); });
		node.setOnMouseExited(e -> { node.setScaleX(1); node.setScaleY(1); });
		// ignore clicks; hover-only interactivity
		node.setOnMousePressed(e -> e.consume());
		node.setOnMouseReleased(e -> e.consume());
	}

	private void wireHoverStyle(TableRow<RecentItem> row, boolean hovered) {
		row.setStyle(hovered ? "-fx-background-color: -row-hover;" : "-fx-background-color: -row-bg;");
	}

	public static class RecentItem {
		private final String subject;
		private final String type;
		private final String access;
		private final String difficulty;

		public RecentItem(String subject, String type, String access, String difficulty) {
			this.subject = subject;
			this.type = type;
			this.access = access;
			this.difficulty = difficulty;
		}

		public String getSubject() { return subject; }
		public String getType() { return type; }
		public String getAccess() { return access; }
		public String getDifficulty() { return difficulty; }
	}
}


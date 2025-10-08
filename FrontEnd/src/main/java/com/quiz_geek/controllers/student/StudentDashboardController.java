package com.quiz_geek.controllers.student;

import com.quiz_geek.models.RecentAssessment;
import com.quiz_geek.models.StudentStat;
import com.quiz_geek.services.core.UserService;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import com.quiz_geek.utils.SvgLoader;
import com.quiz_geek.utils.Constants;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class StudentDashboardController {

    // root and columns
    @FXML private ScrollPane rootScroll;
    @FXML private VBox leftColumn;
    @FXML private VBox rightColumn;

    // header & stats
    @FXML private Label greetingNameLabel;
    @FXML private Label streakValueLabel;
    @FXML private Label questionsCompletedLabel;
    @FXML private Label assessmentsEnrolledLabel;

    // calendar
    @FXML private GridPane calendarGrid;
    @FXML private ComboBox<String> monthCombo;
    @FXML private ComboBox<Integer> yearCombo;
    // removed nav buttons per update

    // table
    @FXML private TableView<RecentAssessment> recentTable;
    @FXML private TableColumn<RecentAssessment, String> colSubject;
    @FXML private TableColumn<RecentAssessment, String> colType;
    @FXML private TableColumn<RecentAssessment, String> colStatus;
    @FXML private TableColumn<RecentAssessment, String> colResult;

    // charts
    @FXML private LineChart<String, Number> lineParticipation;
    @FXML private ChoiceBox<String> participationRange;
    @FXML private PieChart pieCompletion;
    @FXML private PieChart pieEvaluation;
    @FXML private BarChart<String, Number> barEnrolled;

    // cards for animation
    @FXML private VBox cardGreeting;
    @FXML private VBox cardStreak;
    @FXML private VBox cardQuestions;
    @FXML private VBox cardEnrolled;
    @FXML private VBox cardCalendar;
    @FXML private VBox cardRecent;
    @FXML private VBox cardParticipation;
    @FXML private VBox cardAssessCompletion;
    @FXML private VBox cardGeneralEval;
    @FXML private VBox cardEnrolledChart;

    private StudentStat stat;
    private YearMonth currentMonth = YearMonth.now();

    @FXML
    public void initialize() {
        // attach stylesheets programmatically to avoid FXML URL element incompatibilities across JavaFX versions
        if (rootScroll != null) {
            rootScroll.getStylesheets().addAll(
                    "/com/quiz_geek/styles/style.css",
                    "/com/quiz_geek/CssFiles/style.css"
            );
        }
        initMockData();
        bindStats();
        setupTable();
        setupCalendarControls();
        renderCalendar();
        setupCharts();
        setupInteractivity();
        playSequentialEntrance();
    }

    private void initMockData() {
        Map<LocalDate, Integer> participation = new HashMap<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 20; i++) {
            LocalDate d = today.minusDays(new Random().nextInt(40));
            participation.put(d, 1 + new Random().nextInt(3));
        }

        stat = new StudentStat(
                11,
                147,
                11,
                participation,
                8,
                2,
                1,
                120,
                27,
                6,
                3,
                2
        );
    }

    private void bindStats() {
        greetingNameLabel.setText("Hello " + UserService.getCurrentUser().getFullName() + "!");
        streakValueLabel.setText(String.valueOf(stat.getDailyStreak()));
        questionsCompletedLabel.setText(String.valueOf(stat.getQuestionsCompleted()));
        assessmentsEnrolledLabel.setText(String.valueOf(stat.getAssessmentsEnrolled()));
    }

    private void setupTable() {
        colSubject.setCellValueFactory(data -> new javafx.beans.property.ReadOnlyStringWrapper(data.getValue().getSubject()));
        colType.setCellValueFactory(data -> new javafx.beans.property.ReadOnlyStringWrapper(data.getValue().getType()));
        colStatus.setCellValueFactory(data -> new javafx.beans.property.ReadOnlyStringWrapper(data.getValue().getStatus()));
        colResult.setCellValueFactory(data -> new javafx.beans.property.ReadOnlyStringWrapper(data.getValue().getResult()));

        ObservableList<RecentAssessment> items = FXCollections.observableArrayList(
                new RecentAssessment("Maths", "Exam", "Pending", "--"),
                new RecentAssessment("Maths", "Practice question", "Completed", "23/30"),
                new RecentAssessment("Maths", "Worksheet", "Completed", "17/20"),
                new RecentAssessment("Maths", "Exam", "Completed", "10/10"),
                new RecentAssessment("Maths", "Practice question", "Completed", "13/15")
        );
        recentTable.setItems(items);
        recentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        recentTable.setRowFactory(tv -> {
            TableRow<RecentAssessment> row = new TableRow<>();
            row.hoverProperty().addListener((obs, oldV, isHover) -> row.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("hover"), isHover));
            return row;
        });
    }

    private void setupCalendarControls() {
        monthCombo.setItems(FXCollections.observableArrayList(
                Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")));
        monthCombo.getSelectionModel().select(currentMonth.getMonthValue()-1);
        yearCombo.setItems(FXCollections.observableArrayList(currentMonth.getYear()-1, currentMonth.getYear(), currentMonth.getYear()+1));
        yearCombo.getSelectionModel().select(Integer.valueOf(currentMonth.getYear()));
        monthCombo.valueProperty().addListener((obs, o, n) -> {
            currentMonth = YearMonth.of(yearCombo.getValue(), monthCombo.getSelectionModel().getSelectedIndex()+1);
            renderCalendar();
        });
        yearCombo.valueProperty().addListener((obs, o, n) -> {
            currentMonth = YearMonth.of(yearCombo.getValue(), monthCombo.getSelectionModel().getSelectedIndex()+1);
            renderCalendar();
        });
    }

    private void renderCalendar() {
        calendarGrid.getChildren().clear();
        // Weekday headers
        String[] days = {"S","M","T","W","T","F","S"};
        for (int i = 0; i < 7; i++) {
            Label l = new Label(days[i]);
            l.getStyleClass().add("overline");
            calendarGrid.add(l, i, 0);
        }
        YearMonth ym = currentMonth;
        LocalDate first = ym.atDay(1);
        int startIdx = first.getDayOfWeek().getValue() % 7; // Sunday index 0
        int length = ym.lengthOfMonth();
        for (int day = 1; day <= length; day++) {
            LocalDate d = ym.atDay(day);
            Label cell = new Label(String.valueOf(day));
            cell.getStyleClass().add("calendar-cell");
            if (stat.getParticipationByDate().containsKey(d)) {
                cell.getStyleClass().add("calendar-active");
                cell.setTooltip(new Tooltip("Participations: " + stat.getParticipationByDate().get(d)));
            }
            int col = (startIdx + day - 1) % 7;
            int row = ((startIdx + day - 1) / 7) + 1;
            calendarGrid.add(cell, col, row);
        }
    }

    private void setupCharts() {
        // Weekly vs Monthly participation with ordered categories
        participationRange.setItems(FXCollections.observableArrayList("Weekly","Monthly"));
        participationRange.getSelectionModel().select(0);
        rebuildParticipationSeries();
        participationRange.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> rebuildParticipationSeries());

        // Completion pie
        pieCompletion.setData(FXCollections.observableArrayList(
                new PieChart.Data("Completed", stat.getCompletedAssessments()),
                new PieChart.Data("In progress", stat.getInProgressAssessments()),
                new PieChart.Data("Not started", stat.getNotStartedAssessments())
        ));

        // Evaluation pie with green/red
        ObservableList<PieChart.Data> evalData = FXCollections.observableArrayList(
                new PieChart.Data("Correct Answers", stat.getCorrectAnswers()),
                new PieChart.Data("Wrong Answers", stat.getWrongAnswers())
        );
        pieEvaluation.setData(evalData);
        pieEvaluation.applyCss();
        for (PieChart.Data d : evalData) {
            d.getNode().setStyle(d.getName().startsWith("Correct") ? "-fx-pie-color: #10B981;" : "-fx-pie-color: #EF4444;");
        }

        // Enrolled bar
        XYChart.Series<String, Number> enrolled = new XYChart.Series<>();
        enrolled.getData().add(new XYChart.Data<>("Practice Questions", stat.getPracticeQuestionsCount()));
        enrolled.getData().add(new XYChart.Data<>("Worksheet", stat.getWorksheetsCount()));
        enrolled.getData().add(new XYChart.Data<>("Exams", stat.getExamsCount()));
        barEnrolled.getData().setAll(enrolled);
        barEnrolled.applyCss();
        String[] colors = {"#6366F1", "#F59E0B", "#06B6D4"};
        for (int i = 0; i < enrolled.getData().size(); i++) {
            final XYChart.Data<String, Number> data = enrolled.getData().get(i);
            final String color = colors[i % colors.length];
            data.getNode().setStyle("-fx-bar-fill: " + color + ";");
        }
    }

    private void rebuildParticipationSeries() {
        String mode = participationRange.getSelectionModel().getSelectedItem();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if ("Monthly".equals(mode)) {
            // aggregate by date of month for current month
            YearMonth ym = currentMonth;
            Map<Integer, Integer> byDay = new LinkedHashMap<>();
            for (int d = 1; d <= ym.lengthOfMonth(); d++) byDay.put(d, 0);
            stat.getParticipationByDate().forEach((date, count) -> {
                if (YearMonth.from(date).equals(ym)) byDay.put(date.getDayOfMonth(), byDay.get(date.getDayOfMonth()) + count);
            });
            for (Map.Entry<Integer, Integer> e : byDay.entrySet()) {
                series.getData().add(new XYChart.Data<>(String.valueOf(e.getKey()), e.getValue()));
            }
            ((CategoryAxis) lineParticipation.getXAxis()).setCategories(FXCollections.observableArrayList(byDay.keySet().stream().map(String::valueOf).toList()));
        } else {
            // weekly: Sun..Sat ordered
            String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
            Map<String, Integer> byDow = new LinkedHashMap<>();
            for (String d : days) byDow.put(d, 0);
            stat.getParticipationByDate().forEach((date, count) -> {
                String key = date.getDayOfWeek().name().substring(0,1) + date.getDayOfWeek().name().substring(1,3).toLowerCase();
                switch (date.getDayOfWeek()) {
                    case SUNDAY -> byDow.put("Sun", byDow.get("Sun") + count);
                    case MONDAY -> byDow.put("Mon", byDow.get("Mon") + count);
                    case TUESDAY -> byDow.put("Tue", byDow.get("Tue") + count);
                    case WEDNESDAY -> byDow.put("Wed", byDow.get("Wed") + count);
                    case THURSDAY -> byDow.put("Thu", byDow.get("Thu") + count);
                    case FRIDAY -> byDow.put("Fri", byDow.get("Fri") + count);
                    case SATURDAY -> byDow.put("Sat", byDow.get("Sat") + count);
                }
            });
            for (String d : days) {
                series.getData().add(new XYChart.Data<>(d, byDow.get(d)));
            }
            ((CategoryAxis) lineParticipation.getXAxis()).setCategories(FXCollections.observableArrayList(days));
        }
        lineParticipation.getData().setAll(series);
    }

    private void setupInteractivity() {
        // Apply hover pseudo-class to all cards
        List<Node> cards = Arrays.asList(
                cardGreeting, cardStreak, cardQuestions, cardEnrolled,
                cardCalendar, cardRecent, cardParticipation,
                cardAssessCompletion, cardGeneralEval, cardEnrolledChart
        );
        for (Node n : cards) {
            n.setOnMouseEntered(e -> n.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("hover"), true));
            n.setOnMouseExited(e -> n.pseudoClassStateChanged(javafx.css.PseudoClass.getPseudoClass("hover"), false));
        }

        // Accent selection for table handled via CSS selection colors; ensure row hover class toggles already set
    }

    private void playSequentialEntrance() {
        List<Node> sequence = Arrays.asList(
                cardGreeting, cardStreak, cardQuestions, cardEnrolled,
                cardCalendar, cardRecent, cardParticipation,
                cardAssessCompletion, cardGeneralEval, cardEnrolledChart
        );
        SequentialTransition st = new SequentialTransition();
        for (Node node : sequence) {
            node.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(260), node);
            ft.setFromValue(0);
            ft.setToValue(1);
            st.getChildren().add(ft);
        }
        st.play();
    }
}



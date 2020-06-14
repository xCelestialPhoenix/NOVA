package seedu.address.ui;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.ui.statisticcard.NextActivityCard;
import seedu.address.ui.statisticcard.StatisticCard;
import seedu.address.ui.statisticcard.TaskCompletionCard;
import seedu.address.ui.statisticcard.WeekNumberCard;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private ActivityListPanel activityListPanel;
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private StatisticCard weekNumberCard;
    private StatisticCard nextActivityCard;
    private StatisticCard taskCompletionCard;
    private final HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane nextActivityCardPlaceholder;

    @FXML
    private StackPane activityListPanelPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane taskCompletionCardPlaceholder;

    @FXML
    private StackPane weekNumberCardPlaceholder;

    /**
     * Instantiates a new Main window.
     *
     * @param primaryStage the primary stage
     * @param logic        the logic
     * @param config       the config
     */
    public MainWindow(Stage primaryStage, Logic logic, Config config) {

        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {

        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {

        activityListPanel = new ActivityListPanel(logic.getFilteredActivityList());
        activityListPanelPlaceholder.getChildren().add(activityListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        weekNumberCard = new WeekNumberCard(logic);
        weekNumberCardPlaceholder.getChildren().add(weekNumberCard.getRoot());

        nextActivityCard = new NextActivityCard();
        nextActivityCardPlaceholder.getChildren().add(nextActivityCard.getRoot());

        taskCompletionCard = new TaskCompletionCard();
        taskCompletionCardPlaceholder.getChildren().add(taskCompletionCard.getRoot());

        updateStatistic();

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {

        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {

        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Show.
     */
    void show() {

        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {

        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {

        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            updateStatistic();

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isResetCalendar()) {
                activityListPanelPlaceholder.getChildren().clear();
                activityListPanel = new ActivityListPanel(logic.getFilteredActivityList());
                activityListPanelPlaceholder.getChildren().add(activityListPanel.getRoot());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the statistic cards
     */
    private void updateStatistic() {

        //Update week number
        int newWeekNumber = logic.calculateWeekNumber(LocalDate.now()) + 1;
        (
                (WeekNumberCard) weekNumberCard).updateData(newWeekNumber);

        //Update the next upcoming activity
        Optional<Activity> activity = logic.getNextActivity();

        if (activity.isEmpty()) {
            nextActivityCard.updateData("", NextActivityCard.DATA_TEXT_FONT_SIZE);
        } else {
            (
                    (NextActivityCard) nextActivityCard).updateData(activity.get());
        }

        //Update task completion status
        TaskCompletionStatistics stats = logic.getTaskCompletionStats();
        (
                (TaskCompletionCard) taskCompletionCard).updateData(stats);
    }

}

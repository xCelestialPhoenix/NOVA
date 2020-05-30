package seedu.address.ui;

import static seedu.address.ui.StatisticCard.NEXT_ACTIVITY_TITLE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Config config;

    // Independent Ui parts residing in this Ui container
    private ActivityListPanel activityListPanel;
    private ResultDisplay resultDisplay;
    private StatisticCard weekNumberCard;
    private StatisticCard nextActivityCard;
    private StatisticCard taskCompletionCard;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane nextActivityCardPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

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
        this.config = config;

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
        personListPanelPlaceholder.getChildren().add(activityListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        weekNumberCard = new StatisticCard(StatisticCard.WEEK_NUM_TITLE, logic);
        weekNumberCardPlaceholder.getChildren().add(weekNumberCard.getRoot());

        nextActivityCard = new StatisticCard(NEXT_ACTIVITY_TITLE, logic);
        nextActivityCardPlaceholder.getChildren().add(nextActivityCard.getRoot());

        taskCompletionCard = new StatisticCard(StatisticCard.TASK_COMPLETION_TITLE, logic);
        taskCompletionCardPlaceholder.getChildren().add(taskCompletionCard.getRoot());

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
     * Gets activity list panel.
     *
     * @return the activity list panel
     */
    public ActivityListPanel getActivityListPanel() {

        return activityListPanel;
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
                personListPanelPlaceholder.getChildren().clear();
                activityListPanel = new ActivityListPanel(logic.getFilteredActivityList());
                personListPanelPlaceholder.getChildren().add(activityListPanel.getRoot());
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

        String newData = "";

        //Update week number
        int newWeekNumber = logic.calculateWeekNumber(LocalDate.now()) + 1;
        newData = String.valueOf(newWeekNumber);
        logger.info("New week number: " + newWeekNumber);
        weekNumberCard.updateData(newData);

        //Update the next upcoming activity
        newData = "";
        Optional<Activity> activity = logic.getNextActivity(LocalDate.now(), LocalTime.now());
        if (activity.isPresent()) {
            newData = activity.get().getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n"
                    + activity.get().getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        }
        nextActivityCard.updateData(newData);

        //Update task completion percentage
        newData = "";
    }

}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class StatisticCard extends UiPart<Node> {

    public static final String WEEK_NUM_TITLE = "Week:";
    public static final String NEXT_EVENT_TITLE = "Next event is at:";
    public static final String TASK_COMPLETION_TITLE = "Task completed:";

    private static final String FXML = "StatisticsCard.fxml";
    private static final String CALENDAR_FILEPATH = "/images/calendar.png";
    private static final String CLOCK_FILEPATH = "/images/clock.png";
    private static final String TICK_FILEPATH = "/images/tick.png";

    @FXML
    private AnchorPane statisticCardRoot;

    @FXML
    private ImageView cardLogoImageView;

    @FXML
    private Label cardTitleLabel;

    @FXML
    private Label cardDataLabel;

    @FXML
    private Label cardDateLabel;

    @FXML
    private TextFlow textFlow;

    public StatisticCard(String title, String data) {
        super(FXML);

        Text text = new Text(data);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Segoe UI Bold", 24));

        if (!title.equals(WEEK_NUM_TITLE)) {
            cardDateLabel.setVisible(false);
        } else {
            cardDateLabel.setStyle("-fx-text-fill: black;-fx-font-weight: bold;");
        }

        try {
            setLogo(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardTitleLabel.setText(title);
        textFlow.getChildren().add(text);
    }

    public void updateData(String data) {
        cardDataLabel.setText(data);
    }

    private void setLogo(String title) throws Exception {
        Image img;
        switch (title) {
        case WEEK_NUM_TITLE:
            img = new Image(this.getClass().getResourceAsStream(CALENDAR_FILEPATH));
            break;
        case NEXT_EVENT_TITLE:
            img = new Image(this.getClass().getResourceAsStream(CLOCK_FILEPATH));
            break;
        case TASK_COMPLETION_TITLE:
            img = new Image(this.getClass().getResourceAsStream(TICK_FILEPATH));
            break;
        default:
            //Something went wrong
            throw new Exception();
        }
        cardLogoImageView.setImage(img);
    }
}

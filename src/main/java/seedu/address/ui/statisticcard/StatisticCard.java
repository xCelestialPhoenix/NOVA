package seedu.address.ui.statisticcard;

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

import seedu.address.ui.UiPart;

/**
 * The type Statistic card.
 */
public class StatisticCard extends UiPart<Node> {

    private static final String FXML = "StatisticsCard.fxml";

    @FXML
    protected AnchorPane statisticCardRoot;

    @FXML
    protected ImageView cardLogoImageView;

    @FXML
    protected Label cardTitleLabel;

    @FXML
    protected Label cardDateLabel;

    @FXML
    protected TextFlow dataTextFlow;

    /**
     * Instantiates a new Statistic card.
     *
     * @param title the title
     * @param data  the data
     */
    public StatisticCard(String title, String data, int dataFontSize) {

        super(FXML);
        cardDateLabel.setVisible(false);
        cardTitleLabel.setText(title);
        writeToDataTextFlow(data, dataFontSize);
    }

    /**
     * Updates data.
     *
     * @param data the data
     */
    public void updateData(String data, int fontSize) {

        writeToDataTextFlow(data, fontSize);
    }

    protected void setLogo(Image image) {

        cardLogoImageView.setImage(image);
    }

    private void writeToDataTextFlow(String data, int fontSize) {

        Text text = new Text(data);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Segoe UI Bold", fontSize));
        dataTextFlow.getChildren().clear();
        dataTextFlow.getChildren().add(text);
    }
}

package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import base.Spinner;
import base.Category;
import base.Item;
import utils.RandomUtil;
import utils.JSONUtil;
import utils.PaneResizeHandler;


public class MainController {
    private Stage window;
    private final Timeline timeline = new Timeline();
    private final String soundEffect = "src/resources/";
    private final Media spinMedia = new Media(new File(soundEffect + "spin.mp3").toURI().toString());
    private final MediaPlayer spinPlayer = new MediaPlayer(spinMedia);
    private JSONUtil jsonUtil;
    private Spinner spinner;
    private HashMap<Category, Label> probabilities = new HashMap<>();
    private HashMap<Item, Label> items = new HashMap<>();
    private List<Item> winnableItems = new ArrayList<>();


    @FXML
    private Label lblMain;

    @FXML
    private Button btnSpin;

    @FXML
    private Button btnExit;

    @FXML
    private Pane PaneMain;

    @FXML
    private VBox vboxDisplay;

    public void setWindow(Stage window) {
        this.window = window;
    }
    
    @FXML
    private void initialize() throws FileNotFoundException {
        double height = window.getHeight();
        double width = window.getWidth();
        PaneMain.setStyle("-fx-background-color: rgb(13,13,13)");
        vboxDisplay.setPrefHeight(window.getHeight());

        lblMain.setPrefWidth(width / 2);
        lblMain.setPrefHeight(height / 4);
        lblMain.setLayoutX(width / 4);
        lblMain.setLayoutY(height / 2.5);
        lblMain.setText("Wheel Spin!");
        lblMain.setTextFill(Color.WHITE);

        btnExit.setPrefWidth(width / 6);
        btnExit.setPrefHeight(width / 12);
        btnExit.setLayoutX(width / 2.4);
        btnExit.setLayoutY(height / 1.3); 
        btnExit.setStyle(
            "-fx-padding: 8 15 15 15;\n" + 
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" + 
            "-fx-background-radius: 8;\n" +
            "-fx-background-color: " +
                " linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), " +
                "#9d4024," + 
                "#d86e3a," + 
                "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" + 
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
            "-fx-font-weight: bold;\n" +
            "-fx-font-size: 1.1em;"
        );

        btnSpin.setPrefWidth(width / 6);
        btnSpin.setPrefHeight(width / 12);
        btnSpin.setLayoutX(width / 2.4);
        btnSpin.setLayoutY(height / 1.6); 
        btnSpin.setStyle(
            "-fx-padding: 8 15 15 15;\n" + 
            "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" + 
            "-fx-background-radius: 8;\n" +
            "-fx-background-color: " +
                " linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), " +
                "#9d4024," + 
                "#d86e3a," + 
                "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" + 
            "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
            "-fx-font-weight: bold;\n" +
            "-fx-font-size: 1.1em;"
        );
        jsonUtil = new JSONUtil();
        spinner = jsonUtil.readJSON();
        setUpVerticalBox();
        window.widthProperty().addListener(
            new PaneResizeHandler.WidthHandler(PaneMain, window)
        );
        
        window.heightProperty().addListener(
            new PaneResizeHandler.HeightHandler(PaneMain, window)
        );
        FileInputStream stream = new FileInputStream("src/resources/logo.png");
        Image coverPhoto = new Image(stream, width / 3, width / 3, true, true);
        ImageView imageView = new ImageView(coverPhoto);
        PaneMain.getChildren().add(imageView);
        imageView.setLayoutX(width / 3);
        imageView.setLayoutY(0); 

    }    

    private void setUpVerticalBox() {
        List<Category> categories = spinner.getCategories();
        setUpProbabilities(categories);
        setUpItems(categories);

    }

    private void setUpItems(List<Category> categories) {
        Font defaultFont = new Font("Arial", 15);
        Label lblTitle = new Label();
        lblTitle.setFont(new Font("Arial", 20.0));
        lblTitle.setStyle("-fx-font-weight: bold");
        lblTitle.setText("Items");
        lblTitle.setTextFill(Color.WHITE);
        vboxDisplay.getChildren().add(lblTitle);
        for (Category category : categories) {
            winnableItems.addAll(category.getItems());
            Label lblName = new Label();
            lblName.setTextFill(Color.WHITE);
            lblName.setFont(defaultFont);
            lblName.setText(category.getName() + ": ");
            lblName.setStyle("-fx-font-weight: bold");

            vboxDisplay.getChildren().add(lblName);
            for (Item item : category.getItems()) {
                HBox hbox = new HBox();
                Label lblItemName = new Label();
                Label lblItemQuant = new Label();
                lblItemName.setTextFill(Color.WHITE);
                lblItemName.setFont(defaultFont);
                lblItemName.setText(item.getName() + ": ");

                lblItemQuant.setFont(defaultFont);
                lblItemQuant.setText("x" + Integer.toString(item.getCurrQuantity()));
                lblItemQuant.setTextFill(Color.WHITE);

                hbox.getChildren().add(lblItemName);
                hbox.getChildren().add(lblItemQuant);

                items.put(item, lblItemQuant);
                vboxDisplay.getChildren().add(hbox);
            }
        }
    }

    private void setUpProbabilities(List<Category> categories) {
        Font defaultFont = new Font("Arial", 15);
        Label lblTitle = new Label();
        lblTitle.setTextFill(Color.WHITE);
        lblTitle.setFont(new Font("Arial", 20.0));
        lblTitle.setStyle("-fx-font-weight: bold");
        lblTitle.setText("Probabilities");
        lblTitle.setTextFill(Color.WHITE);
        vboxDisplay.getChildren().add(lblTitle);
        for (Category category : categories) {
            HBox hbox = new HBox();
            vboxDisplay.getChildren().add(hbox);
            Label lblName = new Label();
            Label lblProbability = new Label();
            lblName.setFont(defaultFont);
            lblName.setText(category.getName() + ": ");
            lblName.setTextFill(Color.WHITE);

            lblProbability.setFont(defaultFont);
            lblProbability.setText(String.format("%.2f%%", 100 * category.getWeighting() / findWeighting()));
            lblProbability.setTextFill(Color.WHITE);
            probabilities.put(category, lblProbability);
            hbox.getChildren().add(lblName);
            hbox.getChildren().add(lblProbability);
        }
    }

    private double findWeighting() {
        double result = 0;
        for (Category category : spinner.getCategories()) {
            result += category.getWeighting();
        }
        return result;
    }

    @FXML
    private void spinLabel(ActionEvent e) throws InterruptedException {
        timeline.stop();
        timeline.getKeyFrames().clear();
        double prev = 0;
        lblMain.setTextFill(Color.WHITE);
        for (double i = 0.1; i < 0.5; i *= 1.05) {
            timeline.getKeyFrames().add(new KeyFrame(
                (prev == 0) ? Duration.ZERO : Duration.seconds(prev),
                (ActionEvent event) -> {
                    spinPlayer.stop();
                    spinPlayer.play();
                    Item randItem = winnableItems.get(RandomUtil.getInt(0, winnableItems.size()));
                    lblMain.setText(randItem.getName());
                }
            ));
            prev += i;
        }

        timeline.setOnFinished((ActionEvent event) -> {
            Item item = spinner.spin();
            spinPlayer.play();
            lblMain.setText(item.getName());
            lblMain.setTextFill(Color.GREEN);
            update();
        });
        timeline.play();
    }

    @FXML
    private void exit(ActionEvent e) throws FileNotFoundException {
        jsonUtil.writeJSON(spinner);
        window.close();
    }

    private void update() {
        for (Category category : probabilities.keySet()) {
            Label label = probabilities.get(category);
            label.setText(String.format("%.2f%%", 100 * category.getWeighting() / findWeighting()));
        }

        for (Item item : items.keySet()) {
            Label label = items.get(item);
            label.setText("x" + Integer.toString(item.getCurrQuantity()));
            if (item.getCurrQuantity() == 0)
                winnableItems.remove(item);
        }
    }

}

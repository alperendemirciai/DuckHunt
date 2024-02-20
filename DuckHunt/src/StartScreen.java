import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URISyntaxException;


public class StartScreen extends Application {
    // Texts and layout used in the Start Screen
    private VBox layout = new VBox(3* DuckHunt.SCALE);
    private Text startLabel;
    private Text exitLabel;
    private Font labelFont;

    // Background
    private Image bImage;
    private BackgroundImage backgroundImage;
    private Background background;

    // Scene
    private Scene startScene;

    // Timeline for animation
    private Timeline timeline;

    // Background Audio
    public static AudioClip audioClipTitle;

    static {
        try {
            audioClipTitle = new AudioClip(StartScreen.class.getResource("assets/effects/Title.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Constructor for the Start Screen
     * It basically sets up the layout and the texts(position, font, color).
     * @throws URISyntaxException
     */
    public StartScreen() throws URISyntaxException {
        bImage = new Image("assets/welcome/1.png");
        backgroundImage = new BackgroundImage(bImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(bImage.getWidth()* DuckHunt.SCALE, bImage.getHeight()* DuckHunt.SCALE, false, false, false, false));
        background = new Background(backgroundImage);
        layout.setBackground(background);

        labelFont = new Font("Arial", 20* DuckHunt.SCALE);
        exitLabel = new Text("PRESS ESC TO EXIT");
        startLabel = new Text("PRESS ENTER TO START");
        startLabel.setFont(labelFont);
        exitLabel.setFont(labelFont);

        startLabel.setFont(Font.font(null, FontWeight.BOLD, 20* DuckHunt.SCALE));
        exitLabel.setFont(Font.font(null, FontWeight.BOLD, 20* DuckHunt.SCALE));
        startLabel.setFill(Color.ORANGE);
        exitLabel.setFill(Color.ORANGE);
        layout.getChildren().addAll(startLabel, exitLabel);

        startLabel.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - startLabel.getLayoutBounds().getWidth()/2);
        exitLabel.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - exitLabel.getLayoutBounds().getWidth()/2);
        startLabel.setTranslateY((backgroundImage.getImage().getHeight()* DuckHunt.SCALE)/2 - startLabel.getLayoutBounds().getHeight()/2+30* DuckHunt.SCALE);
        exitLabel.setTranslateY((backgroundImage.getImage().getHeight()* DuckHunt.SCALE)/2 - exitLabel.getLayoutBounds().getHeight()/2 + 30* DuckHunt.SCALE);

        audioClipTitle.setVolume(DuckHunt.VOLUME);

    }


    /**
     * This method animates the start screen and handles the user input.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) {


        if(!(audioClipTitle.isPlaying())) {
            audioClipTitle.setCycleCount(AudioClip.INDEFINITE);
            audioClipTitle.play();
        }

        startScene = new Scene(layout, backgroundImage.getImage().getWidth()* DuckHunt.SCALE, backgroundImage.getImage().getHeight()* DuckHunt.SCALE);

        primaryStage.setResizable(false);
        primaryStage.setTitle("HUBBM Duck Hunt");
        primaryStage.getIcons().add(new Image("assets/favicon/1.png"));


        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0.5), e -> {
            if (startLabel.getOpacity() == 1) {
                startLabel.setOpacity(0);
                exitLabel.setOpacity(0);
            } else {
                startLabel.setOpacity(1);
                exitLabel.setOpacity(1);
            }
        }));
        timeline.play();


        primaryStage.setScene(startScene);
        primaryStage.show();

        startScene.setOnKeyPressed(e -> {

            if (e.getCode().toString().equals("ENTER")) {
                try {
                    new SelectionScreen().start(primaryStage);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            else if (e.getCode().toString().equals("ESCAPE")) {
                primaryStage.close();
            }
           
        });

    }
}

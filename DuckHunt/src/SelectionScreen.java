import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class SelectionScreen extends Application {
    private VBox layout = new VBox(DuckHunt.SCALE);
    private Text startLabel;
    private Text exitLabel;
    private Text selectLabel;
    private Font labelFont;
    private Scene selectionScene;
    private Image defCrossImage;
    private ImageView crosshairView;
    private Image defBImage;
    private BackgroundImage backgroundImage;
    private Background background;
    private int crosshairSelection = 1;
    private int backgroundSelection = 1;
    private String soundPath = "assets/effects/Intro.mp3";
    private Media backgroundSound = new Media(StartScreen.class.getResource(soundPath).toURI().toString());

    private MediaPlayer mediaPlayer = new MediaPlayer(backgroundSound);

    /**
     * Constructor for the Selection Screen
     * This method basically sets up the layout and the texts(position, font, color).
     * @throws URISyntaxException
     */
    public SelectionScreen() throws URISyntaxException {
        mediaPlayer.setVolume(DuckHunt.VOLUME);

        defBImage = new Image("assets/background/1.png");
        backgroundImage = new BackgroundImage(defBImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(defBImage.getWidth()* DuckHunt.SCALE, defBImage.getHeight()* DuckHunt.SCALE, false, false, false, false));
        background = new Background(backgroundImage);
        layout.setBackground(background);

        defCrossImage = new Image("assets/crosshair/1.png");
        crosshairView = new ImageView(defCrossImage);
        crosshairView.setFitWidth(10* DuckHunt.SCALE);
        crosshairView.setFitHeight(10* DuckHunt.SCALE);

        crosshairView.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - crosshairView.getFitWidth()/2);
        crosshairView.setTranslateY((backgroundImage.getImage().getHeight()* DuckHunt.SCALE)/2 - crosshairView.getFitHeight()/2);
        layout.getChildren().add(crosshairView);

        labelFont = new Font("Arial", 8* DuckHunt.SCALE);

        selectLabel = new Text("USE ARROW KEYS TO NAVIGATE");
        startLabel = new Text("PRESS ENTER TO START");
        exitLabel = new Text("PRESS ESC TO EXIT");

        selectLabel.setFont(labelFont);
        startLabel.setFont(labelFont);
        exitLabel.setFont(labelFont);

        selectLabel.setFont(Font.font(null, FontWeight.BOLD, 8* DuckHunt.SCALE));
        startLabel.setFont(Font.font(null, FontWeight.BOLD, 8* DuckHunt.SCALE));
        exitLabel.setFont(Font.font(null, FontWeight.BOLD, 8* DuckHunt.SCALE));

        selectLabel.setFill(Color.ORANGE);
        startLabel.setFill(Color.ORANGE);
        exitLabel.setFill(Color.ORANGE);

        selectLabel.setTranslateY(0);
        startLabel.setTranslateY(0);
        exitLabel.setTranslateY(0);

        selectLabel.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - selectLabel.getLayoutBounds().getWidth()/2);
        startLabel.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - startLabel.getLayoutBounds().getWidth()/2);
        exitLabel.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - exitLabel.getLayoutBounds().getWidth()/2);

        layout.getChildren().addAll(selectLabel, startLabel, exitLabel);
    }

    /**
     * This screen is used to select the background and the crosshair.
     * It handles the key events and changes the crosshair and background accordingly with direction keys.
     * @param primaryStage the primary stage for this application, onto which
     *  the application scene can be set. The primary stage will be embedded in
     *  the browser if the application was launched as an applet.
     *  Applications may create other stages, if needed, but they will not be
     *  primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        selectionScene = new Scene(layout, defBImage.getWidth()* DuckHunt.SCALE, defBImage.getHeight()* DuckHunt.SCALE);

        primaryStage.setResizable(false);
        primaryStage.setScene(selectionScene);
        primaryStage.show();

        selectionScene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    if(crosshairSelection == 7){
                        crosshairSelection = 1;
                    }
                    else {
                        crosshairSelection++;
                    }

                    changeCrosshair(crosshairSelection);
                    break;
                case DOWN:
                    if(crosshairSelection == 1){
                        crosshairSelection = 7;
                    }
                    else {
                        crosshairSelection--;
                    }

                    changeCrosshair(crosshairSelection);
                    break;
                case LEFT:
                    if(backgroundSelection == 1){
                        backgroundSelection = 6;
                    }
                    else {
                        backgroundSelection--;
                    }

                    changeBackground(backgroundSelection);
                    break;
                case RIGHT:
                    if(backgroundSelection == 6){
                        backgroundSelection = 1;
                    }
                    else {
                        backgroundSelection++;
                    }

                    changeBackground(backgroundSelection);
                    break;
                case ENTER:

                    selectionScene.addEventFilter(javafx.scene.input.KeyEvent.ANY, javafx.scene.input.KeyEvent::consume);

                    StartScreen.audioClipTitle.stop();
                    mediaPlayer.play();
                    mediaPlayer.setOnEndOfMedia(() -> {

                        try {
                            
                            new Level1(new Image("assets/crosshair/" + crosshairSelection + ".png"), new Image("assets/background/" + backgroundSelection + ".png"), new Image("assets/foreground/" + backgroundSelection + ".png")).start(primaryStage);
                            
                            } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case ESCAPE:
                    try {
                        mediaPlayer.stop();
                        new StartScreen().start(primaryStage);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
            }

        });


    }

    /**
     * This method is used to change the crosshair image.
     * @param i the number of the crosshair image.
     */
    public void changeCrosshair(int i){
        defCrossImage = new Image("assets/crosshair/" + i + ".png");
        crosshairView.setImage(defCrossImage);
        crosshairView.setFitWidth(10* DuckHunt.SCALE);
        crosshairView.setFitHeight(10* DuckHunt.SCALE);
        crosshairView.setTranslateX((backgroundImage.getImage().getWidth()* DuckHunt.SCALE)/2 - crosshairView.getFitWidth()/2);
        crosshairView.setTranslateY((backgroundImage.getImage().getHeight()* DuckHunt.SCALE)/2 - crosshairView.getFitHeight()/2);
    }

    /**
     * This method is used to change the background image.
     * @param i the number of the background image.
     */
    public void changeBackground(int i){
        defBImage = new Image("assets/background/" + i + ".png");
        backgroundImage = new BackgroundImage(defBImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(defBImage.getWidth()* DuckHunt.SCALE, defBImage.getHeight()* DuckHunt.SCALE, false, false, false, false));
        background = new Background(backgroundImage);
        layout.setBackground(background);

    }

}

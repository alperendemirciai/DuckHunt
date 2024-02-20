import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URISyntaxException;

public class Level1 extends Application {
    private Scene levelScene;
    private HBox layout = new HBox();
    private StackPane root = new StackPane();
    private Pane foregroundPane = new Pane();


    private DuckPane duckFirst;

    private Image crosshairSelected;
    private ImageCursor crosshair;
    private Image foregroundSelected;
    private BackgroundImage foregroundImage;
    private Background foreground;
    private Image backgroundSelected;
    private BackgroundImage backgroundImage;
    private Background background;

    private int duckCount=1;
    private int ammo=3;
    private int level=1;
    private boolean isFlashing=false;
    private boolean isSoundPlayed=false;


    private Text levelText;
    private Text ammoText;
    private Text winText;
    private Text nextlevelText;
    private Text loseText;
    private Text restartText;
    private Text exitText;
    private Font labelFont;

    private String rifleSoundPath = "assets/effects/Gunshot.mp3";
    private Media rifleSound;
    private MediaPlayer mediaPlayerRifle;
    private String winSoundPath = "assets/effects/LevelCompleted.mp3";
    private String loseSoundPath = "assets/effects/GameOver.mp3";
    private AudioClip audioClipWin;
    private AudioClip audioClipLose;


    private Timeline nextlevelAnimation;
    private Timeline exitTextAnimation;


    /**
     * Constructor for the Level 1
     * This level includes 1 duck flying horizontally and 3 bullets.
     * It basically sets up the layout and the texts(position, font, color).
     * It also sets up the background and foreground.
     * It also sets up the crosshair and audio.
     *
     * @param crosshairSelected is the image of the selected crosshair in Selection Screen.
     * @param backgroundSelected is the image of the selected background in Selection Screen.
     * @param foregroundSelected is the image of the selected foreground in Selection Screen.
     * @throws URISyntaxException
     */
    public Level1(Image crosshairSelected, Image backgroundSelected,Image foregroundSelected) throws URISyntaxException {


        this.crosshairSelected = crosshairSelected;
        this.backgroundSelected = backgroundSelected;
        this.foregroundSelected = foregroundSelected;


        duckFirst = new DuckPane(backgroundSelected,"black");

        labelFont = new Font("Arial", 8* DuckHunt.SCALE);
        labelFont = Font.font(labelFont.getFamily(), FontWeight.BOLD, labelFont.getSize());
        winText = new Text("YOU WIN!");
        nextlevelText = new Text("Press ENTER to play next level");
        levelText = new Text("Level 1/6");
        ammoText = new Text("Ammo Left: 3");
        loseText = new Text("GAME OVER!");
        restartText = new Text("Press ENTER to play again");
        exitText = new Text("Press ESC to exit");

        levelText.setFont(labelFont);
        ammoText.setFont(labelFont);
        loseText.setFont(new Font("Arial Bold", 15* DuckHunt.SCALE));
        restartText.setFont(new Font("Arial Bold", 15* DuckHunt.SCALE));
        exitText.setFont(new Font("Arial Bold", 15* DuckHunt.SCALE));
        winText.setFont(new Font("Arial Bold", 15* DuckHunt.SCALE));
        nextlevelText.setFont(new Font("Arial Bold", 15* DuckHunt.SCALE));

        levelText.setFill(Color.ORANGE);
        ammoText.setFill(Color.ORANGE);
        winText.setFill(Color.ORANGE);
        nextlevelText.setFill(Color.ORANGE);
        loseText.setFill(Color.ORANGE);
        restartText.setFill(Color.ORANGE);
        exitText.setFill(Color.ORANGE);

        levelText.setTranslateX((backgroundSelected.getWidth()* DuckHunt.SCALE/2)-levelText.getLayoutBounds().getWidth()/2);
        levelText.setTranslateY(0);
        ammoText.setTranslateX((backgroundSelected.getWidth()* DuckHunt.SCALE)-(ammoText.getLayoutBounds().getWidth()+ DuckHunt.SCALE*35));
        ammoText.setTranslateY(0);

        backgroundImage = new BackgroundImage(backgroundSelected, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(backgroundSelected.getWidth()* DuckHunt.SCALE, backgroundSelected.getHeight()* DuckHunt.SCALE, false, false, false, false));
        background = new Background(backgroundImage);
        layout.setBackground(background);

        foregroundImage = new BackgroundImage(foregroundSelected, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(foregroundSelected.getWidth()* DuckHunt.SCALE, foregroundSelected.getHeight()* DuckHunt.SCALE, false, false, false, false));
        foreground = new Background(foregroundImage);
        foregroundPane.setBackground(foreground);

        crosshair = new ImageCursor(crosshairSelected);
        crosshair.hotspotXProperty().subtract(crosshairSelected.widthProperty().divide(2));
        crosshair.hotspotYProperty().subtract(crosshairSelected.heightProperty().divide(2));

        foregroundPane.setCursor(crosshair);

        rifleSound = new Media(getClass().getResource(rifleSoundPath).toURI().toString());


        audioClipWin = new AudioClip(getClass().getResource(winSoundPath).toURI().toString());
        audioClipLose = new AudioClip(getClass().getResource(loseSoundPath).toURI().toString());
        audioClipLose.setVolume(DuckHunt.VOLUME);
        audioClipWin.setVolume(DuckHunt.VOLUME);

        nextlevelText.setTranslateX((backgroundSelected.getWidth() * DuckHunt.SCALE / 2) - nextlevelText.getLayoutBounds().getWidth() / 2);
        nextlevelText.setTranslateY((backgroundSelected.getHeight() * DuckHunt.SCALE / 2) - nextlevelText.getLayoutBounds().getHeight() / 2 + 20* DuckHunt.SCALE);
        winText.setTranslateX((backgroundSelected.getWidth() * DuckHunt.SCALE / 2) - winText.getLayoutBounds().getWidth() / 2);
        winText.setTranslateY((backgroundSelected.getHeight() * DuckHunt.SCALE / 2) - winText.getLayoutBounds().getHeight() / 2);

        loseText.setTranslateX((backgroundSelected.getWidth() * DuckHunt.SCALE / 2) - loseText.getLayoutBounds().getWidth() / 2);
        loseText.setTranslateY((backgroundSelected.getHeight() * DuckHunt.SCALE / 2) - loseText.getLayoutBounds().getHeight() / 2 - 20* DuckHunt.SCALE);
        restartText.setTranslateX((backgroundSelected.getWidth() * DuckHunt.SCALE / 2) - restartText.getLayoutBounds().getWidth() / 2);
        restartText.setTranslateY((backgroundSelected.getHeight() * DuckHunt.SCALE / 2) - restartText.getLayoutBounds().getHeight() / 2);
        exitText.setTranslateX((backgroundSelected.getWidth() * DuckHunt.SCALE / 2) - exitText.getLayoutBounds().getWidth() / 2);
        exitText.setTranslateY((backgroundSelected.getHeight() * DuckHunt.SCALE / 2) - exitText.getLayoutBounds().getHeight() / 2 + 20* DuckHunt.SCALE);

        loseText.setVisible(false);
        restartText.setVisible(false);
        exitText.setVisible(false);
        winText.setVisible(false);
        nextlevelText.setVisible(false);
        foregroundPane.getChildren().addAll(winText, nextlevelText, loseText, restartText, exitText);

    }


    /**
     * This method is called to start the Level1 scene.
     * It handles the cases of the game such as the player winning or losing the level.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        levelText.setText("Level "+level+"/6");
        ammoText.setText("Ammo Left: "+ammo);

        layout.getChildren().addAll(levelText,ammoText);

        root.getChildren().addAll(layout);
        root.getChildren().addAll(duckFirst.getDuckPane());
        root.getChildren().addAll(foregroundPane);


        levelScene = new Scene(root,backgroundSelected.getWidth()* DuckHunt.SCALE,backgroundSelected.getHeight()* DuckHunt.SCALE);
        duckFirst.playHorizontal();

        levelScene.setOnMouseClicked(e->{

            if(ammo>0 && duckCount==0){
                displayWin(primaryStage);
            }
            else if(ammo>0) {
                ammo--;
                ammoText.setText("Ammo Left: " + ammo);

                mediaPlayerRifle = new MediaPlayer(rifleSound);
                mediaPlayerRifle.setVolume(DuckHunt.VOLUME);
                mediaPlayerRifle.play();
                double mouseX = e.getSceneX();
                double mouseY = e.getSceneY();

                if (mouseX >= duckFirst.getDuckX() && mouseX <= duckFirst.getDuckX() + duckFirst.getDuckWidth() && mouseY >= duckFirst.getDuckY() && mouseY <= duckFirst.getDuckY() + duckFirst.getDuckHeight() && !duckFirst.isDuckHit()) {
                    duckFirst.stopAnimation();
                    duckFirst.hitDuck();
                    duckFirst.playFall();
                    duckCount--;

                }
                if(duckCount==0){
                    displayWin(primaryStage);
                } else if (ammo==0) {
                    displayLose(primaryStage);
                }
            } else if (duckCount>0) {
                displayLose(primaryStage);

            }

        });
        primaryStage.setScene(levelScene);
        primaryStage.show();



    }

    /**
     * This method is called to display the win screen.
     * It handles the key events of the game such as the player pressing the enter key to go to the next level.
     * It also displays the win text and the next level text.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     */
    public void displayWin(Stage primaryStage){
        //levelScene.addEventFilter(javafx.scene.input.KeyEvent.ANY, javafx.scene.input.KeyEvent::consume);

        if(!(audioClipWin.isPlaying()) && !isSoundPlayed){
            audioClipWin.play();
            isSoundPlayed = true;
        }
        nextlevelText.setVisible(true);
        winText.setVisible(true);
        if(!isFlashing) {
            nextlevelAnimation = new Timeline();
            nextlevelAnimation.setCycleCount(Timeline.INDEFINITE);
            nextlevelAnimation.getKeyFrames().add(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0.5), event -> {
                if (nextlevelText.getOpacity() == 1) {
                    nextlevelText.setOpacity(0);
                } else {
                    nextlevelText.setOpacity(1);
                }
            }));
            isFlashing = true;
            nextlevelAnimation.play();
        }
        levelScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                nextlevelAnimation.stop();
                audioClipWin.stop();
                try {
                    new Level2(crosshairSelected,backgroundSelected,foregroundSelected).start(primaryStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    /**
     * This method is called to display the lose screen.
     * It handles the key events of the game such as the player pressing the enter key to restart the level.
     * It also displays the lose text and the restart text.
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     */
    public void displayLose(Stage primaryStage){
        //levelScene.addEventFilter(javafx.scene.input.KeyEvent.ANY, javafx.scene.input.KeyEvent::consume);

        if(!(audioClipLose.isPlaying()) && !isSoundPlayed){
            audioClipLose.play();
            isSoundPlayed = true;
        }

        loseText.setVisible(true);
        restartText.setVisible(true);
        exitText.setVisible(true);
        if(!isFlashing) {
            exitTextAnimation = new Timeline();
            exitTextAnimation.setCycleCount(Timeline.INDEFINITE);
            exitTextAnimation.getKeyFrames().add(new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0.5), event -> {
                if (exitText.getOpacity() == 1) {
                    exitText.setOpacity(0);
                    restartText.setOpacity(0);
                } else {
                    exitText.setOpacity(1);
                    restartText.setOpacity(1);
                }
            }));
            isFlashing = true;
            exitTextAnimation.play();
        }
        levelScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                exitTextAnimation.stop();
                audioClipLose.stop();
                try {
                    new Level1(crosshairSelected,backgroundSelected,foregroundSelected).start(primaryStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                exitTextAnimation.stop();
                audioClipLose.stop();
                try {
                    new StartScreen().start(primaryStage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

}

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class DuckPane {
    private Image backgroundSelected;

    private String duckColor;

    private Image duckImage;
    private ImageView duckView;
    private double duckX=0;
    private double duckY=0;
    private double duckWidth;
    private double duckHeight;
    private double dxDuck=20* DuckHunt.SCALE;
    private double dyDuck=20* DuckHunt.SCALE;
    private int currentDuck=1;
    private boolean isDuckHit=false;

    private AudioClip duckFallSound;

    private Pane duckPane = new Pane();

    private Timeline animation;


    /**
     * Constructor for the DuckPane class
     * It takes in the background image and the color of the duck to be displayed
     * It then creates the duck image and sets the size and position of the duck
     * It then adds the duck to the duckPane
     * It also creates the sound for when the duck falls
     * @param backgroundSelected
     * @param duckColor
     */
    public DuckPane(Image backgroundSelected,String duckColor){
        this.backgroundSelected = backgroundSelected;
        this.duckColor = duckColor;

        duckImage = new Image("assets/duck_"+duckColor+"/4.png");
        duckView = new ImageView(duckImage);
        duckView.setFitHeight(duckImage.getHeight()* DuckHunt.SCALE);
        duckView.setFitWidth(duckImage.getWidth()* DuckHunt.SCALE);
        duckHeight = duckImage.getHeight()* DuckHunt.SCALE;
        duckWidth = duckImage.getWidth()* DuckHunt.SCALE;
        duckView.setTranslateX(duckX);
        duckView.setTranslateY(duckY);
        duckPane.getChildren().add(duckView);
        duckFallSound = new AudioClip(getClass().getResource("assets/effects/DuckFalls.mp3").toString());


    }

    /**
     * This method is called when the duck is hit
     * It changes the image of the duck to the falling duck
     * It also plays the sound of the duck falling
     */
    public boolean isDuckHit() {
        return isDuckHit;
    }


    /**
     * This method is called for setting the ducks' x position
     * @param duckX
     */
    public void setDuckX(double duckX) {
        this.duckX = duckX;
        duckView.setTranslateX(duckX);
    }
    /**
     * This method is called for setting the ducks' y position
     * @param duckY
     */
    public void setDuckY(double duckY) {
        this.duckY = duckY;
        duckView.setTranslateY(duckY);
    }
    /**
     * This method is called for setting the duck move horizontally
     */
    public void playHorizontal(){
        animation = new Timeline(new KeyFrame(Duration.millis(200), e->moveDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /**
     * This method is called for animating the fall of duck
     */
    public void playFall(){
        animation = new Timeline(new KeyFrame(Duration.millis(200), e->fallDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /**
     * This method is called for setting the duck move diagonally
     */
    public void playDiagonal(){
        animation = new Timeline(new KeyFrame(Duration.millis(200), e->collideDuck()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

    }

    /**
     * This method is called to get the ducks width
     */
    public double getDuckWidth() {
        return duckWidth;
    }

    /**
     * This method is called to get the ducks height
     */
    public double getDuckHeight() {
        return duckHeight;
    }

    /**
     * This method is called to get the ducks' x position
     */
    public double getDuckX(){
        return duckX;
    }

    /**
     * This method is called to get the ducks' y position
     */
    public double getDuckY(){
        return duckY;
    }

    /**
     * This method is called to get the duckPane to show it on the screen
     */
    public Pane getDuckPane(){
        return duckPane;
    }

    /**
     * This method is called to stop the animation of the duck
     */
    public void stopAnimation(){
        animation.stop();
    }

    /**
     * This method is generated for hit moment of the duck
     * It changes the image of the duck to the falling duck
     */
    public void hitDuck(){
        isDuckHit=true;
        duckFallSound.setVolume(DuckHunt.VOLUME);
        duckFallSound.play();
        duckImage = new Image("assets/duck_"+duckColor+"/7.png");
        duckView.setImage(duckImage);
        duckView.setFitHeight(duckImage.getHeight()* DuckHunt.SCALE);
        duckView.setFitWidth(duckImage.getWidth()* DuckHunt.SCALE);
    }

    /**
     * This method is generated for the fall animation of the duck
     * It changes the image of the duck to the falling duck
     * It also changes the position of the duck vertically
     */
    private void fallDuck() {
        duckView.setScaleY(1);
        dyDuck=20;
        duckImage = new Image("assets/duck_"+duckColor+"/8.png");
        duckView.setImage(duckImage);
        duckView.setFitHeight(duckImage.getHeight()* DuckHunt.SCALE);
        duckView.setFitWidth(duckImage.getWidth()* DuckHunt.SCALE);

        if(dxDuck>0){
            duckView.setScaleX(1);
        }
        else if(dxDuck<0){
            duckView.setScaleX(-1);
        }


        if (duckY< backgroundSelected.getHeight()* DuckHunt.SCALE-duckHeight){
            duckY+=dyDuck;
            duckView.setTranslateY(duckY);
        }

    }

    /**
     * This method is generated for the diagonal animation of the duck
     * It changes the image of the duck to the flying duck
     * It also changes the position of the duck both horizontally and vertically in a collusive way
     * It also changes the direction of the duck when it collides with the borders both vertically and horizontally
     */
    private void collideDuck() {
        if(duckX<0 || duckX> backgroundSelected.getWidth()* DuckHunt.SCALE-duckWidth){

            dxDuck*=-1;
        }
        if(duckY<0 || duckY> backgroundSelected.getHeight()* DuckHunt.SCALE-duckHeight){

            dyDuck*=-1;
        }

        if(currentDuck==1){
            duckImage = new Image("assets/duck_"+duckColor+"/1.png");
            duckView.setImage(duckImage);
            currentDuck=2;
        }
        else if(currentDuck==2){
            duckImage = new Image("assets/duck_"+duckColor+"/2.png");
            duckView.setImage(duckImage);
            currentDuck=3;
        }
        else if(currentDuck==3){
            duckImage = new Image("assets/duck_"+duckColor+"/3.png");
            duckView.setImage(duckImage);
            currentDuck=1;
        }

        if(dxDuck > 0) {
            duckView.setScaleX(1);
        } else {
            duckView.setScaleX(-1);
        }
        if (dyDuck > 0) {
            duckView.setScaleY(-1);
        } else {
            duckView.setScaleY(1);
        }

        duckX+=dxDuck;
        duckY+=dyDuck;
        duckView.setTranslateX(duckX);
        duckView.setTranslateY(duckY);

    }

    /**
     * This method is generated for the horizontal animation of the duck
     * It changes the image of the duck to the flying duck with wings up and down periodically
     * It also changes the position of the duck horizontally and vertically(ossilating).
     */
    private void moveDuck() {
        double levelY = 5* DuckHunt.SCALE;
        if(duckX<0 || duckX> backgroundSelected.getWidth()* DuckHunt.SCALE-duckWidth){
            dxDuck*=-1;
        }

        if(currentDuck==1){
            duckImage = new Image("assets/duck_"+duckColor+"/4.png");
            duckView.setImage(duckImage);
            duckView.setTranslateY(10* DuckHunt.SCALE);
            currentDuck=2;
            levelY=5* DuckHunt.SCALE;
        }
        else if(currentDuck==2){
            duckImage = new Image("assets/duck_"+duckColor+"/5.png");
            duckView.setImage(duckImage);
            currentDuck=3;
        }
        else if(currentDuck==3){
            duckImage = new Image("assets/duck_"+duckColor+"/6.png");
            duckView.setImage(duckImage);
            duckView.setTranslateY(duckY-10* DuckHunt.SCALE);
            currentDuck=1;
            levelY=-10* DuckHunt.SCALE;
        }

        if(dxDuck > 0) {
            duckView.setScaleX(1);
        } else {
            duckView.setScaleX(-1);
        }

        duckX+=dxDuck;
        duckY+=levelY;
        duckView.setTranslateX(duckX);
        duckView.setTranslateY(duckY);

    }
}

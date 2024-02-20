import javafx.application.Application;

import java.net.URISyntaxException;

public class DuckHunt {
	
    // SCALE constant is for scaling the game.
	// This constant generally varies between 1 and 4.
    public static final double SCALE = 3;
	
	//VOLUME constant is for adjusting the volume of the sound effects.
	// This constant varies between 0 and 1.0 
    public static final double VOLUME = 0.5;

    /**
     * Driver method which launches the application
     * @param args
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws URISyntaxException {
        Application.launch(StartScreen.class, args);
    }

}
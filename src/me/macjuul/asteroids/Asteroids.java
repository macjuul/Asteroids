package me.macjuul.asteroids;

import java.util.HashSet;

import javafx.application.Application;
import javafx.stage.Stage;
import me.macjuul.asteroids.util.Util;

public class Asteroids extends Application {
	public static HashSet<Integer> KEYS = new HashSet<Integer>();
	public static int WIDTH;
    public static int HEIGHT;
    public static int FRAME;
    
    public static Stage window;
    
	public static void main(String[] args) {
		WIDTH = 1000;
		HEIGHT = 650;
		
		launch(args);
	}

	@Override
	public void start(Stage ps) throws Exception {
		window = ps;
		
		// Setting window settings
		window.setTitle("Asteroids Java v0.1");
		window.setResizable(false);
		
		window.setMinWidth(WIDTH);
		window.setMaxHeight(HEIGHT);
		window.setWidth(WIDTH);
		window.setHeight(HEIGHT);
		
		Util.getResource("icon_128.png").toString();
		
		// Open the window
		window.show();
	}
	
}
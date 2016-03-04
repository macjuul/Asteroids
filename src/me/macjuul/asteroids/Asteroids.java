package me.macjuul.asteroids;

import java.util.HashSet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.macjuul.asteroids.spaceship.Spaceship;
import me.macjuul.asteroids.util.Util;

public class Asteroids extends Application {
	public final static int WALL_WIDTH = 170;
	public final static Font SPACE_FONT = Font.loadFont(Util.getResource("SpaceFont.ttf").toString(), 36);
	
	public static int WIDTH;
    public static int HEIGHT;
    public static int FRAME;
    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;
    
    public static WindowControl control;
    public static Stage window;
    public static Canvas cvs;
    public static MediaPlayer music;
    public static MediaPlayer breakSound;
    public static MediaPlayer explosionSound;
    public static Game render;
    public static int score;
    
    public static Spaceship spaceship = new Spaceship();
    public static HashSet<KeyCode> keys = new HashSet<KeyCode>();
    public static int shipFrame = 0;
    public static long frame = 0;
    public static boolean gameRunning = true;
    public static boolean gameActive = false;
    
	public static void main(String[] args) {
		WIDTH = 1100;
		HEIGHT = 680;
		
		launch(args);
	}
	
	public static void updateDimensions(Boolean updateCanvas) {
		CANVAS_WIDTH = WIDTH - (WALL_WIDTH * 2);
		CANVAS_HEIGHT = HEIGHT;
		
		if(updateCanvas) {
			Canvas cvs = control.getCanvas(1);
			cvs.setWidth(Asteroids.WIDTH);
			cvs.setHeight(Asteroids.HEIGHT);
			
			Image wall = Util.getImage("side_wall.png");
			
			Canvas leftWall =  control.getLeftWall();
			Canvas rightWall =  control.getRightWall();
	    	
	    	GraphicsContext lw = leftWall.getGraphicsContext2D();
	    	GraphicsContext rw = rightWall.getGraphicsContext2D();
	    	
	    	leftWall.setHeight(Asteroids.HEIGHT);
	    	rightWall.setHeight(Asteroids.HEIGHT);
	    	
	    	double wall_height = (((Asteroids.WALL_WIDTH * 100) / wall.getWidth()) / 100) * wall.getHeight();
	    	
	    	for(int h = 0; h < Asteroids.HEIGHT; h += wall_height) {
	    		lw.drawImage(wall, 0, wall_height * h, Asteroids.WALL_WIDTH, wall_height);
	        	Util.drawRotatedImage(rw, wall, 180, 0, wall_height * h, Asteroids.WALL_WIDTH, wall_height);
	    	}
		}
	}

	@Override
	public void start(Stage ps) throws Exception {
		window = ps;
		control = new WindowControl();
		
		music = new MediaPlayer(new Media(Util.getResource("music.mp3").toString()));
		breakSound = new MediaPlayer(new Media(Util.getResource("asteroid_break.mp3").toString()));
		explosionSound = new MediaPlayer(new Media(Util.getResource("explosion.mp3").toString()));
		
		window.setTitle("Asteroids: Space adventure - By macjuul");
		
		Image icon_128 = Util.getImage("icon_128.png");
		Image icon_32 = Util.getImage("icon_32.png");
		Image icon_16 = Util.getImage("icon_16.png");
		
		window.getIcons().addAll(icon_128, icon_32, icon_16);
		
		window.setMinWidth(WIDTH);
		window.setMinHeight(HEIGHT);
		window.setWidth(WIDTH);
		window.setHeight(HEIGHT);
		
		Parent layout = (Parent) control.loadLayout();
		
		Scene s = new Scene(layout);
		
		// Set our stylesheet
		s.getStylesheets().add(Util.getResource("window/style.css").toExternalForm());
		
		s.setCursor(Cursor.CROSSHAIR);
		
		window.setScene(s);
		
		window.widthProperty().addListener((ObservableValue<? extends Number> ov, Number oldN, Number newN) -> {
	        WIDTH = newN.intValue();
	        
	        updateDimensions(true);
	    });
		
		window.heightProperty().addListener((ObservableValue<? extends Number> ov, Number oldN, Number newN) -> {
			HEIGHT = (int) newN.intValue();
	        
	        updateDimensions(true);
	    });
		
		// Open the window
		window.show();
		
		window.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
	}
	
}
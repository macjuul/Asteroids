package me.macjuul.asteroids;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import me.macjuul.asteroids.layers.MenuLayer;
import me.macjuul.asteroids.layers.SpaceLayer;
import me.macjuul.asteroids.util.Util;

public class WindowControl extends BorderPane implements Initializable {
	public AnimationTimer gameLoop;
	
	private GraphicsContext gc;
	
	@FXML
	private Canvas cvs1;
	
	@FXML
	private Canvas cvs2;
	
	@FXML
	private Canvas cvs3;
	
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private StackPane layout;
	
	@FXML
	private Canvas left_wall;
	
	@FXML
	private Canvas right_wall;
	
	@FXML
	private ProgressBar progress;

    public Object loadLayout() {
        FXMLLoader fxmlLoader = new FXMLLoader(Util.getResource("window/GameLayout.fxml"));
        fxmlLoader.setController(this);

        try {
            return fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
    	left_wall.setWidth(Asteroids.WALL_WIDTH);
    	right_wall.setWidth(Asteroids.WALL_WIDTH);
    	
    	left_wall.setHeight(Asteroids.HEIGHT);
    	right_wall.setHeight(Asteroids.HEIGHT);
    	
    	Image wall = Util.getImage("side_wall.png");
    	
    	GraphicsContext lw = left_wall.getGraphicsContext2D();
    	GraphicsContext rw = right_wall.getGraphicsContext2D();
    	
    	lw.drawImage(wall, 0, 0, Asteroids.WALL_WIDTH, 700);
    	rw.drawImage(wall, 0, 0, Asteroids.WALL_WIDTH, 700);
    	
    	cvs1.setWidth(Asteroids.WIDTH);
    	cvs1.setHeight(Asteroids.HEIGHT);
    	cvs2.setWidth(Asteroids.WIDTH);
    	cvs2.setHeight(Asteroids.HEIGHT);
    	cvs3.setWidth(Asteroids.WIDTH);
    	cvs3.setHeight(Asteroids.HEIGHT);
    	
        gc = cvs1.getGraphicsContext2D();
        
        Asteroids.render = new Game(gc);
        Asteroids.render.addLayer(new SpaceLayer());
        Asteroids.render.addLayer(new MenuLayer());
        Asteroids.render.start();
        
        Asteroids.window.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
        	Asteroids.keys.add(e.getCode());
        });
        
        Asteroids.window.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
        	Asteroids.keys.remove(e.getCode());
        });
    }
    
    public Canvas getCanvas(int n) {
    	switch(n) {
    	case 2: return cvs2;
    	case 3: return cvs3;
    	}
		return cvs1;
    }
    
    public GraphicsContext getGraphics() {
    	return gc;
    }
}

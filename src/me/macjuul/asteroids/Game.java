package me.macjuul.asteroids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import me.macjuul.asteroids.layers.Layer;
import me.macjuul.asteroids.util.Util;

public class Game extends AnimationTimer {
	public static final int xStart = Asteroids.WALL_WIDTH + 8;
	public static List<ArrayList<Integer>> bullets = new ArrayList<ArrayList<Integer>>();
	public static List<Asteroid> asteroids = new ArrayList<Asteroid>();
	
	private GraphicsContext gfx;
	private List<Layer> layers;
	public double asteroidSpeedModifier;
	public Image explosionSprite;
	public double explosionSpriteHeight;
	public double explosionSpriteWidth;
	public List<Explosion> explosions;
    
    public Game(GraphicsContext gfx) {
    	this.gfx = gfx;
    	this.layers = new ArrayList<Layer>();
    	this.asteroidSpeedModifier = 0;
    	this.explosionSprite = Util.getImage("explosion-spritesheet.png");
    	this.explosions = new ArrayList<Explosion>();
    	this.explosionSpriteHeight = explosionSprite.getHeight();
    	this.explosionSpriteWidth = explosionSprite.getWidth() / 26;
    	
    	Asteroids.window.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			double x = e.getX();
			double y = e.getY();
			
			Iterator<Layer> i = layers.iterator();
			
			while(i.hasNext()) {
				i.next().onClick(x, y);
			}
        });
    }
    
    public void addLayer(Layer s) {
    	this.layers.add(s);
    	s.init();
    }
    
    public void setLayer(int index, Layer s) {
    	this.layers.set(index, s);
    }
    
    public void removeLayer(int index) {
    	this.layers.remove(index);
    }
    
    public void removeAllLayers() {
    	this.layers.clear();
    }
    
    public Layer getLayer(int index) {
    	return this.layers.get(index);
    }

	@Override
	public void handle(long n) {
		if(Asteroids.window.isFocused() && Asteroids.gameRunning) {
			Asteroids.frame++;
			Iterator<Layer> i = layers.iterator();
			
			this.asteroidSpeedModifier += 0.001;
			
			while(i.hasNext()) {
				i.next().render(gfx);
			}
		}
	}
}

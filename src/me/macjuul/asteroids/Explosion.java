package me.macjuul.asteroids;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class Explosion {
	private int x;
	private int y;
	private double velocity;
	private int frame;
	private Image frameImg;
	private double scale = 1;
	
	public Explosion(int x, int y, double vel, double scale) {
		this.x = x;
		this.y = y;
		this.velocity = vel;
		this.frame = 0;
		this.scale = scale;
		
		Asteroids.render.explosions.add(this);
	}
	
	public Image update() {
		if(Asteroids.frame % 2 == 0) {
			frame++;
		}
		
		int frameLoc = (int) Asteroids.render.explosionSpriteWidth * frame;
		
		PixelReader reader = Asteroids.render.explosionSprite.getPixelReader();
		WritableImage asteroidImage = new WritableImage(reader, frameLoc, 0, (int) Asteroids.render.explosionSpriteWidth, (int) Asteroids.render.explosionSpriteHeight);
		
		this.frameImg = asteroidImage;
		
		return frameImg;
	}
	
	public double getScale() {
		return this.scale;
	}
	
	public int move() {
		y += velocity;
		
		return y;
	}
	
	public boolean isFinished() {
		return frame >= 24;
	}
	
	public int getX() {
		return x;
	}
}

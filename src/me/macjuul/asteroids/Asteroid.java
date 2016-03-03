package me.macjuul.asteroids;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import me.macjuul.asteroids.util.Util;

public class Asteroid {
	private int x;
	private int y;
	private double size;
	private int rotation;
	private int rotationSpeed;
	private boolean rotationDir;
	private Image asteroidImage;
	
	public Asteroid() {
		this.y = -100;
		this.size = Util.randomBetween(0.25, 0.55);
		this.rotation = 0;
		this.rotationSpeed = (int) (1 / this.size);
		this.rotationDir = new Random().nextBoolean();
		
		Image sprites = Util.getImage("asteroids.png");
		double asteroidWidth = sprites.getWidth() / 4;
		double asteroidHeight = sprites.getHeight();
		
		this.x = Util.randomBetween((int) (Asteroids.WALL_WIDTH + (asteroidWidth / 2)), (int) (Asteroids.WIDTH - Asteroids.WALL_WIDTH - (asteroidWidth / 2)));
		
		PixelReader reader = sprites.getPixelReader();
		WritableImage asteroidImage = new WritableImage(reader, (int) (asteroidWidth * Util.randomBetween(0, 3)), 0, (int) asteroidWidth, (int) asteroidHeight);
	
		this.asteroidImage = asteroidImage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public Image getAsteroidImage() {
		return asteroidImage;
	}
	
	public boolean rotationDir() {
		return rotationDir;
	}
}

package me.macjuul.asteroids.spaceship;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;
import me.macjuul.asteroids.Asteroids;
import me.macjuul.asteroids.Game;
import me.macjuul.asteroids.util.Util;

public class Spaceship {
	private SpaceshipSkin selectedSkin = SpaceshipSkin.EXO_FIGHTER;
	public static int position;
    public static final int width = 100;
    public static final int height = 136;
    public static int velocity = 0;
    public static int y = 0;
    public static boolean dead = false;
    public static boolean mayMove = false;
    public static boolean mayShoot = true;
    public static boolean inPlace = false;
    public static byte angle = 1;
    public static final double momentum = 0.91;
    private AudioClip shootSound = new AudioClip(Util.getResource("shoot.mp3").toString());
    
    private void shoot_bullet(int x) {
    	ArrayList<Integer> bullet = new ArrayList<Integer>();
    	bullet.add(Game.xStart + position + x + 10);
    	bullet.add(Asteroids.CANVAS_HEIGHT - y + 20);
    	bullet.add((int) angle);
    	bullet.add(0);
    	Game.bullets.add(bullet);
    }
	
	public void shoot() {
		if(mayShoot) {
			shoot_bullet(0);
			shoot_bullet(77);
			
			mayShoot = false;
			
			Util.setTimeout(600L, () -> {
				mayShoot = true;
			});
			
			shootSound.play();
		}
	}
	
	public void damage(int a) {}
	
	public void setCurrentSkin(SpaceshipSkin skin) {
		this.selectedSkin = skin;
	}
	
	public SpaceshipSkin getCurrentSkin() {
		return this.selectedSkin;
	}
}

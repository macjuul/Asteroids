package me.macjuul.asteroids.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import me.macjuul.asteroids.Asteroids;
import me.macjuul.asteroids.Game;
import me.macjuul.asteroids.util.Util;

public class MenuLayer implements Layer {
	private Image splashImg;
	private double splashWidth;
	private double splashWidthRaw;
	private double splashHeight;
	private double splashHeightRaw;
	private double scale = 1;
	private double scaleMax = 0.05;
	private double scaleFactor = 0.002;
	private boolean scaleState = true;
	private double opacity = 1;
	private boolean fade = false;
	private MediaPlayer music;
	private double musicVolume = 0; // 0.3
	
	@Override
	public void init() {
		this.splashImg = Util.getImage("splash.png");
		this.splashWidthRaw = (splashImg.getWidth() / 2);
		this.splashHeightRaw = (splashImg.getHeight() / 2);
		this.music = new MediaPlayer(new Media(Util.getResource("music.mp3").toString()));
		
		music.setVolume(musicVolume);
		music.play();
	}

	@Override
	public void render(GraphicsContext gfx) {
		if(scaleState) {
			scale += scaleFactor;
			
			if(scale > 1 + scaleMax) {
				scaleState = !scaleState;
			}
		} else {
			scale -= scaleFactor;
			
			if(scale < 1 - scaleMax) {
				scaleState = !scaleState;
			}
		}
		
		splashWidth = splashWidthRaw * scale;
		splashHeight = splashHeightRaw * scale;
		
		if(fade) {
			opacity -= 0.04;
			if(Asteroids.frame % 10 == 0) {
				musicVolume -= 0.04;
				music.setVolume(musicVolume);
			}
			gfx.setGlobalAlpha(opacity);
		}
		
		gfx.drawImage(splashImg, Game.xStart + (Asteroids.CANVAS_WIDTH / 2) - (splashWidth / 2), 100, splashWidth, splashHeight);
	
		gfx.setStroke(Color.WHITE);
		gfx.setFill(Color.WHITE);
		gfx.setLineWidth(4);
		gfx.setFont(Asteroids.SPACE_FONT);
		
		gfx.strokeRoundRect(Game.xStart + 100, Asteroids.HEIGHT - 280, 180, 60, 20, 20);
		gfx.strokeRoundRect(Asteroids.WIDTH - Game.xStart - 280, Asteroids.HEIGHT - 280, 180, 60, 20, 20);
		gfx.strokeRoundRect(Asteroids.WIDTH - (Asteroids.WIDTH / 2) - 90, Asteroids.HEIGHT - 180, 180, 60, 20, 20);
		
		gfx.fillText("Play", Game.xStart + 150, Asteroids.HEIGHT - 236, 180);
		gfx.fillText("Settings", Asteroids.WIDTH - Game.xStart - 270, Asteroids.HEIGHT - 236, 180);
		gfx.fillText("About", Asteroids.WIDTH - (Asteroids.WIDTH / 2) - 60, Asteroids.HEIGHT - 135, 180);
		
		if(fade) {
			gfx.setGlobalAlpha(1);
			
			if(opacity <= 0 && musicVolume <= 0) {
				Asteroids.render.removeLayer(1);
				Asteroids.render.addLayer(new PlayLayer());
			}
		}
	}

	@Override
	public void onClick(double x, double y) {
		if(x > Game.xStart + 90
		&& x < Game.xStart + 270
		&& y > Asteroids.HEIGHT - 300
		&& y < Asteroids.HEIGHT - 240) {
			fade = true;
		}
	}

}

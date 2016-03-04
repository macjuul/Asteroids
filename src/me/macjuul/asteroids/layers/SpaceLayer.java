package me.macjuul.asteroids.layers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import me.macjuul.asteroids.Asteroids;
import me.macjuul.asteroids.util.Util;

public class SpaceLayer implements Layer {
	private Image space;
	private int spaceWidth;
    private int spaceHeight;
    private int yStart;
    
	@Override
	public void init() {
		this.space = Util.getImage("space.png");
    	this.spaceWidth = Double.valueOf(space.getWidth()).intValue();
    	this.spaceHeight = Double.valueOf(space.getHeight()).intValue();
	}

	@Override
	public void render(GraphicsContext gfx) {
		yStart = (int) (((Asteroids.frame * 1.5) % spaceHeight) - spaceHeight);
		
        for(int x = 0; x < Asteroids.CANVAS_WIDTH + spaceWidth; x += spaceWidth) {
            for(int y = 0; y < Asteroids.CANVAS_HEIGHT * 2; y += spaceHeight) {
                gfx.drawImage(space, x, y + yStart);
            }
        }
	}

	@Override
	public void onClick(double x, double y) {}

}

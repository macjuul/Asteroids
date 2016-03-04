package me.macjuul.asteroids.layers;

import javafx.scene.canvas.GraphicsContext;

public interface Layer {
	
	public void init();
	
	public void render(GraphicsContext gfx);
	
	public void onClick(double x, double y);
}

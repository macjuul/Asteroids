package me.macjuul.asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javafx.embed.swing.JFXPanel;

@SuppressWarnings("serial")
public class Render extends JFXPanel {
    
    @Override
    public void paint(Graphics g) {
        Graphics2D gfx = (Graphics2D) g;
        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Image space = Util.getImage("space.png");
        int spaceWidth = space.getWidth(null);
        int spaceHeight = space.getHeight(null);
        int yStart = (int) (((Main.frame * 1.5) % spaceHeight) - spaceHeight);
        
        for(int x = 0; x < Main.WIDTH; x += spaceWidth) {
            for(int y = 0; y < Main.HEIGHT * 2; y += spaceHeight) {
                gfx.drawImage(space, x, y + yStart, null);
            }
        }
        
        Image technicWall = Util.getImage("side_wall.png");
        int wallWidth = (int) (technicWall.getWidth(null) / 2.1);
        int wallHeight = (int) (technicWall.getHeight(null) / 2.1);
        
        for(int y = 0; y < Main.HEIGHT; y += wallHeight) {
            gfx.drawImage(technicWall, 0, y, wallWidth, wallHeight, null);
        }
        
        for(int y = 0; y < Main.HEIGHT; y += wallHeight) {
            gfx.drawImage(technicWall, Main.WIDTH, y, -wallWidth, wallHeight, null);
        }
        
        BufferedImage ship = Util.getBufferedImage("spaceship/default.png"); 
        ship = ship.getSubimage(Main.shipFrame, 0, 100, 136);
        gfx.drawImage(ship, Spaceship.position, Main.HEIGHT - 260, null);
        
        for(int b : GameHandler.bullets.keySet()) {
        	HashMap<String, Object> bullet = GameHandler.bullets.get(b);
        	gfx.setColor(Color.RED);
        	gfx.fillRect((int) bullet.get("position"), (int) bullet.get("y"), 3, 10);
        }
    }
}

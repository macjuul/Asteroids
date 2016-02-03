package me.macjuul.asteroids;

import java.util.HashMap;

public class Spaceship {
    public static int position = Main.WIDTH / 2 - 50;
    public static final int width = 100;
    public static final int height = 136;
    public static int velocity = 0;
    public static boolean dead = false;
    public static final double momentum = 0.91;
    private static ShipType skin = ShipType.DEFAULT;
    private static int lastBullet = 0;
    
    public static void shoot() {
        Util.playSound("shoot.mp3");
        
        HashMap<String, Object> bullet1_data = new HashMap<String, Object>();
        HashMap<String, Object> bullet2_data = new HashMap<String, Object>();
        
        int bullet1 = lastBullet + 1;
        int bullet2 = lastBullet + 2;
        		
		lastBullet += 2;
		
		int y = Main.HEIGHT - 235;
        
        bullet1_data.put("position", Spaceship.position + 10);
        bullet1_data.put("y", y);
        bullet1_data.put("angle", 1);
        
        bullet2_data.put("position", Spaceship.position + 86);
        bullet2_data.put("y", y);
        bullet2_data.put("angle", 1);
        
        GameHandler.bullets.put(bullet1, bullet1_data);
        GameHandler.bullets.put(bullet2, bullet2_data); 
    }
    
    public static void damage() {
        
    }
    
    public static String getSkin() {
        return skin.toString();
    }
    
    public static void setSkin(String skin) {
        Spaceship.skin = ShipType.valueOf(skin);
    }
}

package me.macjuul.asteroids;

public class Spaceship {
    public static int position = Main.WIDTH / 2 - 50;
    public static final int width = 100;
    public static final int height = 136;
    public static boolean dead = false;
    private static ShipType skin = ShipType.DEFAULT;
    
    public static void shoot() {
        
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
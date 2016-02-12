package me.macjuul.asteroids.old;

import java.util.HashMap;

public class GameHandler {
    public static boolean gameRunning = false;
    public static boolean music = false;
    public static Difficulty difficultyMode = Difficulty.NORMAL;
    public static HashMap<Integer, HashMap<String, Object>> bullets = new HashMap<Integer, HashMap<String, Object>>();
    public static HashMap<Integer, HashMap<String, Object>> asteroids = new HashMap<Integer, HashMap<String, Object>>();
    
    public static void startGame() {
        GameHandler.gameRunning = true;
    }
    
    public static void pauseGame() {
        
    }
    
    public static void unpauseGame() {
        
    }
    
    public static void endGame() {
        GameHandler.gameRunning = false;
    }
    
    public static void spawnAsteroid() {
    	
    }
}

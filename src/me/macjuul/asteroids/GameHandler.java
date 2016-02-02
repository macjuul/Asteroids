package me.macjuul.asteroids;

public class GameHandler {
    public static boolean gameRunning = false;
    public static boolean music = false;
    public static Difficulty difficultyMode = Difficulty.NORMAL;
    
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
}

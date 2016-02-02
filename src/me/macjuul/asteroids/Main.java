package me.macjuul.asteroids;

import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
    public static int WIDTH = 1200;
    public static JFrame window;
    public static int HEIGHT = 800;
    public static int frame = 0;
    public static int shipFrame = 0;

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        final JFrame window = new JFrame("Asteroids - By macjuul");
        URL iconURL = Main.class.getResource("/res/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);

        window.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        window.setLocationRelativeTo(null);
        window.setIconImage(icon.getImage());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new Render());
        window.setVisible(true);
        
        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension size = window.getSize();
                Main.WIDTH = (int) size.getWidth();
                Main.HEIGHT = (int) size.getHeight();
                
                Spaceship.position = Main.WIDTH / 2 - 50;
            }
        });
        
        /* Test stuffs */
        
        Util.playSound("music.mp3");
        
        Util.setTimeout(1000L, new Runnable() {
            public void run() {
                Util.playSound("explosion.mp3");
            }
        });
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    switch(e.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        System.out.print("Down");
                        break;
                    case KeyEvent.KEY_RELEASED:
                        System.out.print("Up");
                        break;
                    }
                    return false;
                }
                return false;
            }
        });
        
        GameHandler.startGame();
        
        while(true) {
            if(GameHandler.gameRunning) {
                frame++;
                
                if(frame % 5 == 0) {
                    shipFrame = frame * 100 % 400;
                }
                
                window.repaint(16);
                Thread.sleep(16);
            }
        }
    }
}

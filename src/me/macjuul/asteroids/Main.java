package me.macjuul.asteroids;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UIManager;

public class Main {
    public static int WIDTH = 1200;
    public static JFrame window;
    public static int HEIGHT = 800;
    public static int frame = 0;
    public static int shipFrame = 0;
    public static HashSet<Integer> keys = new HashSet<Integer>();

    public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
    	
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
    	
    	final JFrame window = new JFrame("Asteroids - By macjuul");
        URL iconURL = Main.class.getResource("/res/icon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        
        JMenuBar menuBar;
    	JMenu menu;
    	window.setMinimumSize(new Dimension((int) (WIDTH / 1.2), (int) (HEIGHT / 1.2)));
        window.setLocationRelativeTo(null);
        window.setIconImage(icon.getImage());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new Render());
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

    	//Create the menu bar.
    	menuBar = new JMenuBar();

    	//Build the first menu.
    	menu = new JMenu("A Menu");
    	menu.setMnemonic(KeyEvent.VK_A);
    	menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
    	menuBar.add(menu);
    	
    	window.setJMenuBar(menuBar);
    	window.setVisible(true);
    	window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension size = window.getSize();
                Main.WIDTH = (int) size.getWidth();
                Main.HEIGHT = (int) size.getHeight();
                
                Spaceship.position = Main.WIDTH / 2 - 50;
            }
        });
    	
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                switch(e.getID()) {
                case KeyEvent.KEY_PRESSED:
                    keys.add(e.getKeyCode());
                    break;
                case KeyEvent.KEY_RELEASED:
                    keys.remove(e.getKeyCode());
                    break;
                }
                return false;
            }
        });
        
        Image technicWall = Util.getImage("side_wall.png");
        int wallWidth = (int) (technicWall.getWidth(null) / 2.1);
        
        GameHandler.startGame();
        
        while(true) {
            if(GameHandler.gameRunning) {
                frame++;
                
                if(frame % 5 == 0) {
                    shipFrame = frame * 100 % 400;
                }
                
                Spaceship.velocity *= Spaceship.momentum;
                Spaceship.position += Spaceship.velocity / 17;
                
                if(!Spaceship.dead){
                	int velocitySpeed = WIDTH / 30;
                	
                	if(keys.contains(KeyEvent.VK_LEFT) || keys.contains(KeyEvent.VK_A)) {
                		Spaceship.velocity -= velocitySpeed;
                	}
            		if(keys.contains(KeyEvent.VK_RIGHT) || keys.contains(KeyEvent.VK_D)) {
            			Spaceship.velocity  += velocitySpeed;
                	}
            		
            		if(Spaceship.position < 0 + wallWidth) {
                        Spaceship.velocity = 0;
                        Spaceship.position = wallWidth;
                    } else if(Spaceship.position > WIDTH - wallWidth - Spaceship.width) {
                        Spaceship.position = WIDTH - wallWidth - Spaceship.width;
                        Spaceship.velocity = 0;
                    }
                }
                
                window.repaint();
                Thread.sleep(16);
            }
        }
    }
}

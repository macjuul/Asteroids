package me.macjuul.asteroids;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Util {
    private static MediaPlayer player;
    
    private static URL getResource(String res) {
        return Util.class.getResource("/res/" + res);
    }
    
    public static Image getImage(String loc) {
        URL img = Util.getResource(loc);
        return new ImageIcon(img).getImage();
    }
    
    public static BufferedImage getBufferedImage(String loc) {
        Image img = getImage(loc);
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimg;
    }
    
    public static BufferedImage getBufferedImage(Image img) {
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimg;
    }
    
    public static synchronized MediaPlayer playSound(String url) {
        try {
            Media hit = new Media(Util.getResource(url).toString());
            player = new MediaPlayer(hit);
            
            
            player.play();
            
            return player;
        } catch(Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception" + ex.getMessage());
        }
        return null;
    }
    
    public static void setTimeout(Long duration, Runnable csr) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.schedule(csr, duration, TimeUnit.MILLISECONDS);
    }
    
    public static void setInterval(Long delay, Runnable csr) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(csr, 0, delay, TimeUnit.MILLISECONDS);
    }
}

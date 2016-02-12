package me.macjuul.asteroids.util;

import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;

public class Util {
    
    public static URL getResource(String res) {
        return ClassLoader.getSystemResource("/res/" + res);
    }
    
    public static Image getImage(String loc) {
        return new Image(Util.getResource(loc).getFile());
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

package me.macjuul.asteroids.util;

import java.net.URL;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import me.macjuul.asteroids.Asteroids;

public class Util {
    
    public static URL getResource(String res) {
        return Util.class.getClassLoader().getResource("res/" + res);
    }
    
    public static Image getImage(String loc) {
        return new Image(Util.getResource(loc).toString());
    }
    
    public static void setTimeout(Long duration, Runnable csr) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.schedule(csr, duration, TimeUnit.MILLISECONDS);
    }
    
    public static void setInterval(Long delay, Runnable csr) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        scheduler.scheduleAtFixedRate(csr, 0, delay, TimeUnit.MILLISECONDS);
    }
    
    public static boolean isKeyDown(KeyCode c) {
    	return Asteroids.keys.contains(c);
    }
    
    private static void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    public static void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }
    
    public static void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy, double width, double height) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + width / 2, tlpy + height / 2);
        gc.drawImage(image, tlpx, tlpy, width, height);
        gc.restore(); // back to original state (before rotation)
    }
    
    public static int randomBetween(int min, int max) {
    	return new Random().nextInt(max - min) + min;
    }
    
    public static double randomBetween(double min, double max) {
    	return min + (max - min) * new Random().nextDouble();
    }
}

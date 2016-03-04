package me.macjuul.asteroids.layers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.macjuul.asteroids.Asteroid;
import me.macjuul.asteroids.Asteroids;
import me.macjuul.asteroids.Explosion;
import me.macjuul.asteroids.Game;
import me.macjuul.asteroids.GameEvents;
import me.macjuul.asteroids.spaceship.Spaceship;
import me.macjuul.asteroids.util.Util;

public class PlayLayer implements Layer {
	private int vel;
	private Image ship;
	private Image blt;
	private Font statFont;
	
	private static Set<MediaPlayer> activePlayers = new HashSet<MediaPlayer>();
    
	@Override
	public void init() {
    	this.ship = Util.getImage("spaceship/" + Asteroids.spaceship.getCurrentSkin().toString().toLowerCase() + ".png");
    	this.blt = Util.getImage("bullet.png");
    	this.statFont = Font.loadFont(Util.getResource("SpaceFont.ttf").toString(), 25);
    	
    	Asteroids.score = 0;
    	
    	Spaceship.position = (Asteroids.CANVAS_WIDTH / 2) - 50;
	}
	
	@Override
	public void render(GraphicsContext gfx) {
		if(Asteroids.frame % 5 == 0) {
            Asteroids.shipFrame = (int) (Asteroids.frame * 100 % 400);
        }

		Spaceship.velocity *= Spaceship.momentum;
        Spaceship.position += Spaceship.velocity / 17;
        
        PixelReader reader = ship.getPixelReader();
		WritableImage spaceship = new WritableImage(reader, Asteroids.shipFrame, 0, Spaceship.width, Spaceship.height);
		
        if(!Spaceship.dead && Spaceship.mayMove) {
        	int velocitySpeed = Asteroids.CANVAS_WIDTH / 30;
        	
        	// Movement
        	if(Util.isKeyDown(KeyCode.A) || Util.isKeyDown(KeyCode.LEFT)) {
        		Spaceship.velocity -= velocitySpeed;
        	}
        	
        	if(Util.isKeyDown(KeyCode.D) || Util.isKeyDown(KeyCode.RIGHT)) {
    			Spaceship.velocity  += velocitySpeed;
        	}
        	
        	// Shooting
        	if(Util.isKeyDown(KeyCode.SPACE) || Util.isKeyDown(KeyCode.UP)) {
    			Asteroids.spaceship.shoot();
        	}
        	
        	// Collision
    		if(Spaceship.position < 0) {
                Spaceship.velocity = 0;
                Spaceship.position = 0;
            } else if(Spaceship.position > Asteroids.WIDTH - (Asteroids.WALL_WIDTH * 2) - 16 - Spaceship.width) {
            	Spaceship.velocity = 0;
                Spaceship.position = Asteroids.WIDTH - (Asteroids.WALL_WIDTH * 2) - 16 - Spaceship.width;
            }
    		
    		byte angle = 1;
    		
    		if(Spaceship.velocity < 0) {
    			vel = 360 + Spaceship.velocity / 28;
                if(vel < 350) {
                    vel = 350;
                    angle = 0;
                }
            } else {
                vel = Spaceship.velocity / 28;
                if(vel > 10) {
                    vel = 10;
                    angle = 2;
                }
            }
    		
    		Spaceship.angle = angle;
        }
        
        if(Spaceship.y < 210) {
        	Spaceship.y += 3;
        } else {
        	if(!Asteroids.gameActive) {
        		Asteroids.gameActive = true;
        		GameEvents.onGameStart();
        		asteroidLoop();
        	}
        	Spaceship.mayMove = true;
        }
        
        Iterator<ArrayList<Integer>> bullet = Game.bullets.iterator();
    	
        while(bullet.hasNext()) {
        	ArrayList<Integer> b = bullet.next();
        	int x = b.get(0);
        	int y = b.get(1);
        	int angle = b.get(2);
        	int dead = b.get(3);
        	
        	if(dead == 1) {
        	    bullet.remove();
        	} else {
            	if(angle == 0) {
            		angle = 345;
            		x -= 3;
            	} else if(angle == 1) {
            		angle = 0;
            	} else {
            		angle = 15;
            		x += 3;
            	}
            	
            	y -= 10;
            	
            	b.set(0, x);
            	b.set(1, y);
    
            	Util.drawRotatedImage(gfx, blt, angle, x, y, 4, 12);
            	
            	Iterator<Asteroid> i = Game.asteroids.iterator();
            	
                while(i.hasNext()) {
                    Asteroid a = i.next();
                    
                    double x1 = a.getX();
                    double y1 = a.getY();
                    double x2 = x + 2;
                    double y2 = y + 6;
                    
                    double distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                    double dist = a.getWidth() * a.getSize() - 10;
                    
                    if(distance < dist) {
                        Asteroids.score++;
                        
                        i.remove();
                        MediaPlayer p = new MediaPlayer(new Media(Util.getResource("asteroid_break.mp3").toString()));
                        new Explosion((int) x1, (int) y1, (1 / a.getSize()) + Asteroids.render.asteroidSpeedModifier, a.getSize() * 2.5);
                        b.set(3, 1);
                        
                        activePlayers.add(p);
                        p.setOnEndOfMedia(() -> {
                            p.dispose();
                            activePlayers.remove(p);
                        });
                        p.play();
                    }
                }
        	}
        }

        Iterator<Asteroid> i = Game.asteroids.iterator();
        while(i.hasNext()) {
        	Asteroid a = i.next();
        	double scale = a.getSize();
			boolean dir = a.rotationDir();
        	int x = a.getX();
        	
        	int y = a.getY();
        	y += (1 / scale) + Asteroids.render.asteroidSpeedModifier;
        	
        	if(y > Asteroids.HEIGHT + 100) {
        		i.remove();
        		continue;
        	}
        	
        	a.setY(y);
        	
        	int rot = a.getRotation();
        	if(dir) {
        		rot += a.getRotationSpeed();
        	} else {
        		rot -= a.getRotationSpeed();
        	}
        	a.setRotation(rot);
        	
        	Image asteroid = a.getAsteroidImage();
        	double asteroidWidth = asteroid.getWidth() * scale;
        	double asteroidHeight = asteroid.getHeight() * scale;
        	
        	Util.drawRotatedImage(gfx, asteroid, rot, x - (asteroidWidth / 2), y - (asteroidHeight / 2), asteroidWidth, asteroidHeight);
        }
        
        Iterator<Explosion> i2 = Asteroids.render.explosions.iterator();
        
        while(i2.hasNext()) {
        	Explosion e = i2.next();
        	
        	if(e.isFinished()) {
        		i2.remove();
        		continue;
        	} else {
        		int x = e.getX();
        		int y = e.move();
        		
        		Image ex = e.update();
        		
        		double width = ex.getWidth() * e.getScale();
        		double height = ex.getHeight() * e.getScale();
        		
        		gfx.drawImage(ex, x - (width / 2), y - (height / 2), width, height);
        	}
        }
        
        Util.drawRotatedImage(gfx, spaceship, vel, Game.xStart + Spaceship.position, Asteroids.CANVAS_HEIGHT - Spaceship.y);
	
        gfx.setFill(Color.WHITE);
        gfx.setFont(statFont);
        gfx.fillText("Score: " + Asteroids.score, Asteroids.WALL_WIDTH + 20, Asteroids.HEIGHT - 35);
	}
	
	public void onClick(double x, double y) {}
	
	private void asteroidLoop() {
		int min = (int) (1000 - (Asteroids.frame / 7));
		int max = (int) (1500 - (Asteroids.frame / 7));
		if(min < 375) min = 500;
		if(max < 750) max = 1000;
		int rand = Util.randomBetween(min, max);
		Util.setTimeout(Long.valueOf(rand), () -> {
			if(Asteroids.gameActive) {
				spawnAsteroid();
				asteroidLoop();
			}
		});
	}
	
	private void spawnAsteroid() {
		Asteroid a = new Asteroid();
		Game.asteroids.add(a);
	}

}

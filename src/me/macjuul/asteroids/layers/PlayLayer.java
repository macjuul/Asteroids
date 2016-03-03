package me.macjuul.asteroids.layers;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import me.macjuul.asteroids.Asteroid;
import me.macjuul.asteroids.Asteroids;
import me.macjuul.asteroids.Explosion;
import me.macjuul.asteroids.Game;
import me.macjuul.asteroids.GameEvents;
import me.macjuul.asteroids.Spaceship.Spaceship;
import me.macjuul.asteroids.util.Util;

public class PlayLayer implements Layer {
	private int vel;
	private Image ship;
	private Image blt;
    
	@Override
	public void init() {
    	this.ship = Util.getImage("spaceship/" + Asteroids.spaceship.getCurrentSkin().toString().toLowerCase() + ".png");
    	this.blt = Util.getImage("bullet.png");
    	
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
        	int z = b.get(1);
        	int angle = b.get(2);
        	
        	if(angle == 0) {
        		angle = 345;
        		x -= 3;
        	} else if(angle == 1) {
        		angle = 0;
        	} else {
        		angle = 15;
        		x += 3;
        	}
        	
        	z -= 10;
        	
        	b.set(0, x);
        	b.set(1, z);

        	Util.drawRotatedImage(gfx, blt, angle, x, z, 4, 12);
        	
        	if(z < -10) {
        		bullet.remove();
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
        	
        	if(y > 300) {
        		i.remove();
        		new Explosion(x, y, (1 / scale) + Asteroids.render.asteroidSpeedModifier, scale * 2.5);
        	}
        	
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
        gfx.setFont(Asteroids.SPACE_FONT);
        gfx.fillText("Score: " + Asteroids.score, Asteroids.WALL_WIDTH + 20, Asteroids.HEIGHT - 30);
	}
	
	public void onClick(double x, double y) {}
	
	private void asteroidLoop() {
		int rand = Util.randomBetween(1000, 1500);
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

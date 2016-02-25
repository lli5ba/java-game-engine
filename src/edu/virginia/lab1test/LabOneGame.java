package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.plaf.synth.SynthSeparatorUI;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.SoundManager;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;

/**
 * Modified by: Leandra Irvine (lli5ba)
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game {
	
	public static int gameHeight = 300;
	public static int gameWidth = 500;
	/* Create a sprite object for our game. We'll use mario */
	//Sprite mario = new Sprite("Mario", "Mario.png");
	Sprite key = new Sprite("Key", "Small_Key.png");
	//AnimatedSprite lily = new AnimatedSprite("Lily", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	PhysicsSprite lily2 = new PhysicsSprite("Lily2", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	QuestManager myQuestManager = new QuestManager();
	Sprite floor = new Sprite("Floor", "floor.png");
	Sprite platform = new Sprite("Platform", "floor.png");
	SoundManager mySoundManager;
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * */
	public LabOneGame() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		super("Lab One Test Game", gameWidth, gameHeight);
		mySoundManager = new SoundManager();
		mySoundManager.LoadMusic("thebestsong", "whatisthis.wav");
		mySoundManager.PlayMusic("thebestsong");
		//lily.setPosition(100, 100);
		lily2.setPosition(20, 100);
		//lily.animate("down");
		key.setPosition(350, 50);
		floor.setPosition(0, 300 - floor.getUnscaledHeight() - 10);
		floor.setScaleX(20);
		platform.setPosition(200, 150);
		key.addEventListener(myQuestManager, PickedUpEvent.KEY_PICKED_UP);
		key.addEventListener(myQuestManager, CollisionEvent.COLLISION);
	}
	
	
	public void rotateSprite(Sprite sprite, ArrayList<String> pressedKeys, String button) {
		if(sprite != null) {
			sprite.update(pressedKeys);
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(button)) {
				sprite.setRotationRadians(sprite.getRotation() + .04);
				}
		}
	}
	
	public void moveSpritePlatformerAnimate(PhysicsSprite sprite, ArrayList<String> pressedKeys){
		/* Make sure sprite is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(sprite != null) {
			sprite.update(pressedKeys);
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
				if (sprite.isOnGround()) {
					sprite.addForce(0, -225, 5);
					sprite.setOnGround(false);
				}
				/*if(sprite.getyPos() > 0) {
					sprite.setyPos(sprite.getyPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "up") {
					sprite.animate("up");
				}*/
			}
			
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				if(sprite.getxPos() > 0) {
					sprite.setxPos(sprite.getxPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "left") {
					sprite.animate("left");
				}
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				
				if(sprite.getxPos() < gameWidth - sprite.getUnscaledWidth()*sprite.getScaleX()){
					sprite.setxPos(sprite.getxPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "right") {
					sprite.animate("right");
				}
			}
		}
	}
	
	public void moveSpriteCartesianAnimate(AnimatedSprite sprite, ArrayList<String> pressedKeys){
		/* Make sure sprite is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(sprite != null) {
			sprite.update(pressedKeys);
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
				if(sprite.getyPos() > 0) {
					sprite.setyPos(sprite.getyPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "up") {
					sprite.animate("up");
				}
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
				if(sprite.getyPos() < gameHeight - sprite.getUnscaledHeight()*sprite.getScaleY()){
					sprite.setyPos(sprite.getyPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "down") {
					sprite.animate("down");
				}
			}
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				if(sprite.getxPos() > 0) {
					sprite.setxPos(sprite.getxPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "left") {
					sprite.animate("left");
				}
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				
				if(sprite.getxPos() < gameWidth - sprite.getUnscaledWidth()*sprite.getScaleX()){
					sprite.setxPos(sprite.getxPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "right") {
					sprite.animate("right");
				}
			}
		}
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		/*moveSpriteCartesian(mario, pressedKeys);
		moveSpriteCartesian(mario2,pressedKeys);
		if(this.lily2 != null) {
			this.lily2.update(pressedKeys);
		}*/
		if (this.lily2 != null) {
			moveSpritePlatformerAnimate(lily2, pressedKeys);
			if (pressedKeys.isEmpty()){
				lily2.stopAnimation();
			}
			if (this.floor != null) {
				lily2.obstacleCollision(floor);
				
			}
			if (this.platform != null) {
				lily2.obstacleCollision(platform);
				
			}
			if (lily2.collidesWith(key) && key.isVisible()) {
				
				key.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, key));
				key.setVisible(false);
			}
		}
		if (this.floor != null) {
			floor.update(pressedKeys);
		}
		if (this.platform != null) {
			platform.update(pressedKeys);
		}
		/*if (this.lily != null) {
			moveSpritePlatformerAnimate(lily, pressedKeys);
			if (pressedKeys.isEmpty()){
				lily.stopAnimation();
			}
//			if (lily.getxPos() + lily.getUnscaledWidth() > key.getxPos() && 
//					lily.getyPos() + lily.getUnscaledHeight() > key.getyPos() &&
//					lily.getyPos() < key.getyPos() + key.getUnscaledHeight() &&
//					lily.getxPos() < key.getxPos() + key.getUnscaledWidth() &&
//					key.isVisible()) {
			if (lily.collidesWith(key) && key.isVisible()) {
				key.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, key));
				key.setVisible(false);
				
			}
		}*/
		
		//mario2.setRotationRadians(mario2.getRotation() + .04);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		/*if(lily != null) {
			lily.draw(g);
		}*/
		if(lily2 != null) {
			lily2.draw(g);
		}
		if(key != null) {
			key.draw(g);
		}
		if(floor != null) {
			floor.draw(g);
		}
		if(platform != null) {
			platform.draw(g);
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * */
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		LabOneGame game = new LabOneGame();
		game.start();
		
	}
}

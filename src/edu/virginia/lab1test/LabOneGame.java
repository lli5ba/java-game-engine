package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

/**
 * Modified by: Leandra Irvine (lli5ba)
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{
	
	public static int gameHeight = 300;
	public static int gameWidth = 500;
	/* Create a sprite object for our game. We'll use mario */
	//Sprite mario = new Sprite("Mario", "Mario.png");
	Sprite key = new Sprite("Key", "Small_Key.png");
	AnimatedSprite lily = new AnimatedSprite("Lily", "Lily.png", "LilySheet.png", "LilySpecs.txt");
	QuestManager myQuestManager = new QuestManager();
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Lab One Test Game", gameWidth, gameHeight);
		lily.setPosition(100, 100);
		//lily.animate("down");
		key.setPosition(200, 100);
		key.addEventListener(myQuestManager, PickedUpEvent.KEY_PICKED_UP);
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
	
	
	public void moveSpriteCartesianAnimate(AnimatedSprite sprite, ArrayList<String> pressedKeys){
		/* Make sure sprite is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(sprite != null) {
			sprite.update(pressedKeys);
			/* update mario's position if a key is pressed, check bounds of canvas */
			if(pressedKeys.contains("Up")) {
				if(sprite.getyPos() > 0) {
					sprite.setyPos(sprite.getyPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "up") {
					sprite.animate("up");
				}
			}
			if (pressedKeys.contains("Down")) {
				if(sprite.getyPos() < gameHeight - sprite.getUnscaledHeight()*sprite.getScaleY()){
					sprite.setyPos(sprite.getyPos() + 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "down") {
					sprite.animate("down");
				}
			}
			if(pressedKeys.contains("Left")) {
				if(sprite.getxPos() > 0) {
					sprite.setxPos(sprite.getxPos() - 1);
				}
				if(!sprite.isPlaying() || sprite.getCurrentAnimation() != "left") {
					sprite.animate("left");
				}
			}
			if (pressedKeys.contains("Right")) {
				
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
		if (this.lily != null) {
			moveSpriteCartesianAnimate(lily, pressedKeys);
			if (pressedKeys.isEmpty()){
				lily.stop();
			}
			if (lily.getxPos() + lily.getUnscaledWidth() > key.getxPos() && 
					lily.getyPos() + lily.getUnscaledHeight() > key.getyPos() &&
					lily.getyPos() < key.getyPos() + key.getUnscaledHeight() &&
					lily.getxPos() < key.getxPos() + key.getUnscaledWidth() &&
					key.isVisible()) {
				key.dispatchEvent(new PickedUpEvent(PickedUpEvent.KEY_PICKED_UP, key));
				key.setVisible(false);
				
			}
		}
		
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
		if(lily != null) {
			lily.draw(g);
		}
		if(key != null) {
			key.draw(g);
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();
		
	}
}

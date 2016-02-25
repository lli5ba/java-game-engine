package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.util.Force;
import edu.virginia.engine.util.GameClock;



public class PhysicsSprite extends AnimatedSprite {
	double mass; //in kg
	GameClock gameClockPhysics;
	double terminalVel; //around 56 m/s
	double gravity;
	double xVel;
	double yVel;
	double yAcc;
	double xAcc;
	double deltaTime;
	ArrayList<Force> forces;
	
	public PhysicsSprite(String id, String imageFileName, String spriteSheetFileName, String specsFileName) {
		super(id, imageFileName, spriteSheetFileName, specsFileName);
		mass = 1; 
		gameClockPhysics = new GameClock();
		gravity = 30.8;
		terminalVel = Math.sqrt((2*mass*gravity)/(1.5));
		forces = new ArrayList<Force>();
		forces.add(new Force(0, gravity, Float.POSITIVE_INFINITY));
		deltaTime = 10.5; //ms
		
	}

	public PhysicsSprite(String id, String imageFileName) {
		super(id, imageFileName);
		mass = 1; 
		gameClockPhysics = new GameClock();
		gravity = 9.8;
		terminalVel = Math.sqrt((2*mass*gravity)/(1.5));
		forces = new ArrayList<Force>();
		forces.add(new Force(0, gravity, Float.POSITIVE_INFINITY));
		deltaTime = 10.5; //ms
	}

	public PhysicsSprite(String id) {
		super(id);
		mass = 1; 
		gameClockPhysics = new GameClock();
		gravity = 9.8;
		terminalVel = Math.sqrt((2*mass*gravity)/1.5);
		forces = new ArrayList<Force>();
		forces.add(new Force(0, gravity, Float.POSITIVE_INFINITY));
		deltaTime = 10.5; //ms
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		this.updateForces();
		if (this.gameClockPhysics.getElapsedTime() > (deltaTime)){
			this.moveSprite(this.gameClockPhysics.getElapsedTime());
			this.gameClockPhysics.resetGameClock();
		}
	}
	
	public void updateForces(){
		ArrayList<Force> toRemove = new ArrayList<Force>();
		for (Force f: forces) {
			if(f.getTimeRemaining() <= 0) {
				toRemove.add(f);
			}
		}
		for (Force f: toRemove) {
			forces.remove(f);
		}
	}
	
	public void moveSprite(double time){
		time = time/100; //convert  
		double totalxForce = 0;
		double totalyForce = 0;
		double initxVel = this.xVel;
		double inityVel = this.yVel;
		for (Force force : forces) {
			totalxForce += force.getxForce();
			totalyForce += force.getyForce();
			force.setTimeRemaining(force.getTimeRemaining() - time);
		}
		if (this.xVel < this.terminalVel) {
			this.xVel = initxVel + (totalxForce/mass)*time;
		}
		this.setxPos(this.getxPos() + initxVel*time + (totalxForce/(2*mass))*Math.pow(time,2));
		
		if (this.yVel < this.terminalVel) {
			this.yVel = inityVel + (totalyForce/mass)*time;
		}
		this.setyPos(this.getyPos() + inityVel*time + (totalyForce/(2*mass))*Math.pow(time,2));
		
	}
	
	public void createSingleForce(double xForce, double yForce) {
		double xCurrForce = mass * xAcc;
		double xNewForce = xCurrForce + xForce;
		this.xAcc = xNewForce/mass;
		
		double yCurrForce = mass * yAcc;
		double yNewForce = yCurrForce + yForce;
		this.yAcc = yNewForce/mass;
	}
	
	public void addForce(double xForce, double yForce, double timeRemaining) {
		forces.add(new Force(xForce, yForce, timeRemaining));
	}
}

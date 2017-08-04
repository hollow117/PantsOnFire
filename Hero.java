///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  Game.java
// File:             Hero.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
///////////////////////////////////////////////////////////////////////////////


import java.util.Random;
import java.util.ArrayList;

/**
 * This class represents the player's character which is a fire fighter who is 
 * able to spray water that extinguishes Fires and Fireballs. They must save as 
 * many Pants from burning as possible, and without colliding with any 
 * Fireballs in the process. 
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Hero {

	private Graphic graphic; // this variable enable use of Graphic class
	private float speed; // this variable will hold information on how fast
	 					 // Hero moves
	private int controlType; // this will hold controlType information

	/**
	 * This constructor initializes a new instance of Hero at the appropriate 
	 * location and using the appropriate controlType.
	 * 
	 * @param float x, float y, int controlType 
	 * 
	 * @return none
	 */
	public Hero(float x, float y, int controlType) {
		speed = 0.12f;
		graphic = new Graphic();
		graphic.setType("HERO");

		graphic.setX(x);
		graphic.setY(y);
		this.controlType = controlType;

	}

	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * 
	 * @param none 
	 * 
	 * @return a reference to this Hero's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	
	/**
	 * This method detects an handles collisions between any active Fireball 
	 * objects, and the current Hero. When a collision is found, this method 
	 * returns true to indicate that the player has lost the Game.
	 * 
	 * @param fireballs - the ArrayList of Fireballs that should be checked 
	 * 		  against the current Hero's position for collisions.
	 * @return true when a Fireball collision is detected, otherwise false
	 */
	public boolean handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (fireballs.get(i) != null && 
					graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is called repeated by the Game to draw and move (based on 
	 * the current controlType) the Hero, as well as to spray new Water in the 
	 * direction that this Hero is currently facing.
	 * 
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * 		  since the last time this update was called.
	 * @param water - the array of Water that the Hero has sprayed in the past, 
	 * 		  and if there is an empty (null) element in this array, they can 
	 * 		  can add a new Water object to this array when the use is pressing 
	 * 		  the appropriate controls.
	 * @return none
	 */
	public void update(int time, Water[] water) {

		boolean addedWater = false;
		for (int i = 0; i < water.length && !addedWater; i++) {
			if (water[i] == null) {
				if (Engine.isKeyPressed("SPACE") || Engine.isKeyHeld("MOUSE")) {

					water[i] = new Water(graphic.getX(), graphic.getY(), 
							graphic.getDirection());
					addedWater = true;

				}
			}
		}

		// CONTROL TYPE 1
		if (controlType == 1) {
			if (Engine.isKeyHeld("D")) {
				graphic.setX(graphic.getX() + (speed * time));
				graphic.setDirection(0);
			}
			if (Engine.isKeyHeld("A")) {
				graphic.setX(graphic.getX() - (speed * time));
				graphic.setDirection((float) Math.PI);
			}
			if (Engine.isKeyHeld("W")) {
				graphic.setY(graphic.getY() - (speed * time));
				graphic.setDirection((float) Math.PI / 2 * 3);
			}
			if (Engine.isKeyHeld("S")) {
				graphic.setY(graphic.getY() + (speed * time));
				graphic.setDirection((float) Math.PI / 2);
			}
		}

		// CONTROL TYPE 2
		else if (controlType == 2) {
			if (Engine.isKeyHeld("D")) {
				graphic.setX(graphic.getX() + (speed * time));
			}
			if (Engine.isKeyHeld("A")) {
				graphic.setX(graphic.getX() - (speed * time));
			}
			if (Engine.isKeyHeld("W")) {
				graphic.setY(graphic.getY() - (speed * time));
			}
			if (Engine.isKeyHeld("S")) {
				graphic.setY(graphic.getY() + (speed * time));
			}
			graphic.setDirection(Engine.getMouseX(), Engine.getMouseY());
		}

		// CONTROL TYPE 3
		else if (controlType == 3) {

			graphic.setDirection(Engine.getMouseX(), Engine.getMouseY());

			float changeX = graphic.getDirectionX() * (speed * time);
			float changeY = graphic.getDirectionY() * (speed * time);

			double distance = Math.sqrt(Math.pow(Engine.getMouseX() - 
					this.graphic.getX() - changeX, 2) + 
					Math.pow(Engine.getMouseY() - this.graphic.getY() - 
							changeY, 2));

			if (distance > 20) {
				graphic.setX(this.graphic.getX() + changeX);
				graphic.setY(this.graphic.getY() + changeY);
			}

		}
		graphic.draw();

	}

}

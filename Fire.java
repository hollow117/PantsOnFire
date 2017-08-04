///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Game.java
// File:             Fire.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
///////////////////////////////////////////////////////////////////////////////


import java.util.Random;

/**
 * This class represents a fire that is burning, which ejects a fireball in a 
 * random direction every 3-6 seconds. This fire can slowly be extinguished 
 * through repeated collisions with water. 
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Fire {
	private Graphic graphic;	// this variable enable use of Graphic class
	private Random randGen;		// this variable enable use of Random class
	private int fireballCountdown; // this variable gets used in update method
	private int heat;	// this variable is used as life of Fire object
	private boolean noHeat;	// this variable is mainly used in update method

	/**
	 * This constructor initializes a new instance of Fire at the appropriate 
	 * location and with the appropriate amount of heat.
	 * 
	 * @param float x, float y, Random randGen
	 * 
	 * @return none
	 */
	public Fire(float x, float y, Random randGen) {
		graphic = new Graphic();
		graphic.setType("FIRE");

		this.randGen = randGen;

		this.fireballCountdown = randGen.nextInt(2999) + 3000;

		graphic.setX(x);
		graphic.setY(y);

		heat = 40;
		noHeat = false;
	}

	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * 
	 * @param none
	 * 
	 * @return a reference to this Fire's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	
	/**
	 * This method detects and handles collisions between any active (!= null) 
	 * Water objects, and the current Fire. When a collision is found, the 
	 * colliding water should be removed, and this Fire's heat should be 
	 * decremented by 1. If this Fire's heat reaches 0, then it's graphic 
	 * should be destroyed, it should no longer eject Fireballs, and its 
	 * shouldRemove() method should start returning true.
	 * 
	 * @param water - is the Array of water objects that have been launched by 
	 * 		  the Hero (ignore any null references within this array).
	 * 
	 * @return none
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {

			if (water[i] != null && 
					graphic.isCollidingWith(water[i].getGraphic())) {
				this.heat--;
				water[i].getGraphic().destroy();
				water[i] = null;

				if (heat < 1) {
					graphic.destroy();

					water[i] = null;
					noHeat = true;
				}

			}
		}
	}

	/**
	 * This method should return false until this Fire's heat drops down to 0. 
	 * After that it should begin to return true.
	 * 
	 * @param none
	 * 
	 * @return true when this Fire's heat is greater than zero, otherwise false
	 */
	public boolean shouldRemove() {
		if (this.heat < 1) {
			return true;
		}
		return false;
	}

	/**
	 * This method is called repeatedly by the Game to draw and sporadically 
	 * launch a new Fireball in a random direction.
	 * 
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * 		  since the last time this update was called.
	 * 
	 * @return null unless a new Fireball was just created and launched. In that 
	 * 		   case, a reference to that new Fireball is returned instead.
	 */
	public Fireball update(int time) {
		graphic.setX(graphic.getX());
		graphic.setY(graphic.getY());

		this.fireballCountdown -= time;

		if (fireballCountdown <= 0 && !noHeat) {
			Fireball fireball = new Fireball(graphic.getX(), graphic.getY(),
					(float) (2 * Math.PI * randGen.nextFloat()));
			fireballCountdown = randGen.nextInt(2999) + 3000;
			return fireball;
		}

		graphic.draw();

		return null;

	}
}

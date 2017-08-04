///////////////////////////////////////////////////////////////////////////////
//                  
// Main Class File:  Game.java
// File:             Pant.java
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
 * This class represents a pair of Pants that the Hero must protect from 
 * burning. Whenever a Pant collides with a Fireball, that Pant will be 
 * replaced by a Fire. 
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Pant {

	private Graphic graphic; // this variable enable use of Graphic class
	private Random randGen; // this variable enable use of Random class
	private boolean letsRemove; // this variable is used mainly in 
								// shouldRemove method. If it holds true, it 
								// will remove number of Pant

	/**
	 * This constructor initializes a new instance of Pant at the appropriate 
	 * location.
	 * 
	 * @param float x, float y, Random randGen
	 * 
	 * @return none
	 */
	public Pant(float x, float y, Random randGen) {
		graphic = new Graphic();
		graphic.setType("PANT");
		graphic.setX(x);
		graphic.setY(y);
		this.randGen = randGen;
		letsRemove = false;
	}

	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * 
	 * @param none
	 * 
	 * @return a reference to this Pant's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	
	/**
	 * This method detects an handles collisions between any active Fireball, 
	 * and the current Pant. When a collision is found, the colliding Fireball 
	 * should be removed from the game (by calling its destroy() method), and 
	 * the current Pant should also be removed from the game (by destroying it 
	 * graphic, and also ensuring that its shouldRemove() method returns true).
	 * 
	 * @param fireballs - the ArrayList of Fireballs that should be checked 
	 * 		  against the current Pant's position for collisions.
	 * 
	 * @return none
	 */
	public Fire handleFireballCollisions(ArrayList<Fireball> fireballs) {
		for (int i = 0; i < fireballs.size(); i++) {
			if (fireballs.get(i) != null && 
					graphic.isCollidingWith(fireballs.get(i).getGraphic())) {
				graphic.destroy();
				letsRemove = true;
				fireballs.get(i).destroy();
				Fire newFire = new Fire(graphic.getX(), 
						graphic.getY(), randGen);
				return newFire;
			}
		}
		return null;
	}

	/**
	 *  This method communicates to the Game whether this Pant is still in use 
	 *  vs. done being used and ready to be removed from the Game's ArrayList 
	 *  of Pants.
	 * 
	 * @param none 
	 * 
	 * @return true when this Pant has been hit by a Fireball, otherwise false.
	 */
	public boolean shouldRemove() {
		if (letsRemove == true) {
			return true;
		}
		return false;
	}

	/**
	 * This method is simply responsible for drawing the current Pant to the 
	 * screen.
	 * 
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * 		  since the last time this update was called.
	 * @return none
	 */
	public void update(int time) {

		graphic.draw();
	}
}

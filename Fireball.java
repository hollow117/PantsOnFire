///////////////////////////////////////////////////////////////////////////////
//                 
// Main Class File:  Game.java
// File:             Fireball.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
///////////////////////////////////////////////////////////////////////////////


/**
 * This class represents a Fireball that is ejected from a burning fire. When a 
 * Fireball hits the Hero, they lose the game. When a Fireball hits a Pant, 
 * those Pants are replaced by a Fire. 
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Fireball {
	private Graphic graphic; // this variable enable use of Graphic class
	private float speed; // this variable will hold information on how fast
						 // fireball moves
	private boolean isAlive;  // this variable indicates whether fireball is 
							  // alive or dead

	/**
	 * This constructor initializes a new instance of Fireball at the specified 
	 * location and facing a specific movement direction.
	 * 
	 * @param float x, float y, float directionAngle
	 * 
	 * @return none
	 */
	public Fireball(float x, float y, float directionAngle) {
		speed = 0.2f;
		graphic = new Graphic();
		graphic.setType("FIREBALL");

		graphic.setX(x);
		graphic.setY(y);
		graphic.setDirection(directionAngle);
		isAlive = true;
	}

	/**
	 * This method detects an handles collisions between any active (!= null) 
	 * Water objects, and the current Fireball. When a collision is found, the 
	 * colliding water should be removed (graphic destroyed and array reference 
	 * set to null), and this fireball should also be removed from the game 
	 * ( graphic destroyed, and shouldRemove() should begin returning true).
	 * 
	 * @param water - is the Array of water objects that have been launched by 
	 * 		  the Hero (ignore any null references within this array).
	 * @return none
	 */
	public void handleWaterCollisions(Water[] water) {
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null && 
					graphic.isCollidingWith(water[i].getGraphic())) {
				isAlive = false;
				graphic.destroy();

				water[i].getGraphic().destroy();
				water[i] = null;
			}
		}
	}
	
	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * 
	 * @param none
	 * 
	 * @return a reference to this Fireball's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}

	/**
	 * This helper method allows other classes (like Pant) to destroy a 
	 * Fireball upon collision. This method should destroy the current 
	 * Fireball's graphic, and ensure that shouldRemove() starts returning true 
	 * instead of false.
	 * 
	 * @param none
	 *            
	 * @return none
	 */
	public void destroy() {
		graphic.destroy();
		isAlive = false;

	}

	/**
	 * This method communicates to the Game whether this Fireball is still in 
	 * use vs. done being used and ready to be removed from the Game's 
	 * ArrayList of Fireballs.
	 * 
	 * @param none
	 *            
	 * @return true when this Fireball has either gone off the screen or 
	 * 		   collided with a Water or Pant object, and false otherwise.
	 */
	public boolean shouldRemove() {
		if (isAlive == false) {
			return true;
		}
		return false;
	}

	/**
	 * This method is called repeatedly by the Game to draw and move the 
	 * current Fireball. When a fireball moves more than 100 pixels beyond any 
	 * edge of the screen, it's graphic should be destroyed and it's 
	 * shouldRemove() method should return true instead of false.
	 * 
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * 	 	  since the last time this update was called.
	 * @return none
	 */
	public void update(int time) {
		graphic.setX(graphic.getX() + graphic.getDirectionX() * (speed * time));
		graphic.setY(graphic.getY() + graphic.getDirectionY() * (speed * time));

		if (graphic.getX() < 100 || graphic.getY() < 100 || 
				graphic.getX() > 700 || graphic.getY() > 500) {
			graphic.destroy();
			isAlive = false;
		}

		graphic.draw();
	}

}

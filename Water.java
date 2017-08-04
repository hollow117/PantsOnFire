///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  Game.java
// File:             Water.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
///////////////////////////////////////////////////////////////////////////////

/**
 * This Water class represents a splash of Water that is sprayed by the Hero to 
 * extinguish Fireballs and Fires, as they attempt to save the Pants. 
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Water {
	private Graphic graphic;  // this variable enable use of Graphic class
	private float speed;  // this variable will hold information on how fast
	 					  // Water moves
	private float distanceTraveled; // this variable will hold distance of 
									// how much Water object moved

	/**
	 * This constructor initializes a new instance of Water at the specified 
	 * location and facing a specific movement direction.
	 * 
	 * @param float x, float y, float direction 
	 * 
	 * @return none
	 */
	public Water(float x, float y, float direction) {
		speed = 0.7f;

		graphic = new Graphic();
		graphic.setType("WATER");

		graphic.setX(x);
		graphic.setY(y);
		graphic.setDirection(direction);
	}

	/**
	 * This is a simple accessor for this object's Graphic, which may be used 
	 * by other objects to check for collisions.
	 * 
	 * @param none 
	 * 
	 * @return a reference to this Water's Graphic object.
	 */
	public Graphic getGraphic() {
		return this.graphic;
	}
	
	/**
	 * This method is called repeatedly by the Game to draw and move the 
	 * current Water. After this Water has moved a total of 200 pixels or 
	 * further, it's graphic should be destroyed and this method should return 
	 * null instead of a reference to this Water.
	 * 
	 * @param time - is the amount of time in milliseconds that has elapsed 
	 * 		  since the last time this update was called.
	 * @return a reference to this water for as long as this water has traveled 
	 * 		   less than 200 pixels. It should then return null, after this 
	 * 		   water has traveled this far.
	 */
	public Water update(int time) {

		graphic.setX(graphic.getX() + graphic.getDirectionX() * (speed * time));
		graphic.setY(graphic.getY() + graphic.getDirectionY() * (speed * time));

		float changeX = graphic.getDirectionX() * (speed * time);
		float changeY = graphic.getDirectionY() * (speed * time);

		distanceTraveled += (float) Math.sqrt(Math.pow(changeX, 2) + 
				Math.pow(changeY, 2)) + (speed * time);


		if (distanceTraveled > 200) {
			return null;
		}
		graphic.draw();
		return this;
	}

}

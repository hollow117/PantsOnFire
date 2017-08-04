///////////////////////////////////////////////////////////////////////////////
//   
// Title:            Pants on Fire
// Files:            Fire.java, Fireball.java, Game.java, Hero.java, Pant.java,
//					 Water.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe
// Email:            choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
//
///////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//                   
// Main Class File:  Game.java
// File:             Game.java
// Semester:         CS302 Spring 2016
//
// Author:           Jason Choe, choe2@wisc.edu
// CS Login:         choe
// Lecturer's Name:  Jim Williams
// Lab Section:      313
//
///////////////////////////////////////////////////////////////////////////////
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Game class coordinates what happens between different objects during
 * the game. It will set up different stages of game with different number
 * of objects with different control type of Hero.
 *
 * no known bugs
 *
 * @author Jason Choe
 */
public class Game {
	private Hero hero; // this enables use of Hero class
	private Water[] water;	// this enables use of Water class to create water

	private ArrayList<Fireball> fireballs; // this sets ArrayList for Fireball
										   // objects
	private ArrayList<Fire> fires;	// this sets ArrayList for Fire objects
	private Random randGen;	// this variable enable use of Random class
	private ArrayList<Pant> pants; // this sets ArrayList for Pant objects
	private int controlType;	// this will hold controlType information

	/**
	 * This constructor initializes a new Game object, so that the Application 
	 * can begin calling this game's update() method to advance game play.
	 * 
	 * @param String level, Random randGen
	 * 
	 * @return none
	 */
	public Game(String level, Random randGen) {
		
		// -------------------------------------------------------
		
		// this section has been commented out for milestone 3
		// hero = new Hero(Engine.getWidth() / 2, Engine.getHeight() / 2,
		// randGen.nextInt(3)+1);
		
		// assigning parameter value randGen to variable randGen in field 
		// section
		this.randGen = randGen; 
		// -------------------------------------------------------
		// setting water 
		int numWater = 8;
		water = new Water[numWater];

		// -------------------------------------------------------
		// setting pants 
		pants = new ArrayList<Pant>();
		
//		commented out for Milestone 3 step 4
//		for (int i = 0; i < numPant; i++) {
//			pants.add(new Pant(Engine.getWidth() * randGen.nextFloat(), 
//					Engine.getHeight() * randGen.nextFloat(),
//					randGen));
//		}
		// -------------------------------------------------------
		// setting fireballs
		fireballs = new ArrayList<Fireball>();

		// -------------------------------------------------------
		// setting fires
		fires = new ArrayList<Fire>();
		
//		commented out for Milestone 3 step 4
//		for (int i = 0; i < numFire; i++) {
//			fires.add(new Fire(Engine.getWidth() * randGen.nextFloat(), 
//					Engine.getHeight() * randGen.nextFloat(),
//					randGen));
//		}
		
		// -------------------------------------------------------
		// loading levels depending on what String level uses
		if (level.equals("RANDOM")) {
			createRandomLevel();
		} else {
			loadLevel(level);
		}
	}

	/**
	 * The Application calls this method repeatedly to update all of the 
	 * objects within your game, and to enforce all of the rules of your game.
	 * 
	 * @param time - is the time in milliseconds that has elapsed since the 
	 * 				 last time this method was called. This can be used to 
	 * 				 control the speed that objects are moving within your game.
	 *          
	 * @return When this method returns "QUIT" the game will end after a short 
	 * 		   3 second pause and message indicating that the player has lost. 
	 * 		   When this method returns "ADVANCE", a short pause and win 
	 * 		   message will be followed by the creation of a new game which 
	 * 		   replaces this one. When this method returns anything else 
	 * 		   (including "CONTINUE"), the Application will simply continue to 
	 * 		   call this update() method as usual.
	 */
	public String update(int time) {
		// -------------------------------------------------------
		// Game's update method calling hero's update method
		hero.update(time, water);
		// Game's update method calling hero's handleFireCollisions method
		if (hero.handleFireballCollisions(fireballs)) {
			return "QUIT";
		}
		
		// -------------------------------------------------------
		// Game's update method calling water's update method
		for (int i = 0; i < water.length; i++) {
			if (water[i] != null) {
				water[i] = water[i].update(time);
			}
		}

		// -------------------------------------------------------
		// Game's update method calling pants' update method
		for (int i = 0; i < pants.size(); i++) {
			pants.get(i).update(time);
		}
		// Game's update method calling pants' handleFireballCollisions method
		for (int i = 0; i < pants.size(); i++) {
			Fire fire = pants.get(i).handleFireballCollisions(fireballs);
			if (fire != null) {
				fires.add(fire);
			}
		}
		// Game's update method calling pants' shouldRemove method
		for (int i = 0; i < pants.size(); i++) {
			if (pants.get(i).shouldRemove()) {
				pants.remove(i);
				System.out.println("pant shouldRemove works");
			}
		}

		System.out.println("pants size is:   " + pants.size());
		// -------------------------------------------------------
		// Game method calling fire's update method
		for (int i = 0; i < fires.size(); i++) {
			Fireball balls = fires.get(i).update(time);
			if (balls != null)
				fireballs.add(balls);
		}
		// Game method calling fire's handleWaterCollisions method
		for (int i = 0; i < fires.size(); i++) {
			for (int j = 0; j < water.length; j++) {
				if (water[j] != null) {
					fires.get(i).handleWaterCollisions(water);
				}
			}
		}
		// Game method calling fire's shouldRemove method
		for (int i = 0; i < fires.size(); i++) {
			if (fires.get(i).shouldRemove()) {
				fires.remove(i);
			}
		}
		System.out.println("fires size is:   " + fires.size());

		// -------------------------------------------------------
		// Game update method calling fireballs' update method
		for (int i = 0; i < fireballs.size(); i++) {
			if (fireballs.get(i) != null) {
				fireballs.get(i).update(time);
			}
		}
		// Game update method calling fireballs' handleWaterCollisions method
		for (int i = 0; i < fireballs.size(); i++) {
			Fireball fireThrown = fireballs.get(i);
			if (fireThrown != null) {
				fireballs.get(i).handleWaterCollisions(water);
			}
		}
		// Game update method calling fireballs' shouldRemove method
		for (int i = 0; i < fireballs.size(); i++) {
			Fireball fireThrown = fireballs.get(i);
			if (fireThrown.shouldRemove()) {
				fireballs.remove(i);
			}
		}

		System.out.println("fireballs size is:   " + fireballs.size());
		// -------------------------------------------------------

		if (fires.size() == 0) {
			return "ADVANCE";
		}

		if (pants.size() == 0) {
			return "QUIT";
		}

		return "CONTINUE";
	}

	/**
	 * This method returns a string of text that will be displayed in the upper 
	 * left hand corner of the screen. Ultimately this should express the number 
	 * of unburned pants remaining in the level. However, this may also be 
	 * useful for displaying messages that help you debug your game.
	 *
	 * @param none
	 * 
	 * @return a string of text to be displayed in the upper-left hand corner 
	 * 		   of the screen by the Application.
	 */
	public String getHUDMessage() {
		return "Pants Left: " + pants.size() + "\nFires Left: " + fires.size();
	}

	/**
	 * This method creates a random level consisting of a single Hero centered 
	 * in the middle of the screen, along with 6 randomly positioned fires, and 
	 * 20 randomly positioned pants.
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public void createRandomLevel() {
		
		int numPant = 20;
		int numFire = 6;
		
		hero = new Hero(Engine.getWidth() / 2, Engine.getHeight() / 2, 
				randGen.nextInt(3) + 1);
		
		for (int i = 0; i < numPant; i++) {
			pants.add(new Pant(Engine.getWidth() * randGen.nextFloat(), 
					Engine.getHeight() * randGen.nextFloat(),
					randGen));
		}
		
		for (int i = 0; i < numFire; i++) {
			fires.add(new Fire(Engine.getWidth() * randGen.nextFloat(), 
					Engine.getHeight() * randGen.nextFloat(),
					randGen));
		}
		
	}

	/**
	 * This method initializes the current game according to the descriptions 
	 * included within the level parameter as described below.
	 * 
	 * @param level - is a string containing the contents of a custom level 
	 * file, which can be read using a new Scanner(level). Try looking through 
	 * a few of the provided level files to see how they are formatted. The 
	 * first line is always the "ControlType: #" where # is either 1, 2, or 3. 
	 * Subsequent lines describe an object TYPE, along with an X and Y position 
	 * that are formatted as: "TYPE @ X, Y". This method should instantiate and 
	 * initialize a new object for each such line.
	 * 
	 * @return none
	 */
	public void loadLevel(String level) {
//--------Setting controlType According to Information Provided by level
		Scanner search1 = new Scanner(level);
		while (search1.hasNext()) {
			String[] control = search1.nextLine().split(" ");
			if (control[0].equals("ControlType:")) {
				String controlTypeStr = control[1];
				System.out.println("control type is " + control[1]);
				this.controlType = Integer.parseInt(controlTypeStr);
			}
		}
		search1.close();

//--------Creating Hero According to Information Provided by level
		Scanner search2 = new Scanner(level);
		while (search2.hasNext()) {
			String line6 = search2.nextLine();
			String[] array6 = line6.split(",");
			String[] array6_1 = array6[0].split("@");

			if (array6_1[0].trim().equals("HERO")) {
				String location6X = array6_1[1].trim();
				Float x6 = Float.parseFloat(location6X);
				String location6Y = array6[1].trim();
				Float y6 = Float.parseFloat(location6Y);
				hero = new Hero(x6, y6, this.controlType);
			}
		}
		search2.close();
		
//--------Creating Fires According to Information Provided by level
		Scanner search3 = new Scanner(level);
		while (search3.hasNext()) {
			String line2 = search3.nextLine();
			String[] array2 = line2.split(",");
			String[] array2_1 = array2[0].split("@");
			if (array2_1[0].trim().equals("FIRE")) {
				String location2X = array2_1[1].trim();
				Float x2 = Float.parseFloat(location2X);
				String location2Y = array2[1].trim();
				Float y2 = Float.parseFloat(location2Y);

				fires.add(new Fire(x2, y2, randGen));
			}
		}
		search3.close();
//--------Creating Pants According to Information Provided by level
		Scanner search4 = new Scanner(level);
		while (search4.hasNext()) {
			String line4 = search4.nextLine();
			String[] array4 = line4.split(",");
			String[] array4_1 = array4[0].split("@");
			if (array4_1[0].trim().equals("PANT")) {
				String location4X = array4_1[1].trim();
				Float x4 = Float.parseFloat(location4X);
				String location4Y = array4[1].trim();
				Float y4 = Float.parseFloat(location4Y);

				pants.add(new Pant(x4, y4, randGen));
			}
		}
		search4.close();

	}

	/**
	 * This method creates and runs a new Game and Engine together. Any command 
	 * line arguments are used to choose custom levels that should be played in 
	 * a particular order. It is also possible to seed the Random number 
	 * generator passed into this Game's constructor by passing a positive 
	 * integer as string first element within this args array.
	 * 
	 * @param args - is the sequence of custom levels to play, with an optional 
	 * first element containing a seed for a random number generator.
	 * 
	 * @return none
	 */
	public static void main(String[] args) {
		Application.startEngineAndGame(args);

	}
}

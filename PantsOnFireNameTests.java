///////////////////////////////////////////////////////////////////////////////
//                   
// Title:            Name Tests for P3: Pants on Fire
// File:             PantsOnFireNameTests.java, and all P3 files
// Course:           CS302, Spring 2016
// Instructors:      Gary Dahl, and Jim Williams
//
// NOTE - DO NOT SUBMIT THIS FILE WITH THE REST OF YOUR P3 CODE
//
///////////////////////////////////////////////////////////////////////////////
 
import java.lang.reflect.*;
import java.util.*;

/**
 * This class allows CS302 students to test whether they have created and 
 * correctly names each of the classes, methods, and fields associated with
 * the three milestones of P3: Pants on Fire (Spring 2016).
 * @author dahl
 */
public class PantsOnFireNameTests
{		
	/**
	 * This is the driver for the name tests.  After the user selects which
	 * milestone they would like to check against, it will run all of the checks
	 * for that milestone and for each of the previous milestones.
	 * @param args Not Used.
	 */
	public static void main(String[] args)
	{		
		Scanner in = new Scanner(System.in);
		System.out.println("Thank you for using the \"P3: Pants on Fire\" "
															+ "Name Tester");
		
		System.out.print("What is the latest milestone you have completed: ");
		int milestone = -1;
		if(in.hasNextInt()) milestone = in.nextInt();
		in.nextLine();
		while(milestone < 1 || milestone > 3)
		{
			System.out.println("Sorry, I only know milestones: 1, 2, and 3.");
			System.out.print("What is the latest milestone you have "
															+ "completed: ");
			if(in.hasNextInt()) milestone = in.nextInt();
			in.nextLine();
		}
		
		switch(milestone)
		{
		case 3:
			checkNamesAndTypes(3);
		case 2:
			checkNamesAndTypes(2);
		case 1:
			checkNamesAndTypes(1);
			break;
		}
		in.close();
	}
	
	/**
	 * This reqs array contains a string describing each field and method that
	 * is required for P3: Pants on Fire.  The first index into this array
	 * corresponds to which milestone a requirement corresponds with.
	 */
	private static String[][] reqs = new String[][] {
		{ // MILESTONE1 ******************************************************
			// Constructor and Update Methods
			"Game: Game(java.lang.String,java.util.Random)",
			"Game: java.lang.String update(java.lang.Integer)",
			"Hero: Hero(java.lang.Float,java.lang.Float,java.lang.Integer)",
			"Hero: java.lang.Void update(java.lang.Integer,[LWater;)",
			"Water: Water(java.lang.Float,java.lang.Float,java.lang.Float)",
			"Water: Water update(java.lang.Integer)",
			// Fields
			"Game: Hero hero",
			"Game: [LWater; water",
			"Game: java.util.Random randGen",
			"Hero: Graphic graphic",
			"Hero: java.lang.Float speed",
			"Hero: java.lang.Integer controlType",
			"Water: Graphic graphic",
			"Water: java.lang.Float speed",
			"Water: java.lang.Float distanceTraveled",
		},
		{ // MILESTONE2 ******************************************************
			// Constructor and Update Methods
			"Pant: Pant(java.lang.Float,java.lang.Float,java.util.Random)",
			"Pant: java.lang.Void update(java.lang.Integer)",
			"Fireball: Fireball(java.lang.Float,java.lang.Float,"
														+ "java.lang.Float)",
			"Fireball: java.lang.Void update(java.lang.Integer)",
			"Fire: Fire(java.lang.Float,java.lang.Float,java.util.Random)",
			"Fire: Fireball update(java.lang.Integer)",
			// getGraphic Methods
			"Hero: Graphic getGraphic()",
			"Water: Graphic getGraphic()",
			"Pant: Graphic getGraphic()",
			"Fireball: Graphic getGraphic()",
			"Fire: Graphic getGraphic()",
			// handleColllision Methods
			"Hero: java.lang.Boolean handleFireballCollisions("
													+ "java.util.ArrayList)",
			"Fireball: java.lang.Void handleWaterCollisions([LWater;)",
			"Fire: java.lang.Void handleWaterCollisions([LWater;)",
			"Pant: Fire handleFireballCollisions(java.util.ArrayList)",
			"Fireball: java.lang.Void destroy()",
			// Fields
			"Game: java.util.ArrayList pants",
			"Game: java.util.ArrayList fireballs",
			"Game: java.util.ArrayList fires",
			"Pant: Graphic graphic",
			"Pant: java.util.Random randGen",
			"Fireball: Graphic graphic",
			"Fireball: java.lang.Float speed",
			"Fireball: java.lang.Boolean isAlive",
			"Fire: Graphic graphic",
			"Fire: java.util.Random randGen",
			"Fire: java.lang.Integer fireballCountdown",
			"Fire: java.lang.Integer heat"
		},
		{ // MILESTONE3 ******************************************************	
			// shouldRemove methods
			"Fireball: java.lang.Boolean shouldRemove()",
			"Pant: java.lang.Boolean shouldRemove()",
			"Fire: java.lang.Boolean shouldRemove()",
			// final methods within Game
			"Game: java.lang.String getHUDMessage()",
			"Game: java.lang.Void createRandomLevel()",
			"Game: java.lang.Void loadLevel(java.lang.String)",
		}
	};	
	
	/**
	 * This method iterates through all of the requirements in req for the
	 * given milestone, and checks for each one.  If all of these checks pass,
	 * then this method will display the SUCCESS message for that milestone.
	 * @param milestone A milestone number: 1, 2, or 3.
	 */
	private static void checkNamesAndTypes(int milestone)
	{
		String[] reqs = PantsOnFireNameTests.reqs[milestone-1];
		boolean completeSuccess = true;
		
		// iterate through all of the requirements in reqs
		for(String req : reqs)
		{
			if(req.contains("(")) // method req contain parenthese
				completeSuccess = checkMethodReq(req) && completeSuccess;
			else                  // All other reqs are for fields
				completeSuccess = checkFieldReq(req) && completeSuccess;
		}	
		
		if(completeSuccess == true)
			System.out.println("SUCCESS - Passed all name tests for "
					+ "milestone #" + milestone );
	}	
	
	/**
	 * This method looks for a public method or constructor with the specified
	 * NAME, PARAM_LIST, and RETURN_TYPE within the specified CLASS. 
	 * @param req A String formatted as "CLASS: RETURN_TYPE NAME(PARAM_LIST)" 
	 * describing a method to check for.
	 * @return true when the method described in req is found, false otherwise.
	 */
	private static boolean checkMethodReq(String req)
	{
		boolean completeSuccess = true;
		
		// read the names of the class, method, params, and return from req
		String className = req.split(":")[0];
		String completeMethodName = req.split(":")[1].trim();
		String[] paramNames = req.substring( req.indexOf('(')+1, 
											 req.indexOf(')') ).split(",");
		// replace single empty string in paramNames with zero strings
		if(paramNames.length == 1 && paramNames[0].length() < 1) 
			paramNames = new String[0];
		String methodName = className; // default to constructor
		String returnName = ""; // default to emptyString for constructor
		// use the space between return type and name to distinguish 
		// other methods from the constructor, get method and return names
		if(completeMethodName.contains(" "))
		{
			methodName = completeMethodName.substring(
										completeMethodName.indexOf(' ')+1, 
										completeMethodName.indexOf('(') );
			returnName = completeMethodName.substring(0,
										completeMethodName.indexOf(' '));
		}
		
		// now that we have the names for the current req, let's look for it
		try 
		{ 
			// convert names into classes
			Class<?> theClass = Class.forName(className);
			Class<?>[] params = new Class<?>[paramNames.length];
			for(int i=0;i<params.length;i++)
			{
				className = paramNames[i]; // for error feedback below
				params[i] = Class.forName(paramNames[i]);
				// convert primitives: from class to primitive types
				try { params[i] = (Class<?>)params[i].getField("TYPE")
																.get(null);
				} catch(NoSuchFieldException | IllegalAccessException e) {}
			}
			Class<?> ret = null;
			if(returnName.length() > 0)
			{
				className = returnName; // for error feedback below
				ret = Class.forName(returnName);
				// convert primitives: from class to primitive types (again)
				try { ret = (Class<?>)ret.getField("TYPE").get(null);
				} catch(NoSuchFieldException | IllegalAccessException  e) {}
			}
			className = theClass.getSimpleName();

			// update completeMethodName to report more clear output
			// before looking for these methods within their class
			completeMethodName = "";
			if(returnName.length() > 0)
				completeMethodName = ret.getSimpleName() + " ";
			completeMethodName += methodName + "(";
			for(int i=0;i<params.length;i++)
			{
				completeMethodName += params[i].getSimpleName();
				if( i < params.length-1 ) completeMethodName += ", ";
			}
			completeMethodName += ")";

			// find constructor/method within class matching params and ret
			if(returnName.length() < 1)
				// constructor
				theClass.getConstructor(params);
			else
			{	// method
				if( !theClass.getMethod(methodName, params).getReturnType()
					.getSimpleName().equals(ret.getSimpleName()) )
				{
					System.out.println("FAILED - Could not find the method "
							+ completeMethodName + " within the class " 
							+ className + " (double check return type).");
					completeSuccess = false;
				}
			}
		}
		// Report on any class names that could not be found
		// (this include parameter and return types)
		catch(ClassNotFoundException e)
		{
			System.out.println("FAILED - Could not find a class named "
					+ className  + ".");
			completeSuccess = false;
		}
		// Report on any methods that could not be found in a class
		catch(NoSuchMethodException e)
		{
			System.out.println("FAILED - Could not find the method " 
					+ completeMethodName + " within the class " 
					+ className + " (method must be public).");				
			completeSuccess = false;
		}
		
		return completeSuccess;
	}
	
	/**
	 * This method looks for a private field of the specified NAME and TYPE
	 * within the specified CLASS. 
	 * @param req A String formatted as "CLASS: TYPE NAME" describing a field
	 * to check for.
	 * @return true when the field described in req is found, false otherwise.
	 */
	private static boolean checkFieldReq(String req)
	{
		boolean completeSuccess = true;
		
		// read the names of the class, field type, and field from req
		String className = req.split(":")[0];
		String fieldTypeName = req.split(":")[1].trim().split(" ")[0].trim();
		String fieldName = req.split(":")[1].trim().split(" ")[1].trim();
				
		// now that we have the names for the current req, let's look for it
		try 
		{ 
			// convert names into classes
			Class<?> theClass = Class.forName(className);
			className = fieldTypeName; // for error feedback below
			Class<?> fieldType = Class.forName(fieldTypeName);
			// convert primitives: from class to primitive types
			try { fieldType = (Class<?>)fieldType.getField("TYPE").get(null);
			} catch(NoSuchFieldException | IllegalAccessException  e) {}
			className = theClass.getSimpleName();

			// find the field by name
			Field field = theClass.getDeclaredField(fieldName);
			// make sure this field is the correct type
			if(!field.getType().equals(fieldType))
			{
				System.out.println("FAILED - The field " + fieldName
						+ " within the class " + className + " should be type"
						+ fieldTypeName.substring(fieldTypeName.lastIndexOf('+')
								+1) + ".");
				completeSuccess = false;
			}
			// make sure this field is private
			try { 
				theClass.getField(fieldName); 
				System.out.println("FAILED - The field " + fieldName
						+ " within the class " + className + "should be "
								+ "private.");
				completeSuccess = false;				
			}
			catch(NoSuchFieldException e) { }
		}
		// Report on any class names that could not be found
		// (this include parameter and return types)
		catch(ClassNotFoundException e)
		{
			System.out.println("FAILED - Could not find a class named "
					+ className  + ".");
			completeSuccess = false;
		}
		// Report on any fields that could not be found in a class
		catch(NoSuchFieldException e)
		{
			System.out.println("FAILED - Could not find the field named " 
					+ fieldName + " within the class " 
					+ className + ".");
			completeSuccess = false;
		}
		
		return completeSuccess;
	}
}

/**
 * This program collects a vector set as input, and returns a resultant vector with 
 * a direction and distance. The program uses basic trigonometry and algebra to add
 * vectors and find their 'right triangle' components. 
 * 
 * For every vector (with both a length and angle), a right triangle can be formed 
 * using a North (vertical) and East (Horizontal) component. By addings these Northern 
 * and Eastern components, a final right triangle can be formed (the legs are the North/East sums). 
 * From this new triangle, the hypotenuse is the resultant vector distance, and 
 * angle ø is the direction.
 * ---------------------------------------------------------------------------------------------------------------------------------
 * Command Line Arguments:
 * It can handle pre-set, command line entered vector sets. Enter a vector array using the the given format: 
 *      
 *      Distance can be entered with any unit, as long as the unit is consistent
 *      Direction can use letters (i.e. NW, NE, S-, -W ect.) or can use degrees.
 *      Example Set: {12 SW, 5 -E, 11 35}
 *      
 * Enter as a command line argument. If a pre-set set is entered, the program will bypass the 
 * normal user input and just calculate the resultant.
 * ---------------------------------------------------------------------------------------------------------------------------------
 * PLEASE NOTE: 
 * 
 * East is considered to be 0º, with further values going Counter-Clockwise (North would be 90º)
 * 
 * ALL angles that are produced from the Vector class are in RADIANS, and MUST BE CONVERTED!
 * ---------------------------------------------------------------------------------------------------------------------------------
 * @Andrew Smith 
 * @December 6, 2015
 */
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VectorAddition
{
    public static void main (String[] args)
    {
        double inputDistance = 0, northSum = 0, eastSum = 0, resultantDis, resultantAng;
        //inputDistance is the new array length. N/E sums are running total of respective components
        String resultant;
        
        ArrayList<Vector> vectors= new ArrayList<Vector>(); //Used for storing inputted arrays
        Scanner scan = new Scanner (System.in);
        DecimalFormat fmt = new DecimalFormat("#.##");
        
        if (args.length != 0) { //Vectors have been pre-set
            Greeting(1); //Alerts user of pre-set vector set
            vectors.addAll(preSetVectorArray(args));
            inputDistance = -1; //Stops while loop
        }else { //Proceed as normal; User enters vectors
            Greeting(2); 
        }
        
        //-----------------------------------------------------------------------------------------------------------------------
        // This loop allows the user to enter in each vector. As noted above, -1 breaks the loop and -2 removes the previously
        // added vector.
        // 
        // This is BYPASSED if a command line argument is passed.
        //-----------------------------------------------------------------------------------------------------------------------
        while (inputDistance != -1) {
            System.out.print ("Enter a vector: ");
            inputDistance = scan.nextDouble();
            
            if (inputDistance >= 0) { //User did not enter -1 or -2
                vectors.add(new Vector(inputDistance, scan.next())); //Direction is scanned; no local variable
                System.out.println (vectors.get(vectors.size()-1) + "\n"); //Prints entered vector
                
            } else if (inputDistance == -2) { //Delete previous array
                System.out.println ("{" + vectors.get(vectors.size()-1) + "} Removed. \n"); //Prints deleted vector
                vectors.remove(vectors.size()-1);
            }
        }
        
        //-----------------------------------------------------------------------------------------------------------------------
        // Iterates over the vectors array. For each vector object, the object's north and east values are added to their 
        // respective running totals. 
        //-----------------------------------------------------------------------------------------------------------------------
        for (int index = 0; index < vectors.size(); index++) { 
            northSum += (vectors.get(index)).getNorth();
            eastSum += (vectors.get(index)).getEast();
        }
        
        //-----------------------------------------------------------------------------------------------------------------------
        // The resultant distance (or length) is found using the Pythagorean equation:
        //      A² + B² = C²
        // or, in this case, a transformed version:
        //      C = √(A² + B²)
        //-----------------------------------------------------------------------------------------------------------------------
        resultantDis = Math.sqrt(Math.pow(northSum,2) + Math.pow(eastSum,2));
        
        //-----------------------------------------------------------------------------------------------------------------------
        // To find the angle (Ø) of the resultant, the angle (Ø₂) of a similar triangle in the first quadrant
        // is found using arctangent. 
        //
        //      Ø₂ = ArcTan (Vertical/Horizontal)
        //
        // The absolute values of Vertical and Horizontal (North/East) are used to keep the angle in the first quadrant
        // of the standard coordinate grid.
        //-----------------------------------------------------------------------------------------------------------------------
        resultantAng = Math.atan(Math.abs(northSum) / Math.abs(eastSum));
        resultantAng = Math.toDegrees(resultantAng);
        
        //-----------------------------------------------------------------------------------------------------------------------
        // After the previous calculations, resultantAng is equal to Ø₂ NOT Ø (Ø₂ is the similar triangle angle)
        // In order to convert Ø₂ into Ø, the following conversions are neccesary.
        //
        // The first three If statements (thus not including the Else) adjust resultantAng if either sum is zero; 
        // Ø₂ is not involved.
        //-----------------------------------------------------------------------------------------------------------------------
        if (resultantDis == 0)  //If distance is zero, the angle is defaulted to zero
            resultantAng = 0;
        else if (northSum == 0) //No northern value; Angle is 0 if eastSum if positive, 180 if negative. 
            resultantAng = (eastSum > 0) ? 0:180; 
        else if (eastSum == 0)  //No eastern value; Angle is 90 if northSum if positive, 270 if negative.
            resultantAng = (northSum > 0) ? 90:270;
        
        else { //Neither sum is zero
            if (northSum < 0 && eastSum < 0) //If both sums are negative, the triangle must be formed in the third 'quadrant'
                resultantAng += 180; //If the angle is in the third quadrant, Ø = 180 + Ø₂
                
            else if (northSum < 0) //Just the northern sum is negative. Must be in fourth quadrant
                resultantAng = 360 - resultantAng; //If the angle is in the fourth quadrant, Ø = 360 - Ø₂
                
            else if (eastSum < 0) //Just the eastern sum is negative. Must be in second quadrant
                resultantAng = 180 - resultantAng; //If the angle is in the second quadrant, Ø = 180 - Ø₂
        } //No final else statement. If both sums are positive (first quadrant), then Ø₂ = Ø (Ø₂ was found in first quadrant)
        
        resultant = "\nThe resultant is: \n" + fmt.format(resultantDis) + " units @" + fmt.format(resultantAng) + " Degrees.";
        System.out.println (resultant);
    }
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Takes an array (passed through command line) and adds each individual vectors.
    // Sleep() method used to force pauses in the printing of entered vectors (for aesthetic reasons only)
    //------------------------------------------------------------------------------------------------------------------------------
    public static ArrayList<Vector> preSetVectorArray(String[] vectorSet) {
        double distance;
        String angle;
        ArrayList<Vector> preSetVectors = new ArrayList<Vector>(); //Temporary ArrayList to be returned
        
        for (int index = 0; index < vectorSet.length; index ++) {
            Scanner scanVector = new Scanner (vectorSet[index]); //Each individual vector
            
            distance = Double.parseDouble((scanVector.next())); 
            angle = scanVector.next();
            
            preSetVectors.add(new Vector(distance, angle));
            System.out.println (preSetVectors.get(preSetVectors.size()-1) + "\n"); //Prints each vector that is added
            sleep(1); //Pauses output
        }
        return preSetVectors;
    }
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Prints the user greeting depending on whether or not a pre-set array has been passed throught the command line
    //------------------------------------------------------------------------------------------------------------------------------
    public static void Greeting (int greetChoice) {
        if (greetChoice == 1) { //Vector set has been pre-defined
            System.out.println ("Vectors have been pre-added. Manually Entering vectors is not neccesary");
            System.out.println ("The vectors are as follows: \n");
            sleep(1);
        } else {
            System.out.println ("Enter a set of vectors, each with a direction and distance. \n");
        
            System.out.println ("Use either cardinal format (12 NW), or include the angle (12 45).");
            System.out.println ("If cardinal, it should be [N/S,E/W], with a dash if none (12 S-).");
            System.out.println ("East is 0º, and angles follow counter-clockwise. \n");
            
            System.out.println ("Enter -1 to stop adding vectors.");
            System.out.println ("Enter -2 to delete previous vector. \n");
        }
    }
    
    public static void sleep (int timeToSleep) { //Time in seconds
        try {
            Thread.sleep(timeToSleep * 1000); //Time is measured in milliseconds and must be adjusted
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

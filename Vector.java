/**
 * This program aims to collect vectors as input, and return a resultant vector through
 * the use of basic trigonometry and the coordinate/compas plane,
 * 
 * @Andrew Smith 
 * @October 5, 2015
 */
import java.util.Scanner;
import java.text.DecimalFormat;
public class Vector
{
    public static void main (String[] args)
    {
        int count = 0; //Makes sure user has entered a vector
        double hypotenuse, finalLength, sideLength, north = 0, east = 0;
        /*hypotenuse is the first vector length, and finalLength is the resultant length
         * sideLength is the computed  sides length given the hypotenuse (both sides of the triangle are equal)
         * north and east are the running vector totals
         * 
         * The algebraic way of solving a list of vectors is to take the net northern and net eastern movements
         * and then find the resulting hypotenuse given the sides of the triangle (using the sums). -6 m North is 
         * the net northern movement of 6 m South. 
         * The resultant equation is:
         * 
         * R = squareRoot[ (sumOfNorthernMovement^2) + (sumOfEasternMovement^2) ]
         * following the equation: C^2 = A^2 + B^2; where the northern sum is the vertical side A, and the eastern sum
         * is the side B.
         */
        String direction, resultant = "", vectorCalculation; //direction is the direction of each individual vector and the resultant is the final vector
        Scanner scan = new Scanner(System.in);
        DecimalFormat fmt = new DecimalFormat("#.##");
        
        System.out.print ("Enter the first vector (i.e. 23 SW or 12 N-) or exit (0 X): "); //User is to enter a number followed by 2 Char direction
        hypotenuse = scan.nextDouble();
        direction = scan.next();
        
        while ((hypotenuse != 0) && (direction.charAt(0) != 'X')) //As long as the user input is not (0 x)
        {
            count++; 
            sideLength = Math.sqrt((Math.pow(hypotenuse, 2) / 2)); 
            /*Both sides of the triangle are equivalent (horizontal = vertical)
             * 
             * The equation: 
             * 2X^2 = C^2
             * Is true for all 45° right triangles, where one side X, where X > 0. Therefore,
             * X = squareRoot[ (C^2)/2 ]
             * is an equivalent equation that solves for single side X. 
             */
            
            vectorCalculation = vectorCalc(sideLength, hypotenuse, direction);
 
            if (vectorCalculation.charAt(0) == 'X') {
                System.out.println ("ERROR");
                System.out.println ("-*No vector Added*-");
            } else {
                String [] parts = vectorCalculation.split("/");
                north += Double.parseDouble(parts[0]);
                east += Double.parseDouble(parts[1]);
            }
            
            System.out.print ("Enter a vector or exit (0 x): ");
            hypotenuse = scan.nextDouble();
            direction = scan.next();
           
        }
        
        if (count > 0) { //User entered a vector
            finalLength = Math.sqrt((Math.pow(north, 2)) + (Math.pow(east, 2))); 
            //A^2 + B^2 = C^2 using north as A and east as B
            resultant += fmt.format(finalLength); //Adds length to the resultant STRING
                
            //Adds directoral prefix to 'resultant'
            if (north > 0) //Net northern movement was positive (over all vectors)
                resultant += " N";
            else if (north < 0)
                resultant += " S";
            else
                resultant += "-";
                
            if (east > 0) //Net eastern movement was positive (over all vectors)
                resultant += "E";
            else if (east < 0)
                resultant += "W";
            else
                resultant += "-";
                
            System.out.print ("The resulting vector is: " + resultant);
        } else 
            System.out.print ("No vectors added");
        }
        
    public static String vectorCalc (double methSide, double methHypotenuse, String methDir)
    {
       double sideLength = methSide, hypotenuse = methHypotenuse, north = 0, east = 0;
       String direction = methDir, error = "";
       DecimalFormat fmt = new DecimalFormat("#.##");
       
       if (direction.length() == 2){
            char firstDir = direction.charAt(0);
            char secondDir = direction.charAt(1);
            
            if (firstDir == '-' || secondDir == '-') {
                if (firstDir == 'N')
                    north = hypotenuse;
                else if (firstDir == 'S')
                    north = hypotenuse * -1;
                else if (secondDir == 'E')
                    east = hypotenuse;
                else if (secondDir == 'W')
                    east = hypotenuse * -1;
                else 
                    error = "X";
            } else {
                if (firstDir == 'N')
                    north = sideLength;
                else if (firstDir == 'S')
                    north = sideLength * -1;
                else 
                    error = "X";
                    
                if (secondDir == 'E')
                    east = sideLength;
                else if (secondDir == 'W')
                    east = sideLength * -1;
                else 
                    error = "X";
           }
        } else 
            error = "X";
       
       return error + fmt.format(north) + "/" +  fmt.format(east);
    }
}

/**
 * Represents a vector with a magnitude/distance and direction. 
 * The vectors, while only using North and East components, are able to act like South and West
 * directions through negative values. (-5 meters North is 5 meters South)
 * 
 * NORTH IS 90º. DO NOT USE COMPASS ANGLES!
 * 
 * ALL ANGLES ARE STORED IN RADIANS.
 * 
 * @Andrew Smith 
 * @11/19/15
 */
import java.text.DecimalFormat;
public class Vector
{
    private double distance, angle, north, east;
    DecimalFormat fmt = new DecimalFormat("#.##");
    //-------------------------------------------------------------------------------------------------
    // Constructs a vector with given Distance (magnitude) and Direction.
    // If the given direction is in degrees, the angle is automatically converted to radians.
    // If the given direction is in cardinal form (N,S,E,W) then these degree values are found and 
    // converted to radians.
    //-------------------------------------------------------------------------------------------------
    public Vector (double Distance, String Direction) {
        distance = Distance;
        if (isNumeric(Direction)) {
            angle = Math.toRadians(Double.parseDouble(Direction));
        }else {
            angle = Math.toRadians(cardinalToAngle(Direction));
        }
        north = calcNorthValue();
        east = calcEastValue();
    }
    
    //-------------------------------------------------------------------------------------------------
    // Checks to see if the given String can be converted to a double. 
    // Return True if able to be converted.
    //-------------------------------------------------------------------------------------------------
    private static boolean isNumeric(String str)  {  
        try  
        {  
            double d = Double.parseDouble(str);  
        }  
        catch(NumberFormatException nfe)  
        {  
            return false;  
        }  
        return true;  
    }
    
    //-------------------------------------------------------------------------------------------------
    // Determines what the given direction is in degrees. Options go counter-clockwise from East
    // to NorthEast to North, and so on. East is 0º degrees.
    //-------------------------------------------------------------------------------------------------
    private static double cardinalToAngle (String Direction) {
       char firstDir = Direction.charAt(0);
       char secondDir = Direction.charAt(1);
            
        if (firstDir == '-' || secondDir == '-') {
            if (firstDir == 'N') //Just North
                return 90;
            else if (firstDir == 'S') //Just South
                return 270;
            else if (secondDir == 'E') //Just East
                return 0;
            else if (secondDir == 'W') //Just West
                return 180;
        } else {
            if (firstDir == 'N') {
                if (secondDir == 'E') //NorthEast
                    return 45;
                else if (secondDir == 'W') //NorthWest
                    return 135;
            }
            else if (firstDir == 'S') {
                if (secondDir == 'E') //SouthEast
                    return 315;
                else if (secondDir == 'W') //SouthWest
                    return 225;
            }
       }
       return -1; //None of the above directions
    }
    
    //-------------------------------------------------------------------------------------------------
    // The height (North Value) of the triangle formed using the vector is equivalent to:
    //      Sin(angle) * Distance
    // Because:
    //      Sin(angle) = North / Distance (Opposite Side / Hypotenuse)
    //-------------------------------------------------------------------------------------------------
    private double calcNorthValue () {
        return Math.sin(angle) * distance;
    }
    
    //-------------------------------------------------------------------------------------------------
    // See above. Cos(angle) = East / Distance (Adjacent Side / Hypotenuse)
    //-------------------------------------------------------------------------------------------------
    private double calcEastValue () {
        return Math.cos(angle) * distance;
    }
    
    //-------------------------------------------------------------------------------------------------
    // Returns the north component of this vector.
    //-------------------------------------------------------------------------------------------------
    public double getNorth () {
        return north;
    }
    
    //-------------------------------------------------------------------------------------------------
    // Returns the east component of this vector.
    //-------------------------------------------------------------------------------------------------
    public double getEast () {
        return east;
    }
    
    public String toString() {
        return distance + " units @" + fmt.format(Math.toDegrees(angle)) + " Degrees";
    }
}


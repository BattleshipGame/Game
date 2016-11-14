package Game;

/**
 * Declares a Point within the player boards
 * @author Maurice Ajluni
 */
public class Point
{
    int x, y;
    
    //Creates a new Point with coordinates x and y
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    //Returns x
    public int getX()
    {
        return x;
    }
    
    //Returns y
    public int getY()
    {
        return y;
    }
    
    //Prints the point as an ordered pair
    @Override
    public String toString()
    {
        return ("(" + x + "," + y + ")");
    }
}
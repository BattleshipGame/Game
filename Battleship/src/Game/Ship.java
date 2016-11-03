package Game;

//Represents a ship on the game board
public class Ship
{
    int size;
    Point[] coordinates;
    int[] hits;
    
    //Creates a new Ship with a Point[]
    public Ship(Point[] points)
    {
        size = points.length;
        coordinates = points;
        hits = new int[size];
    }
    
    //Determines if the Ship is sunk
    public boolean isSunk()
    {
        for(int i : hits)
            if(i == 0)
                return false;
        
        return true;
    }
    
    //Returns the size of the Ship
    public int getSize()
    {
        return size;
    }
    
    //Returns the Point[] of the Ship's coordinates
    public Point[] getCoordinates()
    {
        return coordinates;
    }
}
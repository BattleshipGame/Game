package Game;

public class Ship
{
    int size;
    Point[] coordinates;
    int[] hits;
    
    public Ship(int size, Point[] points)
    {
        this.size = size;
        coordinates = points;
        hits = new int[size];
    }
    
    public boolean isSunk()
    {
        for(int i : hits)
            if(i == 0)
                return false;
        
        return true;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public Point[] getCoordinates()
    {
        return coordinates;
    }
}
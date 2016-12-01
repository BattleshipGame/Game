package Game;

import java.io.Serializable;
/**
 * Represents a ship on the game board
 * @author Maurice Ajluni
 */
public abstract class Ship extends Board
{
    int size;
    Point[] coordinates;
    int[] hits;
    String name;
    
    public Ship(int x, int y, int size, boolean horizontal)
    {
        super (x, y, size, horizontal);
        
    }
    
    public enum shipType implements Serializable
    {
        Carrier, Destroyer, PatrolBoat, Submarine, BattleShip;
    }
    //Creates a new Ship with a Point[]
    /*
    public Ship(Point[] points)
    {
        size = points.length;
        coordinates = points;
        hits = new int[size];
        
        switch(size)
        {
            case 2:
                name = "Patrol Boat";
                break;
            case 3:
                name = "Submarine";
                break;
            case 4:
                name = "Battleship";
                break;
            case 5:
                name = "Carrier";
                break;
            default:
                name = "Ship";
                break;
        }
        
    }
    */
    //Determines if the Ship is sunk
    @Override
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
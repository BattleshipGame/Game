package Game;

/**
 *
 * @author Chris
 */
public interface BattleshipData 
{
public static final int SIDE_LENGTH = 10;
public static final int SHIP_COUNT = 5;
    
    //using enums for board state to maintain clarity
    static enum tile {
        vacant, miss, occupied, destroyed
    };
    
    
}

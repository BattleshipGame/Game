package Game;

/**
 *
 * @author Chris
 */
public interface BattleshipData 
{
public  final int SIDE_LENGTH = 10;
public  final int SHIP_COUNT = 5;

//uses int constants for board state to avoid converting between enums and int arrays
public final int EMPTY = 0;
public final int MISS = 1;
public final int OCCUPIED = 2;
public final int DESTROYED = 3;

public final int VERTICAL = 0;
public final int HORIZONTAL = 1;
}

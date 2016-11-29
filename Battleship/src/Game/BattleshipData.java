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

/**
 * Converts between lower case letters and their actual grid coordinates,
 * uses default method in interface instead of abstract or helper class to avoid multiple inheritance and
 * keep utility info and methods together
 * @param c Lower case character to be converted to a number
 * @return Number result
 */
default int charToNumber(char c)
{
    return c - 97;//a has ascii value of 97
}
}

package Game;

public abstract class PatrolBoat extends Ship
{
    public PatrolBoat (int x, int y, int lenght, boolean horizontal)
    {
        super (x, y, lenght, horizontal);
    }
    public String getType()
    {
        return "patrolBoat";
    }
}
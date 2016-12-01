package Game;

public abstract class DestroyerShip extends Ship
{
    public DestroyerShip (int x, int y, int lenght, boolean horizontal)
    {
        super (x, y, lenght, horizontal);
    }
    public String getType()
    {
        return "destroyerShip";
    }
}
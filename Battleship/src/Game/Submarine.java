package Game;

public abstract class Submarine extends Ship
{
    public Submarine (int x, int y, int lenght, boolean horizontal)
    {
        super (x, y, lenght, horizontal);
    }
    public String getType()
    {
        return "submarine";
    }
}

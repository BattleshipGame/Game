package Game;

public abstract class CarrierShip extends Ship
{
    public CarrierShip (int x, int y, int lenght, boolean horizontal)
    {
        super (x, y, lenght, horizontal);
    }
    public String getType()
    {
        return "carrierShip";
    }
}
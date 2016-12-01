package Game;

public interface GUIConsole 
{
    public void displayStrik (int x, int y);
    public void displayMissed (int x, int y);
    public void displayShip (Ship ship);
}

//class GUI extends JFrame implements GUIConsole
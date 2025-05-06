package Game;
 

/**
 * A location in the World
 *
 * @author Jack
 * @version v0.1
 */
public class Location implements Place
{
    public int x;
    public int y;
    /**
     * Construct location with x and y
     */
    public Location(int x, int y)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
    }
    
    public Location copy(){
        return new Location(this.x, this.y);
    }
    public Location inverse(){
        return new Location(-this.x, -this.y);
    }

    /**
     * Translate this location by another location
     *
     * @param  loc  another location to translate by
     */
    public void translate(Location loc)
    {
        this.x+=loc.x;
        this.y+=loc.y;
    }
    
    /**
     * Translate this location by x and y
     *
     * @param  x  x to translate by
     * @param  y  y to translate by
     */
    public void translate(int x, int y)
    {
        this.x+=x;
        this.y+=y;
    }
}

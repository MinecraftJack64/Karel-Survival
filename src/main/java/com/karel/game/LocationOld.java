package com.karel.game;
 

/**
 * A LocationOld in the World
 *
 * @author Jack
 * @version v0.1
 */
public class LocationOld implements Place
{
    public int x;
    public int y;
    /**
     * Construct LocationOld with x and y
     */
    public LocationOld(int x, int y)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
    }
    
    public LocationOld copy(){
        return new LocationOld(this.x, this.y);
    }
    public LocationOld inverse(){
        return new LocationOld(-this.x, -this.y);
    }

    /**
     * Translate this LocationOld by another LocationOld
     *
     * @param  loc  another LocationOld to translate by
     */
    public void translate(LocationOld loc)
    {
        this.x+=loc.x;
        this.y+=loc.y;
    }
    
    /**
     * Translate this LocationOld by x and y
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

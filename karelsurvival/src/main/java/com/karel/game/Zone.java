package com.karel.game;
 

public class Zone implements Place
{
    public Location tip1, tip2;
    public Zone(Location tip1, Location tip2){
        this.tip1 = tip1;
        this.tip2 = tip2;
    }
    public void translate(Location loc)
    {
        tip1.translate(loc);
        tip2.translate(loc);
    }
    public void translate(int x, int y)
    {
        tip1.translate(x, y);
        tip2.translate(x, y);
    }
    public void translateNE(int x, int y)
    {
        tip2.translate(x, y);
    }
    public void translateSW(int x, int y)
    {
        tip1.translate(x, y);
    }
    public void translateNE(Location loc)
    {
        tip2.translate(loc);
    }
    public void translateSW(Location loc)
    {
        tip1.translate(loc);
    }
    public int getArea(){
        return (tip2.y-tip1.y)*(tip2.x-tip1.x);
    }
    public int getPerimeter(){
        return 2*((tip2.y-tip1.y)+(tip2.x-tip1.x));
    }
    public Location getCenter(){
        return new Location((tip2.x+tip1.x)/2, (tip2.y+tip1.y)/2);
    }
    public void reformat(){
        if(tip1.x>tip2.x){
            int x = tip2.x;
            tip2.x = tip1.x;
            tip1.x = x;
        }
        if(tip1.y>tip2.y){
            int y = tip2.y;
            tip2.y = tip1.y;
            tip1.y = y;
        }
    }
    public Zone copy(){
        return new Zone(this.tip1.copy(), this.tip2.copy());
    }
}

package com.karel.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Write a description of class EntrancePortal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EntrancePortal extends GridObject
{
    static HashSet<Integer> registeredColors = new HashSet<Integer>();
    List<GridEntity> exiters = new ArrayList<GridEntity>();
    EntrancePortal other;
    GridObject source;
    int health = 14;
    int colorCode = -1; // red, green, blue, yellow, purple, cyan, orange
    public EntrancePortal(GridObject source){
        this.source = source;
        setImage("Projectiles/Static/portal.png");
        setTeam(source.getTeam());
    }
    public void link(EntrancePortal other){
        colorCode = Greenfoot.getRandomNumber(7);
        while(isRegistered(colorCode)){
            colorCode = Greenfoot.getRandomNumber(7);
        }
        if(this.other==null){
            this.other = other;
            other.link(this, colorCode); // link is called a second time on original caller
        }
        updateTint(colorCode);
        registerColor(colorCode);
    }
    public void link(EntrancePortal other, int color){
        colorCode = color;
        this.other = other;
        updateTint(colorCode);
    }
    public static void registerColor(int color){
        registeredColors.add(color);
    }
    public static void deregisterColor(int color){
        registeredColors.remove(color);
    }
    public static boolean isRegistered(int color){
        if(registeredColors.size()==7)return false;
        return registeredColors.contains(color);
    }
    public void updateTint(int color){
        switch(color){
            case 0:
                setTint(255, 0, 0);
                break;
            case 1:
                setTint(0, 255, 0);
                break;
            case 2:
                setTint(0, 0, 255);
                break;
            case 3:
                setTint(255, 255, 0);
                break;
            case 4:
                setTint(255, 0, 255);
                break;
            case 5:
                setTint(0, 255, 255);
                break;
            case 6:
                setTint(255, 127, 0);
                break;
        }
    }
    public void update(){
        if(other==null)return;
        explodeOn(health*5, "ally", (g)->{
            if(!exiters.contains(g)){
                if(g.canBePulled()){
                    g.pullTowards(this, 10, this);
                    if(distanceTo(g)<=10){
                        lowerHealth();
                        other.receive(g);
                    }
                }else{
                    exiters.add(g);
                }
            }
        }, null);
        for(int i = exiters.size()-1; i >= 0; i--){
            if(exiters.get(i).isDead()||distanceTo(exiters.get(i))>health*5){
                exiters.remove(i);
            }
        }
    }
    public void animate(){
        setRotation(getRotation()+5);
        super.animate();
    }
    public void lowerHealth(){
        health--;
        scaleTexture((int)(health*2.5+35), (int)(health*2.5+35));
    }
    public void receive(GridEntity g){
        g.pullTo(getX(), getY(), this);
        exiters.add(g);
        lowerHealth();
        if(health<=0){
            other.kill();
            this.kill();
        }
    }
    public void notifyWorldRemove(){
        deregisterColor(colorCode);
        super.notifyWorldRemove();
    }
    public void kill(){
        //placeholder
        super.die();
        getWorld().removeObject(this);
    }
}



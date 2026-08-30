package com.karel.game.gridobjects.gridentities.zombies.portal;

import com.karel.game.EntrancePortal;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;

/**
 * Write a description of class PortalZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PortalZombie extends Zombie
{
    private static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.meatshield};
    private int portals, life;
    public String getStaticTextureURL(){return "portalzareln.png";}
    /**
     * Initilise this rocket.
     */
    public PortalZombie()
    {
        startHealth(250);
        portals = 1;
    }
    public PortalZombie(int portals, int portalLife){
        this();
        this.portals = portals;
        life = portalLife;
    }
    public void behave(){
        super.behave();
        if(portals>0){
            if(distanceTo(getTarget())<400||getPercentHealth()<0.8){
                createPortal();
            }
        }
    }
    @Override
    public boolean prioritizeTarget(){
        return portals>0;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 500;
    }
    public void createPortal(){
        if(portals>0&&canAttack()){
            double ang = face(getTarget(), false);
            double d = 400;
            double x = getX()+Math.cos((ang-90)*Math.PI/180)*d, y = getY()+Math.sin((ang-90)*Math.PI/180)*d;
            EntrancePortal one = new EntrancePortal(this, life);
            addObjectHere(one);
            EntrancePortal two = new EntrancePortal(this, life);
            getWorld().addObject(two, x, y);
            one.link(two);
            portals--;
        }
    }
    public String getName(){
        return "Portal Zombie";
    }
    @Override
    public String getZombieID(){
        return "portal";
    }
}

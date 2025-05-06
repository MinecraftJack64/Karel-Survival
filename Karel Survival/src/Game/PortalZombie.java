package Game;
import greenfoot.*;
import java.util.List;

/*
 * classes
 * fila-mint
 * reinforce-mint: shieldzombie
 * bombard-mint: explodingzombie
 * arma-mint: zombie that controls bombs that drop down
 * contain-mint
 * spear-mint
 * pepper-mint
 * enchant-mint
 * winter-mint
 * appease-mint: shooterzombie stays at a distance and shoots small low damage bullets at you, slower than normal
 * ail-mint: poisonzombie that occasionally lets out a cloud that poisons you and boosts nearby zombies and leaves behind a poison area on death. Will slow you if you are too close to it. Very slow and tanky
 * conceal-mint: ninjazombies remain invisible and wait for the perfect opportunity to strike. They run towards you and when they're next to you, they throw 9 ninja stars while quickly circling you 3 times. They then run away and wait again. They occasionally reveal their location for short intervals of time
 * enlighten-mint
 * enforce-mint: zombie
 */
/**
 * Write a description of class PortalZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PortalZombie extends Zombie
{
    private int counterct;
    private int portals;
    private GreenfootImage rocket = new GreenfootImage("russianzareln.png");
    /**
     * Initilise this rocket.
     */
    public PortalZombie()
    {
        rocket.scale(45, 45);
        setImage(rocket);
        startHealth(250);
        portals = 1;
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
    //ovveride this
    public int getXP(){
        return 500;
    }
    public void createPortal(){
        if(portals>0&&canAttack()){
            double ang = face(getTarget(), false);
            double d = 400;
            double x = getRealX()+Math.cos((ang-90)*Math.PI/180)*d, y = getRealY()+Math.sin((ang-90)*Math.PI/180)*d;
            EntrancePortal one = new EntrancePortal(this);
            addObjectHere(one);
            EntrancePortal two = new EntrancePortal(this);
            getWorld().addObject(two, x, y);
            one.link(two);
            portals--;
        }
    }
    public String getName(){
        return "Portal Zombie";
    }
}

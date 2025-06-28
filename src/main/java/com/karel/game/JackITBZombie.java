package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.ShieldID;

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
 * Write a description of class JackITBZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JackITBZombie extends Zombie
{
    private int windspeed;//5 second winding, up to speed 150(75 degrees per frame)
    private int windcooldown = 120;
    //Big: cooldown: 90, height: 150, distance: 350
    //Medium: cooldown: 60, height: 75, distance: 150
    //Small: cooldown: 30, height 25, distance: 50
    private GridEntity inside;
    public String getStaticTextureURL(){return "russianzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    /**
     * Initilise this rocket.
     */
    public JackITBZombie()
    {
        this(Greenfoot.getRandomNumber(4)==3);
    }
    public JackITBZombie(boolean spawnJoker){
        scaleTexture(100, 70);
        setRotation(0);
        setSpeed(1);
        startHealthShield(new ArmorShield(new ShieldID(this), 800));
        inside = !spawnJoker?new Zombie(){public boolean wasInOriginalWave(){return false;}}:new JokerZombie(){public boolean wasInOriginalWave(){return false;}};
    }
    //ovveride this
    public int getXP(){
        return 800;
    }
    public void behave(){
        if(getHeight()==0){
            if(windcooldown>0){
                windcooldown--;
            }else{
                if(canMove()){
                    if(windspeed<150){
                        if(canAttack()){
                            windspeed++;
                            if(windspeed==150){
                                launchInside();
                                kill(this);
                            }
                        }
                    }
                    setRotation(getRotation()+windspeed/2);
                }else{
                    windspeed = 0;
                }
            }
        }
    }
    public void applyphysics(){
        if(getHeight()>0){
            setHeight(getHeight()-100);
            if(getHeight()<=0){
                doLanding();
                setHeight(0);
            }
        }
    }
    /*public void behaveOld(){
        if(jumpcooldown<=0){
            if(distanceTo(getTarget())<getJumpDistance()&&inside!=null&&getHeight()==0){//spawn inside
                getWorld().addObject(inside, getX(), getY());
                inside = null;
                jumpcooldown = getJumpCooldown();
            }else if(canMove()){
                double d = Math.min(distanceTo(getTarget().getX(), getTarget().getY()), getJumpDistance()*getMultipliedSpeed());
                initiateJump(face(getTarget(), false), d, getJumpHeight());
                jumpcooldown = getJumpCooldown();
            }
        }
        jumpcooldown--;
    }*/
    public void launchInside(){
        if(inside==null){
            return;
        }
        addObjectHere(inside);
        inside.knockBack(0, 0, 300, this);
        inside = null;
    }
    public void die(GridObject source){
        if(inside==null){
            super.die(source);
            return;
        }
        addObjectHere(inside);
        inside = null;
        super.die(source);
    }
    public void doLanding(){
        if(!canAttack()){
            return;
        }
        explodeOn(getExplosionRange(), getDamage(), null);//smash explosion later
    }
    public void feast(){
        launchInside();
        kill(this);
    }
    public double getGravity(){
        return 1;
    }
    public int getDamage(){
        return 400;
    }
    public int getExplosionRange(){
        return 120;
    }
    public boolean canBePulled(){
        return false;
    }
    
    public String getName(){
        return "Jack-in-the-box Zombie";
    }
}

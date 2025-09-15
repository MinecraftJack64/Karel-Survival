package com.karel.game.gridobjects.gridentities.zombies.jackinthebox;

import com.karel.game.Target;
import com.karel.game.ArmorShield;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.joker.JokerZombie;
import com.karel.game.weapons.ShieldID;
public class JackITBZombie extends Zombie
{
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.tank, ZombieClass.controller};
    private Target targ;
    private int windspeed;//5 second winding, up to speed 150(75 degrees per frame)
    private int windcooldown = 120;
    private GridEntity inside;
    public String getStaticTextureURL(){return "jackzareln.png";}
    public JackITBZombie()
    {
        this(Greenfoot.getRandomNumber(4)==3);
        int h = Greenfoot.getRandomNumber(10);
        if(h>0){
            targ = new Target();
        }
        setHeight(h*100);
    }
    public JackITBZombie(boolean spawnJoker){
        scaleTexture(100, 70);
        setRotation(0);
        setSpeed(4);
        startHealthShield(new ArmorShield(new ShieldID(this), 800));
        inside = !spawnJoker?new Zombie():new JokerZombie();
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
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
        }else{
            if(targ!=null&&!targ.isInWorld()){
                getWorld().addObject(targ, getX(), getY());
            }
            double monangle = face(getTarget(), false);
            walk(monangle, 1);
            if(targ!=null){
                targ.setLocation(getX(), getY());
            }
        }
    }
    public void applyPhysics(){
        if(getHeight()>0){
            setHeight(getHeight()-50);
            if(getHeight()<=0){
                doLanding();
                setHeight(0);
            }
        }
        super.applyPhysics();
    }
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
        if(targ!=null&&targ.isInWorld()){
            getWorld().removeObject(targ);
            targ = null;
        }
        if(!canAttack()){
            return;
        }
        explodeOn(getExplosionRange(), getDamage(), null);//smash explosion later
    }
    public boolean canFly(){
        return true;
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

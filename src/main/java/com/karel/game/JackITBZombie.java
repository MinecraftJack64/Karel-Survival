package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.ShieldID;
public class JackITBZombie extends Zombie
{
    private int windspeed;//5 second winding, up to speed 150(75 degrees per frame)
    private int windcooldown = 120;
    private GridEntity inside;
    public String getStaticTextureURL(){return "russianzareln.png";}
    public JackITBZombie()
    {
        this(Greenfoot.getRandomNumber(4)==3);
    }
    public JackITBZombie(boolean spawnJoker){
        scaleTexture(100, 70);
        setRotation(0);
        setSpeed(1);
        startHealthShield(new ArmorShield(new ShieldID(this), 800));
        inside = !spawnJoker?new Zombie():new JokerZombie();
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

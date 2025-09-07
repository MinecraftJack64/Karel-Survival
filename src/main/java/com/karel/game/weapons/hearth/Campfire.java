package com.karel.game.weapons.hearth;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Pet;
import com.raylib.Texture;

/**
 * A campfire that constantly heals allies and damages enemies.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Campfire extends Pet
{
    private int animationFrame = 0;
    private int gadgetCooldown = 0;
    private boolean dying = false;
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/hearth/proj.png");
    public String getStaticTextureURL(){return "karelnOff.png";}
    public Campfire(GridEntity hive)
    {
        super(hive);
        setImage("hearth/pet0.png");
        startHealth(400);
        inherit(hive);
        scaleTexture(50);
    }
    public void behave()
    {
        if(gadgetCooldown==0){
            explodeOn(150, (g)->{
                if(isAggroTowards(g))damage(g, 8);
                else if(isAlliedWith(g))heal(g, 4);
            }, null);
        }else{
            explodeOn(600, (g)->{
                if(isAggroTowards(g))damage(g, 10);
                else if(isAlliedWith(g))heal(g, 5);
            }, null);
            gadgetCooldown--;
        }
        if(!dying) heal(this, 1);
        else damage(this, 10);
    }
    public void startDie(){
        dying = true;
    }
    public void heal(int amt, GridObject source){
        super.heal(source!=this?0:amt, source);
    }
    public void render(){
        if(++animationFrame>11)animationFrame=0;
        int range = 150;
        if(gadgetCooldown>0){
            setImage("hearth/petUpgrade"+(animationFrame/3)+".png");
            range = 600;
        }
        else{
            setImage("hearth/pet"+(animationFrame/3)+".png");
        }
        renderTexture(auraTexture, getX(), getY(), range*2, range*2, getRotation(), 127);
        super.render();
    }
    public void gadget(){
        gadgetCooldown = 200;
    }
}

package com.karel.game.gridobjects.gridentities.zombies.hugging;

import com.karel.game.Greenfoot;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.raylib.Texture;
public class HuggingZombie extends Zombie
{
    public String getStaticTextureURL(){return "huggingzareln.png";}
    private ZombieClass[] classes = new ZombieClass[]{ZombieClass.meatshield};
    private Texture rocket = Greenfoot.loadTexture(spriteOrigin()+"sadhuggingzareln.png");
    private Texture rocket2 = Greenfoot.loadTexture(spriteOrigin()+"huggingzareln.png");
    private boolean attack = true;
    public HuggingZombie()
    {
        scaleTexture(55, 55);
        setSpeed(10);
        startHealth(500);
    }
    public void attack(){
        if(attack){
            super.attack();
            attack = false;
            setImage(rocket2);
        }
    }
    public void walk(double ang, double mult){
        super.walk(ang, mult);
        attack = true;
        setImage(rocket);
    }
    public boolean canBePulled(){
        return false;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
    public int getXP(){
        return 90;
    }
    public String getName(){
        return "Hugging Zombie";
    }
}

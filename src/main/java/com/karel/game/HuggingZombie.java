package com.karel.game;
import com.raylib.Texture;
import static com.raylib.Raylib.loadTexture;
public class HuggingZombie extends Zombie
{
    private Texture rocket = loadTexture("sadhuggingzareln.png");
    private Texture rocket2 = loadTexture("huggingzareln.png");
    private boolean attack = true;
    /**
     * Initilise this rocket.
     */
    public HuggingZombie()
    {
        scaleTexture(55, 55);
        setSpeed(10);
        startHealth(1000);
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
    //ovveride this
    public int getXP(){
        return 500;
    }
    public String getName(){
        return "Hugging Zombie";
    }
    /*public void damage(int amt){
        if(shieldhealth>0&&amt>0){
            shieldhealth-=amt;
            shieldBar.setValue(shieldhealth);
            if(shieldhealth<=0){
                shieldhealth = 0;
                setImage(rocket2);
            }
        }else
            super.damage(amt);
    }*/
}

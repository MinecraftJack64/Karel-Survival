package com.karel.game.gridobjects.gridentities.zombies.guardianangel;

import com.karel.game.ExternalImmunityShield;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.ShieldID;

/**
 * Write a description of class GuardianAngelZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuardianAngelZombie extends Zombie
{
    private ShieldID myShield, immuneShield;
    public String getStaticTextureURL(){return "angelzareln.png";}
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    /**
     * Initilise this rocket.
     */
    public GuardianAngelZombie()
    {
        scaleTexture(20, 20);
        setSpeed(4.5);
        startHealth(777);
        myShield = new ShieldID(this);
        immuneShield = new ShieldID(this, "GuardianAngelZombie immunity");
        applyShield(new ExternalImmunityShield(myShield, -1));
    }
    public void die(GridObject killer){
        if(hasMounter()){
            try{((GridEntity)getMounter()).removeShield(immuneShield);}catch(Exception e){}
        }
        super.die(killer);
    }
    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(hasMounter()){
            return;
        }
        if(getHeight()==0){
            setHeight(1);
        }
        if(!hasShield(myShield)){
            applyShield(new ExternalImmunityShield(myShield, -1));
        }
        super.behave();
        damage(this, 1);
    }
    public void attack(){
        if(isAggroTowards(getTarget()))super.attack();
        else if(isAlliedWith(getTarget())){
            getTarget().mount(this, 90, 60);
            //removeShield(myShield);
            heal(this, getMaxHealth());
            getTarget().applyShield(new GuardianShield(immuneShield, this));
        }
    }
    
    @Override
    public boolean isPotentialTarget(GridEntity entity){
        return isAlliedWith(entity)&&entity.canDetect()&&!(entity instanceof GuardianAngelZombie);
    }
    
    //ovveride this
    public int getXP(){
        return 777;
    }
    
    public boolean canFly(){
        return true;
    }
    
    public String getName(){
        return "Guardian Angel Zombie";
    }
    public static class GuardianShield extends Shield
    {
        GridEntity guardian;
        public GuardianShield(ShieldID myG, GridEntity guardian){
            super(myG);
            this.guardian = guardian;
        }
        public int processDamage(int dmg, GridObject source){
            guardian.hit(dmg, guardian);
            return source==getHolder()?dmg:0;//does not stop damage if source is self
        }
        public boolean damage(int amt, GridObject source){
            return false;
        }
        public boolean canBreak(){
            return false;
        }
    }
}

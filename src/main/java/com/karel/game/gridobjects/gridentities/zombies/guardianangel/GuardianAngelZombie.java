package com.karel.game.gridobjects.gridentities.zombies.guardianangel;

import com.karel.game.ExternalImmunityShield;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Shield;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.exorcist.DemonZombie;
import com.karel.game.shields.ShieldID;

/**
 * Write a description of class GuardianAngelZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuardianAngelZombie extends Zombie
{
    public static final ZombieClass[] classes = new ZombieClass[]{ZombieClass.support, ZombieClass.pressurer};
    private ShieldID myShield, immuneShield;
    public String getStaticTextureURL(){return "angelzareln.png";}
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
            setDetectable(true);
        }
        super.behave();
        damage(this, 1);
    }
    public void attack(){
        if(isAggroTowards(getTarget())||getTarget() instanceof DemonZombie)super.attack();
        else if(isAlliedWith(getTarget())){
            getTarget().mount(this, 90, 60);
            setDetectable(false);
            //removeShield(myShield);
            heal(this, getMaxHealth());
            getTarget().applyShield(new GuardianShield(immuneShield, this));
        }
    }
    
    @Override
    public boolean isPotentialTarget(GridEntity entity){
        return isAlliedWith(entity)&&entity.canDetect()&&!(entity instanceof GuardianAngelZombie)||(entity instanceof DemonZombie);
    }
    
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    @Override
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
    @Override
    public String getZombieID(){
        return "guardianangel";
    }
}

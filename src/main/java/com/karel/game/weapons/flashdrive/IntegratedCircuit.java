package com.karel.game.weapons.flashdrive;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.LightningStrike;
import com.karel.game.Pet;

/**
 * Used by FlashDrive
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IntegratedCircuit extends Pet
{   
    private LightningStrike bolt;
    private boolean started = false;
    private static int attackrange = 50;
    private boolean upgraded;
    private int boltCooldown = 30;
    /**
     * Initilise this rocket.
     */
    public IntegratedCircuit(GridEntity hive, boolean upgraded)
    {
        super(hive);
        this.upgraded = upgraded;
        setSpeed(1.5);
        startHealth(600);
        inherit(hive);
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave()
    {
        if(bolt==null&&!started){
            started = true;
            bolt = new Awakener(this);
            addObjectHere(bolt);
        }
        if(bolt!=null){
            return;
        }
        double monangle = face(getTarget(), canMove());
        if(distanceTo(getTarget())>attackrange)walk(monangle, 1);
        else{
            if(canAttack()){
                attack();
            }
        }
        boltCooldown--;
        if(boltCooldown<=0){
            boltCooldown = 30;
            if(upgraded){
                if(distanceTo(getTarget())<attackrange*8&&isAggroTowards(getTarget())){
                    getTarget().addObjectHere(new FlashLightningStrike(this));
                }else if(getHealth()<getMaxHealth()){
                    addObjectHere(new FlashLightningStrike(this));
                }else if(isAlliedWith(getTarget())&&getTarget().getPercentHealth()<1){
                    getTarget().addObjectHere(new FlashLightningStrike(this));
                }
            }
        }
        
    }
    public void attack(){
        explodeOn(attackrange, "enemy", (g)->{
            if(Math.abs(face(g, false)-getTargetRotation())<30){
                damage(g, 15);
            }
        }, null);
    }
    public void boltDone(){
        bolt = null;
    }
    public void die(GridObject g){
        bolt = new Awakener(this);
        addObjectHere(bolt);
        super.die(g);
    }
    private static class Awakener extends LightningStrike{
        public Awakener(GridObject source){
            super(source);
            setDamage(450);
            setHeight(4500);
        }
        public void die(){
            super.die();
            ((IntegratedCircuit)getSource()).boltDone();
        }
    }
    private static class FlashLightningStrike extends LightningStrike{
        public FlashLightningStrike(GridObject source){
            super(source);
            setDamage(40);
            setHeight(2500);
            setHitAllies(true);
            setSelfHit(true);
        }
        public void doHit(GridEntity g){
            if(!isAlliedWith(g))super.doHit(g);
            else{
                heal(g, getDamage());
                System.out.println(g);
                System.out.println(getPower());
            }
        }
    }
    public String getPetID(){
        return "flashdrive-ic";
    }
}

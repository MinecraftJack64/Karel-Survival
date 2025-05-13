package com.karel.game;
import java.util.List;

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
    private static int attackrange = 35;
    /**
     * Initilise this rocket.
     */
    public IntegratedCircuit(GridEntity hive)
    {
        super(hive);
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
            setDamage(600);
            setRealHeight(4500);
        }
        public void die(){
            super.die();
            ((IntegratedCircuit)getSource()).boltDone();
        }
    }
}

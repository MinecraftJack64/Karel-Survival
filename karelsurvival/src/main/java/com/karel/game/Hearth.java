package com.karel.game;
import java.util.List;

/**
 * Write a description of class Hearth here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hearth extends Weapon
{
    private double focus = 0;//40 framse to reach 1
    private static final int ult = 1500;
    public void fire(){
        //do 8 damage while not moving and 4 damage while moving
        //range 75
        List<GridEntity> l = getHolder().getGEsInRange(150);
        for(GridEntity g:l){
            if(getHolder().isAggroTowards(g))getHolder().damage(g, (int)(focus*(getHand().isMoving()?4:8)));
        }
    }
    public void fireUlt(){
        Sounds.play("crossbowshoot");
        double x = getHand().getTargetX();
        double y = getHand().getTargetY();
        double d = Math.min(400, getHolder().distanceTo(x, y));
        CampfireDropper bullet = new CampfireDropper(getHand().getTargetRotation(), d, d/2, new Campfire(getHolder()), getHolder());
        getHolder().addObjectHere(bullet);
        focus = 0;
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(getHand().isAttacking()&&focus<1){
            focus+=0.025;
        }else if(!getHand().isAttacking()&&focus>0){
            focus-=0.025;
        }
        if(!getHand().isMoving()&&!getHand().isAttacking()){
            chargeUlt((int)((1-focus)*10));
        }
        updateAmmo((int)(focus*40));
    }
    public Hearth(ItemHolder actor){
        super(actor);
        //chargeUlt(1500);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(40, (int)(focus*40));
    }
    public String getName(){
        return "Hearth";
    }
    public int getRarity(){
        return 1;
    }
}







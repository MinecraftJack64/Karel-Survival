package com.karel.game;
/**
 * Write a description of class Necromancer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Necromancer extends Weapon
{
    private static final int ult = 1000;
    private Lifesteal lasso;
    private int ultchargecooldown = 0;
    GridEntity hypno;
    public void fire(){//one full ammo deals 350 damage
        if (lasso==null) 
        {
            Lifesteal bullet = new Lifesteal(getHand().getTargetRotation(), hypno, getAttackUpgrade()==1, getHolder());
            getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
            //bullet.move ();
            lasso = bullet;
            Sounds.play("lifestealshoot");
        }
    }
    public void fireUlt(){
        Hypnotizer bullet = new Hypnotizer(getHand().getTargetRotation(), getHolder(), this);
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("swirl");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
        if(hypno!=null&&(hypno.isDead()||!hypno.getTeam().equals(getHolder().getTeam()))){
            //System.out.println(hypno.getTeam());
            hypno = null;
        }
    }
    public void notifyHypno(GridEntity targ){
        hypno = targ;
        //System.out.println("hypno notified");
    }
    public Necromancer(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Necromancer";
    }
    public int getRarity(){
        return 6;
    }
}





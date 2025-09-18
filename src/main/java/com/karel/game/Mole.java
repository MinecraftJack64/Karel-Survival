package com.karel.game;
import java.util.List;

import com.karel.game.gridobjects.hitters.Hitter;

/**
 * Write a description of class Mole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mole extends Hitter
{
    private int reloadDelayCount;
    private int gunReloadTime = 15;
    private MoleUppercut mu;
    private MoleSinkhole ms;
    private MoleSpikePuddle msp;
    private double tx, ty;
    private boolean inUlt = false;
    public Mole(GridObject source)
    {
        super(source);
        reloadDelayCount = gunReloadTime;
    }
    public void attackAt(double x, double y, boolean issuper, boolean canfast){
        target(x, y);
        if(reloadDelayCount>=gunReloadTime){
            setVisible(false);
            double d = Math.min(250, distanceTo(x, y));
            mu = new MoleUppercut(getRotation(), d, inUlt()&&canfast?40:80, issuper, this, this);
            addObjectHere(mu);
            reloadDelayCount = 0;
        }
    }
    public void notifyLand(double x, double y){
        pullTo(x, y);
        mu = null;
        setVisible(true);
    }
    public void target(double x, double y){
        tx = x;
        ty = y;
    }
    public void startUlt(){
        ms = new MoleSinkhole(0, this);
        addObjectHere(ms);
        inUlt = true;
    }
    public void stopUlt(){
        ms.die();
        ms = null;
        inUlt = false;
    }
    public void gadget(){
        if(inUlt)stopUlt();
        pullTo(getSource().getX(), getSource().getY());
        explodeOn(120, 200);
        explodeOnEnemies(120, (g)->{
            g.knockBack(face(g, false), 100, 20, this);
        });
        msp = new MoleSpikePuddle(this);
        addObjectHere(msp);
    }
    public boolean canGadget(){
        return !(msp!=null&&msp.isInWorld());
    }
    public boolean inUlt(){
        return inUlt;
    }
    public void update(){
        super.update();
        if(mu==null)reloadDelayCount++;
        if(!inUlt()){
            face(tx, ty, true);
            if(distanceTo(tx, ty)>30&&reloadDelayCount>=gunReloadTime){
                move(getRotation(), 15);
            }
        }else{
            attackAt(getX(), getY(), false, false);
        }
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
    }
}

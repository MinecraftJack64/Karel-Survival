package com.karel.game.weapons.spear;
import java.util.HashMap;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;
import com.raylib.Color;
import com.raylib.Raylib;
import com.raylib.Vector2;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ThrownSpear extends Bullet
{
    /** The damage this bullet will deal */
    private static final int maxdrawcooldown = 15;

    private static Color retColor = new Color((byte)255, (byte)200, (byte)0, (byte)127), loadColor = new Color((byte)234, (byte)0, (byte)255, (byte)255);
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    //private int life = 10;
    GridEntity returnto;
    boolean isreturning;
    int phase = 0;
    int drawcooldown = maxdrawcooldown;
    SpearWeapon myspear;
    HashMap<GridEntity, Integer> scores = new HashMap<GridEntity, Integer>();
    public ThrownSpear(boolean ir, GridEntity targ, SpearWeapon sp, double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(17);
        setLife(26);
        setDamage(225);
        setNumTargets(-1);
        setImage(!isreturning?"Weapons/spear/spear.png":"Weapons/spear/spear-return.png");
        scaleTexture(60);
        setRotation(getRotation()+180);
        returnto = targ;
        isreturning = ir;
        myspear = sp;
    }
    public void applyPhysics(){
        if(!isreturning){//part of main attack
            if(phase==1)super.applyPhysics();//throw
            else if(phase==0){//draw back
                setLocation(Math.cos((getDirection()-90)*Math.PI/180)*(drawcooldown-maxdrawcooldown)+returnto.getX(), Math.sin((getDirection()-90)*Math.PI/180)*(drawcooldown-maxdrawcooldown)+returnto.getY());
                drawcooldown--;
                if(drawcooldown<=0){
                    // check if throw spear
                    if(myspear.throwSpear()){phase = 1;setDamage(150);myspear.leaveHand();}
                    else {phase = 2;drawcooldown = -15;}
                }
            }else if(phase==2){//stabbing
                setLocation(Math.cos((getDirection()-90)*Math.PI/180)*(drawcooldown)+returnto.getX(), Math.sin((getDirection()-90)*Math.PI/180)*(drawcooldown)+returnto.getY());
                drawcooldown += 10;
                checkHit();
                if(drawcooldown>=120){
                    isreturning = true;//return but is no longer attack
                    setAggression(false);
                }
            }
        }else{//part of ult
            setDirection(face(returnto, true));
            setRotation(getDirection()+90);
            setLife(2);
            super.applyPhysics();
            if(distanceTo(returnto)<9){
                myspear.returnSpear(isAttack()/*is this called back or from melee*/?scores:null);
                myspear.enterHand();
                super.die();
            }
        }
    }
    public void render(){
        super.render();
        //draw line between source and me
        if(isreturning&&isAttack()){
            Raylib.drawLineEx(
                new Vector2(renderTransformX((int)getX()), renderTransformY((int)(getY()-getHeight()))),
                new Vector2(renderTransformX((int)returnto.getX()), renderTransformY((int)(returnto.getY()-returnto.getHeight()))),
                7,
                retColor
            );
            if(scores.size()>0){
                Raylib.drawLineEx(
                    new Vector2(renderTransformX((int)getX()), renderTransformY((int)(getY()-getHeight()))),
                    new Vector2(renderTransformX((int)returnto.getX()), renderTransformY((int)(returnto.getY()-returnto.getHeight()))),
                    4,
                    loadColor
                );
            }
        }
    }
    public void expire(){
        LandedSpear ls = new LandedSpear(myspear, scores);
        addObjectHere(ls);
        super.expire();
    }
    public void injectScores(HashMap<GridEntity, Integer> sc){
        scores = sc;
    }
    
    public void doHit(GridEntity targ){
        if(isreturning||phase==1){
            scores.put(targ, scores.getOrDefault(targ, 0) + 1);
        }else{
            targ.knockBack(getDirection(), 40, 20, this);
        }
        super.doHit(targ);
    }
}

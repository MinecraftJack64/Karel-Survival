package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.HashMap;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ThrownSpear extends Bullet
{
    /** The damage this bullet will deal */
    private static final int maxdrawcooldown = 15;
    
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
        setLife(20);
        setDamage(200);
        setNumTargets(-1);
        returnto = targ;
        isreturning = ir;
        myspear = sp;
    }
    public void applyPhysics(){
        if(!isreturning){//part of main attack
            if(phase==1)super.applyPhysics();//throw
            else if(phase==0){//draw back
                setRealLocation(Math.cos((getDirection()-90)*Math.PI/180)*(drawcooldown-maxdrawcooldown)+returnto.getRealX(), Math.sin((getDirection()-90)*Math.PI/180)*(drawcooldown-maxdrawcooldown)+returnto.getRealY());
                drawcooldown--;
                if(drawcooldown<=0){
                    // check if throw spear
                    if(myspear.throwSpear()){phase = 1;setDamage(100);myspear.leaveHand();}
                    else {phase = 2;drawcooldown = -15;}
                }
            }else if(phase==2){//stabbing
                setRealLocation(Math.cos((getDirection()-90)*Math.PI/180)*(drawcooldown)+returnto.getRealX(), Math.sin((getDirection()-90)*Math.PI/180)*(drawcooldown)+returnto.getRealY());
                drawcooldown += 10;
                checkHit();
                if(drawcooldown>=120){
                    isreturning = true;//return but is no longer attack
                    setAggression(false);
                }
            }
        }else{//part of ult
            setDirection(face(returnto, true));
            setRealRotation(getDirection()+90);
            setLife(2);
            super.applyPhysics();
            if(distanceTo(returnto)<9){
                myspear.returnSpear(isAttack()/*is this called back or from melee*/?scores:null);
                myspear.enterHand();
                super.die();
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

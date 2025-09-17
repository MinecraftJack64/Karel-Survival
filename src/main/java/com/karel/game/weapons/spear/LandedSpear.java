package com.karel.game.weapons.spear;
import java.util.HashMap;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.collectibles.Collectible;
import com.raylib.Texture;

/**
 * represents the Spear weapon when it is on the ground. When collected by the player, it returns to their hand
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class LandedSpear extends Collectible
{
    private SpearWeapon myspear;
    private HashMap<GridEntity, Integer> hs;
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/spear/wave.png");
    public LandedSpear(SpearWeapon sp, HashMap<GridEntity, Integer> scores)
    {
        myspear = sp;
        sp.notifySpear(this);
        hs = scores;
        setRange(60);
        setImage("Weapons/spear/spear-ground.png");
        scaleTexture(50, 50);
        setTeam(myspear.getHolder().getTeam());
    }
    public GridObject getTarget(){
        return myspear.getHolder();
    }
    public void update(){
        int ct = 3;
        for(GridEntity g:getGEsInRange(60)){
            if(isAggroTowards(g)){
                damage(g, 1);
                ct--;
            }
            if(ct<=0){
                break;
            }
        }
        super.update();
    }
    public void collect(GridObject targ){
        myspear.returnSpear(hs);
        myspear.removeSpear();
        myspear.enterHand();
        super.collect(targ);
        Sounds.play("LandedSpear.collect");
    }
    public void callBack(){
        ThrownSpear bullet = new ThrownSpear(true, myspear.getHolder(), myspear, face(myspear.getHolder(), false), this);
        bullet.injectScores(hs);
        myspear.removeSpear();
        addObjectHere(bullet);
        super.die();
    }
    public void render(){
        super.render();
        renderTexture(auraTexture, getX(), getY(), 120, 120, getRotation(), 127);
    }
    public void notifyDamage(GridEntity target, int amt){
        if(!myspear.getHolder().isDead()){
            myspear.getHolder().notifyDamage(target, amt);
        }else{
            super.notifyDamage(target, amt);
        }
    }
}



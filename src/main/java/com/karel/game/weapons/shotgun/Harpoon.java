package com.karel.game.weapons.shotgun;

import com.karel.game.Boomerang;
import com.karel.game.GridEntity;
import com.karel.game.Sounds;
import com.karel.game.StunEffect;
import com.karel.game.weapons.EffectID;
import com.raylib.Raylib;
import com.raylib.Vector2;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Harpoon extends Boomerang
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private GridEntity target;
    private EffectID pullStun, afterStun;
    private int phase;
    
    public Harpoon(double rotation, GridEntity source, boolean fast)
    {
        super(rotation, source);
        setRotation(rotation);
        setImage("Weapons/shotgun/projUlt.png");
        scaleTexture(40);
        setSpeed(fast ? 30 : 15);
        setReturnSpeed(fast ? 20 : 10);
        setLife(40);
        setDamage(0);
        setExpireReturn(2);//die if no target hit
        pullStun = new EffectID(this, "pull");
        afterStun = new EffectID(this, "pullfinish");
        phase = 2;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void doReturn()
    {
        if(phase==2){
            super.doReturn();
            if(!target.isDead()){
                //stun target
                if(!target.pullTo(getX(), getY())){
                    setLocation(getSource().getX(), getSource().getY());
                    phase = 3;
                    getSource().stun(pullStun);
                    if(!getSource().pullTo(getX(), getY())){
                        dieForReal();
                    }
                }
            }
        }else{
            if(target==null||(target).isDead()||distanceTo(target)<20){
                dieForReal();
                return;
            }
            setRotation(face(target, false)+90);
            move(getRotation()-90, getSpeed());
            if(!getSource().isDead()){
                //stun source
                if(!getSource().pullTo(getX(), getY())){
                    setLocation(target.getX(), target.getY());
                    phase = 2;
                    getSource().unstun(pullStun);
                    if(!target.pullTo(getX(), getY())){
                        dieForReal();
                    }
                }
            }
        }
    }
    public void dieForReal(){
        if(target!=null&&!target.isDead()){
            target.unstun(pullStun);
            target.applyEffect(new StunEffect(20, getSource(), afterStun));
        }
        if(phase==3){
            if(getSource()!=null&&!getSource().isDead()){
                getSource().unstun(pullStun);
            }
        }
        super.dieForReal();
    }
    public GridEntity getSource(){
        return (GridEntity)(super.getSource());
    }
    public void render(){
        super.render();
        //draw line between source and me
        Raylib.drawLineEx(
            new Vector2(renderTransformX((int)getX()), renderTransformY((int)getY())),
            new Vector2(renderTransformX((int)getSource().getX()), renderTransformY((int)getSource().getY())),
            7,
            Raylib.DARKBROWN
        );
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void doHit(GridEntity asteroid)
    {
        if (asteroid != null&&isAggroTowards(asteroid)&&asteroid.getHeight()==0){
            asteroid.stun(pullStun);
            target = asteroid;
            Sounds.play("lassotighten");
        }
    }
}

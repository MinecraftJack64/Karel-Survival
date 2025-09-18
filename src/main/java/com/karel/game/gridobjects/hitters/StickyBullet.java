package com.karel.game.gridobjects.hitters;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * A bullet that sticks onto the first enemy for a short while
 * 
 * @author Poul Henriksen
 */
public class StickyBullet extends Bullet
{
    private int stickCooldown;
    public StickyBullet(double rotation, int stick, GridObject source)
    {
        super(rotation, source);
        stickCooldown = stick;
    }
    public StickyBullet(double rotation, GridObject source){
        this(rotation, 30, source);
    }
    public void applyPhysics(){
        System.out.println("PHYSICS CALL");
        if(hasMounter()){
            stickCooldown--;
            if(stickCooldown<=0){
                tryUnmount();
            }
        }else
        super.applyPhysics();
    }
    public void tryUnmount(){
        GridEntity g = (GridEntity)getMounter();
        g.unmount(this);
        onUnstick(g);
    }
    public void setStickCooldown(int n){
        stickCooldown = n;
    }
    public void onUnstick(GridEntity target){
        damage(target, getDamage(target));
    }
    public void doHit(GridEntity targ){
        if(!hasMounter()){
            targ.mount(this);
        }else{
            super.doHit(targ);
        }
    }
    public void die(){
        if(hasMounter()){
            getMounter().unmount(this);
        }
        super.die();
    }
}

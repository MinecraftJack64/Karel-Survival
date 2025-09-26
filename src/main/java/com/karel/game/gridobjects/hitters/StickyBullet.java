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
    private boolean isStuck;
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
        if(hasMounter()&&isStuck){
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
        isStuck = false;
        onUnstick(g);
    }
    public void setStickCooldown(int n){
        stickCooldown = n;
    }
    public boolean isStuck(){
        return isStuck;
    }
    public void onUnstick(GridEntity target){
        damage(target, getDamage(target));
    }
    public void onStick(GridEntity target){
        //
    }
    public void doHit(GridEntity targ){
        if(!hasMounter()){
            targ.mount(this);
            onStick(targ);
            isStuck = true;
        }else{
            super.doHit(targ);
        }
    }
    public void die(){
        if(hasMounter()){
            getMounter().unmount(this);
            isStuck = false;
        }
        super.die();
    }
}

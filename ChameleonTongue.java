import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class ChameleonTongue extends Boomerang
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private GridEntity target;
    private boolean hasTarget;
    private boolean hashitfirst = false;
    private boolean sticky;
    
    public ChameleonTongue(double rotation, boolean ispurple, boolean issticky, GridEntity source)
    {
        super(rotation, source);
        setSpeed(25);
        setReturnSpeed(20);
        setLife(18);
        setDamage(60);
        sticky = issticky;
        if(ispurple){
            setNumTargets(-1);
        }
    }
    public GridEntity target(){
        return target;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void doReturn()
    {
        if(!hasTarget){
            super.doReturn();
        }else{
            super.doReturn();
            if(!target.isDead()){
                target.stun();
                if(!target.pullTo(getRealX(), getRealY())){
                    target.unstun();
                    target = null;
                    hasTarget = false;
                }
            }
        }
    }
    public void dieForReal(){
        if(target!=null&&!target.isDead()){
            target.unstun();
            target.applyeffect(new StunEffect(10, getSource()));
        }
        super.dieForReal();
    }
    public GridEntity getSource(){
        return (GridEntity)(super.getSource());
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    public void doHit(GridEntity asteroid)
    {
        super.doHit(asteroid);
        if(sticky)asteroid.applyeffect(new SpeedPercentageEffect(0.5, 10));
        if(getNumTargets()>=0&&(asteroid.getPercentHealth()<=(sticky?0.35:0.2)||asteroid.getHealth()<=getPower()*100)){
            asteroid.stun();
            target = asteroid;
            hasTarget = true;
            Sounds.play("lassotighten");
        }else{
            //
        }
        hashitfirst = true;
    }
    public boolean covertDamage(){
        return hashitfirst;
    }
}

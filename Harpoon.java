import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

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
    private int phase;
    
    public Harpoon(double rotation, GridEntity source)
    {
        super(rotation, source);
        setSpeed(15);
        setReturnSpeed(10);
        setLife(40);
        setDamage(0);
        setExpireReturn(2);//die if no target hit
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
                target.stun();
                if(!target.pullTo(getRealX(), getRealY())){
                    setRealLocation(getSource().getRealX(), getSource().getRealY());
                    phase = 3;
                    getSource().stun();
                    if(!getSource().pullTo(getRealX(), getRealY())){
                        dieForReal();
                    }
                }
            }
        }else{
            if(target==null||(target).isDead()||distanceTo(target)<20){
                dieForReal();
                return;
            }
            setRealRotation(face(target, false)+90);
            move(getRealRotation()-90, getSpeed());
            if(!getSource().isDead()){
                getSource().stun();
                if(!getSource().pullTo(getRealX(), getRealY())){
                    setRealLocation(target.getRealX(), target.getRealY());
                    phase = 2;
                    getSource().unstun();
                    if(!target.pullTo(getRealX(), getRealY())){
                        dieForReal();
                    }
                }
            }
        }
    }
    public void dieForReal(){
        if(target!=null&&!target.isDead()){
            target.unstun();
            target.applyeffect(new StunEffect(20, getSource()));
        }
        if(phase==3){
            if(getSource()!=null&&!getSource().isDead()){
                getSource().unstun();
            }
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
        if (asteroid != null&&isAggroTowards(asteroid)&&asteroid.getRealHeight()==0){
            asteroid.stun();
            target = asteroid;
            Sounds.play("lassotighten");
        }
    }
}

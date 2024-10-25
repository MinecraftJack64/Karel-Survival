import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Lasso extends Projectile
{
    /** The damage this bullet will deal */
    private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 40;
    private double speed;
    private boolean isSingleTarget;
    private int phase;
    private GridEntity target;
    private boolean hasreturned;
    private boolean wasted;
    
    public Lasso()
    {
        super(null);
    }
    
    public Lasso(double rotation, GridObject source)
    {
        super(source);
        setRealRotation(rotation+90);
        //addForce(new Vector(rotation, 15));
        setTeam(source.getTeam());
        setSpeed(15);
        setSingleTarget(true);
        phase = 1;
        hasreturned = false;
    }
    public void setSpeed(double speed){
        this.speed = speed;
    }
    public double getSpeed(){
        return speed;
    }
    public boolean isSingleTarget(){
        return isSingleTarget;
    }
    public void setSingleTarget(boolean ist){
        isSingleTarget = ist;
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void act()
    {
        if(life <= 0) {
            die();
        } 
        else {
            if(phase==1){
                life--;
                move(getRealRotation()-90, getSpeed());
                checkAsteroidHit();
            }else if(phase==2){
                if(getSource()==null||getSource().isDead()||distanceTo(getSource())<20){
                    die();
                    return;
                }
                setRealRotation(face(getSource(), false)+90);
                move(getRealRotation()-90, getSpeed());
                if(!target.isDead()){
                    target.stun();
                    if(!target.pullTo(getRealX(), getRealY())){
                        setRealLocation(getSource().getRealX(), getSource().getRealY());
                        phase = 3;
                        getSource().stun();
                        if(!getSource().pullTo(getRealX(), getRealY())){
                            die();
                        }
                    }
                }
            }else{
                if(target==null||(target).isDead()||distanceTo(target)<20){
                    die();
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
                            die();
                        }
                    }
                }
            }
        }
    }
    public void die(){
        getWorld().removeObject(this);
        if(target!=null&&!target.isDead()){
            target.unstun();
            target.applyeffect(new StunEffect(20, getSource()));
        }
        if(phase==3){
            if(getSource()!=null&&!getSource().isDead()){
                getSource().unstun();
            }
        }
        hasreturned = true;
        wasted = life>=34;
        super.die();
    }
    public boolean hasReturned(){
        return hasreturned;
    }
    public boolean wasted(){
        return wasted;
    }
    public GridEntity getSource(){
        return (GridEntity)(super.getSource());
    }
    
    /**
     * Check whether we have hit an asteroid.
     */
    private void checkAsteroidHit()
    {
        GridEntity asteroid = (GridEntity) getOneIntersectingObject(GridEntity.class);
        if (asteroid != null&&isAggroTowards(asteroid)&&asteroid.getRealHeight()==0){
            asteroid.stun();
            target = asteroid;
            Sounds.play("lassotighten");
            phase = 2;
            setSpeed(10);
        }
    }
}

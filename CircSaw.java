import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class CircSaw extends Boomerang
{
    /** The damage this bullet will deal */
    private int damage = 25;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 50;
    private double speed;
    private boolean isSingleTarget;
    private int phase;
    private GridEntity targ;
    private boolean hasreturned;
    
    public CircSaw(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(18);
        setNumTargets(-1);
        phase = 1;
        hasreturned = false;
        setDamage(100);
        setLife(25);
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        setRealRotation(getRealRotation()+30);
        if(phase==1){
            super.applyPhysics();
        }else if(phase==3){
            if(getSpeed()<18){
                setSpeed(getSpeed()+6);
            }
            if(getSource()==null||distanceTo(getSource())<getSpeed()/2){
                dieForReal();
                return;
            }
            setDirection(face(getSource(), false)+90);
            move(getDirection()-90, getSpeed());
        }else{
            setLife(1);
            setSpeed(getSpeed()-6);
            super.applyPhysics();
            if(getSpeed()==0){
                phase = 3;
                clearHitStory();
            }
        }
        checkHit();
    }
    public void dieForReal(){
        hasreturned = true;
        super.dieForReal();
    }
    public boolean hasReturned(){
        return hasreturned;
    }
    public void expire(){
        phase = 2;
    }
    public void returnNow(){
        setSpeed(22);
        phase = 3;
    }
    public void bounceBack(){
        setSpeed(18);
        if(phase==3){
            setDirection(getDirection()+180);
        }
        phase = 2;
    }
    public void doHit(GridEntity targ){
        if(targ.hasShield()&&targ.getShield().canBreak()){
            targ.getShield().damage((int)(getDamage()*3*getPower()), this);
            if(phase==3){
                bounceBack();
            }else{
                returnNow();
            }
        }
        super.doHit(targ);
    }
}

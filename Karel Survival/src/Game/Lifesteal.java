package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Lifesteal extends Boomerang
{
    /** The damage this bullet will deal */
    private int damage = 35;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 50;
    private double speed;
    private boolean isSingleTarget;
    private int phase;
    private int dmgdone = 0;
    private GridEntity targ;
    GridEntity hypnoed;
    
    public Lifesteal(double rotation, GridEntity hypno, boolean returnfast, GridObject source)
    {
        super(rotation, source);
        setSpeed(14);
        setNumTargets(1);
        hypnoed = hypno;
        if(returnfast){
            setReturnSpeed(30);
        }else{
            setReturnSpeed(20);
        }
    }
    /**
     * The bullet will damage asteroids if it hits them.
     */
    public void applyPhysics()
    {
        if(damage<165){
            damage+=10;
            setDamage(damage);
        }
        super.applyPhysics();
    }
    public void dieForReal(){
        if(getSource()!=null&&getSource() instanceof GridEntity){
            heal(((GridEntity)getSource()), dmgdone);
        }
        if(targ!=null)super.notifyDamage(targ, dmgdone);
        super.dieForReal();
    }
    
    public void notifyDamage(GridEntity target, int amt){
        this.targ = target;
        dmgdone+=amt;
    }
    public void startReturn(){
        super.startReturn();
        Sounds.play("lifesteal");
        if(hypnoed!=null){
            setSource(hypnoed);
        }
    }
}

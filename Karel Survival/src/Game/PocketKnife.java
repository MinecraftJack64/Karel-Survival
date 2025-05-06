package Game;
import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.HashMap;

/**
 * blowgun ult
 * 
 * @author Poul Henriksen
 */
public class PocketKnife extends Melee
{
    /** The damage this bullet will deal */
    //private static final int damage = 50;
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int life = 20;
    private double degtotarg, radius;
    private Blowgun weapon;
    private double offsetx, offsety;
    private HashMap<GridEntity, Integer> poisonscores;
    public PocketKnife(double rotation, double radius, double offsetdistance, HashMap<GridEntity, Integer> scores, Blowgun w, GridObject source)
    {
        super(rotation, source);
        setLife(life);
        setDamage(200);
        setNumTargets(-1);
        degtotarg = rotation-90;
        this.radius = radius;
        weapon = w;
        offsetx = offsetdistance*Math.cos(degtotarg*Math.PI/180);
        offsety = offsetdistance*Math.sin(degtotarg*Math.PI/180);
        poisonscores = scores;
    }
    
    public void applyPhysics(){
        if(life <= 0) {
            expire();
        } 
        else {
            life--;
            double centerx = offsetx+getSource().getRealX(), centery = offsety+getSource().getRealY();
            setRealLocation(centerx-radius*Math.cos(degtotarg*Math.PI/180), centery-radius*Math.sin(degtotarg*Math.PI/180));
            setRealRotation(degtotarg+90);
            degtotarg+=18;
            checkHit();
        }
    }
    public void doHit(GridEntity targ){
        if(targ.willNotify(this)){
            if(weapon!=null){
                weapon.notifySlash(targ);
            }
        }
        int poisonmultiplier = 0;
        if(poisonscores!=null){
            if(poisonscores.containsKey(targ)){
                poisonmultiplier = poisonscores.get(targ);
                poisonscores.put(targ, (poisonmultiplier+=3));
                if(poisonmultiplier>13)poisonscores.put(targ, 13);
            }else{
                poisonscores.put(targ, 1);
            }
        }
        super.doHit(targ);
        if(poisonmultiplier>0)damage(targ, poisonmultiplier*10);
    }
    public boolean covertDamage(){
        return true;
    }
}

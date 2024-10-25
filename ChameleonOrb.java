import greenfoot.*;
/**
 * Write a description of class ChameleonOrb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ChameleonOrb extends Collectible
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    private int healing = 200;
    private int color;
    private Chameleon mycham;
    private int cooldown = 15;
    public ChameleonOrb(Chameleon mycham, int color)
    {
        this.mycham = mycham;
        this.color = color;
    }
    public void kAct(){
        if(cooldown>0){
            cooldown--;
        }else{
            super.kAct();
        }
    }
    public void collect(GridObject targ){
        mycham.notifyColorChange(color, healing, (GridEntity)targ);
        super.collect(targ);
        Sounds.play("ChameleonOrb.collect");
    }
}

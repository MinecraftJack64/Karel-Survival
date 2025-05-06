package Game;
import greenfoot.*;
/**
 * a collectible dropped by the Chameleon weapon when the player kills a zombie by pulling them, changes the Chameleon's color when collected by the player
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class ChameleonOrb extends Collectible
{
    private int healing = 200;
    private int color;
    private Chameleon mycham;
    public ChameleonOrb(Chameleon mycham, int color)
    {
        this.mycham = mycham;
        this.color = color;
        setCooldown(15);
    }
    public GridObject getTarget(){
        return mycham.getHolder();
    }
    public void collect(GridObject targ){
        mycham.notifyColorChange(color, healing, (GridEntity)targ);
        super.collect(targ);
        Sounds.play("ChameleonOrb.collect");
    }
}

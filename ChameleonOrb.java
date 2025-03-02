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

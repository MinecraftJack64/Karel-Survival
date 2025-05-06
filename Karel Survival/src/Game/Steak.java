package Game;
import greenfoot.*;
/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Steak extends Collectible
{
    private int healing = 100;
    private int life = 150;
    public Steak(GridObject source){
        inherit(source);
        setRange(100);
    }
    public boolean isPotentialTarget(GridEntity t){return isAlliedWith(t)||isAggroTowards(t);}
    public void kAct(){
        super.kAct();
        life--;
        if(life<=0){
            die();
        }
    }
    public void collect(GridObject targ){
        GridEntity t = (GridEntity)targ;
        if(isAlliedWith(t)){
            heal(t, healing);
            t.applyEffect(new PowerPercentageEffect(1.25, 90, this));
        }
        else if(isAggroTowards(t))t.applyEffect(new PoisonEffect(healing/4, 30, 4, this));
        super.collect(targ);
        Sounds.play("Steak.collect");
    }
}

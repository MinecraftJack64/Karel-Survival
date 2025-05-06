package Game;
import greenfoot.*;
/**
 * a collectible usually dropped from zombies when killed that can be collected by the player, healing them
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class UltHarvestBeeper extends Collectible
{
    private int healing = 50;
    private Farmer myFarmer;
    public UltHarvestBeeper(Farmer farmer)
    {
        myFarmer = farmer;
        setRange(150);
        setSpeed(12);
        setCooldown(10);
    }
    public GridObject getTarget(){
        return myFarmer.getHolder();
    }
    public void collect(GridObject targ){
        heal(((GridEntity)targ), healing);
        myFarmer.notifyBeeperCollect();
        super.collect(targ);
        Sounds.play("UltHarvestBeeper.collect");
    }
}

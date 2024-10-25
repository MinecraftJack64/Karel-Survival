import greenfoot.*;
import java.util.List;

/**
 * Write a description of class TeslaCoil here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TeslaCoil extends Weapon
{
    private static final int ult = 1800;
    private TeslaCoilZap zap;
    public void fire(){
        if(zap!=null){
            zap.attackAt(getHolder().getTargetX(), getHolder().getTargetY());
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        ChargeBomb bullet = new ChargeBomb(getHolder().getTargetRotation(), getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        //
    }
    public void equip(){
        super.equip();
        zap = new TeslaCoilZap(getHolder());
        getHolder().getWorld().addObject(zap, getHolder().getRealX(), getHolder().getRealY());
    }
    public void unequip(){
        getHolder().getWorld().removeObject(zap);
        zap = null;
        super.unequip();
    }
    public TeslaCoil(GridObject actor){
        super(actor);
    }
    public String getName(){
        return "Tesla Coil";
    }
    public int getRarity(){
        return 3;
    }
}

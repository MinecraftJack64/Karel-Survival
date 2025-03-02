import greenfoot.*;
import java.util.List;

/**
 * Write a description of class PetMole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PetMole extends Weapon
{
    private static final int ult = 1800;
    private Mole zap;
    public void fire(){
        if(zap.getWorld()!=null){
            if(zap.inUlt()){
                zap.stopUlt();
            }
            zap.attackAt(getHolder().getTargetX(), getHolder().getTargetY(), getAttackUpgrade()==1, getUltUpgrade()==1);
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        if(zap.getWorld()!=null){
            if(zap.inUlt()){
                zap.stopUlt();
            }
            zap.startUlt();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(zap.getWorld()!=null)
            zap.target(getHolder().getTargetX(), getHolder().getTargetY());
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().addObject(zap, getHolder().getRealX(), getHolder().getRealY());
    }
    public void unequip(){
        if(zap.inUlt()){
            zap.stopUlt();
        }
        getHolder().getWorld().removeObject(zap);
        super.unequip();
    }
    public PetMole(GridObject actor){
        super(actor);
        zap = new Mole(getHolder());
    }
    public String getName(){
        return "Mole";
    }
    public int getRarity(){
        return 3;
    }
}

import greenfoot.*;
import java.util.List;

/**
 * Write a description of class Weedwacker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weedwacker extends Weapon
{
    private static final int ult = 1600;
    private WeedwackerBlade drone;
    private int resurrect = 120;
    public void fire(){
        if(drone!=null){
            //
        }
        //show the lightning
        //Sounds.play("electicity");
    }
    public void fireUlt(){
        if(drone!=null){
            drone.ult();
        }else{
            drone = new WeedwackerBlade(getHolder());
            getHolder().addObjectHere(drone);
            resurrect = 120;
            drone.ult();
        }
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(drone!=null&&drone.isDead()){
            drone = null;
        }
        if(drone==null){
            if(resurrect<=0){
                drone = new WeedwackerBlade(getHolder());
                getHolder().addObjectHere(drone);
                resurrect = 120;
            }
            resurrect--;
        }
    }
    public void equip(){
        super.equip();
        drone.untrap();
        getHolder().addObjectHere(drone);
    }
    public void unequip(){
        drone.trap();
        getHolder().getWorld().removeObject(drone);
        super.unequip();
    }
    public Weedwacker(GridObject actor){
        super(actor);
        drone = new WeedwackerBlade(getHolder());
        chargeUlt(100);
    }
    public String getName(){
        return "Weedwacker";
    }
    public int getRarity(){
        return 4;
    }
}

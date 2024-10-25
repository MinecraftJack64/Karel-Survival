/**
 * Write a description of class CatClaw here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CatClaw extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 20;
    private int reloadDelayCount;
    private static final int ult = 700;
    private boolean toland;
    public void fire(){
        if (reloadDelayCount >= gunReloadTime&&!toland) 
        {
            for(int deg = -21; deg<=21; deg+=14){
                Claw mbullet = new Claw(getHolder().getTargetRotation()+deg, holder);
                getHolder().getWorld().addObject (mbullet, getHolder().getRealX(), getHolder().getRealY());
            }
            Sounds.play("clawunsheath");
            reloadDelayCount = 0;
        }
    }
    public void fireUlt(){
        if(toland){
            return;
        }
        Mousetrap bullet = new Mousetrap(getHolder()){
            public boolean covertDamage(){
                return true;
            }
        };
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        double d = Math.min(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), 300);
        toland = true;
        getHolder().initiateJump(getHolder().getTargetRotation(), d, 75);
        setLocked(true);
    }
    public void reload(){
        reloadDelayCount++;
        updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
    }
    public int getUlt(){
        return ult;
    }
    public CatClaw(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        chargeUlt(700);
        toland = false;//
        //getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public void doLanding(){
        if(toland){
            toland = false;
            for(int i = 0; i < 8; i++){
                SwipingClaw bullet = new SwipingClaw(getHolder().getRealRotation()+i*45, 70, getHolder());
                getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
            }
            setLocked(false);
        }
    }
    public String getName(){
        return "Cat Claw";
    }
    public int getRarity(){
        return 4;
    }
}

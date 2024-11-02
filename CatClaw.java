/**
 * Write a description of class CatClaw here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CatClaw extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 20;
    private int nextclawcooldown = 0;
    private int reloadDelayCount;
    private static final int ult = 700;
    private boolean toland;
    private int clawtofire = 0;
    private Claw claws[] = new Claw[4];
    public void fire(){
        if(continueUse()){
            if(nextclawcooldown<=0){
                fireClaw(clawtofire);
                clawtofire++;
                if(clawtofire==4){
                    clawtofire = 0;
                    setContinueUse(false);
                    setPlayerLockRotation(false);
                }else{
                    nextclawcooldown = 1;
                }
            }else{
                nextclawcooldown--;
            }
        }else if (reloadDelayCount >= gunReloadTime&&canFire()) 
        {
            fireClaw(clawtofire);
            setContinueUse(true);
            setPlayerLockRotation(true);
            clawtofire++;
            Sounds.play("clawunsheath");
            reloadDelayCount = 0;
            nextclawcooldown = 1;
        }
    }
    public void fireClaw(int c){
        claws[c] = new Claw(getHolder().getTargetRotation()+(-21+14*c), getHolder());
        getHolder().addObjectHere(claws[c]);
    }
    public void fireUlt(){
        if(!canFire()){
            cancelUltReset();
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
    public boolean canFire(){
        if(toland){
            return false;
        }
        for(Claw c: claws){
            if(c!=null&&!c.hasReturned()){
                return false;
            }
        }
        return true;
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
                getHolder().applyeffect(new PowerPercentageEffect(2, 60));
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

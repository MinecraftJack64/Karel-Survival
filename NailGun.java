import greenfoot.*;
import java.util.List;

/**
 * Write a description of class NailGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NailGun extends Weapon
{
    private static final int gunReloadTime = 30;
    private static final int waveReloadTime = 5;
    private static final int spread = 5;
    private int atkphase = 1;//1-3: shoot nails, 4 - shoot drill
    private boolean drillnext = false;
    private int reloadDelayCount;
    private int wavesleft = 0;
    private int r[] = {500, 350, 250, 100};
    private int r2[] = {500, 375, 300, 200};
    private int d[] = {100, 75, 50, 35};
    private static final int ult = 1500;
    public void fire(){
        if(continueUse()){
            if(reloadDelayCount>=waveReloadTime){
                fireWave();
                checkStopAttack();
            }
        }else{
            if (reloadDelayCount >= gunReloadTime) 
            {
                if(drillnext){
                    //shoot drill
                    reloadDelayCount = -40;
                    getAmmoBar().disable();
                    MiniDrillHead bullet = new MiniDrillHead(getHolder().getTargetRotation(), getHolder());
                    getHolder().addObjectHere(bullet);
                    drillnext = false;
                }else{
                    wavesleft = atkphase;
                    setPlayerLockRotation(true);
                    setContinueUse(true);
                    fireWave();
                    checkStopAttack();
                }
            }
        }
    }
    public void checkStopAttack(){
        if(wavesleft==0){
            atkphase++;
            if(atkphase>=5){
                atkphase = 1;
            }
            setPlayerLockRotation(false);
            setContinueUse(false);
        }
    }
    public void fireWave(){
        double beginning = -atkphase*spread/2;
        for(int i = 0; i < atkphase; i++){
            double shift = beginning+i*spread;
            Nail bullet = new Nail(getAttackUpgrade()==1?r2[atkphase-1]:r[atkphase-1], d[atkphase-1], getHolder().getTargetRotation()+shift/2, atkphase==4?this:null, getHolder());
            getHolder().getWorld().addObject(bullet, getHolder().getRealX()+shift*Math.cos(Math.PI/180*getHolder().getTargetRotation()), getHolder().getRealY()+shift*Math.sin(Math.PI/180*getHolder().getTargetRotation()));
        }
        reloadDelayCount = 0;
        wavesleft--;
    }
    public void fireUlt(){
        ProtonWave bullet = new ProtonWave(getHolder());
        getHolder().getWorld().addObject(bullet, getHolder().getRealX(), getHolder().getRealY());
        Sounds.play("protonwave");
        List<GridEntity> l = getHolder().getGEsInRange(185);
        for(GridEntity g:l){
            if(getHolder().isAggroTowards(g)){
                g.hit(700, bullet);
                //g.applyeffect(new StunEffect(30, getSource()));
            }
        }
        //Explosion exp = new Explosion(3);
        //getHolder().addObjectHere(exp);
        //Sounds.play("explode");
        reloadDelayCount = 0;
    }
    public int getUlt(){
        return ult;
    }
    public void notifyHit(){
        drillnext = true;
    }
    public void reload(){
        if(reloadDelayCount<0){
            reloadDelayCount++;
            if(reloadDelayCount==0){
                getAmmoBar().reset();
                updateAmmo(0);
            }
        }else{
            reloadDelayCount++;
            if(drillnext){
                updateAmmo(gunReloadTime+1);
            }else if(wavesleft>0){
                updateAmmo(0);
            }else{
                updateAmmo(Math.min(reloadDelayCount, gunReloadTime));
            }
        }
    }
    public NailGun(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(gunReloadTime, reloadDelayCount);
    }
    public String getName(){
        return "Nailgun";
    }
    public int getRarity(){
        return 2;
    }
}
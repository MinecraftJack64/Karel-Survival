import greenfoot.*;
/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shotgun extends Weapon
{
    private static final int gunReloadTime = 5;
    private static final int ammoReloadTime = 25;
    private static final int maxAmmo = 3;
    private int reloadDelayCount;
    private int ammo;
    private int ammoReloadDelay;
    private static final int ult = 500;
    private IBoomerang lasso;
    private int ultchargecooldown = 0;
    private int disabledcooldown = 0;
    private boolean nextammosupercharged = false;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo>0&&lasso==null&&disabledcooldown==0) 
        {
            if(nextammosupercharged){
                disabledcooldown = 80;
                getAmmoBar().disable();
            }
            for(int deg = -30; deg<=30; deg+=nextammosupercharged?2:10){
                Projectile mbullet = getAttackUpgrade()==1?(new SuperShot(getHolder().getTargetRotation()+deg, holder)):(new Shot(getHolder().getTargetRotation()+deg, holder));
                getHolder().getWorld().addObject (mbullet, holder.getRealX(), holder.getRealY());
            }
            //bullet.move ();
            Sounds.play("shotgunshoot");
            reloadDelayCount = 0;
            ammoReloadDelay = 0;
            ammo--;
            nextammosupercharged = false;
        }
    }
    public void fireUlt(){
        if(lasso!=null||disabledcooldown>0){
            return;
        }
        if(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY())<50){
            if(ammo<maxAmmo)ammo++;
            reloadDelayCount = gunReloadTime;
            nextammosupercharged = true;
            Sounds.play("shotgunjam");
            return;
        }
        Harpoon bullet = new Harpoon(getHolder().getTargetRotation(), getHolder());
        getHolder().addObjectHere(bullet);
        //bullet.move ();
        lasso = bullet;
        Sounds.play("lassoshoot");
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(disabledcooldown<0){
            disabledcooldown = 0;
        }
        if(disabledcooldown>0){
            disabledcooldown--;
            if(disabledcooldown ==0){
                getAmmoBar().reset();
            }
        }else{
            reloadDelayCount++;
            if(reloadDelayCount>=gunReloadTime){
                if(ammo<maxAmmo){
                    ammoReloadDelay++;
                    if(ammoReloadDelay>=ammoReloadTime){
                        ammo++;
                        ammoReloadDelay = 0;
                    }
                }
            }
            if(ultchargecooldown<=0){
                chargeUlt(10);
                ultchargecooldown = 2;
            }else
                ultchargecooldown--;
            if(nextammosupercharged){
                updateAmmo(getAmmoBar().getMax()+1);
            }else updateAmmo(ammo*ammoReloadTime+ammoReloadDelay);
        }
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
    }
    public Shotgun(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammoReloadDelay = 0;
        ammo = 1;
    }
    public void chargeUlt(int amt){
        if(disabledcooldown==0&&!nextammosupercharged)super.chargeUlt(amt);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(maxAmmo*ammoReloadTime, ammo*ammoReloadTime+ammoReloadDelay, 3);
    }
    public String getName(){
        return "Shotgun";
    }
    public int getRarity(){
        return 1;
    }
}

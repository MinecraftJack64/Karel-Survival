import greenfoot.*;
/**
 * Write a description of class Shotgun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shotgun extends Weapon implements AmmoHolder
{
    private static final int gunReloadTime = 5;
    private int reloadDelayCount;
    private AmmoManager ammo;
    private static final int ult = 500;
    private IBoomerang lasso;
    private int ultchargecooldown = 0;
    private int disabledcooldown = 0;
    private boolean nextammosupercharged = false;
    public void fire(){//one full ammo deals 350 damage
        if (reloadDelayCount >= gunReloadTime&&ammo.hasAmmo()&&lasso==null&&disabledcooldown==0) 
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
            ammo.useAmmo();
            nextammosupercharged = false;
        }
    }
    public void fireUlt(){
        if(lasso!=null||disabledcooldown>0){
            return;
        }
        if(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY())<50){
            ammo.donateAmmo(1);
            reloadDelayCount = gunReloadTime;
            nextammosupercharged = true;
            Sounds.play("shotgunjam");
            return;
        }
        double d = Math.min(getHolder().distanceTo(getHolder().getTargetX(), getHolder().getTargetY()), 600);
        lasso = getUltUpgrade()==1?new Lasso(getHolder().getTargetRotation(), d, getHolder()):new Harpoon(getHolder().getTargetRotation(), getHolder());
        getHolder().addObjectHere((GridObject)lasso);
        //TEST
        //getHolder().addObjectHere(new Harpoon(getHolder().getTargetRotation(), getHolder()));
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
        }else if(lasso==null){
            reloadDelayCount++;
            if(reloadDelayCount>=gunReloadTime){
                ammo.reload();
            }
            if(ultchargecooldown<=0){
                chargeUlt(10);
                ultchargecooldown = 2;
            }else
                ultchargecooldown--;
            if(nextammosupercharged){
                updateAmmo(getAmmoBar().getMax()+1);
            }else updateAmmo(ammo.getAmmoBar());
        }
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
    }
    public Shotgun(GridObject actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        ammo = new AmmoManager(25, 2, 3);
    }
    public void chargeUlt(int amt){
        if(disabledcooldown==0&&!nextammosupercharged)super.chargeUlt(amt);
    }
    public void equip(){
        super.equip();
        getHolder().getWorld().gameUI().newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public String getName(){
        return "Shotgun";
    }
    public int getRarity(){
        return 1;
    }
}

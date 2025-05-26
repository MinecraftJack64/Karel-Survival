package com.karel.game;

import com.karel.game.weapons.Weapon;

/**
 * Write a description of class Highjacker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Highjacker extends Weapon
{
    private static final int ult = 2500, reload = 50;
    private Scissors lasso;
    private int ultchargecooldown = 0, ammo;
    private boolean mounted = false;
    private GridEntity mount;
    private static final int mountHeight = 60;
    private TeamSwitchEffect hypno = null;//keep a reference of the hypnotization to clear early if needed
    public void fire(){//one full ammo deals 350 damage
        if(mounted){
            if(ammo>=reload&&mount.isOnGround()){
                double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 400);
                HijackHealer bullet = new HijackHealer (getHand().getTargetRotation(), d, d/2, getHolder(), mount);
                mount.addObjectHere(bullet);
                ammo = 0;
            }
        }else if (lasso == null) 
        {
            Scissors bullet = new Scissors(getHand().getTargetRotation(), getHolder());
            getHolder().addObjectHere(bullet);
            //bullet.move ();
            lasso = bullet;
            Sounds.play("lifestealshoot");
        }
    }
    public void fireUlt(){
        if(lasso!=null){//do not do ult if still attacking
            cancelUltReset();
            return;
        }
        if(mounted){
            unmount();
        }else{
            double d = Math.min(getHolder().distanceTo(getHand().getTargetX(), getHand().getTargetY()), 350);
            Hijack bullet = new Hijack(getHand().getTargetRotation(), d, 75, this, getHolder());
            getHolder().addObjectHere(bullet);
        }
    }
    public void notifyHit(GridEntity targ){
        if(!targ.mount(getHolder(), 0, 0, mountHeight))return;
        mount = targ;
        mounted = true;
        setPlayerLockMovement(true);
        getHolder().pullTo(targ.getRealX(), targ.getRealY(), targ.getRealHeight()+mountHeight);
        hypno = new TeamSwitchEffect(getHolder().getTeam(), 500, getHolder());
        mount.applyEffect(hypno);
        mount.applyShield(new ExternalImmunityShield(new ShieldID("highjacker immunity"), 90));
        ammo = reload;
        newAmmo(reload, ammo);
        chargeUlt(2500);
    }
    public void unmount(){
        mounted = false;
        setPlayerLockMovement(false);
        if(mount.isInWorld())mount.unmount(getHolder());
        getHolder().pullTo(getHolder().getRealX(), getHolder().getRealY(), 0);
        if(hypno.getDuration()>0){
            int duration = hypno.getDuration();
            hypno.clear();
            getHolder().explodeOnEnemies(100, (e)->{
                e.applyEffect(new StunEffect(duration, getHolder()));
            });
            hypno = null;
        }
        getHolder().explodeOnEnemies(100, (e)->{
            if(e!=mount)e.knockBack(getHolder().face(e, false), 80, 20, getHolder());
        });
        getHolder().applyShield(new ExternalImmunityShield(new ShieldID("highjacker immunity"), 90));
        mount = null;
    }
    public void confirmUnmount(){
        mounted = false;
        mount = null;
        setPlayerLockMovement(false);
        getHolder().pullTo(getHolder().getRealX(), getHolder().getRealY(), 0);
        getHolder().explodeOnEnemies(100, (e)->{
            if(e!=mount)e.knockBack(getHolder().face(e, false), 80, 20, getHolder());
        });
        getHolder().applyShield(new ExternalImmunityShield(new ShieldID("highjacker immunity"), 90));
    }
    public int getUlt(){
        return ult;
    }
    public void reload(){
        if(lasso!=null&&lasso.hasReturned()){
            lasso = null;
        }
        if(mounted){
            if(hypno.getDuration()>0&&!mount.isDead()){
                ammo++;
                updateAmmo(Math.min(ammo, reload));
            }else{
                unmount();
                dischargeUlt(2500);
            }
        }else if(isPlayerMovementLocked()){
            confirmUnmount();
        }
        chargeUlt(2);
    }
    public boolean canAttackInAir(){
        return mounted;
    }
    public boolean canUltInAir(){
        return mounted;
    }
    public Highjacker(ItemHolder actor){
        super(actor);
    }
    public String getName(){
        return "Highjacker";
    }
    public int getRarity(){
        return 5;
    }
}







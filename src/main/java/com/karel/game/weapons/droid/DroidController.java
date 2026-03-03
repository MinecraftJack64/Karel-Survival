package com.karel.game.weapons.droid;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.Location;
import com.karel.game.Sounds;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;
import com.raylib.Raylib;

/**
 * Write a description of class GrenadeLauncher here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class DroidController extends Weapon {
    private static final int gunReloadTime = 15;
    private int reloadDelayCount;
    private Droid droid;
    private int toAttack;
    private boolean overriding;
    private static final int ult = 1200;

    public void fire() {
        if (toAttack != 0) {
            if(droid!=null)
            switch (toAttack) {
                case 1:
                    droid.dash(getHand().getTargetRotation());
                    break;
                case 2:
                    droid.jailbreak(getHand().getTargetRotation(), getHand().getTargetDistance());
                    break;
            }
            toAttack = 0;
        }
        if (reloadDelayCount >= gunReloadTime && getAmmo().hasAmmo()) {
            double x = !overriding?getHand().getTargetX():droid.getOverrideTargetX(), y = !overriding?getHand().getTargetY():droid.getOverrideTargetY(), r = !overriding?getHand().getTargetRotation():droid.getOverrideTargetRotation();
            double d = Math.min(getHolder().distanceTo(x, y), 400);
            Wrench bullet = new Wrench(r, d, d / 2, getHolder(), droid!=null?this:null);
            getHolder().getWorld().addObject(bullet, getHolder().getX(), getHolder().getY());
            Sounds.play("airtoss");
            reloadDelayCount = 0;
            getAmmo().useAmmo();
        }
    }

    public void fireUlt() {
        if (droid == null) {
            droid = new Droid(this, getHolder());
            getHolder().addObjectHere(droid);
        } else if(overriding){
            cancelOverride();
        }else{
            if (droid.getMode() != 0) {
                droid.setMode(0);
                toAttack = 0;
            } else
                switch (getSelectedPalette()) {
                    case 0:
                        droid.setMode(1);
                        toAttack = 1;
                        break;
                    case 1:
                        droid.fallBack();
                        break;
                    case 2:
                        droid.setMode(2);
                        break;
                    case 3:
                        droid.setMode(3);
                        break;
                    case 4:
                        toAttack = 2;
                        break;
                    case 5:
                        if(!droid.isInWorld())return;
                        setLocked(true);
                        droid.override();
                        overriding = true;
                        break;
                }
        }
    }

    public void notifyHit(Location l) {
        if(droid!=null){
            droid.setTarget(l);
        }
    }
    public void notifyHit(GridEntity l) {
        if(droid!=null){
            droid.setTarget(l);
        }
    }

    public void reload(double speed) {
        reloadDelayCount++;
        if (reloadDelayCount >= gunReloadTime) {
            super.reload(speed);
        }
    }

    public void update() {
        super.update();
        if (droid != null && droid.isDead()) {
            if(overriding)cancelOverride();
            droid = null;
            dischargeUlt(getUlt());
        }
        if (droid != null && droid.getMode() != 0||overriding) {
            chargeUlt(50);
        }
        if(ultReady()&&droid!=null&&droid.getMode()==0){
            showPalette();
        }else{
            hidePalette();
        }
    }

    public void cancelOverride(){
        if(droid!=null)droid.cancelOverride();
        overriding = false;
        setLocked(false);
    }

    public void onGadgetActivate() {
        //
    }
    public boolean canActivateGadget(){
        return super.canActivateGadget()&&droid!=null&&!droid.isDead();
    }

    public int defaultGadgets() {
        return 2;
    }

    public int getUlt() {
        return ult;
    }

    public void unequip() {
        if (droid != null) {
            getHolder().getWorld().removeObject(droid);
            dischargeUlt(getUlt());
        }
        droid = null;
        super.unequip();
    }

    public DroidController(ItemHolder actor) {
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
        setPaletteOffset(-90);
        addToPalette(new PaletteSlice(Raylib.YELLOW, null, "Charge!"));
        addToPalette(new PaletteSlice(Raylib.BLUE, null, "Fall Back!"));
        addToPalette(new PaletteSlice(Raylib.RED, null, "Assault Mode!"));
        addToPalette(new PaletteSlice(Raylib.PURPLE, null, "Heal Mode!"));
        addToPalette(new PaletteSlice(Raylib.ORANGE, null, "Jailbreak!"));
        addToPalette(new PaletteSlice(Raylib.GREEN, null, "Manual Override!"));
    }

    public String getName() {
        return "Droid";
    }

    public int getRarity() {
        return 6;
    }
}

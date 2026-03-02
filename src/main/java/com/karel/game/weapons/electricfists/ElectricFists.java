package com.karel.game.weapons.electricfists;

import java.util.HashSet;

import com.karel.game.Greenfoot;
import com.karel.game.GridObject;
import com.karel.game.ItemHolder;
import com.karel.game.Sounds;
import com.karel.game.gridobjects.hitters.Bullet;
import com.karel.game.gridobjects.hitters.Projectile;
import com.karel.game.physics.LandingHandler;
import com.karel.game.shields.ShieldID;
import com.karel.game.trackers.AmmoManager;
import com.karel.game.weapons.Weapon;
import com.raylib.Texture;

/**
 * Write a description of class Pointpinner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ElectricFists extends Weapon implements LandingHandler
{
    private static final int gunReloadTime = 20, shotTime = 5, specialReloadTime = 150;
    private int shotDelay = 0;
    private double reloadDelayCount;
    private int specialReload;
    private int remainingShots = 0;
    private boolean dir = false;
    private int ultPhase = 0;
    private int chargeTime = 30;
    private ProjectileSwallowShield shield;
    private HashSet<Projectile> projectiles = new HashSet<>();
    private static final int ult = 3000;
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/lovestrike/aura.png");
    public void fire(){
        if(continueUse()){
            if(shotDelay>0){
                shotDelay--;
            }else{
                if(useGadget()){
                    //
                }else{
                    ElectricFist bullet = new ElectricFist(getHand().getTargetRotation()+(remainingShots%2==(dir?1:0)?45:-45), getHand().getTargetX(), getHand().getTargetY(), getHolder());
                    getHolder().addObjectHere(bullet);
                }
                if(remainingShots>0){
                    shotDelay = shotTime;
                    remainingShots--;
                }else{
                    if(specialReload>=specialReloadTime){
                        specialReload = 0;
                        getHolder().addObjectHere(new ElectricUppercut(getHand().getTargetRotation(), getHolder()));
                    }
                    onInterrupt();
                }
            }
        }else if (reloadDelayCount >= gunReloadTime&&getAmmo().hasAmmo()) {
            if(isUsingGadget()){
                //
            }else{
                ElectricFist bullet = new ElectricFist(getHand().getTargetRotation(), getHand().getTargetX(), getHand().getTargetY(), getHolder());
                getHolder().addObjectHere(bullet);
            }
            shotDelay = shotTime;
            remainingShots = 3;
            setContinueUse(true);
            setPlayerLockRotation(true);
            getAmmo().useAmmo();
            Sounds.play("gunshoot");
        }
    }
    public void onInterrupt(){
        if(!continueUse()){
            specialReload = 0;
        }
        setContinueUse(false);
        setContinueUlt(false);
        setPlayerLockMovement(false);
        setPlayerLockRotation(false);
        reloadDelayCount = 0;
        dir = !dir;
        disableSpecial();
        projectiles.clear();
        if(getAttackUpgrade()==1){
            newSpecial(specialReloadTime, specialReload);
        }
    }
    public void fireUlt(){
        if(continueUlt()){
            if(ultPhase==0){
                if(chargeTime>0){
                    chargeTime--;
                    updateSpecial(chargeTime);
                }else if(chargeTime==0){
                    setPlayerLockMovement(false);
                    getHolder().initiateJump(getHand().getTargetRotation(), Math.min(300, getHand().getTargetDistance()), 100);
                    chargeTime = -1;
                }
            }else{
                if(chargeTime>0){
                    chargeTime--;
                }else if(shield.getRecord().size()>0){
                    setPlayerLockMovement(false);
                    var r = shield.getRecord();
                    var l = r.removeLast();
                    for(Bullet b: l){
                        getHolder().addObjectHere(b);
                        b.setDirection(getHand().getTargetRotation());
                        getHolder().heal(getHolder(), 50);
                    }
                    updateSpecial(r.size());
                }else{
                    onInterrupt();
                }
            }
        }else{
            ultPhase = 0;
            setContinueUlt(true);
            setPlayerLockMovement(true);
            chargeTime = 30;
            newSpecial(chargeTime, chargeTime);
            shield = new ProjectileSwallowShield(new ShieldID(this), chargeTime);
            getHolder().applyShield(shield);
        }
    }
    public void doLanding(){
        if(continueUlt()){
            ultPhase = 1;
            chargeTime = 15;
            setPlayerLockMovement(true);
            newSpecial(30, 30);
            getHolder().explodeOn(150, 100);
            getHolder().knockBackOnEnemies(150, 50);
        }
    }
    public int getUlt(){
        return ult;
    }
    public void equip(){
        super.equip();
        if(getAttackUpgrade()==1){
            newSpecial(specialReloadTime, specialReload);
        }
    }
    public void onGadgetActivate(){
        //
    }
    @Override
    public int defaultGadgets(){
        return 2;
    }
    public void reload(double at){
        reloadDelayCount++;
        if(getAttackUpgrade()==1&&specialReload<specialReloadTime){
            specialReload++;
            updateSpecial(specialReload);
        }
        super.reload(at);
    }
    public void update(){
        if(!continueUlt()) for(GridObject g: getHolder().getWorld().allObjects()){
            if(getHolder().distanceTo(g)<150&&g.isPotentialTarget(getHolder())&&g instanceof Projectile p&&!projectiles.contains(p)){
                projectiles.add(p);
                chargeUlt(100);
            }
        }
        super.update();
    }
    public void render(){
        getHolder().renderTexture(auraTexture, getHolder().getX(), getHolder().getY(), 300, 300, 0, 50);
    }
    public ElectricFists(ItemHolder actor){
        super(actor);
        reloadDelayCount = gunReloadTime;
        setAmmo(new AmmoManager(30, 3, 3));
    }
    public String getName(){
        return "Electric Fists";
    }
    public int getRarity(){
        return 2;
    }
}







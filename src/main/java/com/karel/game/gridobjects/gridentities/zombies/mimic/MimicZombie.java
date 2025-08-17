package com.karel.game.gridobjects.gridentities.zombies.mimic;

import com.karel.game.GridEntity;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.ZombieClass;
import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.Weapon.BotGuide;

public class MimicZombie extends Zombie {
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer};

    private boolean movementLock;
    private boolean rotationLock;

    public MimicZombieHand hand = new MimicZombieHand();
    private Weapon weapon;

    private Mimicker lasso;

    public String getStaticTextureURL(){return "gunzareln.png";}
    private static double mimicRange = 600;
    /**
     * Initilise this rocket.
     */
    public MimicZombie()
    {
        setSpeed(4);
        startHealth(600);
    }
    @SuppressWarnings("static-access")
    public void behave()
    {
        double monangle = rotationLock?getRotation():face(getTarget(), canMove());
        if(weapon!=null){
            BotGuide bg = weapon.getBotGuide();
            if(bg.shouldUseUlt()&&weapon.ultReady()||weapon.continueUlt()){
                int ideal = bg.getUltIdealRange();
                if(weapon.continueUlt()){
                    weapon.ult();
                }
                if(bg.getUltEffectiveRange()<distanceTo(getTarget())){
                    walk(monangle, 1);
                }else{
                    if(canAttack()&&!weapon.continueUlt())weapon.ult();
                    if(ideal+10<distanceTo(getTarget())){
                        walk(monangle, 0.8);
                    }else if(ideal-10>distanceTo(getTarget())){
                        walk(monangle+180, 0.8);
                    }
                }
                return;
            }
            int ideal = bg.getIdealRange();
            if(weapon.continueUse())weapon.use();
            if(bg.getEffectiveRange()<distanceTo(getTarget())){
                walk(monangle, 1);
            }else{
                if(canAttack()&&!weapon.continueUse())weapon.use();
                if(ideal+10<distanceTo(getTarget())){
                    walk(monangle, 0.8);
                }else if(ideal-10>distanceTo(getTarget())){
                    walk(monangle+180, 0.8);
                }
            }
            weapon.tick();
            return;
        }
        if(distanceTo(getTarget())>mimicRange)walk(monangle, 1);
        else{
            if(lasso==null)fireMimicker();
            if(!lasso.isInWorld())lasso = null;
        }
    }
    public boolean canMove(){
        return super.canMove()&&!movementLock;
    }
    public void doLanding(){
        super.doLanding();
        if(weapon instanceof LandingHandler lh){
            lh.doLanding();
        }
    }
    public void fire() 
    {
        if (canAttack()){
            weapon.use();
        }
    }
    public void fireMimicker(){
        lasso = new Mimicker(getTarget(), this);
        getTarget().addObjectHere(lasso);
    }
    public void notifyHit(Weapon w){
        if(w!=null){
            weapon = w;
            w.equip();
        }
    }
    public void notifyDamage(GridEntity target, int amt) {
        if(target!=null&&isAlliedWith(target))return;
        if (weapon!=null) {
            weapon.chargeUlt(amt);
        }
    }
    //ovveride this
    public int getXP(){
        return 800;
    }
    public boolean prioritizeTarget(){
        return true;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public MimicZombieHand getHand(){
        return hand;
    }
    public String getName(){
        return "Mimic Zombie";
    }
    public class MimicZombieHand implements ItemHolder{
        //TODO
        private boolean targetLocked;
        private double prevX, prevY;
        @Override
        public double getTargetRotation(){
            return getHolder().getRotation();
        }
        public double getTargetX(){
            return targetLocked?prevX:getHolder().getTarget().getX();
        }
        public double getTargetY(){
            return targetLocked?prevY:getHolder().getTarget().getY();
        }
        public void setTargetLock(boolean t){
            if(t){
                prevX = getTargetX();
                prevY = getTargetY();
            }
            targetLocked = t;
        }
        public void setRotationLock(boolean t){
            rotationLock = t;
        }
        public void setMovementLock(boolean t){
            movementLock = t;
        }
        public boolean isAttacking(){
            return distanceTo(getTarget())<=mimicRange;
        }
        public boolean isMoving(){
            return distanceTo(getTarget())>30;
        }
        public double getReloadSpeed(){
            return getHolder().getReloadMultiplier();
        }
        public boolean isMainWeapon(){
            return false;
        }
        public MimicZombie getHolder(){
            return MimicZombie.this;
        }
    }
}

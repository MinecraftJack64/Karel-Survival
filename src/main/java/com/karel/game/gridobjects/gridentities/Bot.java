package com.karel.game.gridobjects.gridentities;
import com.karel.game.GridEntity;
import com.karel.game.Item;
import com.karel.game.ItemAccepter;
import com.karel.game.ItemHolder;
import com.karel.game.LandingHandler;
import com.karel.game.Pet;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.Weapon.BotGuide;

/**
 * Write a description of class ShooterZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bot extends Pet implements ItemAccepter
{
    private boolean movementLock;
    private boolean rotationLock;

    public BotHand hand = new BotHand();
    private Weapon weapon;
    private boolean needToEquip;
    GridEntity target;

    public String getStaticTextureURL(){return "chick.png";}
    /**
     * Initilise this rocket.
     */
    public Bot(GridEntity source)
    {
        super(source);
        setSpeed(6);
        startHealth(2000);
        scaleTexture(55);
    }
    public void behave()
    {
        target = getTarget();
        if(target==null){
            if(weapon!=null)weapon.tick();
            return;
        }
        double monangle = rotationLock?getRotation():face(target, canMove());
        if(weapon!=null){
            BotGuide bg = weapon.getBotGuide();
            if(!weapon.continueUse()&&(bg.shouldUseUlt()&&weapon.ultReady()||weapon.continueUlt())){
                int ideal = bg.getUltIdealRange();
                if(weapon.continueUlt()){
                    weapon.ult();
                }
                if(bg.getUltEffectiveRange()<distanceTo(target)){
                    walk(monangle, 1);
                }else{
                    if(canAttack()&&!weapon.continueUlt())weapon.ult();
                    if(ideal+10<distanceTo(target)){
                        walk(monangle, 0.8);
                    }else if(ideal-10>distanceTo(target)){
                        walk(monangle+180, 0.8);
                    }
                }
                return;
            }
            int ideal = bg.getIdealRange();
            if(weapon.continueUse())weapon.use();
            if(bg.getEffectiveRange()<distanceTo(target)){
                walk(monangle, 1);
            }else{
                if(canAttack()&&!weapon.continueUse()&&bg.shouldUse())weapon.use();
                if(ideal+10<distanceTo(target)){
                    walk(monangle, 0.8);
                }else if(ideal-10>distanceTo(target)){
                    walk(monangle+180, 0.8);
                }
            }
            weapon.tick();
            return;
        }
        if(distanceTo(target)>300)walk(monangle, 1);
        else{
            //
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
    public void setWeapon(Weapon w){
        if(w!=weapon&&weapon!=null){
            weapon.unequip();
        }
        weapon = w;
        if(isInWorld()){
            weapon.equip();
        }else{
            needToEquip = true;
        }
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        if(needToEquip){
            needToEquip = false;
            weapon.equip();
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
    public BotHand getHand(){
        return hand;
    }
    @Override
    public void acceptItem(Item item) {
        weapon = (Weapon)item;
    }
    public String getName(){
        return "Bot Zombie";
    }
    public class BotHand implements ItemHolder{
        //TODO
        private boolean targetLocked;
        private double prevX, prevY;
        @Override
        public double getTargetRotation(){
            return getHolder().getRotation();
        }
        public double getTargetX(){
            return targetLocked?prevX:getHolder().target.getX();
        }
        public double getTargetY(){
            return targetLocked?prevY:getHolder().target.getY();
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
            return true;//TODO
        }
        public boolean isMoving(){
            return true;//TODO
        }
        public double getReloadSpeed(){
            return getHolder().getReloadMultiplier();
        }
        public boolean isMainWeapon(){
            return false;
        }
        public Bot getHolder(){
            return Bot.this;
        }
    }
}

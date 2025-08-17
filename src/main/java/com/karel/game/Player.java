package com.karel.game;

import com.karel.game.weapons.ShieldID;
import com.karel.game.weapons.Weapon;
import com.karel.game.weapons.blade.Blade;
import com.karel.game.weapons.blowgun.Blowgun;
import com.karel.game.weapons.captorch.CapsaicinTorch;
import com.karel.game.weapons.catclaw.CatClaw;
import com.karel.game.weapons.critters.LilCritters;
import com.karel.game.weapons.crossbow.Crossbow;
import com.karel.game.weapons.crystalgun.CrystalGun;
import com.karel.game.weapons.gun.Gun;
import com.karel.game.weapons.inferno.Inferno;
import com.karel.game.weapons.necromancer.Necromancer;
import com.karel.game.weapons.paintgun.PaintGun;
import com.karel.game.weapons.pointpinner.Pointpinner;
import com.karel.game.weapons.scream.Scream;
import com.karel.game.weapons.shotgun.Shotgun;
import com.karel.game.weapons.slicer.Slicer;
import com.karel.game.weapons.spear.SpearWeapon;
import com.karel.game.weapons.teslacoil.TeslaCoil;
import com.karel.game.weapons.weedwacker.Weedwacker;
import com.karel.game.weapons.doublegun.DoubleGun;
import com.karel.game.weapons.easterbasket.EasterBasket;
import com.karel.game.weapons.fastfood.FastFood;
import com.karel.game.weapons.gale.Gale;
import com.raylib.Texture;

public class Player extends GridEntity {
    boolean autoaim = false, autoattack = false, autoult = false, isattacking = false, ismoving = false;
    private Vector lastMoveDirection = new Vector(0, 0);
    private PlayerHand hand;
    private Texture rocket = Greenfoot.loadTexture("kareln.png");
    private Texture off = Greenfoot.loadTexture("karelnOff.png");
    private Item[] inventory;
    private boolean currentlyscrolling;
    private int currentItemIndex;
    private boolean movementLocked, rotationLocked, targetLocked;
    private double oldtargetx, oldtargety;
    private int hacooldown;
    private int diff;
    private static int[] diffhealths = new int[]{1, 5000, 2500, 750, 1};
    private static int[] diffheal = new int[]{0, 4, 2, 1, 0};
    private static int[] diffmaxsprint = new int[]{100000, 400, 200, 100, 50};
    private static int[] diffsprintrecovery = new int[]{100000, 3, 2, 1, 1};
    private boolean sprinting;
    private int maxsprint;
    private int sprintrecoverrate;
    private int sprint;
    private double sprintamt = 1;
    public Player() {
        hand = new PlayerHand();
        scaleTexture(45, 45);
        this.setImage(this.rocket);
        diff = Game.currentDiff();
        if(diff==0){
            startHealthShield(new ImmunityShield(new ShieldID(this), -1));
        }else{
            this.startHealth(diffhealths[diff]);
        }
        maxsprint = diffmaxsprint[diff];
        sprintrecoverrate = diffsprintrecovery[diff];
        sprint = maxsprint;
        Game.gameUI().newSprint(maxsprint);
        this.setTeam("player");
        this.inventory = new Item[50];
        this.inventory[0] = new Gale(getHand());
        this.inventory[1] = new NailGun(getHand());
        this.inventory[2] = new Crossbow(getHand());
        this.inventory[4] = new RockCatapult(getHand());
        this.inventory[5] = new Necromancer(getHand());
        this.inventory[6] = new CatClaw(getHand());
        this.inventory[7] = new Slicer(getHand());
        this.inventory[8] = new WaterBalloons(getHand());
        this.inventory[9] = new Lovestrike(getHand());
        this.inventory[10] = new CapsaicinTorch(getHand());
        this.inventory[11] = new Reaper(getHand());
        this.inventory[12] = new LilCritters(getHand());
        this.inventory[13] = new AirPump(getHand());
        this.inventory[14] = new CrystalGun(getHand());
        this.inventory[15] = new Weedwacker(getHand());
        this.inventory[16] = new LymphCannon(getHand());
        this.inventory[17] = new DroneRemote(getHand());
        this.inventory[18] = new Shotgun(getHand());
        this.inventory[19] = new Chameleon(getHand());
        this.inventory[20] = new DoubleGun(getHand());
        this.inventory[21] = new Sudo(getHand());
        this.inventory[22] = new Hearth(getHand());
        this.inventory[23] = new Blade(getHand());
        this.inventory[24] = new GlueGun(getHand());
        this.inventory[25] = new TeslaCoil(getHand());
        this.inventory[26] = new EasterBasket(getHand());
        this.inventory[27] = new Pointpinner(getHand());
        this.inventory[28] = new AirPump(getHand());
        this.inventory[29] = new Flail(getHand());
        this.inventory[30] = new Highjacker(getHand());
        this.inventory[31] = new PetMole(getHand());
        this.inventory[32] = new Soup(getHand());
        this.inventory[33] = new FlashDrive(getHand());
        this.inventory[34] = new Inferno(getHand());
        this.inventory[35] = new Blowgun(getHand());
        this.inventory[36] = new PaintGun(getHand());
        this.inventory[37] = new Scream(getHand());
        this.inventory[38] = new TrapSetter(getHand());
        this.inventory[39] = new Fireworks(getHand());
        this.inventory[40] = new JadeCutter(getHand());
        this.inventory[41] = new GrenadeLauncher(getHand());
        this.inventory[42] = new Farmer(getHand());
        this.inventory[43] = new Gun(getHand());
        this.inventory[44] = new FastFood(getHand());
        for(Item i: inventory){
            if(i!=null){
                ((Weapon)i).setAttackUpgrade(1);
                ((Weapon)i).setUltUpgrade(1);
                ((Weapon)i).donateGadgets(((Weapon)i).defaultGadgets());
            }
        }
        //rarities: common, uncommon, rare, epic, legendary, seasonal
        this.switchToSlot(0);
        this.hacooldown = 0;
    }
    
    public PlayerHand getHand(){
        return hand;
    }

    public Item nextSlot() {
        if(getHeldItem()!=null&&getHeldItem().isLocked()){
            return getHeldItem();
        }
        int og = this.getHeldSlot();
        while(this.switchToSlot(this.getHeldSlot() + 1 >= this.getInventorySize() ? 0 : this.getHeldSlot() + 1) == null&&getHeldSlot()!=og) {
        }

        return this.getHeldItem();
    }
    public void nextEmptySlot() {
        if(getHeldItem()!=null&&getHeldItem().isLocked()){
            return;
        }
        int og = this.getHeldSlot();
        while(this.switchToSlot(this.getHeldSlot() + 1 >= this.getInventorySize() ? 0 : this.getHeldSlot() + 1) != null&&getHeldSlot()!=og) {
        }
    }

    public Item prevSlot() {
        if(getHeldItem()!=null&&getHeldItem().isLocked()){
            return getHeldItem();
        }
        int og = this.getHeldSlot();
        while(this.switchToSlot(this.getHeldSlot() - 1 < 0 ? this.getInventorySize() - 1 : this.getHeldSlot() - 1) == null&&getHeldSlot()!=og) {
        }

        return this.getHeldItem();
    }
    public void prevEmptySlot() {
        if(getHeldItem()!=null&&getHeldItem().isLocked()){
            return;
        }
        int og = this.getHeldSlot();
        while(this.switchToSlot(this.getHeldSlot() - 1 < 0 ? this.getInventorySize() - 1 : this.getHeldSlot() - 1) != null&&getHeldSlot()!=og) {
        }
    }

    public Item switchToSlot(int slot) {
        if(getHeldItem()!=null&&getHeldItem().isLocked()){
            return getHeldItem();
        }
        if (this.getHeldItem() != null) {
            this.getHeldItem().unequip();
        }

        this.setHeldSlot(slot);
        if (this.getHeldItem() != null) {
            this.getHeldItem().equip();
            Game.changeHeldItem(this.getHeldItem().getName());
        } else {
            Game.changeHeldItem("");
        }

        return this.getHeldItem();
    }
    
    public Item equipAtSlot(Item i){
        Item old = this.getHeldItem();
        if(old!=null){
            old.unequip();
            Game.changeHeldItem("");
        }
        this.inventory[currentItemIndex] = i;
        if(i!=null){
            i.equip();
            Game.changeHeldItem(this.getHeldItem().getName());
        }
        return old;
    }

    public Item getHeldItem() {
        return this.inventory[this.currentItemIndex];
    }

    public int getHeldSlot() {
        return this.currentItemIndex;
    }

    public void setHeldSlot(int id) {
        this.currentItemIndex = id;
    }

    public int getInventorySize() {
        return this.inventory.length;
    }
    public void behave() {
        if (this.getHeldItem() instanceof Tickable) {
            ((Tickable)this.getHeldItem()).tick();
        }
        autoaim = Game.isShiftDown();
        autoult = Game.autoUlt();
        autoattack = Game.autoAttack();
        this.checkKeys();
        face(getHand().getTargetX(), getHand().getTargetY(), canMove()&&!rotationLocked);
        if (this.hacooldown > 20 && !this.isDead()/* && getWorld().getGame().getDifficulty<2*/) {
            heal(this, diffheal[diff]);
        }

        ++this.hacooldown;
        /*Iterator var7 = s.iterator();

        while(var7.hasNext()) {
            Beeper chk = (Beeper)var7.next();
            if(getWorld().currentMode().equals("protect")){
                ((Protect)(getWorld().getGame())).getBaby().heal(chk.collect()*5);
            }
        }*/

    }
    public boolean acceptingFrags(){
        return !Game.getGame().beeperbagfull();
    }
    
    //TODO
    @Override
    public double getTargetRotation(){
        return getRotation();
    }
    public double getLastMoveDirection(){
        return lastMoveDirection.getDirection();
    }
    public double getTargetX(){
        if(autoaim){
            GridEntity targ = getNearestTarget();
            if(targ!=null)return targ.getX();
        }else if(targetLocked){
            return oldtargetx;
        }
        return getWorld().getGridMouseX()+getScrollX();
    }
    public double getTargetY(){
        if(autoaim){
            GridEntity targ = getNearestTarget();
            if(targ!=null)return targ.getY();
        }else if(targetLocked){
            return oldtargety;
        }
        return getWorld().getGridMouseY()+getScrollY();
    }
    public void setTargetLock(boolean t){
        if(!targetLocked&&t){
            oldtargetx = getTargetX();
            oldtargety = getTargetY();
        }
        targetLocked = t;
    }
    public void setRotationLock(boolean t){
        rotationLocked = t;
    }
    public void setMovementLock(boolean t){
        movementLocked = t;
    }

    private void checkKeys() {
        //handle sprinting
        String sprintkey = "sprint";
        if(Greenfoot.isActive(sprintkey)&&!sprinting&&sprint>=6/*decreasing rate*/){
            sprinting = true;
            Game.gameUI().startSprint();
        }else if(!Greenfoot.isActive(sprintkey)||sprint<2){
            sprinting = false;
        }
        if(sprinting&&isMoving()){
            sprint-=2;
            Game.gameUI().setSprint(sprint);
        }else{
            if(sprint<maxsprint){
                sprint+=sprintrecoverrate;
                if(sprint>=maxsprint){
                    sprint = maxsprint;
                }
                Game.gameUI().setSprint(sprint);
            }//else sprint is already max
            else if(!Greenfoot.isActive(sprintkey)){
                Game.gameUI().stopSprint();
            }
        }
        if(sprinting){
            sprintamt = 2;
        }else{
            sprintamt = 1;
        }
        ismoving = false;
        //handle movement
        int xd = 0, yd = 0;
        if(canMove()&&!movementLocked){
            if (!Greenfoot.isActive("right")) {
                if (Greenfoot.isActive("left")) {
                    xd = -1;
                    ismoving = true;
                }
            } else {
                xd = 1;
                ismoving = true;
            }

            if (!Greenfoot.isActive("up")) {
                if (Greenfoot.isActive("down")) {
                    yd = 1;
                    ismoving = true;
                }
            } else {
                yd = -1;
                ismoving = true;
            }
        }
        Vector v = new Vector(xd, yd, 0);
        walk(v.getDirection()+90, (int)(sprintamt*v.getLength()));

        //handle item switching
        if (Greenfoot.isActive("inventoryLeft")) {
            if (!this.currentlyscrolling) {
                this.prevSlot();
                this.currentlyscrolling = true;
            }
        } else if (Greenfoot.isActive("inventoryRight")) {
            if (!this.currentlyscrolling) {
                this.nextSlot();
                this.currentlyscrolling = true;
            }
        } else {
            this.currentlyscrolling = false;
        }
        
        //handle item using
        if(canAttack()){
            Item i = getHeldItem();
            boolean didspecial = false;
            if(i instanceof Weapon){
                Weapon w = (Weapon)i;
                if(Greenfoot.isActive("inventory")){// TODO remove debug code
                    w.chargeUlt(100);
                }
                didspecial = true;
                if (Greenfoot.isActive("gadget")&&!w.continueUlt()&&w.activateGadget()) {
                    this.hacooldown = 0;
                } else if ((Greenfoot.isActive("ult")||autoult||w.continueUlt())&&w.ult()) {
                    this.hacooldown = 0;
                }else{
                    didspecial = false;
                }
            }
            if (this.getHeldItem() != null&&(Game.isAttackDown()||autoattack||getHeldItem().continueUse())&&!didspecial) {
                isattacking = true;
                this.getHeldItem().use();
    
                this.hacooldown = 0;
            }else{
                isattacking = false;
            }
        }
    }
    public boolean isAttacking(){
        return isattacking;
    }
    public boolean isMoving(){
        return ismoving;
    }

    public void die(GridObject killer) {
        super.die(killer);
        //this.getWorld().gameOver();
        this.setImage(this.off);
    }
    public void revive(){
        super.reviveWithHealth();
        this.setImage(rocket);
    }

    public void hit(int dmg, GridObject source) {
        if (!this.isDead()) {
            super.hit(dmg, source);
        }

        this.hacooldown = 0;
    }

    public void notifyDamage(GridEntity target, int amt) {
        if(target!=null&&isAlliedWith(target))return;
        if (this.getHeldItem() instanceof Weapon) {
            ((Weapon)this.getHeldItem()).chargeUlt(amt);
        }
    }
    public void doLanding(){
        if(getHeldItem() instanceof LandingHandler){
            ((LandingHandler)getHeldItem()).doLanding();
        }
    }
    public Item[] getInventory(){
        return inventory;
    }
    public void giveItem(Item i){
        nextEmptySlot();
        equipAtSlot(i);
    }
    @Override
    public boolean removeOnDeath(){
        return false;
    }
    public String getName(){
        return "Player";
    }
    
    public class PlayerHand implements ItemHolder{
        //TODO
        @Override
        public double getTargetRotation(){
            return getHolder().getTargetRotation();
        }
        public double getTargetX(){
            return getHolder().getTargetX();
        }
        public double getTargetY(){
            return getHolder().getTargetY();
        }
        public void setTargetLock(boolean t){
            getHolder().setTargetLock(t);
        }
        public void setRotationLock(boolean t){
            getHolder().setRotationLock(t);
        }
        public void setMovementLock(boolean t){
            getHolder().setMovementLock(t);
        }
        public boolean isAttacking(){
            return getHolder().isAttacking();
        }
        public boolean isMoving(){
            return getHolder().isMoving();
        }
        public double getReloadSpeed(){
            return getHolder().getReloadMultiplier();
        }
        public boolean isMainWeapon(){
            return true; // Player's hand is always the main weapon holder
        }
        public Player getHolder(){
            return Player.this;
        }
    }
}


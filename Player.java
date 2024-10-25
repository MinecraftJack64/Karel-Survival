import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.Iterator;
import java.util.List;

public class Player extends GridEntity {
    boolean autoaim = false, isattacking = false, ismoving = false;
    private Weapon weapon;
    private GreenfootImage rocket = new GreenfootImage("kareln.png");
    private GreenfootImage off = new GreenfootImage("karelnOff.png");
    private Item[] inventory;
    private boolean currentlyscrolling;
    private int currentItemIndex;
    private boolean movementLocked, rotationLocked;
    private int hacooldown;
    private int diff;
    private static int[] diffhealths = new int[]{1, 5000, 2500, 750, 1};
    private static int[] diffheal = new int[]{0, 2, 1, 0, 1};
    private static int[] diffmaxsprint = new int[]{100000, 400, 200, 100, 50};
    private static int[] diffsprintrecovery = new int[]{100000, 3, 2, 1, 1};
    private boolean sprinting;
    private int maxsprint;
    private int sprintrecoverrate;
    private int sprint;
    private double sprintamt = 1;
    public Player() {
        this.rocket.scale(45, 45);
        this.off.scale(45, 45);
        this.setImage(this.rocket);
        this.setRotation(0);
        diff = KWorld.me.currentDiff();
        this.startHealth(diffhealths[diff]);
        if(diff==0){
            applyShield(new ExternalImmunityShield(this, -1));
        }
        maxsprint = diffmaxsprint[diff];
        sprintrecoverrate = diffsprintrecovery[diff];
        sprint = maxsprint;
        getWorld().gameUI().newSprint(maxsprint);
        this.setTeam("player");
        this.inventory = new Item[20];
        this.inventory[0] = new Pointpinner(this);
        this.inventory[1] = new Shotgun(this);
        this.inventory[2] = new Crossbow(this);
        this.inventory[4] = new RockCatapult(this);
        this.inventory[5] = new Necromancer(this);
        this.inventory[6] = new CatClaw(this);
        this.inventory[7] = new Slicer(this);
        this.inventory[8] = new WaterBalloons(this);
        this.inventory[9] = new Lovestrike(this);
        this.inventory[10] = new CapsaicinTorch(this);
        this.inventory[11] = new Blowgun(this);
        this.inventory[12] = new Hearth(this);
        this.inventory[13] = new AirPump(this);
        this.inventory[14] = new CrystalGun(this);
        this.inventory[15] = new Weedwacker(this);
        this.inventory[16] = new LymphCannon(this);
        this.inventory[17] = new NailGun(this);
        this.inventory[18] = new SpearWeapon(this);
        this.inventory[19] = new Chameleon(this);
        for(Item i: inventory){
            if(i!=null)((Weapon)i).setAttackUpgrade(1);
        }
        //rarities: common, uncommon, rare, epic, legendary, seasonal
        this.switchToSlot(0);
        this.hacooldown = 0;
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
            this.getWorld().changeHeldItem(this.getHeldItem().getName());
        } else {
            this.getWorld().changeHeldItem("");
        }

        return this.getHeldItem();
    }
    
    public Item equipAtSlot(Item i){
        Item old = this.getHeldItem();
        if(old!=null){
            old.unequip();
            this.getWorld().changeHeldItem("");
        }
        this.inventory[currentItemIndex] = i;
        if(i!=null){
            i.equip();
            this.getWorld().changeHeldItem(this.getHeldItem().getName());
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
    public double getTargetX(){
        if(autoaim){
            GridEntity targ = this.getNearestTarget();
            if(targ!=null)return targ.getRealX();
        }
        return this.getWorld().getMouseX();
    }
    public double getTargetY(){
        if(autoaim){
            GridEntity targ = this.getNearestTarget();
            if(targ!=null)return targ.getRealY();
        }
        return this.getWorld().getMouseY();
    }
    public double getTargetRotation(){
        return getRealRotation();
    }
    public void setRotationLock(boolean t){
        rotationLocked = t;
    }
    public void setMovementLock(boolean t){
        movementLocked = t;
    }
    public void behave() {
        if (this.getHeldItem() instanceof Tickable) {
            ((Tickable)this.getHeldItem()).tick();
        }
        autoaim = getWorld().isShiftDown();
        this.checkKeys();
        double targang = face(getTargetX(), getTargetY(), canMove()&&!rotationLocked);
        if (this.hacooldown > 20 && !this.isDead()/* && getWorld().getGame().getDifficulty<2*/) {
            this.heal(diffheal[diff]);
        }

        ++this.hacooldown;
        List<Beeper> s = this.getObjectsInRange(10, Beeper.class);
        /*Iterator var7 = s.iterator();

        while(var7.hasNext()) {
            Beeper chk = (Beeper)var7.next();
            if(getWorld().currentMode().equals("protect")){
                ((Protect)(getWorld().getGame())).getBaby().heal(chk.collect()*5);
            }
        }*/

    }
    public boolean acceptingFrags(){
        return !getWorld().getGame().beeperbagfull();
    }

    private void checkKeys() {
        ismoving = false;
        if(canMove()&&!movementLocked){
            if (!Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("d")) {
                if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
                    this.translate(-this.getMultipliedSpeed()*sprintamt, 0.0D);
                    ismoving = true;
                }
            } else {
                this.translate(this.getMultipliedSpeed()*sprintamt, 0.0D);
                ismoving = true;
            }

            if (!Greenfoot.isKeyDown("up") && !Greenfoot.isKeyDown("w")) {
                if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
                    this.translate(0.0D, this.getMultipliedSpeed()*sprintamt);
                    ismoving = true;
                }
            } else {
                this.translate(0.0D, -this.getMultipliedSpeed()*sprintamt);
                ismoving = true;
            }
        }

        if (Greenfoot.isKeyDown("x")) {
            if (!this.currentlyscrolling) {
                this.prevSlot();
                this.currentlyscrolling = true;
            }
        } else if (Greenfoot.isKeyDown("c")) {
            if (!this.currentlyscrolling) {
                this.nextSlot();
                this.currentlyscrolling = true;
            }
        } else {
            this.currentlyscrolling = false;
        }

        if(canAttack()){
            if (this.getHeldItem() instanceof Weapon&&(Greenfoot.isKeyDown("space")||getHeldItem().continueUlt())) {
                ((Weapon)this.getHeldItem()).ult();
    
                this.hacooldown = 0;
            } else if (this.getHeldItem() != null&&(this.getWorld().lastClicked||getHeldItem().continueUse())) {
                isattacking = true;
                this.getHeldItem().use();
    
                this.hacooldown = 0;
            }else{
                isattacking = false;
            }
        }
        String sprintkey = "alt";
        if(Greenfoot.isKeyDown(sprintkey)&&!sprinting&&sprint>=6/*decreasing rate*/){
            sprinting = true;
            getWorld().gameUI().startSprint();
        }else if(!Greenfoot.isKeyDown(sprintkey)||sprint<2){
            sprinting = false;
        }
        if(sprinting){
            sprint-=2;
            getWorld().gameUI().setSprint(sprint);
        }else{
            if(sprint<maxsprint&&!Greenfoot.isKeyDown(sprintkey)){
                sprint+=sprintrecoverrate;
                if(sprint>=maxsprint){
                    sprint = maxsprint;
                    getWorld().gameUI().stopSprint();
                }
                getWorld().gameUI().setSprint(sprint);
            }//else sprint is already max
        }
        if(sprinting){
            sprintamt = 2;
        }else{
            sprintamt = 1;
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
}
/*
 * weapons:
 * Minigun(Gun) - shoots long range pellets quickly
 * ult: unleashes a proton wave that fries zombies
 * Rock(RockCatapult) - launches large rocks over walls and zombies
 * ult: shoots an asteroid that splits when it hits an enemy
 * Gale(Blower) - blows a fast narrow cone of wind that slows zombies in it, pushing them back if they're light or slow enough
 * ult: blasts the ground pushing all nearby zombies away
 * Blade(Sword) - sweeps over a wide area right in front of it and damages zombies
 * ult: slices the sword quickly around you, damaging nearby enemies
 * Slicer(Saw) - throws a spinning circular saw blade like a boomerang, damaging zombies both ways. Returns faster and deals more damage if it hits a wall or shield
 * ult: shoots a saw that chains on multiple enemies
 * Flash(Flashlight) - light that sees in the dark that attacks by flashing in a cone in front of it, making zombies catch fire during the day and stun them temporarily at night
 * ult: turn into a high range high damage laser that turns slowly and lights up everything around it
 * Crossbow(PoisonBow) - shoots 3 poisonous arrows that can be charged up to be more focused and have more range
 * ult: shoots multiples arrows into the air that rain down in an area
 * Shotgun(Shotgun) - shoots in a spray pattern(deals most damage at point blank range)
 * ult: shoots a harpoon that drags in a zombie
 * Mousetrap(Trap) - drops a mouse trap that will disappear after a long time. If a zombie steps on it they will be damaged over time and stunned for a while
 * ult: throw a box of mousetraps that quickly lays them out nearby
 */
/**
 * Write a description of class Weapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Weapon implements Item, Tickable
{
    private int ultcharge = 0;
    private boolean ultready;
    GridObject holder;
    private boolean slotlocked;
    private boolean isusing;
    private boolean isulting;
    private boolean rotlocked, movelocked;
    public int atkup;
    public int ultup;
    public boolean ultshield = false;//set to true by ult to make next attempt to reset ult charge fail
    public void setUltUpgrade(int id){
        atkup = id;
    }
    public int getUltUpgrade(){
        return atkup;
    }
    public void setAttackUpgrade(int id){
        atkup = id;
    }
    public int getAttackUpgrade(){
        return atkup;
    }
    public void use(){
        fire();
    }
    public void tick(){
        reload();
    }
    public abstract void fire();
    public abstract void fireUlt();
    public abstract void reload();
    public abstract int getUlt();
    public void ult(){
        /*if (reloadDelayCount >= gunReloadTime*5) 
        {
            ProtonWave bullet = new ProtonWave (getRotation());
            getWorld().addObject (bullet, getX(), getY());
            //bullet.move ();
            reloadDelayCount = 0;
        }*/
        if(ultready){
            fireUlt();
            resetUltCharge();
        }
    }
    public void resetUltCharge(){
        if(!ultshield){
            ultcharge = 0;
            ultready = false;
            getHolder().getWorld().setUltCharge(ultcharge);
        }else{
            ultshield = false;
        }
    }
    public void cancelUltReset(){
        ultshield = true;
    }
    public Weapon(GridObject g){
        holder = g;
        ultready = false;
    }
    public Player getHolder(){
        return (Player)holder;
    }
    public void chargeUlt(int amt){
        ultcharge+=amt;
        if(ultcharge>=getUlt()){
            ultcharge = getUlt();
            ultready = true;
        }
        getHolder().getWorld().setUltCharge(ultcharge);
    }
    public void updateAmmo(int amt){
        getHolder().getWorld().gameUI().setAmmo(amt);
    }
    public void equip(){
        getHolder().getWorld().newUltCharge(getUlt(),ultcharge);
        Sounds.play("equip");
    }
    public boolean isUltReady(){
        return ultready;
    }
    public void unequip(){
        getHolder().getWorld().disableUltCharge();
        getHolder().getWorld().gameUI().disableAmmo();
    }
    public AmmoBar getAmmoBar(){
        return getHolder().getWorld().gameUI().getAmmoBar();
    }
    public void setLocked(boolean t){
        slotlocked = t;
    }
    public void setPlayerLockRotation(boolean t){
        rotlocked = t;
        getHolder().setRotationLock(t);
    }
    public void setPlayerLockMovement(boolean t){
        movelocked = t;
        getHolder().setMovementLock(t);
    }
    public boolean isLocked(){
        return slotlocked||rotlocked||movelocked||isusing||isulting;
    }
    public boolean isRotLocked(){
        return rotlocked;
    }
    public boolean continueUse(){
        return isusing;
    }
    public boolean continueUlt(){
        return isulting;
    }
    public void setContinueUse(boolean v){
        isusing = v;
    }
    public void setContinueUlt(boolean v){
        isulting = v;
    }
}
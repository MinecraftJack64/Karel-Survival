import greenfoot.*;
import java.util.List;

/*
 * classes
 * fila-mint
 * reinforce-mint: shieldzombie
 * bombard-mint: explodingzombie
 * arma-mint: zombie that controls bombs that drop down
 * contain-mint
 * spear-mint
 * pepper-mint
 * enchant-mint
 * winter-mint
 * appease-mint: shooterzombie stays at a distance and shoots small low damage bullets at you, slower than normal
 * ail-mint: poisonzombie that occasionally lets out a cloud that poisons you and boosts nearby zombies and leaves behind a poison area on death. Will slow you if you are too close to it. Very slow and tanky
 * conceal-mint: ninjazombies remain invisible and wait for the perfect opportunity to strike. They run towards you and when they're next to you, they throw 9 ninja stars while quickly circling you 3 times. They then run away and wait again. They occasionally reveal their location for short intervals of time
 * enlighten-mint
 * enforce-mint: zombie
 */
/**
 * Write a description of class JackITBZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JackITBZombie extends Zombie
{
    private int windspeed;//5 second winding, up to speed 150(75 degrees per frame)
    private int windcooldown = 120;
    //Big: cooldown: 90, height: 150, distance: 350
    //Medium: cooldown: 60, height: 75, distance: 150
    //Small: cooldown: 30, height 25, distance: 50
    private GridEntity inside;
    private GreenfootImage rocket = new GreenfootImage("russianzareln.png");
    //private GreenfootImage rocketWithThrust = new GreenfootImage("rocketWithThrust.png");
    //private int shieldhealth = 300;
    /**
     * Initilise this rocket.
     */
    public JackITBZombie()
    {
        this(Greenfoot.getRandomNumber(4)==3);
    }
    public JackITBZombie(boolean spawnJoker){
        rocket.scale(100, 70);
        setImage(rocket);
        setRealRotation(0);
        setSpeed(1);
        startHealth(1, false);
        applyShield(new HealthShield(this, 800));
        inside = !spawnJoker?new Zombie(){public boolean wasInOriginalWave(){return false;}}:new JokerZombie(){public boolean wasInOriginalWave(){return false;}};
    }
    //ovveride this
    public int getXP(){
        return 800;
    }
    public void behave(){
        if(getRealHeight()==0){
            if(windcooldown>0){
                windcooldown--;
            }else{
                if(canMove()){
                    if(windspeed<150){
                        if(canAttack()){
                            windspeed++;
                            if(windspeed==150){
                                launchInside();
                                hit(getShield().getHealth(), this);//intended
                            }
                        }
                    }
                    setRealRotation(getRealRotation()+windspeed/2);
                }else{
                    windspeed = 0;
                }
            }
        }
    }
    public void applyphysics(){
        if(getRealHeight()>0){
            setRealHeight(getRealHeight()-100);
            if(getRealHeight()<=0){
                doLanding();
                setRealHeight(0);
            }
        }
    }
    /*public void behaveOld(){
        if(jumpcooldown<=0){
            if(distanceTo(getTarget())<getJumpDistance()&&inside!=null&&getRealHeight()==0){//spawn inside
                getWorld().addObject(inside, getRealX(), getRealY());
                inside = null;
                jumpcooldown = getJumpCooldown();
            }else if(canMove()){
                double d = Math.min(distanceTo(getTarget().getRealX(), getTarget().getRealY()), getJumpDistance()*getMultipliedSpeed());
                initiateJump(face(getTarget(), false), d, getJumpHeight());
                jumpcooldown = getJumpCooldown();
            }
        }
        jumpcooldown--;
    }*/
    public void launchInside(){
        if(inside==null){
            return;
        }
        addObjectHere(inside);
        inside.knockBack(0, 0, 300, this);
        inside = null;
    }
    public void die(GridObject source){
        if(inside==null){
            return;
        }
        addObjectHere(inside);
        inside = null;
        super.die(source);
    }
    public void doLanding(){
        if(!canAttack()){
            return;
        }
        explodeOn(getExplosionRange(), getDamage(), null);//smash explosion later
    }
    public void feast(){
        //
    }
    public double getGravity(){
        return 1;
    }
    public int getDamage(){
        return 400;
    }
    public int getExplosionRange(){
        return 120;
    }
    public boolean canBePulled(){
        return false;
    }
    
    public String getName(){
        return "Jack-in-the-box Zombie";
    }
}

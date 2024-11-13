import java.util.ArrayList;

/**
 * Write a description of class Baby here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Baby extends GridEntity
{
    private int lvl;
    private int gunReloadTime, reloadDelayCount;
    public Baby(){
        startHealth(2000);
        setTeam("player");
        lvl = 0;
        gunReloadTime = 32;
        reloadDelayCount = 0;
        repair();
    }
    public void behave(){
        switch(lvl){
            case 0:
            break;
            case 1:
                reloadDelayCount++;
                if(reloadDelayCount>=gunReloadTime){
                    Bullet bullet = new Bullet(face(getNearestTarget(), false), this);
                    getWorld().addObject(bullet, getRealX(), getRealY());
                    reloadDelayCount = 0;
                }
            break;
        }
    }
    public void repair(){
        lvl++;
    }
}

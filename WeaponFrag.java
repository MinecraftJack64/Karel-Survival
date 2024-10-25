import greenfoot.*;
/**
 * Write a description of class WeaponFrag here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeaponFrag extends Collectible
{
    /** The damage this bullet will deal */
    
    /** A bullet looses one life each act, and will disappear when life = 0 */
    
    
    /*
    public void kAct()
    {
        if(!getWorld().gameStatus().equals("running"))return;
        int targang = (int)getAngle(getWorld().getPlayer().getX(), getWorld().getPlayer().getY())+90;
        int monangle = targang;
        //setRotation(monangle);
        if(distanceTo(getWorld().getPlayer().getX(),getWorld().getPlayer().getY())<40&&getWorld().getPlayer().acceptingFrags())move(monangle, 9);
    }*/
    public void collect(GridObject targ){
        getWorld().getGame().collectWeaponFrag();
        Sounds.play("weaponfragcollect");
        super.collect(targ);
    }
}

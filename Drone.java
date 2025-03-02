/**
 * represents the drone hovering above a player with the DroneRemote weapon. At the command of the weapon, it shoots 4 consecutive bullets at a targeted location, which also changes at the command of the weapon
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Drone extends GridObject implements SubAffecter
{
    private GridEntity source;
    private double ddx, ddy;
    public int hoverheight = 100;
    private int remainingshots = 0;//4
    private int tbs = 0;//5
    private int ammo = 0;//60
    public Drone(GridEntity source){
        this.source = source;//do not set power
    }
    public GridObject getSource(){
        return source;
    }
    public void kAct(){
        setRealLocation(source.getRealX(), source.getRealY());
        setRealRotation(90+source.getAngle(source.getRealX()+ddx, source.getRealY()+ddy));
        if(getRealHeight()<source.getRealHeight()+hoverheight-2.5){
            this.setRealHeight(getRealHeight()+5);
        }else if(getRealHeight()>source.getRealHeight()+hoverheight+2.5){
            this.setRealHeight(getRealHeight()-5);
        }
        ammo++;
        if(remainingshots>0){
            attack();
        }
    }
    public void attack(){
        if(ammo>=60){remainingshots = 4; ammo = 0;}
        if(remainingshots>0&&tbs<=0){
            tbs = 5;
            remainingshots--;
            AerialBullet b = new AerialBullet(getRealRotation(), getRealHeight(), source.distanceTo(source.getRealX()+ddx, source.getRealY()+ddy)/(hoverheight/15), 15, source);
            addObjectHere(b);
        }else if(tbs>0){
            tbs--;
        }
    }
    public void reposition(double dx, double dy){
        ddx = dx;
        ddy = dy;
    }
}

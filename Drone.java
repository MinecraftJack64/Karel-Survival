/**
 * Write a description of class Drone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Drone extends Pet implements SubAffecter
{
    private GridEntity source;
    private double ddx, ddy;
    public int hoverheight = 100;
    private int remainingshots = 0;//3
    private int tbs = 0;//5
    private int ammo = 0;//60
    public Drone(GridEntity source){
        this.source = source;//do not set power
    }
    public GridObject getSource(){
        return source;
    }
    public void act(){
        setRealLocation(source.getRealX(), source.getRealY());
        setRealRotation(90+source.getAngle(source.getRealX()+ddx, source.getRealY()+ddy));
        if(getRealHeight()<source.getRealHeight()+hoverheight){
            this.setRealHeight(getRealHeight()+5);
        }
        ammo++;
        if(remainingshots>0){
            attack();
        }
    }
    public void attack(){
        if(ammo>=60){remainingshots = 3; ammo = 0;}
        if(remainingshots>0&&tbs<=0){
            tbs = 5;
            remainingshots--;
            AerialBullet b = new AerialBullet(getRealRotation(), getRealHeight(), source.distanceTo(source.getRealX()+ddx, source.getRealY()+ddy)/(hoverheight/10), 10, source);
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

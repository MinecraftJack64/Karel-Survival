import java.util.List;

/**
 * Write a description of class Mole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Mole extends Projectile
{
    private int speed = 15;
    public Mole(GridObject source)
    {
        super(source);
        setVisible(false);
        setTeam(source.getTeam());
    }
    public void attackAt(double x, double y){
        setRealLocation(x, y);
        setVisible(true);
    }
    public void attack(){
        MoleUppercut mu = new MoleUppercut(0,0,120,this);
        addObjectHere(mu);
    }
    public void moveTo(double x, double y){
        if(distanceTo(x, y)<speed){
            move(getAngle(x, y)+90, speed);
        }
    }
    public void kAct(){
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
    }
}

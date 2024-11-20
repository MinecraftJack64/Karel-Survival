import java.util.List;

/**
 * Write a description of class StaticJadeBlade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StaticJadeBlade extends Projectile{
    private int attackdelay = 15;
    private int size = 45;//60 damage
    private static final int maxsize = 300;
    public StaticJadeBlade(GridObject source)
    {
        super(source);
        setTeam(source.getTeam());
    }
    public void fire(double r){
        //
    }
    public boolean canAttack(){
        return true;
    }
    public void kAct(){
        if(getSource()!=null)setRealLocation(getSource().getRealX(), getSource().getRealY());
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
    }
}

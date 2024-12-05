import java.util.List;

/**
 * Write a description of class TeslaCoilZap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TeslaCoilZap extends Hitter
{
    private int attacked;
    private boolean weak;
    public TeslaCoilZap(GridObject source, boolean isweak)
    {
        super(source);
        attacked = 0;
        weak = isweak;
        setVisible(false);
        setTeam(source.getTeam());
    }
    public void attackAt(double x, double y){
        attacked = 1;
        setRealLocation(x, y);
        setVisible(true);
        List<GridEntity> l = getGEsInRange(100);
        //int dmg = (int)(14*Math.pow(2, -0.8*l.size())+1)*(int)Math.max(0, Math.max(1, 1-(distanceTo(getSource())-400)/100));//500 should be 0, 400 should be 1
        if(l.size()>0){
            double totaldmg = 18*Math.pow(2, -l.size());
            double distmult = Math.sqrt(400-Math.min(400, distanceTo(getSource())))/20;
            int dmg = (int)((totaldmg/l.size()+2)*distmult)/(weak?2:1);
            for(GridEntity g:l){
                if(isAggroTowards(g))damage(g, dmg);
            }
        }
    }
    public void kAct(){
        if(attacked>0){
            attacked--;
        }else{
            if(getSource()!=null)setRealLocation(getSource().getRealX(), getSource().getRealY());
            setVisible(false);
        }
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
    }
}

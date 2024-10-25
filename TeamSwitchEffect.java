import greenfoot.*;
/**
 * Write a description of class TeamSwitchEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TeamSwitchEffect extends Effect
{
    int duration;
    GridObject source;
    String team;
    public TeamSwitchEffect(int duration, String team, GridObject source){
        this.duration = duration;
        this.source = source;
        this.team = team;
    }
    public boolean affect(GridEntity e){
        //System.out.println("Poison info: "+nextinterval+" "+remainingtimes);
        duration--;
        e.setFakeTeam(team);
        if(duration<=0){
            e.clearFakeTeam();
            return false;
        }
        return true;
    }
    public void appliedTo(GridEntity source){
        source.setFakeTeam(team);
        Sounds.play("hypnotize");
    }
}

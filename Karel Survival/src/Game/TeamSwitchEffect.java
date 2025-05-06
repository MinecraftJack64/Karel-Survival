package Game;
import greenfoot.*;
/**
 * Temporarily makes the target part of another team
 * @author MinecraftJack64
 */
public class TeamSwitchEffect extends MaliciousEffect
{
    String team;
    public TeamSwitchEffect(String team, int duration, GridObject source){
        super(duration, source);
        this.team = team;
    }
    public void onApply(){
        getTarget().setFakeTeam(team);
        Sounds.play("hypnotize");
    }
    public void onClear(){
        getTarget().clearFakeTeam();
    }
}

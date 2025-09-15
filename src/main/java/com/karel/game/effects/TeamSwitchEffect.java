package com.karel.game.effects;

import com.karel.game.GridObject;
import com.karel.game.Sounds;

/**
 * Temporarily makes the target part of another team. Also known as hypnotization.
 * ID "team_switch"
 * @author MinecraftJack64
 */
public class TeamSwitchEffect extends MaliciousEffect
{
    String team;
    public TeamSwitchEffect(String team, int duration, GridObject source){
        super(duration, source);
        this.team = team;
    }
    public String getStaticTextureURL(){
        return "Symbols/Effects/team_switch.png";
    }
    public void onApply(){
        getTarget().setFakeTeam(team);
        Sounds.play("hypnotize");
    }
    public void onClear(){
        getTarget().clearFakeTeam();
    }
}

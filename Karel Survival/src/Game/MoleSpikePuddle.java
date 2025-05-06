package Game;
import java.util.HashSet;
/**
 * Write a description of class MoleSpikePuddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MoleSpikePuddle extends Puddle
{
    private HashSet<GridEntity> lastAffected;
    private EffectID slow;
    public MoleSpikePuddle(GridObject source){
        super(120, 1, 90, source);
        setTrackAfterHit(true);
        lastAffected = new HashSet<GridEntity>();
        slow = new EffectID(this);
    }
    public void doHit(GridEntity t){
        /*if(!getHitStory().contains(t))*/t.setSpeedMultiplier(0.5, slow);
    }
    public void afterHit(GridEntity t){
        t.setSpeedMultiplier(1, slow);
    }
}

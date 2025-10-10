package com.karel.game.weapons.reaper;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;
public class BatSwarm extends Bullet
{
    private Reaper reaper;
    public BatSwarm(double rotation, GridObject source)
    {
        super(rotation, source);
        setSpeed(13);
        setLife(37);
        setDamage(125);
        setNumTargets(-1);
    }
    public BatSwarm(double rotation, Reaper reaper, GridObject source){
        this(rotation, source);
        this.reaper = reaper;
    }
    public void notifyDamage(GridEntity targ, int amt){
        super.notifyDamage(targ, amt);
        if(getSource() instanceof GridEntity)heal((GridEntity)getSource(), amt);
        if(reaper!=null)reaper.notifyHit();
    }
}

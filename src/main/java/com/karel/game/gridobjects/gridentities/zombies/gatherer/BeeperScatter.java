package com.karel.game.gridobjects.gridentities.zombies.gatherer;

import com.karel.game.Dropper;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.collectibles.Beeper;

public class BeeperScatter extends Dropper {
    public BeeperScatter(double rotation, double targetdistance, double height, GridObject source)
    {
        super(rotation, targetdistance, height, new Beeper(50), source);
    }
}

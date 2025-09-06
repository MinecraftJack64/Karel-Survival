package com.karel.game.effects;

import java.util.function.Consumer;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;

/**
 * After a set interval, deal specified damage(poison) or apply a preset effect once.
 * ID "infection"
 * @author (your name)
 * @version (a version number or a date)
 */
public class InfectionEffect extends PoisonEffect
{
    Consumer<GridEntity> onInfect;
    public InfectionEffect(Consumer<GridEntity> onInfect, int interval, GridObject source){
        super(0, interval, 1, source);
        this.onInfect = onInfect;
    }
    public InfectionEffect(int damage, int interval, GridObject source){
        super(damage, interval, 1, source);
    }
    public void damage(GridEntity e){
        super.damage(e);
        if(onInfect != null) onInfect.accept(e);
    }
}

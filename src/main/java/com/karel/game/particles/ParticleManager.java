package com.karel.game.particles;

import com.karel.game.Tickable;

//Used by the world specifically to create and manage particles
public interface ParticleManager extends Tickable{
    public void tick();
}

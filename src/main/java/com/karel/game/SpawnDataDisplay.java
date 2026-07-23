package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.ui.Overlay;

public class SpawnDataDisplay extends Overlay{
    public ZombieSpawner zs = null;
    public void render(){
        if(zs!=null){
            int x = (int)getX();
            for(GridEntity ge: zs.currentlySpawned){
                if(ge instanceof Zombie){
                    System.out.println(ge.getName()+" health: "+ge.getHealth()+" isDead? "+ge.isDead());
                    renderTexture(Greenfoot.loadTexture(ge.getImageURL()), x, getY(), 40, 40, 0, ge.isDead()?70:255);
                    x+=40;
                }
            }
            System.out.println(zs.toSpawn);
            System.out.println("Max health: "+zs.totalHealth());
            System.out.println("total health: "+zs.totalHealth()+" time until next spawn: "+zs.forceNextSpawn+"\n");
        }
    }
    public boolean isInGridWorld(){
        return false;
    }
}

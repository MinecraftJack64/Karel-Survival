package com.karel.game.gridobjects.gridentities.totems;

import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.raylib.Texture;

public class Totem extends GridEntity {
    private Texture auraTexture = Greenfoot.loadTexture("Weapons/hearth/proj.png");
    private int range = 100;
    private int decay = 0;
    public Totem(){
        startHealth(200);
    }
    public void behave(){
        explodeOn(range, (g)->{doHit(g);});
        hit(decay, this);
    }
    public void doHit(GridEntity target){
        //
    }
    public void render(){
        renderTexture(auraTexture, getX(), getY(), range*2, range*2, getRotation(), 70);
        super.render();
    }
    public String getTotemID(){
        return "";
    }
    public String getEntityID(){return "totem-"+getTotemID();}
}

package com.karel.game;
import java.util.*;

/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends Hitter
{
    public Projectile(GridObject source){
        super(source);
    }
    public void applyPhysics(){
        //
    }
    public void update(){
        applyPhysics();
    }
}

package com.karel.game;

/**
 * Write a description of class Dasher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dasher  
{
    double speed, direction;//how much to move in one frame, how wide to do things
    int time;//how many frames to move
    GridEntity subject;//which gridentity is dashing
    public Dasher(double direction, double speed, int time, GridEntity subject){
        this.time = time;
        this.speed = speed;
        this.subject = subject;
        this.direction = direction;
    }
    public GridEntity getSubject(){
        return subject;
    }
    public boolean dash(){//return false if dashing is done
        if(subject.isDead()||!subject.canBePulled()){
            return false;
        }
        System.out.println(subject.pull(direction, speed, subject));
        time--;
        if(time<=0){
            return false;
        }
        return true;
    }
}

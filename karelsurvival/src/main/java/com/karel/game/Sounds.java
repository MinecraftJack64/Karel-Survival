package com.karel.game;
/**
 * Write a description of class Sounds here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sounds  
{
    private static int volume = 0;
    static String thifn="  eggcrack,, rocketcrash, zombiedrop,  lassotighten, shotgunjam, mousetrapsnap,  click, stun, hypnotize, armorshieldbreak, crossbowshoot, lifestealshoot, lassoshoot, protonwave, swirl, setuptrap, craft, craftfailed, emptycraft, equip";
    public static void play(String id){
        //System.out.println(id);
        if(volume>0&&!thifn.contains(id)){
            try{Greenfoot.playSound(id+".wav");}catch(Exception e){System.out.println(e);}
        }
    }
    public static void setVolume(int v){
        volume = v;
    }
}

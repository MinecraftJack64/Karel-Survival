package com.karel.game;

import com.raylib.Texture;
import com.raylib.Sound;
import com.raylib.Raylib;
import com.karel.images.TextureLoader;
import com.karel.sounds.SoundLoader;

public class Greenfoot {
    public static int getRandomNumber(int lim){
        return (int)(Math.random()*lim);
    }
    public static double getRandomNumber(){
        return Math.random();
    }
    public static boolean isActive(String k){
        return IO.isActive(k);
    }
    public static boolean isMouseDown(){
        return IO.isMouseDown();
    }
    public static int getMouseX(){return Raylib.getMouseX();}
    public static int getMouseY(){return Raylib.getMouseY();}
    public static Texture loadTexture(String url){
        return TextureLoader.loadTexture(url);
    }
    public static Sound loadSound(String url){
        return SoundLoader.loadSound(url);
    }
}

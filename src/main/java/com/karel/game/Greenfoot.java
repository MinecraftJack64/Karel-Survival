package com.karel.game;

import com.raylib.Texture;
import com.karel.images.TextureLoader;

public class Greenfoot {
    public static int getRandomNumber(int lim){
        return (int)(Math.random()*lim);
    }
    public static boolean isActive(String k){
        return IO.isActive(k);
    }
    public static int getMouseX(){return 0;}
    public static int getMouseY(){return 0;}
    public static Texture loadTexture(String url){
        return TextureLoader.loadTexture(url);
    }
}

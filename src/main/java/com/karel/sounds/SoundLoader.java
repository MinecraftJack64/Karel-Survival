package com.karel.sounds;

import com.raylib.Raylib;
import com.raylib.Sound;
import java.util.HashMap;


public class SoundLoader {
    static HashMap<String, Sound> soundCache = new HashMap<>();
    public static Sound loadSound(String url){
        if(soundCache.containsKey(url)){
            return soundCache.get(url);
        }
        Sound texture = Raylib.loadSound("src/main/java/com/karel/sounds/"+url);
        soundCache.put(url, texture);
        return texture;
    }
}

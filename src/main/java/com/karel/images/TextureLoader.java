package com.karel.images;

import java.util.HashMap;

import com.raylib.Raylib;
import com.raylib.Texture;

public class TextureLoader {
    static HashMap<String, Texture> textureCache = new HashMap<>();
    public static Texture loadTexture(String url){
        if(textureCache.containsKey(url)){
            return textureCache.get(url);
        }
        Texture texture = Raylib.loadTexture("src/main/java/com/karel/images/"+url);
        textureCache.put(url, texture);
        return texture;
    }
}

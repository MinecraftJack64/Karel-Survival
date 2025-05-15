package com.karel.images;

import com.raylib.Raylib;
import com.raylib.Texture;

public class TextureLoader {
    public static Texture loadTexture(String url){
        return Raylib.loadTexture("src/main/java/com/karel/images/"+url);
    }
}

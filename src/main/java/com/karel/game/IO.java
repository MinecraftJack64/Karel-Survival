package com.karel.game;

import java.util.HashMap;
import com.raylib.Raylib;
import static com.raylib.Raylib.KeyboardKey.*;
/**
 * store mappings
 */
public class IO {
    //known mappings
    //attack, ult, left, right, up, down, autoaim, craft, gadget, sprint, pause, inventory, inventoryLeft, inventoryRight
    public static final HashMap<String, Integer> mappings = new HashMap<String, Integer>();
    static{
        mappings.put("left", KEY_LEFT);
        mappings.put("right", KEY_RIGHT);
        mappings.put("up", KEY_UP);
        mappings.put("down", KEY_DOWN);
        mappings.put("inventoryLeft", KEY_X);
        mappings.put("inventoryRight", KEY_C);
    }
    public static boolean isActive(String action){
        return mappings.containsKey(action)&&Raylib.isKeyDown(mappings.get(action)); // TODO
    }
}
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
        mappings.put("left", KEY_A);
        mappings.put("right", KEY_D);
        mappings.put("up", KEY_W);
        mappings.put("down", KEY_S);
        mappings.put("ult", KEY_SPACE);
        mappings.put("pause", KEY_ESCAPE);
        mappings.put("inventoryLeft", KEY_X);
        mappings.put("inventoryRight", KEY_C);
    }
    public static boolean isActive(String action){
        if(action.equals("attack")){return Raylib.isMouseButtonDown(Raylib.MouseButton.MOUSE_BUTTON_LEFT);}
        return mappings.containsKey(action)&&Raylib.isKeyDown(mappings.get(action)); // TODO
    }
}
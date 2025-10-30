package com.karel.game;

import java.util.HashMap;
import com.raylib.Raylib;
import com.raylib.Vector2;

import static com.raylib.Raylib.KeyboardKey.*;
/**
 * store mappings
 */
public class IO {
    //known mappings remaining
    //inventory
    private static String controlType = "keyboard";
    private static int prevTouchCount = 0;
    private static Location touchPoints[] = new Location[10];
    public static final HashMap<String, Integer> mappings = new HashMap<String, Integer>();
    static{
        mappings.put("left", KEY_A);
        mappings.put("right", KEY_D);
        mappings.put("up", KEY_W);
        mappings.put("down", KEY_S);
        mappings.put("targetleft", KEY_LEFT);
        mappings.put("targetright", KEY_RIGHT);
        mappings.put("targetforward", KEY_UP);
        mappings.put("targetback", KEY_DOWN);
        mappings.put("ult", KEY_SPACE);
        mappings.put("attack", KEY_RIGHT_CONTROL);
        mappings.put("sprint", KEY_LEFT_ALT);
        mappings.put("autoaim", KEY_LEFT_SHIFT);
        mappings.put("craft", KEY_E);
        mappings.put("gadget", KEY_Q);
        mappings.put("pause", KEY_ESCAPE);
        mappings.put("inventoryLeft", KEY_X);
        mappings.put("inventoryRight", KEY_C);
        mappings.put("inventory", KEY_Z);
        if(!controlType.equals("keyboard")){
            //
        }
    }
    public static boolean isActive(String action){
        if(action.equals("attack")){return Raylib.isMouseButtonDown(Raylib.MouseButton.MOUSE_BUTTON_LEFT);}
        return mappings.containsKey(action)&&Raylib.isKeyDown(mappings.get(action)); // TODO
    }
    public static boolean isMouseDown(){
        return Raylib.isMouseButtonDown(Raylib.MouseButton.MOUSE_BUTTON_LEFT);
    }
    public static void updateTouchPoints(){
        int max = Math.min(Raylib.getTouchPointCount(), 10);
        System.out.println("Max Points: "+Raylib.getTouchPointCount());
        if(prevTouchCount==max){
            //just update
        }else{
            prevTouchCount = max;
        }
        for(int i = 0; i < max; i++){
            Vector2 v = Raylib.getTouchPosition(i);
            touchPoints[i] = new Location(v.getX(), v.getY());
            System.out.println(touchPoints[i].x+", "+touchPoints[i].y);
        }
        System.out.println();
        if(max>0)throw new UnsupportedOperationException();
    }
    public static Location[] getTouchPoints(){
        return touchPoints;
    }
    public static int getMouseX(){return Raylib.getMouseX();}
    public static int getMouseY(){return Raylib.getMouseY();}
}
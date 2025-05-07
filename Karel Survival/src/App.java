//package src;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;

public class App{

    public static void main(String[] args){
        Raylib rlj = new Raylib();
        rlj.core.InitWindow(800, 600, "Raylib-J Example");

        while (!rlj.core.WindowShouldClose()){
            rlj.core.BeginDrawing();
            rlj.core.ClearBackground(Color.WHITE);
            rlj.text.DrawText("Hello, World!", 800 - (rlj.text.MeasureText("Hello, World!", 20)/2), 300, 20, Color.DARKGRAY);
            rlj.core.EndDrawing();
        }
        //System.out.println("HELLO");
    }

}
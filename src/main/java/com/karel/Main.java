package com.karel;

import com.karel.game.Game;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(800, 450, "Demo");
        setTargetFPS(60);
        //System.out.println(loadTexture("src/main/java/com/karel/images/kareln.png"));
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(RAYWHITE);
            drawText("Hello world", 190, 200, 20, VIOLET);
            Game.tick();
            endDrawing();
        }
        closeWindow();
    }
}
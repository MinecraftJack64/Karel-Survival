package com.karel;

import com.karel.game.Game;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(800, 450, "Demo");
        setTargetFPS(60);
        int x = 0;
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(RAYWHITE);
            drawText("Hello world", (int)(190+50*Math.sin(x)), 200, 20, VIOLET);
            Game.tick();
            System.out.println(Game.world.allEntities.size());
            x++;
            endDrawing();
        }
        closeWindow();
    }
}
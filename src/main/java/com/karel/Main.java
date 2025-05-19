package com.karel;

import com.karel.game.Game;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(800, 450, "Demo");
        setTargetFPS(60);
        setWindowState(ConfigFlags.FLAG_WINDOW_RESIZABLE);
        maximizeWindow();
        int x = 0;
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(RAYWHITE);
            Game.tick();
            //System.out.println(Game.world.allKActors());
            x++;
            endDrawing();
        }
        closeWindow();
    }
}
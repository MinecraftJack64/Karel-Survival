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
            drawText("Hello world", (int)(190+50*Math.sin(x*1.0/180)), 200, 20, VIOLET);
            Game.tick();
            x++;
            endDrawing();
        }
        closeWindow();
    }
}
package com.karel;

import com.karel.game.Game;
import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(1200, 800, "Karel Survival");
        setTargetFPS(48);
        setWindowState(ConfigFlags.FLAG_WINDOW_RESIZABLE);
        setExitKey(KeyboardKey.KEY_F4);
        maximizeWindow();
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(WHITE);
            Game.tick();
            endDrawing();
        }
        closeWindow();
    }
}
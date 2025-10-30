package com.karel;

import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.lang.Lang;
import com.raylib.Raylib.ConfigFlags;
import com.raylib.Raylib.KeyboardKey;

import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(1200, 800, Lang.get("title"));
        initAudioDevice();
        setTargetFPS(Game.FPS);
        setWindowState(ConfigFlags.FLAG_WINDOW_RESIZABLE);
        setWindowIcon(loadImageFromTexture(Greenfoot.loadTexture("kareln.png")));
        setExitKey(KeyboardKey.KEY_F4);
        maximizeWindow();
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(WHITE);
            Game.tick();
            endDrawing();
        }
        //TODO: Save
        closeWindow();
    }
}
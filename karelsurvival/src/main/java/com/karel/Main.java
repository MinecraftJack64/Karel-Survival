package com.karel;

import static com.raylib.Raylib.*;

public class Main {
    public static void main(String args[]) {
        initWindow(800, 450, "Demo");
        setTargetFPS(60);
        while (!windowShouldClose()) {
            beginDrawing();
            clearBackground(RAYWHITE);
            drawText("Hello world", 190, 200, 20, VIOLET);
            endDrawing();
        }
        closeWindow();
    }
}
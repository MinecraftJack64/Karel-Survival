package com.karel.game.ui;

import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.text.TextDisplay;
import com.raylib.Color;
import com.raylib.Raylib;
import com.karel.game.Game;

/**
 * Write a description of class PauseUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PauseUI extends UI 
{
    private Button resumeButton, quitButton;
    private TextDisplay go;
    public void create(){
        go = new TextDisplay("PAUSED", 90, Raylib.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0-90);
        resumeButton = new Button(150, 75, "Resume", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.togglePause();
            }
        };
        getWorld().addObject(resumeButton, go.getRealX(), go.getBottom()+25);
        quitButton = new Button(150, 75, "Quit", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
            }
        };
        getWorld().addObject(quitButton, go.getRealX(), resumeButton.getBottom()+100);
        menuBG();
    }
    
    public void showPauseMenu(){
        setPauseMenu(true);
    }
    public void hidePauseMenu(){
        setPauseMenu(false);
    }
    public void setPauseMenu(boolean b){
        resumeButton.setVisible(b);
        resumeButton.setActive(b);
        quitButton.setVisible(b);
        quitButton.setActive(b);
        go.setVisible(b);
    }
}

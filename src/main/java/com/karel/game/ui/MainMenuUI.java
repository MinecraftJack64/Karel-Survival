package com.karel.game.ui;

import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.text.TextDisplay;
import com.karel.game.ui.unitDisplay.KarelDisplay;
import com.karel.game.Game;
import com.raylib.Raylib;

/**
 * Write a description of class MainMenuUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenuUI extends UI 
{
    private Button playButton, modeSelectButton, dfcSelectButton;
    private TextDisplay go, modeText, dfcText, tip, modeDesc, diffDesc;
    public void create(){
        go = new TextDisplay("KAREL SURVIVAL", 90, Raylib.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight-7)/2.0);
        modeText = new TextDisplay("Mode", 25, Raylib.GREEN);
        getWorld().addObject(modeText, go.getX()-200, go.getBottom()+15);
        dfcText = new TextDisplay("Difficulty", 25, Raylib.GREEN);
        getWorld().addObject(dfcText, go.getX()+200, go.getBottom()+15);
        modeSelectButton = new Button(150, 75, Game.selectedModeName(), Raylib.GREEN){
            public void click(){
                //example of click function
                Game.nextMode();
                setText(Game.selectedModeName());
                modeDesc.setText(Game.getModeDescription());
            }
        };
        getWorld().addObject(modeSelectButton, modeText.getX(), modeText.getBottom()+40);
        modeDesc = new TextDisplay(Game.getModeDescription(), 20, Raylib.GRAY);
        getWorld().addObject(modeDesc, modeSelectButton.getX(), modeSelectButton.getY()+50);
        dfcSelectButton = new Button(150, 75, Game.selectedDiffName(), Raylib.GREEN){
            public void click(){
                //example of click function
                Game.nextDiff();
                setText(Game.selectedDiffName());
                diffDesc.setText(Game.getDifficultyDescription());
            }
        };
        getWorld().addObject(dfcSelectButton, dfcText.getX(), dfcText.getBottom()+40);
        diffDesc = new TextDisplay(Game.getDifficultyDescription(), 20, Raylib.GRAY);
        getWorld().addObject(diffDesc, dfcSelectButton.getX(), dfcSelectButton.getY()+50);
        playButton = new Button(200, 100, "PLAY", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.startGame(Game.selectedMode());
            }
        };
        getWorld().addObject(playButton, go.getX(), dfcSelectButton.getBottom()+200);
        tip = new TextDisplay(Game.getTip(), 20, Raylib.BLUE);
        getWorld().addObject(tip, playButton.getX(), playButton.getY()-80);
        
        KarelDisplay rocket = new KarelDisplay();
        getWorld().addToGrid(rocket, 12, 2);
        Button journal = new Button(50, 50, "Journal", Raylib.GREEN){
            public void click(){
                Game.openJournal();
            }
        };
        getWorld().addToGrid(journal, 14, 1);
        Button tinker = new Button(50, 50, "Tinker", Raylib.GREEN){
            public void click(){
                //Game.openTinker();
            }
        };
        getWorld().addToGrid(tinker, 10, 1);
        Button edit = new Button(100, 50, "Edit", Raylib.GREEN){
            public void click(){
                //Game.openPlayerEdit();
            }
        };
        getWorld().addToGrid(edit, 12, 0);
        resetBG();
    }
}

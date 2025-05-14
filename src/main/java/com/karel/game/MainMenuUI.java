package com.karel.game;
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
        go = new TextDisplay("KAREL SURVIVAL", 90, Color.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0-45);
        modeText = new TextDisplay("Mode", 25, Color.GREEN);
        getWorld().addObject(modeText, go.getRealX()-200, go.getBottom()+15);
        dfcText = new TextDisplay("Difficulty", 25, Color.GREEN);
        getWorld().addObject(dfcText, go.getRealX()+200, go.getBottom()+15);
        modeSelectButton = new Button(150, 75, getWorld().selectedModeName(), Color.GREEN){
            public void click(){
                //example of click function
                getWorld().nextMode();
                setText(getWorld().selectedModeName());
                modeDesc.setText(getWorld().getModeDescription());
            }
        };
        getWorld().addObject(modeSelectButton, modeText.getRealX(), modeText.getBottom()+40);
        modeDesc = new TextDisplay(getWorld().getModeDescription(), 20, Color.GRAY);
        getWorld().addObject(modeDesc, modeSelectButton.getRealX(), modeSelectButton.getRealY()+50);
        dfcSelectButton = new Button(150, 75, getWorld().selectedDiffName(), Color.GREEN){
            public void click(){
                //example of click function
                getWorld().nextDiff();
                setText(getWorld().selectedDiffName());
                diffDesc.setText(getWorld().getDifficultyDescription());
            }
        };
        getWorld().addObject(dfcSelectButton, dfcText.getRealX(), dfcText.getBottom()+40);
        diffDesc = new TextDisplay(getWorld().getDifficultyDescription(), 20, Color.GRAY);
        getWorld().addObject(diffDesc, dfcSelectButton.getRealX(), dfcSelectButton.getRealY()+50);
        playButton = new Button(200, 100, "PLAY", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().startGame(getWorld().selectedMode());
            }
        };
        getWorld().addObject(playButton, go.getRealX(), dfcSelectButton.getBottom()+200);
        tip = new TextDisplay(getWorld().getTip(), 20, Color.BLUE);
        getWorld().addObject(tip, playButton.getRealX(), playButton.getRealY()-60);
        
        KarelDisplay rocket = new KarelDisplay();
        getWorld().addToGrid(rocket, 12, 10);
    }
}

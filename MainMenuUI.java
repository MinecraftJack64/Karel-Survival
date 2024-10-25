import greenfoot.*;
/**
 * Write a description of class MainMenuUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MainMenuUI extends UI 
{
    private Button playButton, modeSelectButton, dfcSelectButton;
    private TextDisplay go, modeText, dfcText;
    public void create(){
        go = new TextDisplay("KAREL ADVENTURE", 90, Color.GREEN);
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
            }
        };
        getWorld().addObject(modeSelectButton, modeText.getRealX(), modeText.getBottom()+40);
        modeSelectButton.addPuppet();
        dfcSelectButton = new Button(150, 75, getWorld().selectedDiffName(), Color.GREEN){
            public void click(){
                //example of click function
                getWorld().nextDiff();
                setText(getWorld().selectedDiffName());
            }
        };
        getWorld().addObject(dfcSelectButton, dfcText.getRealX(), dfcText.getBottom()+40);
        dfcSelectButton.addPuppet();
        playButton = new Button(200, 100, "PLAY", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().startGame(getWorld().selectedMode());
            }
        };
        getWorld().addObject(playButton, go.getRealX(), dfcSelectButton.getBottom()+200);
        playButton.addPuppet();
        
        FakePlayer rocket = new FakePlayer();
        getWorld().addToGrid(rocket, 12, 10);
    }
}

import greenfoot.*;
import java.util.ArrayList;

/**
 * Write a description of class GameUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameUI extends UI  
{
    public Counter scoreCounter, waveCounter;
    private UltBar ultCharge;
    private AmmoBar ammo;
    private StatusBar sprint;
    private TextDisplay heldItem, ultMessage, ultMessage2, weaponmessagerarity, weaponmessage, weaponmessage2, weaponmessage3, sprintText, tut1, tut2;
    private Button goToMenuBtn, nextLvlBtn;
    private BossBar boss;
    private ArrayList<TextDisplay> weaponchances = new ArrayList<TextDisplay>();
    public void create(){
        waveCounter = new Counter("Wave ", 20);
        getWorld().addObject(waveCounter, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(0)*1.0-15);
        waveCounter.setValue(1);
        
        heldItem = new TextDisplay("Item", 20, Color.GRAY);
        getWorld().addObject(heldItem, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight-1)*1.0-15);
        
        ammo = new AmmoBar(0, 1, 120, 10);
        ammo.setVisible(false);
        getWorld().addObject(ammo, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight-2)*1.0+15);
        
        sprint = new StatusBar(0, 1, 120, 10, Color.MAGENTA);
        sprint.setVisible(false);
        getWorld().addObject(sprint, getWorld().gridXToRealX(getWorld().gridwidth)*0.8, ammo.getRealY());
        
        sprintText = new TextDisplay("Sprint!", 20, Color.MAGENTA);
        sprintText.setVisible(false);
        getWorld().addObject(sprintText, sprint.getRealX(), sprint.getRealY()+30);
        
        scoreCounter = new Counter("Score: ", 20);
        getWorld().addObject(scoreCounter, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, waveCounter.getBottom());
        
        ultCharge = new UltBar(100, 350, 30);
        getWorld().addObject(ultCharge, 25, 400.0);

        ultMessage = new TextDisplay("Space", 20, Color.ORANGE);
        ultMessage2 = new TextDisplay("to fire!", 20, Color.ORANGE);
        getWorld().addObject(ultMessage, 35, 170.0);
        getWorld().addObject(ultMessage2, 35, ultMessage.getBottom());
        ultMessage.setVisible(false);
        ultMessage2.setVisible(false);
        
        weaponmessagerarity = new ShyTextDisplay("", 20, Color.GRAY);
        getWorld().addObject(weaponmessagerarity, getWorld().gridXToRealX(getWorld().gridwidth)/5.0, getWorld().gridYToRealY(getWorld().gridheight)-150);
        weaponmessage = new ShyTextDisplay("You found a weapon fragment", 20, Color.GRAY);
        getWorld().addObject(weaponmessage, weaponmessagerarity.getRealX(), weaponmessagerarity.getBottom()+10);
        weaponmessage2 = new ShyTextDisplay("Press e to craft", 20, Color.GRAY);
        getWorld().addObject(weaponmessage2, weaponmessage.getRealX(), weaponmessage.getBottom()+10);
        weaponmessage3 = new ShyTextDisplay("You have 0 fragments", 20, Color.GRAY);
        getWorld().addObject(weaponmessage3, weaponmessage2.getRealX(), weaponmessage2.getBottom()+10);
        tut1 = new TextDisplay("", 20, Color.GRAY);
        getWorld().addObject(tut1, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        tut2 = new TextDisplay("", 20, Color.GRAY);
        getWorld().addObject(tut2, tut1.getRealX(), tut1.getBottom()+10);
    } 
    public void setTutorial(String thing){
        tut1.setText(thing);
    }
    public void setTutorial2(String thing){
        tut2.setText(thing);
    }
    /**
     * This method is called when the game is over to display the final score.
     */
    public void gameOver(boolean beaths) //SURVIVAL+OTHER MODES
    {
        TextDisplay go = new TextDisplay("GAME OVER", 60, Color.RED);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(kill!=null){
            TextDisplay killer = new TextDisplay("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill), 20, Color.RED.darker());
            getWorld().addObject(killer, go.getRealX(), go.getBottom());
            scoreCounter.setRealLocation(scoreCounter.getRealX(), killer.getBottom());
        }else{
            scoreCounter.setRealLocation(scoreCounter.getRealX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        if(!beaths){
            hs = new Counter("High Score: ", 20);
            ((Counter)hs).setTarget(getWorld().getHighScore());
            ((Counter)hs).setColor(Color.GREEN.darker());
            getWorld().addObject(hs, scoreCounter.getRealX(), scoreCounter.getBottom());
        }else{
            TextDisplay beatscore = new TextDisplay("You Beat Your High Score!", 20, Color.GREEN.darker());
            getWorld().addObject(beatscore, scoreCounter.getRealX(), scoreCounter.getBottom());
            hs = beatscore;
        }
        Counter waveCounter;
        if(getWorld().currentMode().equals("survival")||getWorld().currentMode().equals("protect")){
            waveCounter = new Counter("Waves Survived: ", 20);
            waveCounter.setTarget(((ZombieSpawner)(getWorld().getGame().getSpawner())).wavelevel-1);
            getWorld().addObject(waveCounter, scoreCounter.getRealX(), hs.getBottom());
        }else{
            waveCounter = new Counter("Level: ", 20);
            waveCounter.setTarget(0);
            getWorld().addObject(waveCounter, scoreCounter.getRealX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getRealX(), waveCounter.getBottom()+25+10);
        goToMenuBtn.addPuppet();
    }
    public void gameOver(int level, boolean complete) //ADVENTURE
    {
        TextDisplay go = new TextDisplay(complete?"LEVEL CLEARED":"GAME OVER", 60, (!complete)?Color.RED:Color.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(!complete&&kill!=null){
            TextDisplay killer = new TextDisplay("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill), 20, Color.RED.darker());
            getWorld().addObject(killer, go.getRealX(), go.getBottom());
            scoreCounter.setRealLocation(scoreCounter.getRealX(), killer.getBottom());
        }else{
            scoreCounter.setRealLocation(scoreCounter.getRealX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        TextDisplay beatscore = new TextDisplay((!complete)?"You failed":"You Beat The Level", 20, Color.GREEN.darker());
        getWorld().addObject(beatscore, scoreCounter.getRealX(), scoreCounter.getBottom());
        hs = beatscore;
        Counter waveCounter;
        if(true){
            waveCounter = new Counter("Level: ", 20);
            waveCounter.setTarget(level);
            getWorld().addObject(waveCounter, scoreCounter.getRealX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getRealX(), waveCounter.getBottom()+25+10);
        goToMenuBtn.addPuppet();
        nextLvlBtn = new Button(140, 60, complete?"Next Level":"Retry", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().goToMenu();
                getWorld().startGame("adventure");
            }
        };
        getWorld().addObject(nextLvlBtn, scoreCounter.getRealX(), goToMenuBtn.getBottom()+100);
        nextLvlBtn.addPuppet();
    }
    
    public void gameOver(String thing, boolean complete) //TUTORIAL
    {
        TextDisplay go = new TextDisplay(complete?"TUTORIAL COMPLETE":"TUTORIAL FAILED", 60, (!complete)?Color.RED:Color.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(!complete&&kill!=null){
            TextDisplay killer = new TextDisplay("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill), 20, Color.RED.darker());
            getWorld().addObject(killer, go.getRealX(), go.getBottom());
            scoreCounter.setRealLocation(scoreCounter.getRealX(), killer.getBottom());
        }else{
            scoreCounter.setRealLocation(scoreCounter.getRealX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        TextDisplay beatscore = new TextDisplay((!complete)?"You Failed the Tutorial":"You Cleared the Tutorial", 20, Color.GREEN.darker());
        getWorld().addObject(beatscore, scoreCounter.getRealX(), scoreCounter.getBottom());
        hs = beatscore;
        TextDisplay waveCounter;
        if(true){
            waveCounter = new TextDisplay("Tutorial", 20, Color.GREEN);
            getWorld().addObject(waveCounter, scoreCounter.getRealX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getRealX(), waveCounter.getBottom()+25+10);
        goToMenuBtn.addPuppet();
        nextLvlBtn = new Button(140, 60, "Retry", Color.GREEN){
            public void click(){
                //example of click function
                getWorld().goToMenu();
                getWorld().startGame("tutorial");
            }
        };
        getWorld().addObject(nextLvlBtn, scoreCounter.getRealX(), goToMenuBtn.getBottom()+100);
        nextLvlBtn.addPuppet();
        nextLvlBtn.setActive(!complete);
    }
    
    

    public void increaseScore(int amt){
        scoreCounter.setTarget(amt);
    }

    public void setUltCharge(int amt){
        ultCharge.setValue(amt);
        if(ultCharge.isFull()){
            ultMessage.setVisible(true);
            ultMessage2.setVisible(true);
        }else{
            ultMessage.setVisible(false);
            ultMessage2.setVisible(false);
        }
    }

    public void newUltCharge(int max, int value){
        ultCharge.setVisible(true);
        ultCharge.setMax(max);
        setUltCharge(value);
    }
    public void disableUltCharge(){
        ultCharge.setVisible(false);
    }
    
    public void setAmmo(int amt){
        ammo.setValue(amt);
    }
    public void newAmmo(int max, int value){
        ammo.clearPhases();
        ammo.setVisible(true);
        ammo.setMax(max);
        setAmmo(value);
        ammo.reset();
    }
    public void newAmmo(int max, int value, int phases){
        newAmmo(max, value);
        ammo.divideIntoPhases(phases);
    }
    public void newAmmo(AmmoManager ammo){
        newAmmo(ammo.getMaxAmmoBar(), ammo.getAmmoBar(), ammo.getMaxAmmo());
    }
    public void disableAmmo(){
        ammo.setVisible(false);
    }
    
    public void newSprint(int max){
        sprint.setMax(max);
        setSprint(max);
    }
    public void startSprint(){
        sprint.setVisible(true);
        sprintText.setVisible(true);
    }
    public void stopSprint(){
        sprint.setVisible(false);
        sprintText.setVisible(false);
    }
    public void setSprint(int val){
        sprint.setValue(val);
    }
    
    public AmmoBar getAmmoBar(){
        return ammo;
    }
    
    public void changeHeldItem(String t){
        heldItem.setText(t);
    }
    public void addBossBar(BossBar bar){
        boss = bar;
        getWorld().addObject(bar, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, 25.0);
    }
    public void removeBossBar(){
        getWorld().removeObject(boss);
        boss = null;
    }
    public void showWeaponFrags(int s){
        weaponmessage3.setVisible(true);
        weaponmessage3.setText("You have "+s+" fragment"+(s==1?"":"s"));
        weaponmessage2.setVisible(true);
        weaponmessage2.setText(s>=2?"Press e to attempt a crafting":"You need at least 2 to craft");
        weaponmessage.setVisible(true);
        weaponmessage.setText("You found a weapon fragment!");
        weaponmessagerarity.setText("");
    }
    public void showWeaponFrags(Item i, int s){
        weaponmessage3.setVisible(true);
        weaponmessage3.setText("You have "+s+" fragment"+(s==1?"":"s"));
        weaponmessage2.setVisible(true);
        weaponmessage2.setText(i==null?"":("You got a "+i.getName()));
        weaponmessage.setVisible(true);
        weaponmessage.setText(i==null?"Crafting failed":"Crafting Succeeded");
        weaponmessagerarity.setVisible(true);
        String[] rarities = new String[]{"Common!", "Uncommon!", "Rare!", "Super Rare!", "Epic!", "Mythic!", "Legendary!", "Seasonal!"};
        Color[] colors = new Color[]{Color.GRAY, Color.GREEN, Color.BLUE, Color.ORANGE, new Color(255, 0, 255), Color.RED, Color.YELLOW, Color.PINK};
        if(i!=null){
            weaponmessagerarity.setText(rarities[i.getRarity()]);
            weaponmessagerarity.setColor(colors[i.getRarity()]);
        }
    }
    public void showWeaponFragsFailed(int s){
        weaponmessage3.setVisible(true);
        weaponmessage3.setText("You have "+s+" fragment"+(s==1?"":"s"));
        weaponmessage2.setVisible(true);
        weaponmessage2.setText("You need at least 2 fragments to craft!");
        weaponmessage.setVisible(true);
        weaponmessage.setText("");
        weaponmessagerarity.setText("");
    }
}

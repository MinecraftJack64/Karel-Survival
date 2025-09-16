package com.karel.game.ui;

import com.karel.game.IAmmoManager;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Game;
import com.karel.game.Item;
import com.karel.game.ZombieSpawner;
import com.karel.game.ui.bars.AmmoBar;
import com.karel.game.ui.bars.BossBar;
import com.karel.game.ui.bars.StatusBar;
import com.karel.game.ui.bars.UltBar;
import com.karel.game.ui.bars.WaveBar;
import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.text.Counter;
import com.karel.game.ui.text.ShyTextDisplay;
import com.karel.game.ui.text.TextDisplay;
import com.karel.game.ui.text.FadeInTextDisplay;
import com.karel.game.Player;
import com.raylib.Color;
import com.raylib.Raylib;

/**
 * Write a description of class GameUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameUI extends UI  
{
    public static final Color DARKRED = new Color((byte)255, (byte)0, (byte)0, (byte)255);
    public Counter scoreCounter, waveCounter;
    public WaveBar waveHealth;
    private UltBar ultCharge;
    private AmmoBar ammo;
    private StatusBar sprint;
    private TextDisplay heldItem, ultMessage, ultMessage2, weaponmessagerarity, weaponmessage, weaponmessage2, weaponmessage3, sprintText, autoaimText, autoaimStatusText, tut1, tut2, bossName;
    private Button goToMenuBtn, nextLvlBtn;
    private BossBar boss;
    public void create(){
        waveCounter = new Counter("Wave ", 20);
        scoreCounter = new Counter("Score: ", 20);
        waveHealth = new WaveBar(1,100,5);
        placeScoreBar();
        
        heldItem = new TextDisplay("Item", 20, Raylib.GRAY);
        getWorld().addObject(heldItem, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight-1)*1.0-15);
        
        ammo = new AmmoBar(0, 1, 120, 10);
        ammo.setVisible(false);
        getWorld().addObject(ammo, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight-2)*1.0+15);
        
        sprint = new StatusBar(0, 1, 120, 10, Raylib.MAGENTA);
        sprint.setVisible(false);
        getWorld().addObject(sprint, getWorld().gridXToRealX(getWorld().gridwidth)*0.8, ammo.getY());
        
        sprintText = new TextDisplay("SPRINT!", 20, Raylib.MAGENTA);
        sprintText.setVisible(false);
        getWorld().addObject(sprintText, sprint.getX(), sprint.getY()+30);

        autoaimText = new TextDisplay("AUTOAIM", 20, Raylib.ORANGE);
        autoaimText.setVisible(false);
        getWorld().addObject(autoaimText, getWorld().gridXToRealX(getWorld().gridwidth)*0.7, ammo.getY());

        autoaimStatusText = new TextDisplay("ON", 20, Raylib. ORANGE);
        autoaimStatusText.setVisible(false);
        getWorld().addObject(autoaimStatusText, autoaimText.getX(), ammo.getY()+30);
        
        
        ultCharge = new UltBar(100, 350, 30);
        ultMessage = new TextDisplay("Space", 20, Raylib.ORANGE);
        ultMessage2 = new TextDisplay("to fire!", 20, Raylib.ORANGE);
        placeUltBar();
        
        weaponmessagerarity = new ShyTextDisplay("", 20, Raylib.GRAY);
        weaponmessage = new ShyTextDisplay("You found a weapon fragment", 20, Raylib.GRAY);
        weaponmessage2 = new ShyTextDisplay("Press e to craft", 20, Raylib.GRAY);
        weaponmessage3 = new ShyTextDisplay("You have 0 fragments", 20, Raylib.GRAY);
        placeCraftMessage();
        tut1 = new TextDisplay("", 20, Raylib.GRAY);
        getWorld().addObject(tut1, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        tut2 = new TextDisplay("", 20, Raylib.GRAY);
        getWorld().addObject(tut2, tut1.getX(), tut1.getBottom()+10);
    }
    public void placeUltBar(){
        getWorld().addObject(ultCharge, 25, 400.0);
        getWorld().addObject(ultMessage, 35, 170.0);
        getWorld().addObject(ultMessage2, 35, ultMessage.getBottom());
        ultMessage.setVisible(false);
        ultMessage2.setVisible(false);
    }
    public void placeScoreBar(){
        getWorld().addObject(waveCounter, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(0)*1.0-15);
        waveCounter.setValue(1);
        getWorld().addObject(scoreCounter, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, waveCounter.getBottom());
        getWorld().addObject(waveHealth, waveCounter.getX(), scoreCounter.getBottom());
    }
    public void placeCraftMessage(){
        getWorld().addObject(weaponmessagerarity, getWorld().gridXToRealX(getWorld().gridwidth)/5.0, getWorld().gridYToRealY(getWorld().gridheight)-150);
        getWorld().addObject(weaponmessage, weaponmessagerarity.getX(), weaponmessagerarity.getBottom()+10);
        getWorld().addObject(weaponmessage2, weaponmessage.getX(), weaponmessage.getBottom()+10);
        getWorld().addObject(weaponmessage3, weaponmessage2.getX(), weaponmessage2.getBottom()+10);
    }
    public void setTutorial(String thing){
        tut1.setText(thing);
    }
    public void setTutorial2(String thing){
        tut2.setText(thing);
    }
    public void newWave(int health){
        if(health<=0)health = 1;
        waveHealth.setMax(health);
        waveHealth.setValue(health);
    }
    public void updateWaveHealth(int n){
        waveHealth.setValue(n);
    }
    /**
     * This method is called when the game is over to display the final score.
     */
    public void gameOver(boolean beaths) //SURVIVAL+OTHER MODES
    {
        menuBG();
        TextDisplay go = new TextDisplay("GAME OVER", 60, Raylib.RED);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(kill!=null){
            TextDisplay killer = new FadeInTextDisplay(kill instanceof Player?"You stepped out of bounds":("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill)), 20, DARKRED);
            getWorld().addObject(killer, go.getX(), go.getBottom());
            scoreCounter.setLocation(scoreCounter.getX(), killer.getBottom());
        }else{
            scoreCounter.setLocation(scoreCounter.getX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        if(!beaths){
            hs = new Counter("High Score: ", 20);
            ((Counter)hs).setTarget(Game.getHighScore());
            ((Counter)hs).setColor(Raylib.DARKGREEN);
            getWorld().addObject(hs, scoreCounter.getX(), scoreCounter.getBottom());
        }else{
            TextDisplay beatscore = new TextDisplay("You Beat Your High Score!", 20, Raylib.DARKGREEN);
            getWorld().addObject(beatscore, scoreCounter.getX(), scoreCounter.getBottom());
            hs = beatscore;
        }
        Counter waveCounter;
        if(Game.currentMode().equals("survival")||Game.currentMode().equals("protect")){
            waveCounter = new Counter("Waves Survived: ", 20);
            waveCounter.setTarget(((ZombieSpawner)(Game.getGame().getSpawner())).wavelevel-1);
            getWorld().addObject(waveCounter, scoreCounter.getX(), hs.getBottom());
        }else{
            waveCounter = new Counter("Level: ", 20);
            waveCounter.setTarget(0);
            getWorld().addObject(waveCounter, scoreCounter.getX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getX(), waveCounter.getBottom()+25+10);
    }
    public void gameOver(int level, boolean complete) //ADVENTURE
    {
        menuBG();
        TextDisplay go = new TextDisplay(complete?"LEVEL CLEARED":"GAME OVER", 60, (!complete)?Raylib.RED:Raylib.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(!complete&&kill!=null){
            TextDisplay killer = new TextDisplay(kill instanceof Player?"You stepped out of bounds":("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill)), 20, DARKRED);
            getWorld().addObject(killer, go.getX(), go.getBottom());
            scoreCounter.setLocation(scoreCounter.getX(), killer.getBottom());
        }else{
            scoreCounter.setLocation(scoreCounter.getX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        TextDisplay beatscore = new TextDisplay((!complete)?"You failed":"You Beat The Level", 20, Raylib.DARKGREEN);
        getWorld().addObject(beatscore, scoreCounter.getX(), scoreCounter.getBottom());
        hs = beatscore;
        Counter waveCounter;
        if(true){
            waveCounter = new Counter("Level: ", 20);
            waveCounter.setTarget(level);
            getWorld().addObject(waveCounter, scoreCounter.getX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getX(), waveCounter.getBottom()+25+10);
        nextLvlBtn = new Button(140, 60, complete?"Next Level":"Retry", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
                Game.startGame("adventure");
            }
        };
        getWorld().addObject(nextLvlBtn, scoreCounter.getX(), goToMenuBtn.getBottom()+100);
    }
    
    public void gameOver(String thing, boolean complete) //TUTORIAL
    {
        menuBG();
        TextDisplay go = new TextDisplay(complete?"TUTORIAL COMPLETE":"TUTORIAL FAILED", 60, (!complete)?Raylib.RED:Raylib.GREEN);
        getWorld().addObject(go, getWorld().gridXToRealX(getWorld().gridwidth)/2.0, getWorld().gridYToRealY(getWorld().gridheight)/2.0);
        GridObject kill = getWorld().getPlayer().getKiller();
        if(!complete&&kill!=null){
            TextDisplay killer = new TextDisplay(kill instanceof Player?"You stepped out of bounds":("You were killed by a "+(kill instanceof GridEntity?((GridEntity)kill).getName():kill)), 20, DARKRED);
            getWorld().addObject(killer, go.getX(), go.getBottom());
            scoreCounter.setLocation(scoreCounter.getX(), killer.getBottom());
        }else{
            scoreCounter.setLocation(scoreCounter.getX(), go.getBottom());
        }
        getWorld().removeObject(waveCounter);
        Overlay hs;
        TextDisplay beatscore = new TextDisplay((!complete)?"You Failed the Tutorial":"You Cleared the Tutorial", 20, Raylib.DARKGREEN);
        getWorld().addObject(beatscore, scoreCounter.getX(), scoreCounter.getBottom());
        hs = beatscore;
        TextDisplay waveCounter;
        if(true){
            waveCounter = new TextDisplay("Tutorial", 20, Raylib.GREEN);
            getWorld().addObject(waveCounter, scoreCounter.getX(), hs.getBottom());
        }
        goToMenuBtn = new Button(140, 60, "Menu", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
            }
        };
        getWorld().addObject(goToMenuBtn, scoreCounter.getX(), waveCounter.getBottom()+25+10);
        nextLvlBtn = new Button(140, 60, "Retry", Raylib.GREEN){
            public void click(){
                //example of click function
                Game.goToMenu();
                Game.startGame("tutorial");
            }
        };
        getWorld().addObject(nextLvlBtn, scoreCounter.getX(), goToMenuBtn.getBottom()+100);
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
    public void newAmmo(IAmmoManager ammo){
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
    public void startAutoAim(){
        autoaimText.setVisible(true);
        autoaimStatusText.setVisible(true);
        autoaimStatusText.setText("ON");
    }
    public void failAutoAim(){
        autoaimText.setVisible(true);
        autoaimStatusText.setVisible(true);
        autoaimStatusText.setText("DISABLED");
    }
    public void loadingAutoAim(){
        autoaimText.setVisible(true);
        autoaimStatusText.setVisible(true);
        autoaimStatusText.setText("FINDING...");
    }
    public void stopAutoAim(){
        autoaimText.setVisible(false);
        autoaimStatusText.setVisible(false);
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
        System.out.println("Adding boss bar "+bar);
        boss = bar;
        waveCounter.setVisible(false);
        waveHealth.setVisible(false);
        scoreCounter.setLocation(waveCounter.getX(), waveCounter.getY());
        bossName = new TextDisplay(bar.getLabel(), 30);
        getWorld().addObject(bossName, waveCounter.getX(), scoreCounter.getBottom());
        getWorld().addObject(bar, waveCounter.getX(), bossName.getBottom());
    }
    public void removeBossBar(){
        System.out.println("Removing boss bar "+boss);
        getWorld().removeObject(boss);
        getWorld().removeObject(bossName);
        waveCounter.setVisible(true);
        waveHealth.setVisible(true);
        placeScoreBar();
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
        Color[] colors = new Color[]{Raylib.GRAY, Raylib.GREEN, Raylib.BLUE, Raylib.ORANGE, new Color((byte)255, (byte)0, (byte)255, (byte)1), Raylib.RED, Raylib.YELLOW, Raylib.PINK};
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

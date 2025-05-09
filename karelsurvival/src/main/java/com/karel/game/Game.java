package com.karel.game;
import java.util.ArrayList;

import com.karel.game.ui.Overlay;
import com.karel.game.ui.bars.StatusBar;

import java.awt.event.*;
/*
 * Welcome to Karel Adventure
 * Move using the wasd or arrow keys
 * Aim with your mouse and click to shoot
 * A zombie is approaching. Shoot at it and don't let it get to you
 * More zombies are coming
 * This zombie(These zombies) seem(s) to be tougher
 * You aren't the only one trying to shoot. Try to dodge this zombie's shots. They'll be more vulnerable when you take care of other zombies first
 * I wonder what these zombies do(exploders)
 * (if damaged by explosions) Uh oh. That zombie badly hurt you, heal up by picking up the beepers on the ground
 * (if not damaged by explosions) Wow that was close. If you are ever hurt, picking up beepers will heal you
 * ...(shield boss)
 * ...(orbital striker)
 * What was that sound?(spray of fungal zombie)
 * Hopefully no more zombies will try to sneak up on you(ninja)
 */

/**
 * The main Karel World, the grid all objects are in. Also manages everything, like the flow of gamemodes
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public class Game
{
    public static World world;
    
    //these store inputs, updated every frame
    public static int lastX = 0, lastY = 0; // last recorded mouse coordinates
    public static boolean lastClicked = false; // whether or not the mouse is down
    
    public static String currentMenu = "mainmenu";
    public static UI ui; // main game ui
    public static UI ui2; // overlay ui, usually pause menu
    
    //mode information, used for mode select
    private static String[] modenames;
    private static String[] modeids;
    private static String[] diffnames;
    private static String[] modeDescriptions;
    private static String[] difficultyDescriptions;
    private static String[] tips;
    private static int cmode; // currently selected mode
    private static int cdiff = 1; // currently selected difficulty
    
    private static int modehs[]; // high scores of each mode
    
    static String gameMode; // current game mode
    static int gameDiff; // current game difficulty
    private static GameMode game; // current game
    private static boolean currentlypausing = false;
    
    private static boolean autoattack = false;
    private static boolean currentlyTogglingAutoattack = false;
    
    private static boolean autoult = false;
    private static boolean currentlyTogglingAutoult = false;
    private static boolean currentlycrafting = false;
    public Game() 
    {
        //setAvailableModes
        setOptions();
        startGame("tutorial");
    }
    public void startGame(String mode){
        //clear old UI
        removeUI();
        //Create the Game UI
        ui = new GameUI();
        initGameUI();
        ui2 = new PauseUI();
        initPauseUI();
        paused = false;
        gameDiff = cdiff;
        //Create the player and initial enemies
        if(mode.equals("protect")){
            game = new Protect();
        }else if(mode.equals("survival")){
            game = new Survival();
        }else if(mode.equals("adventure")){
            game = new Adventure();
        }else if(mode.equals("tutorial")){
            game = new Tutorial();
        }else if(mode.equals("sandbox")){
            //
        }else if(mode.equals("journal")){
            //
        }
        game.setUI((GameUI)ui);
        world = new World();
        game.startGame();
        currentMenu = "game";
        gameMode = mode;
    }
    public void nextMode(){
        cmode++;
        if(cmode>=modeids.length){
            cmode = 0;
        }
    }
    public void nextDiff(){
        cdiff++;
        if(cdiff>=diffnames.length){
            cdiff = 0;
        }
    }
    public String selectedMode(){
        return modeids[cmode];
    }
    public int selectedDiff(){
        return cdiff;
    }
    public String currentMode(){
        return gameMode;
    }
    public int currentDiff(){
        return gameDiff;
    }
    public String selectedModeName(){
        return modenames[cmode];
    }
    public String selectedDiffName(){
        return diffnames[cdiff];
    }
    public String getDifficultyDescription(){
        return difficultyDescriptions[cdiff];
    }
    public String getModeDescription(){
        return modeDescriptions[cmode];
    }
    public String getTip(){
        return "Tip: "+tips[Greenfoot.getRandomNumber(tips.length)]+"!";
    }
    public void setOptions(){
        modenames = new String[]{"Adventure", "Survival", "Protect"};
        modeids = new String[]{"adventure", "survival", "protect"};
        modehs = new int[]{0, 0, 0};
        diffnames = new String[]{"Peaceful", "Easy", "Normal", "Hard", "Impossible"};
        difficultyDescriptions = new String[]{"Do not worry about dying, just kill zombies!", "Weaker zombies and more health", "Nice and basic", "Tougher zombies and lower health", "One hit and you die!"};
        modeDescriptions = new String[]{"Progress through challenging worlds and discover the story!", "Endless waves of zombies! Survive as long as you can!", "Protecting your turret is your only goal!"};
        tips = new String[]{"Allow your Blade wielding teammates the kill", "Try Impossible difficulty >:)", "Stay away from Fungal Zombies, they do a ton of damage", "Press the alt key to sprint"};
        //difficulties: peaceful: immunity shield, easy: 5000, normal: 2500, hard: 750, impossible: 1
    }
    public void removeUI(){
        for(KActor k: getObjects(KActor.class)){
            removeObject(k);
        }
    }
    public void initGameUI(){
        //Create the UI
        ui.create();
    }
    public void initPauseUI(){
        //Create the UI
        ui2.create();
    }
    
    /**
     * This method is called when the game is over to display the final score.
     */
    
    public Teams getTeams(){
        return game.getTeams();
    }
    public GameMode getGame(){
        return game;
    }
    public Player getPlayer(){
        return game.getPlayer();
    }
    public String gameStatus(){
        if(game==null){
            return "";
        }
        return game.getStatus();
    }

    public void increaseScore(int amt){
        game.increaseScore(amt);
    }
    public int getHighScore(){
        return game.getHighScore();
    }

    public void setUltCharge(int amt){
        if(gameUI()!=null)gameUI().setUltCharge(amt);
    }

    public void newUltCharge(int max, int value){
        if(gameUI()!=null)gameUI().newUltCharge(max, value);
    }
    public void disableUltCharge(){
        if(gameUI()!=null)gameUI().disableUltCharge();
    }
    
    public void changeHeldItem(String t){
        if(gameUI()!=null)gameUI().changeHeldItem(t);
    }
    public GameUI gameUI(){
        return ui instanceof GameUI?(GameUI)ui:null;
    }
    public PauseUI pauseUI(){
        return ui2 instanceof PauseUI?(PauseUI)ui2:null;
    }

    public void act(){
        if(Greenfoot.getMouseInfo()!=null){
            lastX = Greenfoot.getMouseInfo().getX();
            lastY = Greenfoot.getMouseInfo().getY();
        }
        if(Greenfoot.mousePressed(null)){
            lastClicked = true;
        }
        if(Greenfoot.mouseClicked(null)){
            lastClicked = false;
        }
        shiftkey = Greenfoot.isKeyDown("shift");
        scroll+=scrollsensor.getScroll();
        boolean ocp = currentlypausing;
        currentlypausing = Greenfoot.isKeyDown("escape");
        if(ocp&&!currentlypausing&&currentMenu.equals("game")&&gameStatus().equals("running")){
            togglePause();
        }
        boolean otaa = currentlyTogglingAutoattack;
        currentlyTogglingAutoattack = Greenfoot.isKeyDown("control");
        if(otaa&&!currentlyTogglingAutoattack&&currentMenu.equals("game")&&gameStatus().equals("running")){
            toggleAutoattack();
        }
        boolean otau = currentlyTogglingAutoult;
        currentlyTogglingAutoult = Greenfoot.isKeyDown("u");
        if(otau&&!currentlyTogglingAutoult&&currentMenu.equals("game")&&gameStatus().equals("running")){
            toggleAutoult();
        }
        if(currentMenu.equals("game")&&gameStatus().equals("running")){
            game.tick();
            for(int i = allEntities().size()-1; i >=0; i--){
                if(i>=allEntities().size())continue;
                GridEntity g = allEntities().get(i);
                if(g.getRealX()<xLowerBound||g.getRealX()>xUpperBound||g.getRealX()>yUpperBound||g.getRealY()<yLowerBound)
                    g.hitIgnoreShield((int)Math.ceil(g.getMaxHealth()/300.0), null);
                
            }
            if(scrollToPlayer){
                scrollX = getGame().getPlayer().getRealX()-600;
                scrollY = getGame().getPlayer().getRealY()-400;
            }
            if(Greenfoot.isKeyDown("e")&&!currentlycrafting){
                game.craftWeapon();
            }
            currentlycrafting = Greenfoot.isKeyDown("e");
        }
    }
    public int getMouseX(){
        return lastX;
    }
    public int getMouseY(){
        return lastY;
    }
    public boolean isMouseDown(){
        return lastClicked;
    }
    public boolean isShiftDown(){
        return shiftkey;
    }
    public void goToMenu(){
        if(game!=null){
            game.stopGame();
            game = null;
            paused = false;
        }
        ui2 = null;
        resetBG();
        ui = new MainMenuUI();
        ui.create();
        currentMenu = "mainmenu";
    }
    public void togglePause(){
        if(isPaused()){
            paused = false;
            pauseUI().hidePauseMenu();
        }else{
            paused = true;
            pauseUI().showPauseMenu();
        }
    }
    public void toggleAutoattack(){
        autoattack = !autoattack;
    }
    public void toggleAutoult(){
        autoult = !autoult;
    }
    public boolean autoAttack(){
        return autoattack;
    }
    public boolean autoUlt(){
        return autoult;
    }
    public ArrayList<GridEntity> allEntities(){
        return allEntities;
    }
    public ArrayList<GridObject> allObjects(){
        return allGridObjects;
    }
    public void cleanUpEntities(){
        for(int i = allEntities.size()-1; i >= 0; i--){
            try{GridEntity obj = allEntities.get(i);
            if(obj.isDead()&&obj.removeOnDeath()||!obj.isInWorld()){
                if(obj.isInWorld()){
                    removeObject(obj);
                }
                allEntities.remove(i);
            }}catch(Exception e){}
        }
    }
    public boolean isPaused(){
        return paused;
    }
    
    public void addObject(KActor a, int x, int y){
        addObject(a, x*1.0, y*1.0);
    }
    public void addObject(KActor a, double x, double y){
        if(a.isInWorld()){
            a.setRealLocation(x, y);
            return;
        }
        a.rx = x;
        a.ry = y;
        if(a.isInGridWorld()){
            x-=getScrollX();
            y-=getScrollY();
        }
        super.addObject(a, (int)x, (int)y);
        a.notifyWorldAdd();
    }
    public void removeObject(KActor a){
        // super.removeObject(a);
        if(a instanceof KActor){
            ((KActor)a).notifyWorldRemove();
        }
    }
}
class ScrollingListener implements MouseWheelListener
{
    int scroll = 0;
     
    public void mouseWheelMoved(MouseWheelEvent MWE)
    {
        scroll+=MWE.getWheelRotation();  
        MWE.consume();
        System.out.println("mouse scrolled");
    }
     
    public int getScroll()
    {
        int a=scroll;
        scroll=0;
        return a;
    }
}
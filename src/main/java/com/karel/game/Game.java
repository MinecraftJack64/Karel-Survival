package com.karel.game;

import com.karel.game.gamemodes.GameMode;
import com.karel.game.gamemodes.adventure.Adventure;
import com.karel.game.gamemodes.protect.Protect;
import com.karel.game.gamemodes.sandbox.Sandbox;
import com.karel.game.gamemodes.survival.Survival;
import com.karel.game.ui.GameUI;
import com.karel.game.ui.MainMenuUI;
import com.karel.game.ui.PauseUI;
import com.karel.game.ui.JournalSelectUI;
import com.karel.game.ui.UI;
import com.karel.game.ui.joysticks.Joystick;

import static com.raylib.Raylib.getFrameTime;
import static com.raylib.Raylib.getScreenHeight;
import static com.raylib.Raylib.getScreenWidth;
import com.raylib.Raylib;
import com.raylib.Sound;
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
    public static final int FPS = 48; // frames per second
    public static float timeScale = 1;
    public static World world;
    
    //these store inputs, updated every frame
    public static int lastX = 0, lastY = 0; // last recorded mouse coordinates
    public static boolean lastClicked = false; // whether or not the mouse is down
    
    public static String currentMenu = "mainmenu";
    private static String nextMenu = "";
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
    
    //TODO: private static int modehs[]; // high scores of each mode

    public static String inputMethod = "touch";
    public static Vector targetVector = new Vector(0, 60);
    public static Vector moveVector = new Vector(0, 0);
    public static boolean shiftkey;
    public static boolean disableGameInputFlag = false;
    
    static String gameMode; // current game mode
    static int gameDiff; // current game difficulty
    private static GameMode game; // current game
    private static boolean currentlypausing = false;
    public static boolean pauseFlag = false;
    
    private static boolean autoattack = false;
    private static boolean currentlyTogglingAutoattack = false;
    
    private static boolean autoult = false;
    private static boolean currentlyTogglingAutoult = false;

    private static boolean soundOn = false;
    //TODO: private static boolean currentlycrafting = false;
    static 
    {
        //setAvailableModes
        setOptions();
        startGame("tutorial");
    }
    public static void startGame(String mode){
        world = new World();
        world.resetBG();
        //Create the Game UI
        ui = new GameUI();
        initGameUI();
        ui2 = new PauseUI();
        initPauseUI();
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
            game = new Sandbox();
        }else if(mode.equals("journal")){
            //
        }
        game.setUI((GameUI)ui);
        game.startGame();
        nextMenu = "game";
        gameMode = mode;
    }
    public static void nextMode(){
        cmode++;
        if(cmode>=modeids.length){
            cmode = 0;
        }
    }
    public static void nextDiff(){
        cdiff++;
        if(cdiff>=diffnames.length){
            cdiff = 0;
        }
    }
    public static String selectedMode(){
        return modeids[cmode];
    }
    public static int selectedDiff(){
        return cdiff;
    }
    public static String currentMode(){
        return gameMode;
    }
    public static int currentDiff(){
        return gameDiff;
    }
    public static String selectedModeName(){
        return modenames[cmode];
    }
    public static String selectedDiffName(){
        return diffnames[cdiff];
    }
    public static String getDifficultyDescription(){
        return difficultyDescriptions[cdiff];
    }
    public static String getModeDescription(){
        return modeDescriptions[cmode];
    }
    public static String getTip(){
        return "Tip: "+tips[Greenfoot.getRandomNumber(tips.length)]+"!";
    }
    public static void setOptions(){
        modenames = new String[]{"Adventure", "Survival", "Protect", "Sandbox"};
        modeids = new String[]{"adventure", "survival", "protect", "sandbox"};
        //TODO: modehs = new int[]{0, 0, 0, 0};
        diffnames = new String[]{"Peaceful", "Easy", "Normal", "Hard", "Impossible"};
        difficultyDescriptions = new String[]{"Do not worry about dying, just kill zombies!", "Weaker zombies and more health", "Nice and basic", "Tougher zombies and lower health", "One hit and you die!"};
        modeDescriptions = new String[]{"Progress through challenging worlds and discover the story!", "Endless waves of zombies! Survive as long as you can!", "Protecting your turret is your only goal!", "A freeform mode to test out different zombies and weapons!"};
        tips = new String[]{"Allow your Blade wielding teammates the kill", "Try Impossible difficulty >:)", "Stay away from Fungal Zombies, they do a ton of damage", "Press the alt key to sprint"};
        //difficulties: peaceful: immunity shield, easy: 5000, normal: 2500, hard: 750, impossible: 1
    }
    public static void initGameUI(){
        //Create the UI
        ui.create();
    }
    public static void initPauseUI(){
        //Create the UI
        ui2.create();
    }
    
    /**
     * This method is called when the game is over to display the final score.
     */
    
    public static Teams getTeams(){
        return game.getTeams();
    }
    public static GameMode getGame(){
        return game;
    }
    public static Player getPlayer(){
        return game.getPlayer();
    }
    public static String gameStatus(){
        if(game==null){
            return "";
        }
        return game.getStatus();
    }

    public static void increaseScore(int amt){
        game.increaseScore(amt);
    }
    public static int getHighScore(){
        return game.getHighScore();
    }

    public static void setUltCharge(int amt){
        if(gameUI()!=null)gameUI().setUltCharge(amt);
    }

    public static void newUltCharge(int max, int value){
        if(gameUI()!=null)gameUI().newUltCharge(max, value);
    }
    public static void disableUltCharge(){
        if(gameUI()!=null)gameUI().disableUltCharge();
    }
    
    public static void changeHeldItem(String t){
        if(gameUI()!=null)gameUI().changeHeldItem(t);
    }
    public static GameUI gameUI(){
        return ui instanceof GameUI?(GameUI)ui:null;
    }
    public static PauseUI pauseUI(){
        return ui2 instanceof PauseUI?(PauseUI)ui2:null;
    }

    public static void tick(){
        timeScale = getFrameTime()/((float)FPS);
        lastX = Greenfoot.getMouseX();
        lastY = Greenfoot.getMouseY();
        lastClicked = Greenfoot.isActive("attack")&&!disableGameInputFlag;
        shiftkey = Greenfoot.isActive("autoaim");
        boolean targetLeft = Greenfoot.isActive("targetleft");
        boolean targetRight = Greenfoot.isActive("targetright");
        boolean targetForward = Greenfoot.isActive("targetforward");
        boolean targetBack = Greenfoot.isActive("targetback");
        int vlr = 7, vfb = 5;
        if(targetLeft){
            targetVector.setDirection(targetVector.getDirection()-vlr);
        }else if(targetRight){
            targetVector.setDirection(targetVector.getDirection()+vlr);
        }
        if(targetBack){
            targetVector.setLength(Math.max(0, targetVector.getLength()-vfb));
        }else if(targetForward){
            targetVector.setLength(Math.min(600, targetVector.getLength()+vfb));
        }
        boolean ocp = currentlypausing;
        currentlypausing = Greenfoot.isActive("pause")||pauseFlag;
        pauseFlag = false;
        if(ocp&&!currentlypausing&&currentMenu.equals("game")&&gameStatus().equals("running")){
            togglePause();
        }
        boolean otaa = currentlyTogglingAutoattack;
        currentlyTogglingAutoattack = Greenfoot.isActive("autoattack");
        if(otaa&&!currentlyTogglingAutoattack&&currentMenu.equals("game")&&gameStatus().equals("running")){
            toggleAutoattack();
        }
        boolean otau = currentlyTogglingAutoult;
        currentlyTogglingAutoult = Greenfoot.isActive("autoult");
        if(otau&&!currentlyTogglingAutoult&&currentMenu.equals("game")&&gameStatus().equals("running")){
            toggleAutoult();
        }
        if(currentMenu.equals("game")){
            disableGameInputFlag = false;
            if(gameStatus().equals("running")||game.usesCustomPause())game.tick();
            world.update();
            ui.update();
            if(isPaused()||game!=null&&(isPaused()&&!game.usesCustomPause()||game.showPauseMenu()))ui2.update();
            setUIScreenScaleAndOffset(world);
            world.render();
            //if(!gameStatus().equals("running"))com.raylib.Raylib.drawRectangle(0, 0, com.raylib.Raylib.getScreenWidth(), com.raylib.Raylib.getScreenHeight(), new com.raylib.Color((byte)-1, (byte)-1, (byte)-1, (byte)100));
            setUIScreenScaleAndOffset(ui);
            ui.render();
            setUIScreenScaleAndOffset(ui2);
            if(isPaused()||game!=null&&(isPaused()&&!game.usesCustomPause()||game.showPauseMenu()))ui2.render();
        }else{
            if(ui!=null){
                setUIScreenScaleAndOffset(ui);
                ui.update();
                ui.render();
            }
        }
        if(!nextMenu.equals("")&&!nextMenu.equals(currentMenu)){
            currentMenu = nextMenu;
            nextMenu = "";
        }
    }
    public static void setUIScreenScaleAndOffset(World ui){
        if(ui!=null)ui.setScreenScaleAndOffset(getScreenWidth(), getScreenHeight());
    }
    public static int getMouseX(){
        return lastX;
    }
    public static int getMouseY(){
        return lastY;
    }
    public static boolean isAttackDown(){
        return lastClicked;
    }
    public static boolean isMouseDown(){
        return Greenfoot.isMouseDown();
    }
    public static boolean isShiftDown(){
        return shiftkey;
    }
    public static String getInputMethod(){
        return inputMethod;
    }
    public static void playSound(Sound sound){
        if(soundOn)Raylib.playSound(sound);
    }
    public static void goToMenu(){
        if(game!=null){
            game.stopGame();
            game = null;
        }
        ui = new MainMenuUI();
        ui.create();
        nextMenu = "mainmenu";
    }
    public static void openJournal(){
        ui = new JournalSelectUI();
        ui.create();
        nextMenu = "journalhome";
    }
    public static void togglePause(){
        if(game.usesCustomPause())game.togglePause();
        else{
            if(isPaused()){
                pauseUI().hidePauseMenu();
            }else{
                pauseUI().showPauseMenu();
            }
            world.togglePause();
        }
    }
    public static void toggleAutoattack(){
        autoattack = !autoattack;
    }
    public static void toggleAutoult(){
        autoult = !autoult;
    }
    public static boolean autoAttack(){
        return autoattack;
    }
    public static boolean autoUlt(){
        return autoult;
    }
    public static boolean isAutoAimAllowed(){
        return cdiff<=1||game instanceof Sandbox;
    }
    public static boolean isPaused(){
        return world.isPaused();
    }
    public static void setTargetVector(Joystick attack) {
        targetVector.setDirection(attack.getAngle());
        targetVector.setLength(attack.getPercentDistance()*600);
    }
    public static void setMovementVector(Joystick attack) {
        moveVector.setDirection(attack.getAngle());
        moveVector.setLength(attack.getPercentDistance());
    }
}
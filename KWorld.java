import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.util.ArrayList;
import greenfoot.core.WorldHandler;
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
 * Space. Something for rockets to fly in.
 * 
 * @author Michael Kolling
 * @version 1.0
 */
public class KWorld extends World
{
    private int startAsteroids = 1;//TMP
    public static KWorld me;
    ScrollingListener scrollsensor = new ScrollingListener();
    public int scroll = 0;
    public Grid<Actor> grid = new Grid<Actor>();
    public ArrayList<GridEntity> allEntities = new ArrayList<GridEntity>();
    public ArrayList<GridObject> allGridObjects = new ArrayList<GridObject>();
    public int lastX = 0, lastY = 0;
    //public ZombieSpawner spawner;
    public boolean lastClicked = false;
    private boolean shiftkey = false;
    //public Player player;
    //public Baby baby;
    public int gridheight = 16;
    public int gridwidth = 24;
    private double gravity = -5;
    private int score = 0;
    //public String gameStatus;
    public String currentMenu = "mainmenu";
    public UI ui;
    public UI ui2;
    //private Teams teams;
    private String[] modenames;
    private String[] modeids;
    private int modehs[];
    private String[] diffnames;
    String gameMode;
    int gameDiff;
    private int cmode;
    private int cdiff = 1;
    private int respawncooldown;
    private GameMode game;
    private boolean paused = false;
    private boolean currentlypausing = false;
    private boolean currentlycrafting = false;
    public KWorld() 
    {
        super(1200, 800, 1, false);
        //add the listener for scrolling
        //WorldHandler.getInstance().getWorldCanvas().addMouseWheelListener(scrollsensor);
        //Initialize background
        me = this;
        resetBG();
        /*for(int i = 0; i < 24; i++){   
        for(int j = 0; j < 16; j++){   
        if((j+i)%4==0){
        Zombie rocket = new Zombie();
        addToGrid(rocket, i, j);
        }else{
        Rocket rocket = new Rocket();
        addToGrid(rocket, i, j);
        }
        }
        }*/
        //setAvailableModes
        setOptions();
        //Set paint order for the KActors
        setPaintOrder(Overlay.class, Projectile.class, Player.class, Zombie.class, Collectible.class, Target.class);
        startGame("tutorial");
        //Explosion.initializeImages();
        //ProtonWave.initializeImages();
        //background.logicalBottomEdge();
        grid.print();
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
    public void setOptions(){
        modenames = new String[]{"Adventure", "Survival", "Protect"};
        modeids = new String[]{"adventure", "survival", "protect"};
        modehs = new int[]{0, 0, 0};
        diffnames = new String[]{"Peaceful", "Easy", "Normal", "Hard", "Impossible"};
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

    public void addToGrid(GridObject a, int x, int y){
        grid.place(a, x, y);
        addObject(a, gridXToRealX(x), gridYToRealY(y));
    }

    public double gridXToRealX(int x){
        return 25+x*50;
    }

    public double gridYToRealY(int y){
        return 25+y*50;
    }

    public int realXToGridX(double x){
        return Math.round((int)(x-25)/50);
    }

    public int realYToGridY(double y){
        return Math.round((int)(y-25)/50);
    }

    public double getGravity(){
        return gravity;
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
    
    public void bossBG(){
        //change BG to boss mode
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        background.setColor(Color.WHITE);
        for(int i = 1; i < gridwidth; i++){
            background.drawLine(i*50, 0, i*50, getHeight());
        }
        for(int j = 1; j < gridheight; j++){
            background.drawLine(0, j*50, getWidth(), j*50);
        }
    }
    public void resetBG(){
        GreenfootImage background = getBackground();
        background.setColor(Color.WHITE);
        background.fill();
        background.setColor(Color.BLACK);
        for(int i = 1; i < gridwidth; i++){
            background.drawLine(i*50, 0, i*50, getHeight());
        }
        for(int j = 1; j < gridheight; j++){
            background.drawLine(0, j*50, getWidth(), j*50);
        }
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
        if(currentMenu.equals("game")&&gameStatus().equals("running")){
            game.tick();
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
    public ArrayList<GridEntity> allEntities(){
        return allEntities;
    }
    public boolean isPaused(){
        return paused;
    }

    public void addObject(Actor a, double x, double y){
        super.addObject(a, (int)x, (int)y);
        if(a instanceof KActor){
            KActor g = (KActor)a;
            g.rx = x;
            g.ry = y;
            if(a instanceof GridEntity){
                allEntities.add((GridEntity)a);
            }
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
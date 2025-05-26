package com.karel.game;
import java.util.ArrayList;

import com.karel.game.ui.Overlay;
import com.karel.game.ui.bars.StatusBar;

import java.awt.event.*;

import com.raylib.Color;
import com.raylib.Raylib;
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
public class World
{
    public Grid<KActor> grid = new Grid<KActor>(); // grid for pathfinding(unused currently)
    private ArrayList<KActor> allKActors = new ArrayList<KActor>();
    public ArrayList<GridEntity> allEntities = new ArrayList<GridEntity>(); // a list of all grid entities in the world
    public ArrayList<GridObject> allGridObjects = new ArrayList<GridObject>(); // a list of all grid objects in the world excluding grid entities
    
    //scrolling
    public double scrollX = 0, scrollY = 0;
    private boolean scrollToPlayer = false;
    
    //these are constant default values, to be deprecated
    public int gridheight = 16;
    public int gridwidth = 24;
    public static final int gridSize = 50; // size of each grid square in pixels
    public double gridSizeScale = 1.0; // scale to screen size
    public int gridXOffset = 0;
    public int xLowerBound = -500, yLowerBound = -500;
    public int xUpperBound = gridSize * gridwidth+500, yUpperBound = gridSize * gridheight+500;
    public String bg = ""; // background image, can be "grid" or "gridBoss"
    private final double gravity = -5;
    
    private boolean paused = false;
    public World() 
    {
    }

    public void addToGrid(GridObject a, int x, int y){
        grid.place(a, x, y);
        addObject(a, gridXToRealX(x), gridYToRealY(y));
    }
    
    public void addToGrid(KActor a, int x, int y){
        addObject(a, gridXToRealX(x), gridYToRealY(y));
    }

    public double gridXToRealX(int x){
        return 25+x*gridSize;
    }

    public double gridYToRealY(int y){
        return 25+y*gridSize;
    }

    public int realXToGridX(double x){
        return Math.round((int)(x-25)/gridSize);
    }

    public int realYToGridY(double y){
        return Math.round((int)(y-25)/gridSize);
    }
    public double getScreenScale(){
        return gridSizeScale;
    }
    public double getScreenOffsetX(){
        return gridXOffset;
    }
    public void setScreenScaleAndOffset(int width, int height){
        //set the screen scale and offset based on the window size
        gridSizeScale = Math.min((double)width/(gridwidth*gridSize), (double)height/(gridheight*gridSize));
        gridXOffset = (int)(width/2.0 - (gridwidth * gridSize * gridSizeScale) / 2.0);
    }
    
    public double getScrollX(){
        return scrollX;
    }
    
    public double getScrollY(){
        return scrollY;
    }

    public double getGravity(){
        return gravity;
    }

    public int getGridMouseX(){
        return (int)((Game.getMouseX()-gridXOffset)/gridSizeScale);
    }
    public int getGridMouseY(){
        return (int)(Game.getMouseY()/gridSizeScale);
    }
    public int getMouseX(){
        return (int)((Game.getMouseX()-gridXOffset)/gridSizeScale);
    }
    public int getMouseY(){
        return (int)(Game.getMouseY()/gridSizeScale);
    }

    /**
     * This method is called when the game is over to display the final score.
     */
    
    public Teams getTeams(){
        return Game.getTeams();
    }
    public Player getPlayer(){
        return Game.getPlayer();
    }
    public String gameStatus(){
        return Game.gameStatus();
    }
    
    public void bossBG(){// TODO: replace with raylib draw
        bg = "gridBoss";
    }
    public void resetBG(){
        bg = "grid";
    }
    public void menuBG(){
        bg = "menu";
    }
    public void drawBG(){
        if(bg.equals("grid")){
            for(int i = 1; i < gridwidth; i++){
                Raylib.drawLine((int)(i*gridSize*gridSizeScale+gridXOffset), 0, (int)(i*gridSize*gridSizeScale+gridXOffset), (int)(gridSize * gridheight*gridSizeScale), Raylib.BLACK);
            }
            for(int j = 1; j < gridheight; j++){
                Raylib.drawLine(0+gridXOffset, (int)(j*gridSize*gridSizeScale), (int)(gridSize*gridwidth*gridSizeScale+gridXOffset), (int)(j*gridSize*gridSizeScale), Raylib.BLACK);
            }
        }else if(bg.equals("gridBoss")){
            Raylib.drawRectangle(gridXOffset, 0, (int)(gridSize*gridwidth*gridSizeScale), (int)(gridSize*gridheight*gridSizeScale), Raylib.BLACK);
            for(int i = 1; i < gridwidth; i++){
                Raylib.drawLine((int)(i*gridSize*gridSizeScale+gridXOffset), 0, (int)(i*gridSize*gridSizeScale+gridXOffset), (int)(gridSize * gridheight*gridSizeScale), Raylib.RED);
            }
            for(int j = 1; j < gridheight; j++){
                Raylib.drawLine(0+gridXOffset, (int)(j*gridSize*gridSizeScale), (int)(gridSize*gridwidth*gridSizeScale+gridXOffset), (int)(j*gridSize*gridSizeScale), Raylib.RED);
            }
        }else if(bg.equals("menu")){
            Raylib.drawRectangle(0, 0, Raylib.getScreenWidth(), Raylib.getScreenHeight(), new Color((byte)0, (byte)0, (byte)0, (byte)128)); // semi-transparent black
        }
    }

    public void update(){
        if(paused){
            return; // do not update if paused
        }
        for(int i = 0; i < allKActors().size(); i++){
            KActor g = allKActors().get(i);
            if(!g.hasMounter())
                g.update();
        }

        // hurt enemies out of bounds
        for(GridEntity g: allEntities()){
            if(g.getRealX()<xLowerBound||g.getRealX()>xUpperBound||g.getRealX()>yUpperBound||g.getRealY()<yLowerBound)
                g.hitIgnoreShield((int)Math.ceil(g.getMaxHealth()/300.0), null);
        }

        //remove all gridobjects that want to be
        for(int i = allKActors().size()-1; i >= 0; i--){
            if(allKActors().get(i).shouldRemove()){
                allKActors().get(i).notifyWorldRemove();
                allKActors().remove(i);
            }
        }
        if(scrollToPlayer){
            scrollX = getPlayer().getRealX()-600;
            scrollY = getPlayer().getRealY()-400;
        }
    }
    public void render(){
        drawBG();
        //render remaining objects MOVE TO render()
        for(KActor g: allKActors()){
            if(g.getOpacity()>0&&!g.hasMounter())g.render();
        }
        Raylib.drawRectangle(0, 0, gridXOffset, Raylib.getScreenHeight(), Raylib.DARKGRAY);
        Raylib.drawRectangle((int)(gridXOffset+gridSize*gridwidth*gridSizeScale), 0, gridXOffset+2, Raylib.getScreenHeight(), Raylib.DARKGRAY);
    }
    public void togglePause(){
        if(isPaused()){
            paused = false;
        }else{
            paused = true;
        }
    }
    public ArrayList<GridEntity> allEntities(){
        return allEntities;
    }
    public ArrayList<GridObject> allObjects(){
        return allGridObjects;
    }
    public ArrayList<KActor> allKActors(){
        return allKActors;
    }
    public boolean isPaused(){
        return paused;
    }
    
    public void addObject(KActor a, int x, int y){
        addObject(a, x*1.0, y*1.0);
    }
    public void addObject(KActor a, double x, double y){
        if(a.getWorld()!=null){
            return;
        }
        a.setWorld(this);
        allKActors.add(a);
        a.setRealLocation(x, y);
        a.notifyWorldAdd();
    }
    public void removeObject(KActor a){
        a.remove();
    }
}
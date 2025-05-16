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
    public int xLowerBound = -500, yLowerBound = -500;
    public int xUpperBound = 1700, yUpperBound = 1300;
    private final double gravity = -5;
    
    private boolean paused = false;
    public World() 
    {
        resetBG();
    }

    public void addToGrid(GridObject a, int x, int y){
        grid.place(a, x, y);
        addObject(a, gridXToRealX(x), gridYToRealY(y));
    }
    
    public void addToGrid(KActor a, int x, int y){
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
    
    public double getScrollX(){
        return scrollX;
    }
    
    public double getScrollY(){
        return scrollY;
    }

    public double getGravity(){
        return gravity;
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
        //change BG to boss mode
        /*GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        background.setColor(Color.WHITE);
        for(int i = 1; i < gridwidth; i++){
            background.drawLine(i*50, 0, i*50, getHeight());
        }
        for(int j = 1; j < gridheight; j++){
            background.drawLine(0, j*50, getWidth(), j*50);
        }*/
    }
    public void resetBG(){
        /*GreenfootImage background = getBackground();
        background.setColor(Color.WHITE);
        background.fill();
        background.setColor(Color.BLACK);
        for(int i = 1; i < gridwidth; i++){
            background.drawLine(i*50, 0, i*50, getHeight());
        }
        for(int j = 1; j < gridheight; j++){
            background.drawLine(0, j*50, getWidth(), j*50);
        }*/
    }

    public void update(){
        if(gameStatus().equals("running")){
            for(KActor g: allKActors()){
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
                    allKActors().remove(i);
                }
            }
            if(scrollToPlayer){
                scrollX = getPlayer().getRealX()-600;
                scrollY = getPlayer().getRealY()-400;
            }
        }
    }
    public void render(){
        //TODO: render background
        //render remaining objects MOVE TO render()
        for(KActor g: allKActors()){
            g.render();
        }
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
    /*public void cleanUpEntities(){
        for(int i = allEntities.size()-1; i >= 0; i--){
            try{GridEntity obj = allEntities.get(i);
            if(obj.isDead()&&obj.removeOnDeath()||!obj.isInWorld()){
                if(obj.isInWorld()){
                    removeObject(obj);
                }
                allEntities.remove(i);
            }}catch(Exception e){}
        }
    }*/
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
        allKActors.remove(a);
        a.notifyWorldRemove();
    }
}
package com.karel.game;

//import static com.raylib.Raylib.*;
import com.raylib.Texture;
/**
 * An actor with additional methods and helpers
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public abstract class KActor
{
    double rx, ry, rh, rrot;
    int rop = 255/*real opacity*/;
    World world;
    Texture image;
    boolean shouldRemove;
    public KActor(){
        setImage(getImage());
    }
    public World getWorld(){
        return Game.world;/*try{
        if(super.getWorld()!=null)return (KWorld)super.getWorld();else return KWorld.me;}catch(Exception e){
            return KWorld.me;
        }*/
        //TODO
        
    }
    public void setRealRotation(double rot){
        rrot = rot;
    }
    public double getRealRotation(){
        return rrot;
    }
    public void setRealLocation(double x, double y){
        rx = x;
        ry = y;
        setRealLocation();
    }
    public void setRealLocation(){
        //TODO
    }
    public double getScrollX(){
        return getWorld().getScrollX();
    }
    public double getScrollY(){
        return getWorld().getScrollY();
    }
    public void setRealLocation(double x, double y, double height){
        rh = height;
        setRealLocation(x, y);
    }
    public void branchOut(KActor m, double deg, double dist){
        setRealLocation(m.getRealX()+getBranchX(deg, dist), m.getRealY()+getBranchY(deg, dist));
    }
    public void branchOut(KActor m, double deg, double dist, double height){
        setRealLocation(m.getRealX()+getBranchX(deg, dist), m.getRealY()+getBranchY(deg, dist), height);
    }
    public double getBranchX(double deg, double dist){
        return dist*Math.cos(deg*Math.PI/180);
    }
    public double getBranchY(double deg, double dist){
        return dist*Math.sin(deg*Math.PI/180);
    }
    public double getRealX(){
        return rx;
    }
    public double getRealY(){
        return ry;
    }
    public double getRealHeight(){
        return rh;
    }
    public void setRealHeight(double h){
        rh = h;
        setRealLocation();
    }
    public void translate(double x, double y){
        setRealLocation(getRealX()+x, getRealY()+y);
    }
    public void setVisible(boolean visible){
        if(!visible){
            setOpacity(0);
        }else{
            setOpacity(255);
        }
    }
    public void setOpacityPercent(double perc){
        setOpacity((int)(perc*255));
    }
    public void setOpacity(int amt){//0-255(visible)
        rop = amt;
        processImage();
    }
    public void processImage(){
        //image
    }
    public int getOpacity(){//0-255(visible)
        return rop;
    }
    public void setImage(Texture g){
        image = g;
        processImage();
    }
    public void setImage(String path){
        setImage(Greenfoot.loadTexture(path));
    }
    public Texture getImage(){
        return image;
    }
    public void setTexture(String path){
        // TODO
    }
    public void scaleTexture(int w, int h){
        //TODO
    }
    public void addKActorHere(KActor obj){
        getWorld().addObject(obj, getRealX(), getRealY());
    }
    public void update(){};
    public void render(){};//TODO params
    public void remove(){}
    public void setWorld(World w){
        world = w;
    }
    public boolean shouldRemove(){
        return shouldRemove;
    }
    public void notifyWorldRemove(){world = null;}
    public void notifyWorldAdd(){setWorld(Game.world);}
    public boolean isInGridWorld(){
        return false;
    }
    public boolean isInWorld(){
        return world!=null;
    }
    public String act() throws ArrayIndexOutOfBoundsException{
        return "r";
    }
}

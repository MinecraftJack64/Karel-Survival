package com.karel.game;
import com.raylib.Texture;
import com.raylib.Raylib;
import com.raylib.Rectangle;
import com.raylib.Vector2;
import com.raylib.Color;
/**
 * An actor with additional methods and helpers
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public abstract class KActor
{
    double rx, ry, rh, rrot;
    int scaleX = 0, scaleY = 0;
    int rop = 255/*real opacity*/;
    World world;
    Texture image;
    boolean shouldRemove;
    private GridObject mounter;
    private String drawCenter = "center"; // "top" or "center"
    private Color transColor = new Color((byte)-1, (byte)-1, (byte)-1, (byte)getOpacity());
    public KActor(){
        if(getStaticTextureURL()!="")setImage(getStaticTextureURL());
    }
    public String getStaticTextureURL(){
        return "";
    }
    public World getWorld(){
        return world;
        
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
    public String spriteOrigin(){
        return "";
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
        setImage(Greenfoot.loadTexture(spriteOrigin()+path));
    }
    public Texture getImage(){
        return image;
    }
    public void setTexture(String path){
        // TODO
    }
    public void scaleTexture(int w, int h){
        scaleX = w;
        scaleY = h;
    }
    public void scaleTexture(int w){
        if(image==null)return;
        scaleX = w;
        scaleY = w*image.getHeight()/image.getWidth();
    }
    public void addKActorHere(KActor obj){
        getWorld().addObject(obj, getRealX(), getRealY());
    }
    public void update(){};
    public void setDrawCenter(String center){
        drawCenter = center;
    }
    public void render(){//TODO: add params like x and y offset and scale
        if(getImage()!=null){
            int w = getImage().getWidth(), h = getImage().getHeight();
            int dw = scaleX==0?w:scaleX, dh = scaleY==0?h:scaleY;
            transColor.setA((byte)getOpacity());
            Vector2 center = drawCenter.equals("top")?new Vector2(renderOriginX(dw/2), 0):new Vector2(renderOriginX(dw/2), renderTransformY(dh/2));
            Raylib.drawTexturePro(getImage(), new Rectangle(0, 0, w, h), new Rectangle(renderTransformX((int)(getRealX())), renderTransformY((int)(getRealY()-getRealHeight())), renderOriginX(dw), renderTransformY(dh)), center, (float)getRealRotation(), transColor);
        }
    };
    public void renderTexture(Texture tex, double x, double y, double scaleX, double scaleY, double rot, int opacity){
        if(tex!=null){
            int w = tex.getWidth(), h = tex.getHeight();
            Raylib.drawTexturePro(tex, new Rectangle(0, 0, w, h), new Rectangle(renderTransformX((int)(x)), renderTransformY((int)(y)), renderOriginX((int)scaleX), renderTransformY((int)scaleY)), new Vector2(renderOriginX((int)(scaleX/2)), renderTransformY((int)(scaleY/2))), (float)(rot), new Color((byte)-1, (byte)-1, (byte)-1, (byte)opacity));
        }
    }
    public int renderTransformX(int x){
        return (int)((x-(isInGridWorld()?getScrollX():0))*getWorld().getScreenScale()+getWorld().getScreenOffsetX());
    }
    public int renderOriginX(int x){
        return (int)((x-(isInGridWorld()?getScrollX():0))*getWorld().getScreenScale());
    }
    public int renderTransformY(int y){
        return (int)((y-(isInGridWorld()?getScrollY():0))*getWorld().getScreenScale());
    }
    public void playSound(String sound){
        if(isInWorld()){
            Game.playSound(Greenfoot.loadSound(sound));
        }
    }
    public void notifyMount(GridObject other){
        if(hasMounter())mounter.unmount(this);
        mounter = other;
    }
    public void notifyUnmount(GridObject other){
        mounter = null;
    }
    public GridObject getMounter(){
        return mounter;
    }
    
    public boolean hasMounter(){
        return mounter!=null;
    }
    public void clearMounting(){
        if(hasMounter())mounter.unmount(this);
    }
    public void remove(){
        shouldRemove = true;
    }
    public void setWorld(World w){
        world = w;
    }
    public boolean shouldRemove(){
        return shouldRemove;
    }
    public void notifyWorldRemove(){world = null;clearMounting();}
    public void notifyWorldAdd(){setWorld(Game.world);shouldRemove = false;}
    public boolean isInGridWorld(){
        return false;
    }
    public boolean isInWorld(){
        return world!=null;
    }
}

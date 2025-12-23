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
    String lastTexturePath = "";
    private GridObject mounter;
    private String drawCenter = "center"; // "top" or "center"
    private Color transColor = new Color((byte)-1, (byte)-1, (byte)-1, (byte)getOpacity());
    private boolean shouldUnmount;
    private boolean mountFixed;
    public KActor(){
        if(getStaticTextureURL()!="")setImage(getStaticTextureURL());
    }
    public String getStaticTextureURL(){
        return "";
    }
    public World getWorld(){
        return world;
    }
    public void setRotation(double rot){
        rrot = rot;
    }
    public double getRotation(){
        return rrot;
    }
    public void setLocation(double x, double y){
        rx = x;
        ry = y;
    }
    public double getScrollX(){
        return getWorld().getScrollX();
    }
    public double getScrollY(){
        return getWorld().getScrollY();
    }
    public void setLocation(double x, double y, double height){
        rh = height;
        setLocation(x, y);
    }
    public void branchOut(KActor m, double deg, double dist){
        setLocation(m.getX()+getBranchX(deg, dist), m.getY()+getBranchY(deg, dist));
    }
    public void branchOut(KActor m, double deg, double dist, double height){
        setLocation(m.getX()+getBranchX(deg, dist), m.getY()+getBranchY(deg, dist), height);
    }
    public double getBranchX(double deg, double dist){
        return dist*Math.cos(deg*Math.PI/180);
    }
    public double getBranchY(double deg, double dist){
        return dist*Math.sin(deg*Math.PI/180);
    }
    public double getX(){
        return rx;
    }
    public double getY(){
        return ry;
    }
    public double getHeight(){
        return rh;
    }
    public void setHeight(double h){
        rh = h;
    }
    public void translate(double x, double y){
        setLocation(getX()+x, getY()+y);
    }
    public double face(KActor obj, boolean face){
        return face(obj.getX(), obj.getY(), face);
    }
    public double face(double x, double y, boolean face){
        double targang = getAngle(x, y)+90;
        double monangle = targang;
        if(face)setRotation(monangle);
        return monangle;
    }
    public boolean isFacing(KActor other){
        return getFacingDistance(other)<75;
    }
    public double getFacingOffset(KActor other){
        return face(other, false)-getTargetRotation();
    }
    public double getFacingDistance(KActor other){
        return Math.abs(getFacingOffset(other));
    }
    public double getTargetRotation(){
        return getRotation();
    }
    public float getAngle(double x, double y) {
        return getAngleBetween(getX(), getY(), x, y);
    }
    public static float getAngleBetween(double x, double y, double x2, double y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y, x2 - x));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    public double distanceTo(double x, double y){
        return Math.sqrt(Math.pow(x-getX(), 2)+Math.pow(y-getY(), 2));
    }
    public double distanceTo(double x, double y, double z){
        return Math.sqrt(Math.pow(x-getX(), 2)+Math.pow(y-getY(), 2)+Math.pow(z-getHeight(), 2));
    }
    public double distanceTo(KActor obj){
        return distanceTo(obj.getX(),obj.getY(), obj.getHeight());
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
    public String getImageURL(){
        return spriteOrigin()+lastTexturePath;
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
        lastTexturePath = path;
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
        if(image==null)scaleTexture(w, w);
        else scaleTexture(w, w*image.getHeight()/image.getWidth());
    }
    public void addKActorHere(KActor obj){
        getWorld().addObject(obj, getX(), getY());
    }
    public void update(){};
    public void setDrawCenter(String center){
        drawCenter = center;
    }
    public void setTint(Color c){
        transColor = c;
    }
    public void setTint(int r, int g, int b){
        setTint(new Color((byte)r, (byte)g, (byte)b, (byte)getOpacity()));
    }
    public Color getTint(){
        return transColor;
    }
    public void resetTint(){
        transColor = new Color((byte)-1, (byte)-1, (byte)-1, (byte)getOpacity());
    }
    public void render(){//TODO: add params like x and y offset and scale
        if(getImage()!=null){
            int w = getImage().getWidth(), h = getImage().getHeight();
            int dw = scaleX==0?w:scaleX, dh = scaleY==0?h:scaleY;
            transColor.setA((byte)getOpacity());
            Vector2 center = drawCenter.equals("top")?new Vector2(renderOriginX(dw/2), 0):new Vector2(renderOriginX(dw/2), renderTransformY(dh/2));
            Raylib.drawTexturePro(getImage(), new Rectangle(0, 0, w, h), new Rectangle(renderTransformX((int)(getX())), renderTransformY((int)(getY()-getHeight())), renderOriginX(dw), renderTransformY(dh)), center, (float)getRotation(), transColor);
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
        shouldUnmount = false;
        mountFixed = false;
    }
    public GridObject getMounter(){
        return mounter;
    }
    /*
     * If is being mounted
     */
    public boolean hasMounter(){
        return mounter!=null;
    }
    public void clearMounting(){
        if(hasMounter())mounter.unmount(this);
    }
    public void remove(){
        shouldRemove = true;
    }
    public void markUnmount(){
        shouldUnmount = true;
    }
    public void setMountFixed(boolean b) {
        mountFixed = b;
    }
    public boolean isFixedMount(){
        return mountFixed;
    }
    public void setWorld(World w){
        world = w;
    }
    public boolean shouldRemove(){
        return shouldRemove;
    }
    public boolean shouldUnmount(){
        return shouldUnmount;
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

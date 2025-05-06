package Game;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

/**
 * An actor with additional methods and helpers
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public abstract class KActor extends Actor
{
    double rx, ry, rh, rrot;
    int rop = 255/*real opacity*/;
    boolean isInWorld;
    public KActor(){
        setImage(getImage());
    }
    public KWorld getWorld(){
        try{
        if(super.getWorld()!=null)return (KWorld)super.getWorld();else return KWorld.me;}catch(Exception e){
            return KWorld.me;
        }
        
    }
    public void setRealRotation(double rot){
        rrot = rot;
        setRotation((int)rrot);
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
        if(getWorld()!=null)
        if(isInGridWorld())setLocation((int)(rx-getScrollX()), (int)(ry-rh-getScrollY()));
        else setLocation((int)(rx), (int)(ry-rh));
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
        processImage(getImage());
    }
    public void processImage(GreenfootImage g){
        g.setTransparency(getOpacity());
    }
    public int getOpacity(){//0-255(visible)
        return rop;
    }
    public void setImage(GreenfootImage g){
        processImage(g);
        super.setImage(g);
    }
    public void setImage(String path){
        super.setImage(path);
        processImage();
    }
    public void addKActorHere(KActor obj){
        getWorld().addObject(obj, getRealX(), getRealY());
    }
    public void notifyWorldRemove(){isInWorld = false;}
    public void notifyWorldAdd(){isInWorld = true;}
    public boolean isInGridWorld(){
        return false;
    }
    public boolean isInWorld(){
        return isInWorld;
    }
}

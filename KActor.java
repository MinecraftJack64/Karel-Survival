import greenfoot.Actor;

/**
 * Write a description of class KActor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KActor extends Actor
{
    double rx, ry, rh, rrot;
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
        if(getWorld()!=null)setLocation((int)rx, (int)(ry-rh));
    }
    public void setRealLocation(double x, double y, double height){
        rh = height;
        setRealLocation(x, y);
    }
    public void branchOut(KActor m, double deg, double dist){
        setRealLocation(m.getRealX()+getBranchX(deg, dist), m.getRealY()+getBranchY(deg, dist));
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
        setLocation((int)rx, (int)(ry-rh));
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
        getImage().setTransparency((int)(perc*255));
    }
    public void setOpacity(int amt){//0-255(visible)
        getImage().setTransparency(amt);
    }
    public int getOpacity(){//0-255(visible)
        return getImage().getTransparency();
    }
    public void addKActorHere(KActor obj){
        getWorld().addObject(obj, getRealX(), getRealY());
    }
    public void notifyWorldRemove(){
        //
    }
    public void notifyWorldAdd(){
        //
    }
}

/**
 * Write a description of class Boomerang here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boomerang extends Bullet implements IBoomerang
{
    private int phase = 0;//0 - regular, 1 - return, 2 - other
    private int returnOnExpire;//0 - on die, 1 - on expire, 2 - on finish
    private double returnSpeed;
    private boolean hasreturned;
    private int damageonreturn = 0;//0 - on phase 0 only, 1 - both ways, 2 - only when returning
    public Boomerang(double dir, GridObject source){
        super(dir, source);
        setReturnSpeed(getSpeed());
        setExpireReturn(0);
        hasreturned = false;
    }
    public void setExpireReturn(int s){
        returnOnExpire = s;
    }
    public void setReturnSpeed(double s){returnSpeed = s;}
    public double getReturnSpeed(){return returnSpeed;}
    public void applyPhysics(){
        if(phase==0){
            super.applyPhysics();
        }else{
            doReturn();
        }
    }
    public void doReturn(){
        if(getSource()==null||distanceTo(getSource())<getReturnSpeed()/2){
            dieForReal();
            return;
        }
        if(damageonreturn>0){
            setNumTargets(-1);
            checkHit();
        }
        setDirection(face(getSource(), false));
        move(getDirection(), getSpeed());
        setRealRotation(getDirection()+90);
    }
    public void expire(){
        if(returnOnExpire==1)startReturn();
        else die();
    }
    public void die(){
        if(returnOnExpire==0)startReturn();
        else dieForReal();
    }
    public void dieForReal(){
        super.die();
        hasreturned = true;
    }
    public void finish(){
        if(returnOnExpire==2)startReturn();
        else die();
    }
    public void startReturn(){
        phase = 1;
        setSpeed(getReturnSpeed());
    }
    public boolean hasReturned(){
        return hasreturned;
    }
}

/**
 * Write a description of class Heart here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Heart extends GridObject implements SubAffecter
{
    private GridEntity target, source;
    private boolean dead;
    public Heart(GridEntity targ, GridEntity source){
        target = targ;
        this.source = source;
        dead = false;
        inherit(source);
    }
    public GridObject getSource(){
        return source;
    }
    public void kAct(){
        if(target.isDead()){
            die();
        }
        setRealRotation(getRealRotation()+18);
        setRealLocation(target.getRealX(), target.getRealY(), target.getRealHeight());
    }
    public void die(){
        for(int i = 0; i < 8; i++){
            Heartbreak bullet = new Heartbreak(getRealRotation()+i*45, source);
            addObjectHere(bullet);
        }
        if(!source.isDead()){
            heal(source, 100);
        }
        super.die();
        dead = true;
        getWorld().removeObject(this);
    }
    public boolean isDead(){
        return dead;
    }
}
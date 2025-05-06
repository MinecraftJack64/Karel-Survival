package Game;
/**
 * Write a description of class Puddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Puddle extends Hitter
{
    private int interval = 9;//how often this puddle does its thing
    private int cooldown = 0;
    private int times = 1;// how many ticks this puddle does left
    public void setInterval(int r){
        interval = r;
    }
    public int getInterval(){
        return interval;
    }
    public Puddle(GridObject source){
        super(source);
        setNumTargets(-1);
        setCollisionMode("radius");
        setClipHits(true);
    }
    public Puddle(int range, int interv, int times, GridObject source){
        this(source);
        setInterval(interv);
        setRange(range);
        this.times = times;
    }
    public Puddle(int damage, int range, int interv, int times, GridObject source){
        this(range, interv, times, source);
        setDamage(damage);
    }
    public void kAct()
    {
        cooldown++;
        if(cooldown>=getInterval()){
            tick();
            cooldown = 0;
            times--;
            if(times<=0){
                die();
            }
        }
    }
    public void tick(){
        checkHit();
        //System.out.println("Puddle: check hit");
    }
    public void die(){
        getWorld().removeObject(this);
    }
}

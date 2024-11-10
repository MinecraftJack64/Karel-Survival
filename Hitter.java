import java.util.*;

/**
 * Write a description of class Hitter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hitter extends GridObject implements SubAffecter
{
    GridObject source;
    private int damage = 0;
    private int targets;
    private boolean isattack = true;
    private boolean hitallies;
    private boolean hitself;
    private boolean multihit;
    private String collidemode = "collide";
    HashSet<GridEntity> hitstory = new HashSet<GridEntity>();
    public Hitter(GridObject source){
        this.source = source;
    }
    public GridObject getSource(){
        return source;
    }
    public void setSource(GridObject s){
        source = s;
    }
    public int getNumTargets(){
        return targets;
    }
    public boolean isAttack(){
        return isattack;
    }
    public boolean willHitAllies(){
        return hitallies;
    }
    public void setHitAllies(boolean d){
        hitallies = d;
    }
    public void setNumTargets(int ist){
        targets = ist;
    }
    public void changeNumTargets(int amt){
        targets+=amt;
    }
    public void setAggression(boolean ist){
        isattack= ist;
    }
    public void setDamage(int dmg){
        damage = dmg;
    }
    public int getDamage(){
        return damage;
    }
    public boolean willSelfHarm(){
        return hitself;
    }
    public void setSelfHarm(boolean will){
        hitself = will;
    }
    public boolean canMultiHit(){
        return multihit;
    }
    public void setMultiHit(boolean will){
        multihit = will;
    }
    public void setCollisionMode(String n){
        collidemode = n;
    }
    public String getCollisionMode(){
        return collidemode;
    }
    public void setHitStory(HashSet<GridEntity> h){
        hitstory = h;
    }
    public void clearHitStory(){
        hitstory.clear();
    }
    public HashSet<GridEntity> getHitStory(){
        return hitstory;
    }
    public void notifyDamage(GridEntity target, int amt){
        if(getSource()!=null){
            getSource().notifyDamage(target, amt);
        }
    }
    /**
     * Check whether we have hit an asteroid.
     */
    public void checkHit()
    {
        HashSet<GridEntity> asteroid = new HashSet<GridEntity>();
        if(getCollisionMode().equals("collide"))asteroid.addAll((ArrayList<GridEntity>)getIntersectingObjects(GridEntity.class));
        if(asteroid.size()==0||getNumTargets()==0){
            return;
        }
        //System.out.println(asteroid.size());
        for(GridEntity thing: asteroid){
            //System.out.println(thing);
            if(thing==getSource()){
                if(willSelfHarm()){
                    //
                }else{
                    continue;//avoid hitting source
                }
            }
            if(isAttack()&&isAggroTowards(thing)&&checkHeight(thing)){
                if(!hitstory.contains(thing)){
                    doHit(thing);
                    onHit(thing);
                    changeNumTargets(-1);
                    if(!canMultiHit())hitstory.add(thing);
                    if(getNumTargets()==0){
                        break;
                    }
                    continue;
                }else{
                    onHit(thing);
                    continue;
                }
            }
            if(willHitAllies()&&isAlliedWith(thing)&&checkHeight(thing)){
                if(!hitstory.contains(thing)){
                    doHit(thing);
                    onHit(thing);
                    changeNumTargets(-1);
                    if(!canMultiHit())hitstory.add(thing);
                    if(getNumTargets()==0){
                        break;
                    }
                }else{
                    onHit(thing);
                    continue;
                }
            }
        }
        if(canMultiHit()){
            setHitStory(asteroid);
        }
        /*if (asteroid != null&&asteroid.getRealHeight()==0){
            getWorld().removeObject(this);
            asteroid.hit(damage, this);
        }*/
    }
    public void doHit(GridEntity asteroid){
        damage(asteroid, getDamage());
    }
    public boolean checkHeight(GridEntity other){
        return Math.abs(other.getRealHeight()-getRealHeight())<5;
    }
    public void onHit(GridEntity thing){
        //
    }
    public boolean covertDamage(){
        return getSource().covertDamage();
    }
}

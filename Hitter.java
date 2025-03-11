import java.util.*;
import java.util.stream.Collectors;

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
    private int range = 0;// for radius collision
    private boolean isattack = true;// hit enemies
    private boolean hitallies;// hit allies
    private boolean hitself;// hit source
    private boolean multihit;// hit enemies more than once
    private boolean trackAfterHit;// if this hitter checks and calls afterHit
    private boolean clipHits;// ignore hitstory, allowing hitting enemies multiple times while passing through them
    private String collidemode = "collide";
    HashSet<GridEntity> hitstory = new HashSet<GridEntity>();
    public Hitter(GridObject source){
        this.source = source;
        inherit(source);
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
    public int getDamage(GridEntity g){
        return getDamage();
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
    public void setRange(int r){
        range = r;
    }
    public int getRange(){
        return range;
    }
    public boolean getTrackAfterHit(){
        return trackAfterHit;
    }
    public void setTrackAfterHit(boolean t){
        trackAfterHit = t;
    }
    public void setClipHits(boolean t){
        clipHits = t;
    }
    public boolean clipHits(){
        return clipHits;
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
        HashSet<GridEntity> asteroid = getColliding();
        if(asteroid.size()==0||getNumTargets()==0){
            return;
        }
        for(GridEntity thing: asteroid){
            if(!processPotentialHit(thing))break;
        }
        if(getTrackAfterHit()){
            HashSet<GridEntity> g = new HashSet<GridEntity>();
            g.addAll(getHitStory());
            g.removeAll(asteroid);
            System.out.println((getHitStory().size()>=1)+" "+(asteroid.size()>=1));
            for(GridEntity e: g){
                if((isAttack()&&isAggroTowards(e)&&checkHeight(e))&&(willHitAllies()&&isAlliedWith(e)&&checkHeight(e)))afterHit(e);
            }
        }
        if(canMultiHit()){
            setHitStory(asteroid);
        }
    }
    //return false if no more targets
    public boolean processPotentialHit(GridEntity thing){
        if(willHit(thing)){
            if(!hitstory.contains(thing)){
                doHit(thing);
                onHit(thing);
                changeNumTargets(-1);
                if(!canMultiHit())hitstory.add(thing);
                if(getNumTargets()==0){
                    return false;
                }
            }else if(clipHits()){
                doHit(thing);
                onHit(thing);
            }else{
                onHit(thing);
            }
        }
        return true;
    }
    public HashSet<GridEntity> getColliding(){
        HashSet<GridEntity> asteroid = new HashSet<GridEntity>();
        if(getCollisionMode().equals("collide"))asteroid.addAll(getCollidingGEs());
        else if(getCollisionMode().equals("radius"))asteroid.addAll(getGEsInRange(getRange()));
        return asteroid;
    }
    public List<GridEntity> getCollidingGEs(){
        List<GridEntity> ges = (List<GridEntity>)getIntersectingObjects(GridEntity.class);
        return ges.stream().filter(g->checkHeight(g)).collect(Collectors.toList());
    }
    public boolean willHit(GridEntity thing){
        return thing==getSource()&&willSelfHarm() || isAttack()&&isAggroTowards(thing) || willHitAllies()&&isAlliedWith(thing);
    }
    public void doHit(GridEntity asteroid){
        damage(asteroid, getDamage(asteroid));
    }
    public boolean checkHeight(GridEntity other){
        return Math.abs(other.getRealHeight()-getRealHeight())<5;
    }
    // always runs even when enemy was hit before
    public void onHit(GridEntity thing){
        //
    }
    public void afterHit(GridEntity thing){
        //
    }
    public boolean covertDamage(){
        return getSource().covertDamage();
    }
}

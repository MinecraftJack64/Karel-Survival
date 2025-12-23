package com.karel.game;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.karel.game.effects.EffectID;
import com.karel.game.particles.Explosion;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Represents an object inside the game world
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public abstract class GridObject extends KActor
{
    private HashMap<KActor, Vector> mounts;
    private ArrayList<EventListener> listeners = new ArrayList<EventListener>();
    String team;
    String faketeam;
    String joinedteam;
    String oldteam;
    private Arc arcmomentum;
    private double dirmomentum;
    private HashMap<EffectID, Double> powerAffecters = new HashMap<EffectID, Double>();
    private double powermultiplier = 1, basepower = 1;
    private int arcframe = 0;
    private boolean grounded;
    private GridEntity cachedTarget;
    private boolean updatingMounts;

    //TODO: HITBOX STUFF
    private int collisionRadius = 25;
    public String getTeam(){
        if(faketeam!=null){
            return faketeam;
        }
        return team;
    }
    public void setTeam(String team){
        oldteam = this.team;
        this.team = team;
        updateTeam();
    }
    public void clearTeam(){
        oldteam = team;
        team = null;
        updateTeam();
    }
    public void clearFakeTeam(){
        faketeam = null;
        updateTeam();
    }
    public void setFakeTeam(String s){
        faketeam = s;
        updateTeam();
    }
    private void updateTeam(){
        if(Objects.equals(getTeam(), joinedteam)||getWorld()==null){
            return;
        }
        if(joinedteam!=null){
            getWorld().getTeams().leaveTeam(this, joinedteam);
        }
        getWorld().getTeams().joinTeam(this, getTeam());
        joinedteam = getTeam();
    }
    /**Use specifically when removed, keep track of old team to be added back */
    private void leaveTeam(){
        if(joinedteam!=null){
            getWorld().getTeams().leaveTeam(this, joinedteam);
            joinedteam = null;
        }
    }
    public void matchTeam(GridObject other){
        if(other!=null&&other.getTeam()!=null&&!other.getTeam().equals(getTeam())){
            setTeam(other.getTeam());
        }
    }
    public void matchTeam(GridEntity other){
        if(other!=null&&!other.isDead()){
            matchTeam((GridObject)other);
        }
    }
    public void matchPower(GridObject other){
        setPower(other.getPower());
    }
    public void matchPower(GridEntity other){
        if(other!=null&&!other.isDead()){
            matchPower((GridObject)other);
        }
    }
    public void inherit(GridObject other){
        matchTeam(other);
        matchPower(other);
    }
    public void updateMounts(){
        if(mounts!=null){
            Iterator<Map.Entry<KActor, Vector>> i = mounts.entrySet().iterator();
            updatingMounts = true;
            while(i.hasNext()){
                var g = i.next();
                g.getKey().branchOut(this, !g.getKey().isFixedMount()?g.getValue().getDirection()+getRotation():g.getValue().getDirection(), g.getValue().getLength(), g.getValue().getHeight());
                if(g.getKey().shouldUnmount()){
                    i.remove();
                }
            }
            updatingMounts = false;
        }
    }
    public void renderMounts(){
        if(mounts!=null){
            for(var g: mounts.entrySet()){
                if(g.getKey().isInWorld())g.getKey().render();
            }
        }
    }
    public double face(GridObject obj, boolean face){
        return face(obj.getX(), obj.getY(), face);
    }
    public double face(double x, double y, boolean face){
        double targang = getAngle(x, y)+90;
        double monangle = targang;
        if(face)setRotation(monangle);
        return monangle;
    }
    public boolean isFacing(GridObject other){
        return getFacingDistance(other)<75;
    }
    public double getFacingOffset(GridObject other){
        return face(other, false)-getTargetRotation();
    }
    public double getFacingDistance(GridObject other){
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
    public double distanceTo(GridObject obj){
        return distanceTo(obj.getX(),obj.getY(), isColumnar()?this.getHeight():obj.getHeight());
    }
    //Ignore height for distance checks
    public boolean isColumnar(){
        return false;
    }
    public double getRandomCellX(){
        return getWorld().gridXToRealX(Greenfoot.getRandomNumber(getWorld().gridwidth));
    }
    public double getRandomCellY(){
        return getWorld().gridYToRealY(Greenfoot.getRandomNumber(getWorld().gridheight));
    }
    
    public double getXAtOffset(int val){
        return getWorld().gridXToRealX(getWorld().realXToGridX(getX())+val);
    }
    public double getYAtOffset(int val){
        return getWorld().gridYToRealY(getWorld().realYToGridY(getY())+val);
    }
    public void move(double degree, double speed){
        degree-=90;
        double deree = degree*Math.PI/180;
        translate((speed*Math.cos(deree)), (speed*Math.sin(deree)));
    }
    public int getNearest(List<? extends GridObject> g){
        int b = -1;
        double bd = 0;
        for(int i = 0; i < g.size(); i++){
            double d = distanceTo(g.get(i));
            if(b==-1||d<bd){
                b = i;
                bd = d;
            }
        }
        return b;
    }
    public GridEntity getParentAffecter(){
        if(!(this instanceof SubAffecter)){
            if(this instanceof GridEntity){
                return (GridEntity)this;
            }
            return null;
        }
        return ((SubAffecter)this).getSource().getParentAffecter();
    }
    public void ground(){
        grounded = true;
    }
    public void unground(){
        grounded = false;
    }
    public boolean isGrounded(){
        return grounded;
    }
    public boolean canBePulled(){
        return !isGrounded()&&!hasMounter();
    }
    public boolean canBePulled(GridObject source){
        return canBePulled()||source==this;
    }
    public void notifyPull(){
        broadcastEvent("pull");
    }
    public void notifyPull(GridObject source){notifyPull();}//TODO
    public boolean pullTo(double x, double y){
        if(canBePulled()){
            setLocation(x, y);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTo(double x, double y, GridObject source){
        if(canBePulled(source)){
            setLocation(x, y);
            notifyPull(source);
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTo(double x, double y, double h){
        if(canBePulled()){
            setLocation(x, y, h);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTo(double x, double y, double h, GridObject source){
        if(canBePulled(source)){
            setLocation(x, y, h);
            notifyPull(source);
            return true;
        }else{
            return false;
        }
    }
    public boolean pullToBranch(GridObject g, double deg, double dist){
        if(canBePulled()){
            branchOut(g, deg, dist);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pullToBranch(GridObject g, double deg, double dist, GridObject source){
        if(canBePulled(source)){
            branchOut(g, deg, dist);
            notifyPull(source);
            return true;
        }else{
            return false;
        }
    }
    public boolean pull(double ang, double speed){
        if(canBePulled()){
            move(ang, speed);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pull(double ang, double speed, GridObject source){
        if(canBePulled(source)){
            move(ang, speed);
            notifyPull(source);
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTowards(GridObject targ, double speed){
        if(distanceTo(targ)<=speed/2){
            return pullTo(targ.getX(), targ.getY());
        }
        else{
            return pull(face(targ, false), speed);
        }
    }
    public boolean pullTowards(GridObject targ, double speed, GridObject source){
        if(distanceTo(targ)<=speed/2){
            return pullTo(targ.getX(), targ.getY(), source);
        }
        else{
            return pull(face(targ, false), speed, source);
        }
    }
    public boolean pullTowards(double x, double y, double speed){
        if(distanceTo(x, y)<=speed/2){
            return pullTo(x, y);
        }
        else{
            return pull(face(x, y, false), speed);
        }
    }
    public boolean pullTowards(double x, double y, double speed, GridObject source){
        if(distanceTo(x, y)<=speed/2){
            return pullTo(x, y, source);
        }
        else{
            return pull(face(x, y, false), speed, source);
        }
    }
    public boolean knockBack(double r, double d, double h, GridObject source){
        if(!canBePulled(source)){
            return false;
        }
        notifyPull(source);
        initiateJump(r, d, h);
        return true;
    }
    public void notifyDamage(GridEntity target, int amt){}
    public boolean isAggroTowards(GridObject other){
        if(other.getTeam()==null||getTeam()==null||!isInWorld())return false;
        return getWorld().getTeams().getAggressions(getTeam()).contains(other.getTeam());
    }
    public boolean isAlliedWith(GridObject other){
        if(other.getTeam()==null||getTeam()==null||!isInWorld())return false;
        return getWorld().getTeams().getAllies(getTeam()).contains(other.getTeam());
    }
    public void die(){
        broadcastEvent("die");
        clearFakeTeam();
        clearTeam();
    }
    public void revive(){
        setTeam(oldteam);
    }
    public int explodeOn(int range, String filter, Consumer<GridEntity> vore, Explosion exp){
        List<GridEntity> l = getGEsInRange(range);
        int tax = 0;
        if(exp!=null){
            addObjectHere(exp);
        }
        if(l.size()==0){
            return 0;
        }
        for(GridEntity g:l){
            switch(filter){
                case "ally":
                    if(isAlliedWith(g)){
                        vore.accept(g);
                        tax++;
                    }
                break;
                case "enemy":
                    if(isAggroTowards(g)){
                        vore.accept(g);
                        tax++;
                    }
                break;
                default://all
                    vore.accept(g);
                    tax++;
                break;
            }
        }
        return tax;
    }
    public int explodeOn(int range, Consumer<GridEntity> vore, Explosion exp){
        return explodeOn(range, "all", vore, exp);
    }
    public int explodeOn(int range, Consumer<GridEntity> vore){
        return explodeOn(range, vore, null);
    }
    public int explodeOn(int range, int dmg){
        if(dmg>=0){
            return explodeOn(range, "enemy", (g)->{damage(g, dmg);}, new Explosion(((double)range)/60));
        }else{
            return explodeOn(range, "ally", (g)->{heal(g, dmg);}, null);
        }
    }
    public int explodeOnAll(int range, int dmg){
        return explodeOn(range, (g)->{damage(g, dmg);}, new Explosion(((double)range)/60));
    }
    public int explodeOn(int range, int dmg, Explosion exp){
        if(dmg>=0){
            return explodeOn(range, "enemy", (g)->{damage(g, dmg);}, exp);
        }else{
            return explodeOn(range, "ally", (g)->{heal(g, dmg);}, exp);
        }
    }
    public int explodeOnEnemies(int range, Consumer<GridEntity> vore){
        return explodeOn(range, "enemy", vore, new Explosion(((double)range)/60));
    }
    public int knockBackOnEnemies(int range, double dist){
        return explodeOn(range, "enemy", (g)->{
                g.knockBack(face(g, false), dist, 30, this);
        }, null);
    }
    public int knockBackOn(int range, double dist){
        return explodeOn(range, "all", (g)->{
                g.knockBack(face(g, false), dist, 30, this);
        }, null);
    }
    public Location findCluster(int range, double radius){
        var all = getTargetsInRange(range);
        Location best = null;
        int bestCount = 1;
        for(int i = 0; i < all.size(); i++){
            GridEntity a = all.get(i);
            for(int j = i+1; j < all.size(); j++){
                GridEntity b = all.get(j);
                double d = a.distanceTo(b);
                if(d>2*radius)continue;

                double md = d/2;
                double h = Math.sqrt(radius*radius-md*md);
                double mx = (a.getX()+b.getX())/2;
                double my = (a.getY()+b.getY())/2;
                double offsetX = -(b.getX()-a.getX())*(h/d);
                double offsetY = -(b.getY()-b.getY())*(h/d);
                Location p1 = new Location(mx+offsetX, my+offsetY);
                Location p2 = new Location(mx-offsetX, my-offsetY);
                for(Location p: new Location[]{p1, p2}){
                    int count = 0;
                    //counting
                    for(GridEntity t: all){
                        double dx = t.getX()-p.x;
                        double dy = t.getY()-p.y;
                        if(Math.sqrt(dx*dx+dy*dy)<radius){
                            count++;
                        }
                    }
                    if(count>bestCount){
                        best = p;
                        bestCount = count;
                    }
                }
            }
        }
        if(best==null){
            if(all.size()>=1){
                return new Location(all.get(0).getX(), all.get(0).getY());
            }
        }
        return best;
    }
    public GridEntity getNearestTarget() {
        if(useTargetCache()&&cachedTarget!=null&&!cachedTarget.isDead()&&isPotentialTarget(cachedTarget)){
            return cachedTarget;
        }
        GridEntity nearestTarget = null;
        double closestDistance = 0;
        if(!isInWorld())return null;
        for (GridEntity entity : this.getWorld().allEntities()) {
            if (isPotentialTarget(entity)) {
                double currentDistance = this.distanceTo(entity);
                
                if (nearestTarget == null || currentDistance < closestDistance) {
                    nearestTarget = entity;
                    closestDistance = currentDistance;
                }
            }
        }
        cachedTarget = nearestTarget;
        return nearestTarget;
    }
    public void clearTargetCache(){
        cachedTarget = null;
    }
    //Ignores whether potential target
    public GridEntity getNearestOtherGE() {
        GridEntity nearestTarget = null;
        double closestDistance = 0;
    
        for (GridEntity entity : this.getWorld().allEntities) {
            if (entity!=this) {
                double currentDistance = this.distanceTo(entity);
                
                if (nearestTarget == null || currentDistance < closestDistance) {
                    nearestTarget = entity;
                    closestDistance = currentDistance;
                }
            }
        }
    
        return nearestTarget;
    }

    public boolean isPotentialTarget(GridEntity entity){
        return isAggroTowards(entity)&&entity.canDetect();
    }
    public double getGravity(){
        return 3;
    }
    public void applyPhysics(){
        if(arcmomentum==null){
            return;
        }
        move(dirmomentum, arcmomentum.getRate());
        setHeight(arcmomentum.getHeight(arcframe));
        arcframe++;
        if(getHeight()<0&&arcframe>0){
            arcmomentum = null;
            dirmomentum = 0;
            arcframe = 0;
            setHeight(0);
            doLanding();
        }
    }
    public boolean isOnGround(){
        return getHeight()<=0;
    }
    public Arc getPhysicsArc(){
        return arcmomentum;
    }
    public void initiateJump(double direction, double distance, double height){
        arcmomentum = new Arc(distance, height, getGravity());
        dirmomentum = direction;
        arcframe = 0;
    }
    // if this object will be notified when it does damage
    public boolean covertDamage(){
        return false;
    }
    // 0 to 1, how much this object should be notified when it does damage
    public double damageSecrecy(){
        return 1;
    }
    // if this object will notify when damaged
    public boolean willNotify(GridObject source){
        return true;
    }
    // 0 to 1, how much this object should notify its damagers
    public double damageExposure(){
        return 1;
    }
    public void doLanding(){}
    public GridObject addObjectHere(GridObject obj){
        getWorld().addObject(obj, getX(), getY());
        return obj;
    }
    public List<GridEntity> getGEsInRange(int rng){
        ArrayList<GridEntity> gs = new ArrayList<GridEntity>();
        for(GridEntity g:getWorld().allEntities()){
            if(this!=g&&distanceTo(g)<=rng){
                gs.add(g);
            }
        }
        return gs;
    }
    public List<GridEntity> getTargetsInRange(int rng){
        ArrayList<GridEntity> gs = new ArrayList<GridEntity>();
        for(GridEntity g:getWorld().allEntities()){
            if(this!=g&&distanceTo(g)<=rng&&isPotentialTarget(g)){
                gs.add(g);
            }
        }
        return gs;
    }
    public <T extends GridObject> List<T> getGOsInRange(int rng, Class<T> cls){
        ArrayList<T> gs = new ArrayList<>();
        for(GridObject g:getWorld().allObjects()){
            if(distanceTo(g)<=rng&&cls.isInstance(g)){
                gs.add(cls.cast(g));
            }
        }
        return gs;
    }
    public void scaleTexture(int w, int h){
        super.scaleTexture(w, h);
        collisionRadius = Math.max(w, h)/2; //TODO: Collisions
    }
    public int getCollisionRadius(){
        return collisionRadius;
    }
    public boolean isCollidingWith(GridObject other){
        return collisionRadius+other.getCollisionRadius()>=distanceTo(other); //TODO: Collisions
    }
    public GridEntity getOneCollidingObject(){
        List<GridEntity> o = getCollidingObjects();
        return o.size()>0?o.get(0):null;
    }
    public List<GridEntity> getCollidingObjects(){
        ArrayList<GridEntity> gs = new ArrayList<GridEntity>();
        for(GridEntity g:getWorld().allEntities()){
            if(this!=g&&isCollidingWith(g)){
                gs.add(g);
            }
        }
        return gs;
    }
    public List<GridEntity> getCollidingGEs(){
        List<GridEntity> ges = (List<GridEntity>)getCollidingObjects();
        return ges.stream().filter(g->checkCollisionHeight(g)).collect(Collectors.toList());
    }
    public boolean checkCollisionHeight(GridEntity other){
        return Math.abs(other.getHeight()-getHeight())<5;
    }
    public double getPower(){
        return powermultiplier*basepower;
    }
    public void setPower(double perc, EffectID ctrl){
        if(perc==1&&powerAffecters.containsKey(ctrl))powerAffecters.remove(ctrl);
        else powerAffecters.put(ctrl, perc);
        double high = 1, low = 1;
        for(double p: powerAffecters.values()){
            if(p<1&&p<low){
                low = p;
            }else if(p>1&&p>high){
                high = p;
            }
        }
        powermultiplier = high*low;
    }
    public void setPower(double perc){
        basepower = perc;
    }
    
    public void heal(GridEntity targ, int amt){
        targ.heal((int)(amt*getPower()), this);
    }
    
    public void damage(GridEntity targ, int amt){
        damage(targ, amt, 1);
    }
    public void damage(GridEntity targ, int amt, double exposure){
        targ.hit((int)(amt*getPower()), exposure, this);
    }
    public void damageIgnoreShield(GridEntity targ, int amt){
        damageIgnoreShield(targ, amt, 1);
    }
    public void damageIgnoreShield(GridEntity targ, int amt, double exposure){
        targ.hitIgnoreShield((int)(amt*getPower()), exposure, this);
    }
    public boolean mountFixed(KActor other){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<KActor, Vector>();
        mounts.put(other, new Vector(other.getX()-getX(), other.getY()-getY(), 0));
        mounts.get(other).setDirection(mounts.get(other).getDirection());
        other.notifyMount(this);
        other.setMountFixed(true);
        getWorld().requestMoveToAfter(this, other);
        return true;
    }
    public boolean mount(KActor other){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<KActor, Vector>();
        mounts.put(other, new Vector(other.getX()-getX(), other.getY()-getY(), 0));
        mounts.get(other).setDirection(mounts.get(other).getDirection()-getRotation());
        other.notifyMount(this);
        getWorld().requestMoveToAfter(this, other);
        return true;
    }
    public boolean mount(KActor other, double deg, double dist){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<KActor, Vector>();
        boolean isNewMount = mounts.get(other)==null;
        mounts.put(other, new Vector(deg, dist));
        if(isNewMount)other.notifyMount(this);
        getWorld().requestMoveToAfter(this, other);
        return true;
    }
    public boolean mount(KActor other, double x, double y, double height){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<KActor, Vector>();
        boolean isNewMount = mounts.get(other)==null;
        mounts.put(other, new Vector(x, y, height));
        if(isNewMount)other.notifyMount(this);
        return true;
    }
    public boolean unmount(KActor other){
        if(mounts!=null){
            if(updatingMounts){
                if(mounts.containsKey(other)){
                    other.markUnmount();
                    other.notifyUnmount(this);
                    return true;
                }
                return false;
            }
            boolean removed = mounts.remove(other)!=null;
            if(removed)other.notifyUnmount(this);
            return removed;
        }
        return false;
    }
    public void unmountAll(){
        if(mounts!=null){
            for(var g: mounts.entrySet()){
                g.getKey().notifyUnmount(this);
            }
            mounts = null;
        }
    }
    
    public void clearMounting(){
        super.clearMounting();
        if(mounts!=null){
            for(var g: mounts.entrySet()){
                g.getKey().notifyUnmount(this);
            }
            mounts = null;
        }
    }
    public void broadcastEvent(String event){//TODO: Add data
        Iterator<EventListener> i = listeners.iterator();
        while(i.hasNext()){
            EventListener f = i.next();
            if(f.on(event, this)){
                i.remove();
            }
        }
    }
    public void addEventListener(EventListener e){
        listeners.add(e);
    }
    public void removeEventListener(EventListener e){
        listeners.remove(e);
    }
    //If this object cannot be passed through by other objects with hitboxes
    public boolean isWall(){
        return !canBePulled();
    }
    //If objects should process hit when colliding with this object
    public boolean hasHitbox(){
        return false;
    }
    public void render(){
        animate();
        super.render();
        renderMounts();
    }
    public void animate(){}
    public void update(){
        super.update();
        updateMounts();
        clearTargetCache();
    }
    // cache target for the current update cycle
    public boolean useTargetCache(){
        return true;
    }
    public void notifyWorldRemove(){
        leaveTeam();
        getWorld().allObjects().remove(this);
        super.notifyWorldRemove();
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        getWorld().allObjects().add(this);
        updateTeam();
    }
    public boolean isInGridWorld(){
        return true;
    }
}

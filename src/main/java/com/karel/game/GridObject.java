package com.karel.game;
import java.util.Objects;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.karel.game.particles.Explosion;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents an object inside the game world
 * 
 * @author MinecraftJack64
 * @version 1.0
 */
public abstract class GridObject extends KActor
{
    private HashMap<GridObject, Vector> mounts;
    private GridObject mounter;
    String team;
    String faketeam;
    String joinedteam;
    String oldteam;
    private Arc arcmomentum;
    private double dirmomentum;
    private double powermultiplier = 1;
    private int arcframe = 0;
    private boolean grounded;
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
        if(Objects.equals(getTeam(), joinedteam)){
            return;
        }
        if(joinedteam!=null){
            getWorld().getTeams().leaveTeam(this, joinedteam);
        }
        getWorld().getTeams().joinTeam(this, getTeam());
        joinedteam = getTeam();
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
    public void setRealLocation(double x, double y){
        super.setRealLocation(x, y);
        updateMounts();
    }
    public void setRealRotation(double rot){
        super.setRealRotation(rot);
        updateMounts();
    }
    public void updateMounts(){
        if(mounts!=null){
            for(var g: mounts.entrySet()){
                g.getKey().branchOut(this, g.getValue().getDirection()+getRealRotation(), g.getValue().getLength(), g.getValue().getHeight());
            }
        }
    }
    public double face(GridObject obj, boolean face){
        return face(obj.getRealX(), obj.getRealY(), face);
    }
    public double face(double x, double y, boolean face){
        double targang = getAngle(x, y)+90;
        double monangle = targang;
        if(face)setRealRotation(monangle);
        return monangle;
    }
    public double getFacingOffset(GridObject other){
        return face(other, false)-getTargetRotation();
    }
    public double getFacingDistance(GridObject other){
        return Math.abs(getFacingOffset(other));
    }
    public double getTargetRotation(){
        return getRealRotation();
    }
    public float getAngle(double x, double y) {
        float angle = (float) Math.toDegrees(Math.atan2(y - getRealY(), x - getRealX()));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    public double distanceTo(double x, double y){
        return Math.sqrt(Math.pow(x-getRealX(), 2)+Math.pow(y-getRealY(), 2));
    }
    public double distanceTo(double x, double y, double z){
        return Math.sqrt(Math.pow(x-getRealX(), 2)+Math.pow(y-getRealY(), 2)+Math.pow(z-getRealHeight(), 2));
    }
    public double distanceTo(GridObject obj){
        return distanceTo(obj.getRealX(),obj.getRealY(), obj.getRealHeight());
    }
    public double getRandomCellX(){
        return getWorld().gridXToRealX(Greenfoot.getRandomNumber(getWorld().gridwidth));
    }
    public double getRandomCellY(){
        return getWorld().gridYToRealY(Greenfoot.getRandomNumber(getWorld().gridheight));
    }
    
    public double getXAtOffset(int val){
        return getWorld().gridXToRealX(getWorld().realXToGridX(getRealX())+val);
    }
    public double getYAtOffset(int val){
        return getWorld().gridYToRealY(getWorld().realYToGridY(getRealY())+val);
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
    public void notifyPull(){}
    public boolean pullTo(double x, double y){
        if(canBePulled()){
            setRealLocation(x, y);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTo(double x, double y, double h){
        if(canBePulled()){
            setRealLocation(x, y, h);
            notifyPull();
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
    public boolean pull(double ang, double speed){
        if(canBePulled()){
            move(ang, speed);
            notifyPull();
            return true;
        }else{
            return false;
        }
    }
    public boolean pullTowards(GridObject targ, double speed){
        if(distanceTo(targ)<=speed/2){
            return pullTo(targ.getRealX(), targ.getRealY());
        }
        else{
            return pull(face(targ, false), speed);
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
    public boolean knockBack(double r, double d, double h, GridObject source){
        if(!canBePulled()){
            return false;
        }
        initiateJump(r, d, h);
        return true;
    }
    public void notifyDamage(GridEntity target, int amt){}
    public boolean isAggroTowards(GridObject other){
        return getWorld().getTeams().getAggressions(getTeam()).contains(other.getTeam());
    }
    public boolean isAlliedWith(GridObject other){
        return getWorld().getTeams().getAllies(getTeam()).contains(other.getTeam());
    }
    public void die(){
        clearFakeTeam();
        clearTeam();
    }
    public void revive(){
        setTeam(oldteam);
    }
    public int explodeOn(int range, String filter, Consumer<GridEntity> vore, Explosion exp){
        List<GridEntity> l = getGEsInRange(range);
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
                    }
                break;
                case "enemy":
                    if(isAggroTowards(g)){
                        vore.accept(g);
                    }
                break;
                default://all
                    vore.accept(g);
                break;
            }
        }
        return l.size();
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
    public GridEntity getNearestTarget() {
        GridEntity nearestTarget = null;
        double closestDistance = 0;
    
        for (GridEntity entity : this.getWorld().allEntities) {
            if (isPotentialTarget(entity)) {
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
        setRealHeight(arcmomentum.getHeight(arcframe));
        arcframe++;
        if(getRealHeight()<0&&arcframe>0){
            arcmomentum = null;
            dirmomentum = 0;
            arcframe = 0;
            setRealHeight(0);
            doLanding();
        }
    }
    public boolean isOnGround(){
        return getRealHeight()<=0;
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
    public void addObjectHere(GridObject obj){
        getWorld().addObject(obj, getRealX(), getRealY());
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
    public <T extends GridObject> List<T> getGOsInRange(int rng, Class<T> cls){
        ArrayList<T> gs = new ArrayList<>();
        for(GridObject g:getWorld().allObjects()){
            if(distanceTo(g)<=rng&&cls.isInstance(g)){
                gs.add(cls.cast(g));
            }
        }
        return gs;
    }
    public GridEntity getOneCollidingObject(){
        return getCollidingObjects().get(0);
    }
    public List<GridEntity> getCollidingObjects(){
        return getGEsInRange(70);//TODO
    }
    public List<GridEntity> getCollidingGEs(){
        List<GridEntity> ges = (List<GridEntity>)getCollidingObjects();
        return ges.stream().filter(g->checkCollisionHeight(g)).collect(Collectors.toList());
    }
    public boolean checkCollisionHeight(GridEntity other){
        return Math.abs(other.getRealHeight()-getRealHeight())<5;
    }
    public double getPower(){
        return powermultiplier;
    }
    public void setPower(double perc, EffectID ctrl){
        powermultiplier = perc;
    }
    public void setPower(double perc){
        powermultiplier = perc;
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
    
    public boolean mount(GridObject other){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<GridObject, Vector>();
        mounts.put(other, new Vector(other.getRealX()-getRealX(), other.getRealY()-getRealY(), 0));
        other.notifyMount(this);
        return true;
    }
    public boolean mount(GridObject other, double deg, double dist){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<GridObject, Vector>();
        mounts.put(other, new Vector(deg, dist));
        other.notifyMount(this);
        return true;
    }
    public boolean mount(GridObject other, double x, double y, double height){
        if(!isInWorld())return false;
        if(mounts==null)mounts = new HashMap<GridObject, Vector>();
        mounts.put(other, new Vector(x, y, height));
        other.notifyMount(this);
        return true;
    }
    public boolean unmount(GridObject other){
        if(mounts!=null){
            boolean removed = mounts.remove(other)!=null;
            if(removed)other.notifyUnmount(this);
            return removed;
        }
        return false;
    }
    
    public void notifyMount(GridObject other){
        if(hasMounter())mounter.unmount(this);
        mounter = other;
    }
    public void notifyUnmount(GridObject other){
        mounter = null;
    }
    
    public void clearMounting(){
        if(hasMounter())mounter.unmount(this);
        if(mounts!=null){
            for(var g: mounts.entrySet()){
                g.getKey().notifyUnmount(this);
            }
            mounts = null;
        }
    }
    
    public GridObject getMounter(){
        return mounter;
    }
    
    public boolean hasMounter(){
        return mounter!=null;
    }
    public boolean isWall(){
        return !canBePulled();
    }
    public void render(){animate();}
    public void animate(){}
    public void update(){}
    public void notifyWorldRemove(){
        super.notifyWorldRemove();
        getWorld().allObjects().remove(this);
        clearMounting();
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        getWorld().allObjects().add(this);
    }
    public boolean isInGridWorld(){
        return true;
    }
}

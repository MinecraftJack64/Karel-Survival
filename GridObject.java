import greenfoot.*;
import java.util.Objects;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.List;

/**
 * Write a description of class GridObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class GridObject extends KActor
{
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
    public void inherit(GridObject other){
        setTeam(other.getTeam());
        setPower(other.getPower());
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
        return !isGrounded();
    }
    public void notifyPull(){
        //
    }
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
        List<GridEntity> l = getObjectsInRange(range, GridEntity.class);
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
    public int explodeOn(int range, int dmg, Explosion exp){
        if(dmg>=0){
            return explodeOn(range, "enemy", (g)->{damage(g, dmg);}, exp);
        }else{
            return explodeOn(range, "ally", (g)->{heal(g, dmg);}, exp);
        }
    }
    public GridEntity getNearestTarget() {
        GridEntity res = null;
        double max = 0.0D;
        Iterator var4 = this.getWorld().allEntities.iterator();

        while(true) {
            GridEntity e;
            do {
                do {
                    if (!var4.hasNext()) {
                        return res;
                    }

                    e = (GridEntity)var4.next();
                } while(!this.isAggroTowards(e));
            } while(!(this.distanceTo(e) < max) && res != null);

            res = e;
            max = this.distanceTo(e);
        }
    }
    public double getGravity(){
        return 3;
    }
    public void applyphysics(){
        if(arcmomentum==null){
            return;
        }
        move(dirmomentum, arcmomentum.getRate());// = -(x^2+bx)/b
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
    public Arc getPhysicsArc(){
        return arcmomentum;
    }
    public void initiateJump(double direction, double distance, double height){
        arcmomentum = new Arc(distance, height, getGravity());
        dirmomentum = direction;
        arcframe = 0;
    }
    public boolean covertDamage(){
        return false;
    }
    public boolean willNotify(GridObject source){
        return true;
    }
    public void doLanding(){}
    public void addObjectHere(GridObject obj){
        getWorld().addObject(obj, getRealX(), getRealY());
    }
    public List<GridEntity> getGEsInRange(int rng){
        return getObjectsInRange(rng, GridEntity.class);
    }
    public double getPower(){
        return powermultiplier;
    }
    public void setPower(double perc){
        powermultiplier = perc;
    }
    
    public void heal(GridEntity targ, int amt){
        targ.heal((int)(amt*getPower()), this);
    }
    
    public void damage(GridEntity targ, int amt){
        targ.hit((int)(amt*getPower()), this);
    }
    public void act(){
        if(getWorld().isPaused())return;
        kAct();
        animate();
    }
    public boolean isWall(){
        return !canBePulled();
    }
    public void animate(){
        //nothing by default
    }
    public void kAct(){
        //
    }
}

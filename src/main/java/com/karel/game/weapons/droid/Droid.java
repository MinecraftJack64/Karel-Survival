package com.karel.game.weapons.droid;

import com.karel.game.BombDropper;
import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Location;
import com.karel.game.Pet;
import com.karel.game.Player;
import com.karel.game.TickingTimeBomb;
import com.karel.game.Vector;
import com.karel.game.effects.EffectID;
import com.karel.game.effects.PullEffect;
import com.karel.game.effects.StunEffect;
import com.karel.game.physics.Dasher;
import com.karel.game.physics.DasherDoer;
import com.karel.game.weapons.mortar.ShotLeader;
import com.raylib.Texture;

/**
 * Write a description of class Critter here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Droid extends Pet {

    public static Texture rocket = Greenfoot.loadTexture("rocket.png");
    public static Texture rocket2 = Greenfoot.loadTexture("rocketWithThrust.png");
    private Texture crosshair = Greenfoot.loadTexture("Controls/target.png");
    private GridEntity target;
    private ShotLeader leader;
    private Location patrol, real;
    private DroidController remote;
    private int ammo = 0;
    private int life = -1;
    private boolean overridden = false;
    private int forcerepatrolcooldown = 30; // 30 cooldown to recalculate patrol location
    private Dasher dash;
    private int mode = 0;
    private int ult = 0; // 300
    private static final int laserrange = 400, patrolrange = 250;

    /**
     * Initilise this rocket.
     */
    public Droid(DroidController spawner, GridEntity hive) {
        super(hive);
        setImage(rocket);
        scaleTexture(50, 50);
        startHealth(1500);
        setSpeed(4);
        if (hive != null)
            inherit(hive);
        remote = spawner;
    }

    /**
     * Do what a rocket's gotta do. (Which is: mostly flying about, and turning,
     * accelerating and shooting when the right keys are pressed.)
     */
    public void behave() {
        double monangle = face(getTarget(), dash == null && canMove());
        ammo++;
        if (dash != null) {
            if (!dash.dash()) {
                dash = null;
            }
        } if(overridden&&remote.isMainWeapon()){
            if(remote.getHolder().isDead()){
                remote.cancelOverride();
                return;
            }
            remote.fire();
            GridEntity g = remote.getHolder();
            GridEntity targ = g.getTarget();
            if(targ==null){
                targ = this;
            }
            if(leader==null){
                leader = new ShotLeader(targ, g);
                g.addObjectHere(leader);
            }
            if(leader.getTarget()!=targ){
                leader = null;
            }
            double gang = g.face(targ, g.canMove());
            while(real==null){
                choosePatrolArea(getX(), getY());
                double d = targ.distanceTo(real.x, real.y);
                if (d > laserrange && d < laserrange + patrolrange) {
                    real = null;
                } else {
                    forcerepatrolcooldown = 30;
                }
            }
            if (g.distanceTo(targ) < laserrange / 2) {
                g.walk(gang + 180, 1.2);
            } else if (real != null) {
                g.walk(g.face(real.x, real.y, false), 1);
                if (g.distanceTo(real.x, real.y) < g.getSpeed()) {
                    real = null;
                }
                forcerepatrolcooldown--;
                if (forcerepatrolcooldown == 0) {
                    real = null;
                }
            }
            Vector v = ((Player)remote.getHolder()).getMovementControlVector();
            walk(v.getDirection()+90, v.getLength());
            face(getWorld().getGridMouseX(), getWorld().getGridMouseY(), canMove());
            ammo++; // double ammo in mode
            if(ammo > reloadtime&&canAttack()&&Game.isAttackDown()||Game.getInputMethod().equals("keyboard")&&Greenfoot.isActive("attack")){
                attack();
                ammo = 0;
            }
            if(Greenfoot.isActive("ult")){
                remote.ult();
            }
        }else if (mode == 0 || mode == 3) {
            if (remote.getAttackUpgrade() == 1) {
                if(leader==null&&distanceTo(getTarget()) <= laserrange){
                    leader = new ShotLeader(getTarget(), this);
                    getTarget().addObjectHere(leader);
                }else if(distanceTo(getTarget()) > laserrange){
                    if(leader!=null)getWorld().removeObject(leader);
                    leader = null;
                }
            }
            if (distanceTo(getTarget()) <= laserrange && ammo > reloadtime && canAttack()
                    && (mode == 0 && isAggroTowards(getTarget()) || mode == 3 && isAlliedWith(getTarget()))) {
                attack();
                ammo = 0;
            }
            if (target != null)
                if (distanceTo(getTarget()) > laserrange) {
                    walk(monangle, 1);
                }
            while (real == null && (patrol != null || target == null)) {
                if (patrol != null) {
                    choosePatrolArea(patrol.x, patrol.y);
                } else {
                    choosePatrolArea(getSpawner().getX(), getSpawner().getY());
                }
                double d = getTarget().distanceTo(real.x, real.y);
                if (d > laserrange && d < laserrange + patrolrange) {
                    real = null;
                } else {
                    forcerepatrolcooldown = 30;
                }
            }
            if (distanceTo(getTarget()) < laserrange / 2 && mode == 0) {
                walk(monangle + 180, 1.2);
            } else if (real != null) {
                walk(face(real.x, real.y, false), 1);
                if (distanceTo(real.x, real.y) < getSpeed()) {
                    real = null;
                }
                forcerepatrolcooldown--;
                if (forcerepatrolcooldown == 0) {
                    real = null;
                }
            }
        } else if (mode == 1) {
            if (distanceTo(getTarget()) > laserrange) {
                walk(monangle, 1);
            } else if (distanceTo(getTarget()) > 20) {
                walk(monangle, 1.2);
            }
            attack();
        } else if (mode == 2) {
            if (distanceTo(getTarget()) > laserrange) {
                walk(monangle, 1);
            } else if (ammo > reloadtime && canAttack() && isAggroTowards(getTarget())) {
                attack();
                ammo = 0;
            }
            if (distanceTo(getTarget()) > 30) {
                walk(monangle, 1.2);
                ult();
            }
        }
        if(life>0){
            life--;
            remote.updateSpecial(life);
            if(life==0){
                remote.disableSpecial();
                kill(this);
            }
        }
    }

    public GridEntity getTarget() {
        if (target != null) {
            if (target.isDead()) {
                target = null;
            } else {
                return target;
            }
        }
        return super.getTarget();
    }

    public void choosePatrolArea(double x, double y) {
        Vector v = new Vector(Greenfoot.getRandomNumber(360), Greenfoot.getRandomNumber(patrolrange));
        real = new Location(x + v.getX(), y + v.getY());
    }

    public void animate() {
        //
    }

    public boolean isPotentialTarget(GridEntity g) {
        return mode == 3 ? g.getHealth() < g.getMaxHealth() && isAlliedWith(g) && g!=this : super.isPotentialTarget(g);
    }

    private static final int reloadtime = 30;

    public void setLife(int d){
        life = d;
    }

    public void attack() {
        double ang = getRotation();
        if(leader!=null){
            Location l = leader.getShotPrediction((int)(distanceTo(leader)/20));
            ang = face(l.x, l.y, true);
            getWorld().removeObject(leader);
            leader = null;
        }
        switch (mode) {
            case 0:
                addObjectHere(new DroidLaser(ang, this));
                break;
            case 1:
                addObjectHere(new DrillBit(ang, this));
                break;
            case 2:
                for (int i = -45; i < 45; i++) {
                    addObjectHere(new DroidShot(ang + i, this));
                }
                break;
            case 3:
                addObjectHere(new DroidHealLaser(ang, this));
        }
    }

    public void fallBack() {
        face(getSpawner(), true);
        int s = 15, l = (int) (distanceTo(getSpawner()) / s);
        dash = new Dasher(getRotation(), s, l, this);
        dash.dash();
        getSpawner().applyEffect(new PullEffect(getSpawner().face(this, true), s, l, getSpawner()));
        heal(getSpawner(), 500);
        heal(this, 500);
    }

    public void jailbreak(double direction, double distance) {
        addObjectHere(new BombDropper(direction, Math.min(400, distance), 300, new TickingTimeBomb(5, this), this));
    }

    public void override(){
        remote.getHand().setControlLock(true);
        overridden = true;
    }

    public void cancelOverride(){
        remote.getHand().setControlLock(false);
        overridden = false;
    }

    public double getOverrideTargetX(){
        if(leader!=null)return leader.getShotPrediction(getGShotTime(leader.getTarget())).x;
        if(remote.getHolder().getTarget()==null)return getX();
        return remote.getHolder().getTarget().getX();
    }

    private int getGShotTime(GridObject target2) {
        GridEntity g = getSpawner();
        Wrench w = new Wrench(g.getRotation(), g.distanceTo(target2), g.distanceTo(target2), this, remote);
        return (int)w.getPath().getDuration();
    }

    public double getOverrideTargetY(){
        if(leader!=null)return leader.getShotPrediction(getGShotTime(leader.getTarget())).y;
        if(remote.getHolder().getTarget()==null)return getY();
        return remote.getHolder().getTarget().getY();
    }

    public double getOverrideTargetRotation(){
        return remote.getHolder().face(getOverrideTargetX(), getOverrideTargetY(), false);
    }

    public void dash(double direction) {
        setRotation(direction);
        dash = new DasherDoer(getRotation(), 20, 25, 50, 150, this);
        dash.dash();
    }

    public void die(GridObject source){
        if(leader!=null){
            getWorld().removeObject(leader);
            leader = null;
        }
        super.die(source);
        if(life>0){
            remote.disableSpecial();
        }
    }

    public void setMode(int m) {
        mode = m;
        leader = null;
        if(mode==3&&target!=null&&isAggroTowards(target)||mode==0&&target!=null&&isAlliedWith(target)){
            target = null;
        }
        switch(mode){
            case 0:
                knockBackOnEnemies(100, 150);
                break;
            case 1:
                if(isAggroTowards(getTarget())&&distanceTo(getTarget())<400){
                    getTarget().applyEffect(new PullEffect(getTarget().face(this, false), 10, 10, this));
                }
                break;
            case 2:
                explodeOn(100, (g)->{
                    g.applyEffect(new StunEffect(30, this, new EffectID(this)));
                });
                break;
            case 3:
                explodeOn(100, -500);
        }
    }

    public void setTarget(Location l) {
        patrol = l;
        target = null;
    }

    public void setTarget(GridEntity g) {
        target = g;
        patrol = null;
    }

    public void ult() {
        if (ult > 300) {
            dash = new Dasher(getRotation(), 15, 8, this);
            dash.dash();
            ult = 0;
        }
    }

    public void render() {
        super.render();
        int scale = 50;
        if (target != null && target.isInWorld()) {
            target.renderTexture(crosshair, target.getX(), target.getY(), scale, scale, 0, 127);
        }
        if (patrol != null) {
            renderTexture(crosshair, patrol.x, patrol.y, scale, scale, 0, 127);
        }
    }

    public void notifyDamage(GridEntity target, int amt) {
        ult += amt;
        super.notifyDamage(target, amt);
    }

    public Droid clone(){
        Droid n = new Droid(remote, getSpawner());
        n.setMode(getMode());
        if(target!=null)n.setTarget(target);
        else if(patrol!=null)n.setTarget(patrol);
        n.setHealth(getHealth());
        return n;
    }

    public int getMode() {
        return mode;
    }

    public String getPetID() {
        return "droid-droid";
    }
}

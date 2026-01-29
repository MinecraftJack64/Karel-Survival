package com.karel.game.weapons.lanternlobber;
import com.karel.game.GridObject;
import com.karel.game.gridobjects.hitters.Bullet;

/**
 * A bullet that can hit asteroids.
 * 
 * @author Poul Henriksen
 */
public class Dragon extends Bullet
{
    public static final int speed = 8, turn = 4;
    DragonFood bait;
    private boolean prevInTurn;
    public Dragon(double rotation, DragonFood bait, GridObject source)
    {
        super(rotation, source);
        setSpeed(speed);
        setImage("button-blue.png");
        setNumTargets(-1);
        setDamage(200);
        this.bait = bait;
    }
    public void applyPhysics(){
        setLife(2);
        setRotation(getDirection());
        double ang = getFacingOffset(bait);
        if(bait.dropped()&&(baitInTurnCircles(0.9)||baitInTurnCircles(1)&&prevInTurn)){
            if(ang<0){
                setDirection(getDirection()+turn);
            }else{
                setDirection(getDirection()-turn);
            }
            prevInTurn = true;
        }
        else{
            if(ang<0){
                setDirection(getDirection()-turn);
            }else{
                setDirection(getDirection()+turn);
            }
            prevInTurn = false;
        }
        super.applyPhysics();
    }
    public void update(){
        super.update();
        if(distanceTo(bait)<getSpeed()*2){
            bait.die();
            die();
        }
    }
    public boolean baitInTurnCircles(double t){
        double r = speed/Math.toRadians(turn)*t;
        double clx = getBranchX(getDirection(), r)+getX(), cly = getBranchY(getDirection(), r)+getY();
        double crx = getBranchX(getDirection()+180, r)+getX(), cry = getBranchY(getDirection()+180, r)+getY();
        //renderTexture(getImage(), clx, cly, r*2, r*2, 0, 127);
        //renderTexture(getImage(), crx, cry, r*2, r*2, 0, 127);
        return bait.distanceTo(clx, cly)<r||bait.distanceTo(crx, cry)<r;
    }
}
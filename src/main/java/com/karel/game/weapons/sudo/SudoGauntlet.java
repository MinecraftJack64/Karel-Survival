package com.karel.game.weapons.sudo;
import java.util.List;

import com.karel.game.GridEntity;
import com.karel.game.GridObject;
import com.karel.game.Hitter;
import com.karel.game.ItemAccepter;
import com.karel.game.ItemFactory;
import com.karel.game.gridobjects.gridentities.zombies.ZombieFactory;

/**
 * Write a description of class TeslaCoilZap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SudoGauntlet extends Hitter
{
    private int attacked;
    private boolean carrying = false;
    private int carryingCooldown = 0;
    public SudoGauntlet(GridObject source)
    {
        super(source);
        attacked = 0;
        setVisible(false);
    }
    public void attackAt(double deg, double dist, int range, boolean scaled, boolean ult, int dmg){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        if(ult){
            List<GridEntity> l = getGEsInRange(range);
            if(l.size()>0){
                for(GridEntity g:l){
                    if(isAggroTowards(g))damage(g, scaled?(int)(dmg*g.getMaxHealth()/200.0):dmg);
                }
            }
        }else{
            if(getNearestTarget()!=null&&isAggroTowards(getNearestTarget())){
                damage(getNearestTarget(), scaled?(int)(dmg*getNearestTarget().getMaxHealth()/200.0):dmg);
            }
        }
    }
    public void healAt(double deg, double dist, int range, boolean scaled, boolean ult, int dmg){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        if(ult){
            List<GridEntity> l = getGEsInRange(range);
            if(l.size()>0){
                for(GridEntity g:l){
                    if(isAggroTowards(g))heal(g, scaled?(int)(dmg*g.getMaxHealth()/200.0):dmg);
                }
            }
        }else{
            if(getNearestTarget()!=null&&isAggroTowards(getNearestTarget())){
                heal(getNearestTarget(), scaled?(int)(dmg*getNearestTarget().getMaxHealth()/200.0):dmg);
            }
        }
    }
    public void move(double deg, double dist, int range, boolean ult){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        if(!carrying){
            if(ult){
                List<GridEntity> l = getGEsInRange(range);
                for(GridEntity g:l){
                    if(isAggroTowards(g)){
                        mount(g, face(g, false)-90, distanceTo(g));
                    }
                }
            }else{
                if(getNearestTarget()!=null&&isAggroTowards(getNearestTarget())){
                    mount(getNearestTarget(), face(getNearestTarget(), false)-90, distanceTo(getNearestTarget()));
                }
            }
            carrying = true;
        }
        carryingCooldown = 2;
    }
    public void applyTeam(double deg, double dist, int range, boolean ult, String team){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        if(ult){
            List<GridEntity> l = getGEsInRange(range);
            if(l.size()>0){
                for(GridEntity g:l){
                    g.setTeam(team);
                }
            }
        }else{
            if(getNearestOtherGE()!=null){
                getNearestOtherGE().setTeam(team);
            }
        }
    }
    public void applyWeapon(double deg, double dist, int range, boolean ult, String weaponID){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        if(ult){
            List<GridEntity> l = getGEsInRange(range);
            if(l.size()>0){
                for(GridEntity g:l){
                    if(g instanceof ItemAccepter a)a.acceptItem(ItemFactory.createItem(weaponID, a.getHand()));
                }
            }
        }else{
            GridEntity g = getNearestOtherGE();
            if(g instanceof ItemAccepter a){
                a.acceptItem(ItemFactory.createItem(weaponID, a.getHand()));
            }
        }
    }
    public void summonZombie(double deg, double dist, String id){
        attacked = 1;
        branchOut(getSource(), deg, dist);
        setVisible(true);
        GridEntity z = ZombieFactory.createZombie(id);
        getSource().getWorld().addObject(z, getX(), getY());
    }
    public void update(){
        if(attacked>0){
            attacked--;
        }else{
            if(carryingCooldown>0){
                carryingCooldown--;
                if(carryingCooldown==0){
                    carrying = false;
                    unmountAll();
                }
            }else{
                if(getSource()!=null)setLocation(getSource().getX(), getSource().getY());
                setVisible(false);
            }
        }
        if(getSource()!=null&&(!(getSource() instanceof GridEntity)||!((GridEntity)getSource()).isDead())&&!getSource().getTeam().equals(getTeam())){//make sure team is same as source
            setTeam(getSource().getTeam());
        }
        super.update();
    }
}

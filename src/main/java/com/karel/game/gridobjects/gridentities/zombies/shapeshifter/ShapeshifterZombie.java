package com.karel.game.gridobjects.gridentities.zombies.shapeshifter;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.GridEntity;
import com.karel.game.Greenfoot;
import com.karel.game.Possessor;
import com.karel.game.ZombieClass;

import java.util.ArrayList;
import java.util.HashSet;

public class ShapeshifterZombie extends Zombie implements Possessor{
    private static ZombieClass[] classes = new ZombieClass[]{ZombieClass.pressurer, ZombieClass.ranger};
    private GridEntity model, possessed;
    private HashSet<GridEntity> priorModels = new HashSet<GridEntity>();
    private boolean shapeshifting;
    private int shapeshiftCooldown = 0; // 20
    private int shapeshiftingCooldown = 0; // 20
    public ShapeshifterZombie(){
        startHealth(400);
        setImage("shapeshiftzareln.png");
        setSpeed(3);
    }
    public void behave(){
        if(!shapeshifting){
            super.behave();
            setImage("shapeshiftzareln.png");
        }else{
            setImage("workingshapeshiftzareln.png");
        }
        if(!shapeshifting){
            if(shapeshiftCooldown>0){
                shapeshiftCooldown--;
                return;
            }
            model = searchModel();
            if(model!=null){
                shapeshiftingCooldown = 20;
                shapeshifting = true;
            }else shapeshiftCooldown = 20;
        }else{
            if(model==null){
                shapeshifting = false;
                return;
            }
            if(shapeshiftingCooldown>0){
                shapeshiftingCooldown--;
                return;
            }
            try{
                possessed = model.getClass().getDeclaredConstructor().newInstance();
                addObjectHere(possessed);
                possessed.setPower(0.8);
                possessed.setExposure(1.2);
                trap();
                getWorld().removeObject(this);
                possessed.possess(this);
                priorModels.add(model);
            }catch(Exception e){
                model = null;
                possessed = null;
                shapeshiftCooldown = 20;
            }
            shapeshifting = false;
        }
    }
    public void tick(){
        if(model!=null&&model.isDead()){
            possessed.addObjectHere(this);
            possessed.unpossess(this);
            possessed.kill(this);
            untrap();
            heal(this, getMaxHealth());
            model = null;
            possessed = null;
        }
    }
    public void onDeath(){
        if(possessed == null)return;
        possessed.unpossess(this);
        possessed.addObjectHere(this);
        untrap();
        hit(50, this);
        shapeshiftCooldown = 20;
        model = null;
        possessed = null;
    }
    public ZombieClass[] getZombieClasses(){
        return classes;
    }
    public GridEntity searchModel(){
        ArrayList<GridEntity> options = new ArrayList<GridEntity>();
        for(GridEntity ge: getWorld().allEntities()){
            if(ge!=this&&isAlliedWith(ge)&&ge instanceof Zombie&&!priorModels.contains(ge)){
                options.add(ge);
            }
        }
        if(options.size()==0)return null;
        return options.get(Greenfoot.getRandomNumber(options.size()));
    }
}

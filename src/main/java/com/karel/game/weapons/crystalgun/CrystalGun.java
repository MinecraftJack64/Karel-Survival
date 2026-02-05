package com.karel.game.weapons.crystalgun;

import com.karel.game.ItemHolder;
import com.karel.game.trackers.SimpleAmmoManager;
import com.karel.game.weapons.Weapon;

/**
 * Write a description of class CrystalGun here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CrystalGun extends Weapon
{
    private static final int gunReloadTime = 25;
    private static final int ult = 1000;
    private Crystal crystal;
    public void fire(){
        if (getAmmo().hasAmmo()) 
        {
            getAmmo().useAmmo();
            for(int deg = 45; deg<=315; deg+=90){
                Shard mbullet = new Shard(deg, getHolder(), getAttackUpgrade()==1);
                getHolder().addObjectHere(mbullet);
            }
            if(getAttackUpgrade()==1){
                for(int deg = 0; deg<=270; deg+=90){
                    Echo mbullet = new Echo(deg, getHolder());
                    getHolder().addObjectHere(mbullet);
                }
            }
        }
    }
    public void fireUlt(){
        for(int deg = 0; deg<=270; deg+=90){
            Crystallizer mbullet = new Crystallizer(deg, getHolder(), getUltUpgrade()==1);
            getHolder().addObjectHere(mbullet);
        }
    }
    public void update(){
        super.update();
        if(crystal!=null&&!crystal.isDead()){
            if(continueGadget()){
                setContinueGadget(false);
            }
        }
    }
    public int defaultGadgets(){
        return 3;
    }
    public void onGadgetActivate(){
        setContinueGadget(true);
        crystal = new Crystal(getHolder(), getHolder());
        getHolder().addObjectHere(crystal);
    }
    public int getUlt(){
        return ult;
    }
    public CrystalGun(ItemHolder actor){
        super(actor);
        setAmmo(new SimpleAmmoManager(gunReloadTime, 1));
    }
    public String getName(){
        return "Crystal Gun";
    }
    public int getRarity(){
        return 4;
    }
    public BotGuide getBotGuide(){
        return new BotGuide();
    }
    public class BotGuide extends Weapon.BotGuide{
        public int getEffectiveRange(){
            return 390;
        }
        public int getIdealRange(){
            return 0;
        }
        public int getUltEffectiveRange(){
            return 350;
        }
        public int getUltIdealRange(){
            return 0;
        }
        public int getUltNumTargets(){
            return -1;
        }
        public boolean shouldUse(){
            return Math.abs((getHolder().face(getHolder().getTarget(), false)+45)%90)<=4||shouldUseUlt()&&getAttackUpgrade()==1;
        }
        //Cross pattern
        public boolean shouldUseUlt(){
            return Math.abs(getHolder().face(getHolder().getTarget(), false)%90)<=4;
        }
    }
}




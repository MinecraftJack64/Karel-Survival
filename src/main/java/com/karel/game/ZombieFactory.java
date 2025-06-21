package com.karel.game;

import com.karel.game.gridobjects.gridentities.zombies.Zombie;
import com.karel.game.gridobjects.gridentities.zombies.doctor.DoctorZombie;
import com.karel.game.gridobjects.gridentities.zombies.easter.EasterZombie;
import com.karel.game.gridobjects.gridentities.zombies.firebreather.FirebreatherZombie;
import com.karel.game.gridobjects.gridentities.zombies.fungal.FungalZombie;
import com.karel.game.gridobjects.gridentities.zombies.hivemind.HivemindZombie;
import com.karel.game.gridobjects.gridentities.zombies.laser.LaserZombie;
import com.karel.game.gridobjects.gridentities.zombies.rocket.RocketZombie;
import com.karel.game.gridobjects.gridentities.zombies.shooter.ShooterZombie;
import com.karel.game.gridobjects.gridentities.zombies.steak.SteakZombie;
import com.karel.game.gridobjects.gridentities.zombies.weedwacker.WeedwackerZombie;

/**
 * Write a description of class ZombieFactory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ZombieFactory  
{
    public static Zombie createZombie(String type){
        switch(type){
            case "zombie": return new Zombie();
            case "shield": return new ShieldZombie();
            case "exploding": return new ExplodingZombie();
            case "shooter": return new ShooterZombie();
            case "hardhat": return new HardHatZombie();
            case "hivemind": return new HivemindZombie();
            //case "zombee": return new Zombee();
            case "marksman": return new MarksmanZombie();
            case "rocket": return new RocketZombie();
            case "fungal": return new FungalZombie();
            case "ninja": return new NinjaZombie();
            case "doctor": return new DoctorZombie();
            case "hornetneck": return new HornetNeckZombie();
            case "hornet": return new Hornet();
            case "laser": return new LaserZombie();
            case "easter": return new EasterZombie();
            case "russiandoll": return new RussianDollZombie();
            case "firebreather": return new FirebreatherZombie();
            case "weedwacker": return new WeedwackerZombie();
            case "wizard": return new Wizard();
            case "portal": return new PortalZombie();
            case "warrior": return new WarriorZombie();
            case "splitter": return new SplitterZombie();
            case "light": return new LightZombie();
            case "heavy": return new HeavyZombie();
            //cop
            //shaman
            case "exorcist": return new ExorcistZombie();
            //case "evilspirit": return new EvilSpiritZombie();
            case "cloudserver": return new CloudServer();
            ////case "shapeshifter": return new ShapeshifterZombie();
            //chief
            //dancer
            case "tractorbeam": return new TractorBeamZombie();
            case "president": return new PresidentZombie();
            case "bodyguard": return new BodyguardZombie();
            case "joker": return new JokerZombie();
            //case "troubleshooter":
            //firing squad
            //inmate
            //gatherer
            //elder
            case "jackinthebox": return new JackITBZombie();
            //case "sick"
            //virus
            case "cannon": return new CannonZombie();
            case "stunt": return new StuntZombie();
            case "watermelon": return new WatermelonZombie();
            case "prisonbreak": return new JailBreakZombie();
            //tornado
            //chocolate
            //drone
            //helicopter
            case "breadboxer": return new BreadBoxerZombie();
            case "guardianangel": return new GuardianAngelZombie();
            //wizard apprentice
            //vacuum
            case "assassin": return new AssassinZombie();
            case "steak": return new SteakZombie();
            //milky
            //kitebaby
            //kitemother
            //bowling
            //case "laser": return new SuperLaserZombie();
            //challenger
            //stripper
            case "arson": return new ArsonZombie();
            //network
            //earthblock
        }
        return new Zombie();
    }
}

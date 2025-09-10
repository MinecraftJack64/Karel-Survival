package com.karel.game.gridobjects.gridentities.zombies;

import com.karel.game.AssassinZombie;
import com.karel.game.BodyguardZombie;
import com.karel.game.Bot;
import com.karel.game.CloudServer;
import com.karel.game.EvilSpiritZombie;
import com.karel.game.GridEntity;
import com.karel.game.HeavyZombie;
import com.karel.game.JackITBZombie;
import com.karel.game.JokerZombie;
import com.karel.game.LightZombie;
import com.karel.game.PresidentZombie;
import com.karel.game.SplitterZombie;
import com.karel.game.TorpedoZombie;
import com.karel.game.TractorBeamZombie;
import com.karel.game.WarriorZombie;
import com.karel.game.WatermelonZombie;
import com.karel.game.Wizard;
import com.karel.game.gridobjects.gridentities.zombies.arson.ArsonZombie;
import com.karel.game.gridobjects.gridentities.zombies.boid.BoidZombie;
import com.karel.game.gridobjects.gridentities.zombies.breadboxer.BreadBoxerZombie;
import com.karel.game.gridobjects.gridentities.zombies.cannon.CannonZombie;
import com.karel.game.gridobjects.gridentities.zombies.doctor.DoctorZombie;
import com.karel.game.gridobjects.gridentities.zombies.easter.EasterZombie;
import com.karel.game.gridobjects.gridentities.zombies.exorcist.DemonZombie;
import com.karel.game.gridobjects.gridentities.zombies.exorcist.ExorcistZombie;
import com.karel.game.gridobjects.gridentities.zombies.exploding.ExplodingZombie;
import com.karel.game.gridobjects.gridentities.zombies.firebreather.FirebreatherZombie;
import com.karel.game.gridobjects.gridentities.zombies.fungal.FungalZombie;
import com.karel.game.gridobjects.gridentities.zombies.guardianangel.GuardianAngelZombie;
import com.karel.game.gridobjects.gridentities.zombies.hardhat.HardHatZombie;
import com.karel.game.gridobjects.gridentities.zombies.herald.ZombieHerald;
import com.karel.game.gridobjects.gridentities.zombies.hivemind.*;
import com.karel.game.gridobjects.gridentities.zombies.hornetneck.Hornet;
import com.karel.game.gridobjects.gridentities.zombies.hornetneck.HornetNeckZombie;
import com.karel.game.gridobjects.gridentities.zombies.ironclad.IroncladZombie;
import com.karel.game.gridobjects.gridentities.zombies.jailbreak.JailBreakZombie;
import com.karel.game.gridobjects.gridentities.zombies.laser.LaserZombie;
import com.karel.game.gridobjects.gridentities.zombies.marksman.MarksmanZombie;
import com.karel.game.gridobjects.gridentities.zombies.mimic.MimicZombie;
import com.karel.game.gridobjects.gridentities.zombies.ninja.NinjaZombie;
import com.karel.game.gridobjects.gridentities.zombies.portal.PortalZombie;
import com.karel.game.gridobjects.gridentities.zombies.rocket.RocketZombie;
import com.karel.game.gridobjects.gridentities.zombies.russiandoll.RussianDollZombie;
import com.karel.game.gridobjects.gridentities.zombies.shapeshifter.ShapeshifterZombie;
import com.karel.game.gridobjects.gridentities.zombies.shield.ShieldZombie;
import com.karel.game.gridobjects.gridentities.zombies.shooter.ShooterZombie;
import com.karel.game.gridobjects.gridentities.zombies.steak.SteakZombie;
import com.karel.game.gridobjects.gridentities.zombies.stunt.StuntZombie;
import com.karel.game.gridobjects.gridentities.zombies.weedwacker.WeedwackerZombie;
import com.karel.game.gridobjects.gridentities.zombies.cowboy.CowboyZombie;

/**
 * Write a description of class ZombieFactory here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ZombieFactory {
    // ALL ZOMBIES
    private static String[] zombieTypes = {
            "zombie",
            "shield",
            "exploding",
            "shooter",
            "hardhat",
            "hivemind",
            "zombee",
            "marksman",
            "rocket",
            "fungal",
            "ninja",
            "doctor",
            "hornetneck",
            "hornet",
            "laser",
            "easter",
            "russiandoll",
            "firebreather",
            "weedwacker",
            "herald",
            "wizard",//
            "portal",
            "warrior",//
            "splitter",//
            "light",
            "heavy",
            "exorcist",
            "demon",
            "evilspirit",
            "cloudserver",//
            "shapeshifter",
            "tractorbeam",//
            "president",//
            "bodyguard",
            "joker",//
            "jackinthebox",//
            "cannon",
            "stunt",
            "watermelon",//
            "jailbreak",
            "breadboxer",
            "guardianangel",
            "assassin",//
            "steak",
            "arson",
            "mimic",
            "ironclad",
            "torpedo",//
            "cowboy",
            "boid",
            "bot" };

    public static GridEntity createZombie(String type) {
        switch (type) {
            case "zombie":
                return new Zombie();
            case "shield":
                return new ShieldZombie();
            case "exploding":
                return new ExplodingZombie();
            case "shooter":
                return new ShooterZombie();
            case "hardhat":
                return new HardHatZombie();
            case "hivemind":
                return new HivemindZombie();
            case "zombee":
                return new Zombee();
            case "marksman":
                return new MarksmanZombie();
            case "rocket":
                return new RocketZombie();
            case "fungal":
                return new FungalZombie();
            case "ninja":
                return new NinjaZombie();
            case "doctor":
                return new DoctorZombie();
            case "hornetneck":
                return new HornetNeckZombie();
            case "hornet":
                return new Hornet();
            case "laser":
                return new LaserZombie();
            case "easter":
                return new EasterZombie();
            case "russiandoll":
                return new RussianDollZombie();
            case "firebreather":
                return new FirebreatherZombie();
            case "weedwacker":
                return new WeedwackerZombie();
            case "herald":
                return new ZombieHerald();
            case "wizard":
                return new Wizard();
            case "portal":
                return new PortalZombie();
            case "warrior":
                return new WarriorZombie();
            case "splitter":
                return new SplitterZombie();
            case "light":
                return new LightZombie();
            case "heavy":
                return new HeavyZombie();
            // cop
            // shaman
            case "demon":
                return new DemonZombie();
            case "exorcist":
                return new ExorcistZombie();
            case "evilspirit":
                return new EvilSpiritZombie();
            case "cloudserver":
                return new CloudServer();
            case "shapeshifter":
                return new ShapeshifterZombie();
            // chief
            // dancer
            case "tractorbeam":
                return new TractorBeamZombie();
            case "president":
                return new PresidentZombie();
            case "bodyguard":
                return new BodyguardZombie();
            case "joker":
                return new JokerZombie();
            // case "troubleshooter":
            // firing squad
            // inmate
            // gatherer
            // elder
            case "jackinthebox":
                return new JackITBZombie();
            // case "sick"
            // virus
            case "cannon":
                return new CannonZombie();
            case "stunt":
                return new StuntZombie();
            case "watermelon":
                return new WatermelonZombie();
            case "jailbreak":
                return new JailBreakZombie();
            // tornado
            // chocolate
            // drone
            // helicopter
            case "breadboxer":
                return new BreadBoxerZombie();
            case "guardianangel":
                return new GuardianAngelZombie();
            // wizard apprentice
            // vacuum
            case "assassin":
                return new AssassinZombie();
            case "steak":
                return new SteakZombie();
            // milky
            // kitebaby
            // kitemother
            // bowling
            // case "laser": return new SuperLaserZombie();
            // challenger
            // stripper
            case "arson":
                return new ArsonZombie();
            // network
            // earthblock
            // factory
            // frisbee
            // buildingblock
            // emotionalsupport
            // smokey
            // decomposer
            // zomband
            // conductor
            // riotshield
            // concentrated
            // gladiator
            // ghost
            // beast
            // beermartyr
            // magma
            // gaslighter
            // mirror
            case "mimic":
                return new MimicZombie();
            // smartsman
            // alltrades
            // ribbondancer
            // camera
            // tapemeasure
            ////pirateship
            // pirate
            // grapplinghook
            // baseball
            case "ironclad":
                return new IroncladZombie();
            // bully
            // hairy
            // hairdresser
            // flow
            case "torpedo":
                return new TorpedoZombie();
            // piratecaptain
            // parrot
            ////camp
            // supernova
            // mole
            // telescreen
            // thoughtpolice
            // sticky
            case "cowboy":
                return new CowboyZombie();
            // pride
            // spy
            case "boid":
                return new BoidZombie(true);
            case "bot":
                return new Bot(null);
        }
        return new Zombie();
    }
    public static String[] getZombieTypes() {
        return zombieTypes;
    }
}

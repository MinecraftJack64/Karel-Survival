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
            //hornet
            case "laserblaster": return new LaserZombie();
            case "easterbunny": return new EasterZombie();
            case "russiandoll": return new RussianDollZombie();
            case "firebreather": return new FlamethrowerZombie();
            case "weedwhacker": return new WeedwackerZombie();
            case "wizard": return new Wizard();
            case "portal": return new PortalZombie();
            case "warrior": return new WarriorZombie();
            case "splitter": return new SplitterZombie();
            //light
            //heavy
            //cop
            //shaman
            case "exorcist": return new ExorcistZombie();
            //evil spirit
            //cloud server
            ////case "shapeshifter": return new ShapeshifterZombie();
            //chief
            //dancer
            //tractor beam
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
            //case "guardianangel"
            //wizard apprentice
            //vacuum
            //assassin
            //steak
            //milky
            //kitebaby
            //kitemother
            //bowling
            //case "laser": return new SuperLaserZombie();
            //challenger
            //stripper
            //arson
            //network
            //earthblock
        }
        return new Zombie();
    }
}

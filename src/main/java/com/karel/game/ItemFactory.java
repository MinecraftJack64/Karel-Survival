package com.karel.game;

import com.karel.game.weapons.acidart.Acidart;
import com.karel.game.weapons.airpump.AirPump;
import com.karel.game.weapons.blade.Blade;
import com.karel.game.weapons.blowgun.Blowgun;
import com.karel.game.weapons.captorch.CapsaicinTorch;
import com.karel.game.weapons.catclaw.CatClaw;
import com.karel.game.weapons.chameleon.Chameleon;
import com.karel.game.weapons.critters.LilCritters;
import com.karel.game.weapons.crossbow.Crossbow;
import com.karel.game.weapons.crystalgun.CrystalGun;
import com.karel.game.weapons.doublegun.DoubleGun;
import com.karel.game.weapons.drone.DroneRemote;
import com.karel.game.weapons.easterbasket.EasterBasket;
import com.karel.game.weapons.farmer.Farmer;
import com.karel.game.weapons.fastfood.FastFood;
import com.karel.game.weapons.flail.Flail;
import com.karel.game.weapons.flashdrive.FlashDrive;
import com.karel.game.weapons.frostspirit.FrostSpirit;
import com.karel.game.weapons.gale.Gale;
import com.karel.game.weapons.gluegun.GlueGun;
import com.karel.game.weapons.grenade.GrenadeLauncher;
import com.karel.game.weapons.gun.Gun;
import com.karel.game.weapons.hearth.Hearth;
import com.karel.game.weapons.highjacker.Highjacker;
import com.karel.game.weapons.inferno.Inferno;
import com.karel.game.weapons.jackolantern.JackOLantern;
import com.karel.game.weapons.jadecutter.JadeCutter;
import com.karel.game.weapons.lovestrike.Lovestrike;
import com.karel.game.weapons.lymphcannon.LymphCannon;
import com.karel.game.weapons.mole.PetMole;
import com.karel.game.weapons.mortar.Mortar;
import com.karel.game.weapons.nailgun.NailGun;
import com.karel.game.weapons.necromancer.Necromancer;
import com.karel.game.weapons.paintgun.PaintGun;
import com.karel.game.weapons.pointpinner.Pointpinner;
import com.karel.game.weapons.reaper.Reaper;
import com.karel.game.weapons.rock.RockCatapult;
import com.karel.game.weapons.salicycle.Salicycle;
import com.karel.game.weapons.scalpel.Scalpel;
import com.karel.game.weapons.scream.Scream;
import com.karel.game.weapons.shotgun.Shotgun;
import com.karel.game.weapons.slicer.Slicer;
import com.karel.game.weapons.snakecharmer.SnakeCharmer;
import com.karel.game.weapons.sodahat.SodaHat;
import com.karel.game.weapons.soup.Soup;
import com.karel.game.weapons.spear.SpearWeapon;
import com.karel.game.weapons.teslacoil.TeslaCoil;
import com.karel.game.weapons.traps.TrapSetter;
import com.karel.game.weapons.trolltest.TrollTest;
import com.karel.game.weapons.waterballoons.WaterBalloons;
import com.karel.game.weapons.weedwacker.Weedwacker;

public class ItemFactory
{
    private static String[] weapons = {"gun",
                                    "rock",
                                    "gale",
                                    "blade",
                                    "slicer",
                                    "crossbow",
                                    "shotgun",
                                    "traps",
                                    "spear",
                                    "weedwacker",
                                    "necromancer",
                                    "easterbasket",
                                    "catclaw",
                                    "mole",
                                    "fireworks",
                                    "teslacoil",
                                    "soup",
                                    "lovestrike",
                                    "drone",
                                    "farmer",
                                    "crystalgun",
                                    "grenade",
                                    "snakecharmer",
                                    "blowgun",
                                    "hearth",
                                    //"mountaineer",
                                    "airpump",
                                    "highjacker",
                                    //"maelstrom",
                                    "scream",
                                    "nailgun",
                                    "lymphcannon",
                                    "gluegun",
                                    //"lanternlobber",
                                    //"whip",
                                    "jadecutter",
                                    "pointpinner",
                                    "waterballoons",
                                    "chameleon",
                                    //"hellphand",
                                    //"monkeystaff",
                                    //"pinball",
                                    //"antimattergun",
                                    "captorch",
                                    //"electricfists",
                                    "reaper",
                                    "critters",
                                    "frostspirit",
                                    //"fairyring",
                                    //"taxcollector",
                                    //"realsim",
                                    //"capacitor",
                                    //"flyswatter",
                                    "sodahat",
                                    //"coco",
                                    "flail",
                                    //"rifle",
                                    //"traffix",
                                    //"spellbook",
                                    //"deathsicle",
                                    //"hyperdrive",
                                    //"gum",
                                    //"tumbleweed",
                                    "paintgun",
                                    "trolltest",
                                    "acidart",
                                    "scalpel",
                                    "flashdrive",
                                    "mortar",
                                    "inferno",
                                    "doublegun",
                                    "fastfood",
                                    "salicycle",
                                    "jackolantern"};
    public static Item createItem(String type, ItemHolder holder)
    {
        switch(type)
        {
            case "gun":
                return new Gun(holder);
            case "rock":
                return new RockCatapult(holder);
            case "gale":
                return new Gale(holder);
            case "blade":
                return new Blade(holder);
            case "slicer":
                return new Slicer(holder);
            case "flash":
                //return new Flash(holder);
            case "crossbow":
                return new Crossbow(holder);
            case "shotgun":
                return new Shotgun(holder);
            case "traps":
                return new TrapSetter(holder);
            case "spear":
                return new SpearWeapon(holder);
            case "weedwacker":
                return new Weedwacker(holder);
            case "necromancer":
                return new Necromancer(holder);
            case "easterbasket":
                return new EasterBasket(holder);
            case "catclaw":
                return new CatClaw(holder);
            case "mole":
                return new PetMole(holder);//
            case "fireworks":
                return new Fireworks(holder);//
            case "teslacoil":
                return new TeslaCoil(holder);
            case "soup":
                return new Soup(holder);//
            case "lovestrike":
                return new Lovestrike(holder);//
            case "drone":
                return new DroneRemote(holder);//
            case "farmer":
                return new Farmer(holder);//
            case "crystalgun":
                return new CrystalGun(holder);
            case "grenade":
                return new GrenadeLauncher(holder);//
            case "snakecharmer":
                return new SnakeCharmer(holder);
            case "blowgun":
                return new Blowgun(holder);
            case "hearth":
                return new Hearth(holder);
            case "mountaineer":
                //return new MountainClimber(holder);
            case "airpump":
                return new AirPump(holder);
            case "highjacker":
                return new Highjacker(holder);
            case "maelstrom":
                //return new Maelstrom(holder);
            case "scream":
                return new Scream(holder);
            case "nailgun":
                return new NailGun(holder);//
            case "lymphcannon":
                return new LymphCannon(holder);//
            case "gluegun":
                return new GlueGun(holder);//
            case "lanternlobber":
                //return new LanternLobber(holder);
            case "whip":
                //return new Whip(holder);
            case "jadecutter":
                return new JadeCutter(holder);//
            case "pointpinner":
                return new Pointpinner(holder);
            case "waterballoons":
                return new WaterBalloons(holder);//
            case "chameleon":
                return new Chameleon(holder);//
            case "hellphand":
                //return new Hellphand(holder);
            case "monkeystaff":
                //return new MonkeyStaff(holder);
            case "pinball":
                //return new Pinball(holder);
            case "antimattergun":
                //return new AntimatterGun(holder);
            case "captorch":
                return new CapsaicinTorch(holder);
            case "electricfists":
                //return new ElectricFists(holder);
            case "reaper":
                return new Reaper(holder);//
            case "critters":
                return new LilCritters(holder);
            case "frostspirit":
                return new FrostSpirit(holder);
            case "fairyring":
                //return new FairyRing(holder);
            case "taxcollector":
                //return new TaxCollector(holder);
            case "realsim":
                //return new RealitySimulator(holder);
            case "capacitor":
                //return new PolarCapacitor(holder);
            case "flyswatter":
                //return new PneumaticFlyswatter(holder);
            case "sodahat":
                return new SodaHat(holder);
            case "coco":
                //return new Coco(holder);
            case "flail":
                return new Flail(holder);
            case "rifle":
                //return new Rifle(holder);
            case "traffix":
                //return new Traffix(holder);
            case "spellbook":
                //return new Spellbook(holder);
            case "deathsicle":
                //return new Deathsicle(holder);
            case "hyperdrive":
                //return new Hyperdrive(holder);
            case "gum":
                //return new Gum(holder);
            case "tumbleweed":
                //return new TumbleweedLauncher(holder);
            case "paintgun":
                return new PaintGun(holder);
            //MISSING A LOT
            case "trolltest":
                return new TrollTest(holder);
            case "acidart":
                return new Acidart(holder);
            //uranium drum
            //puppeteer
            case "scalpel":
                return new Scalpel(holder);
            //MISSING A LOT
            case "flashdrive":
                return new FlashDrive(holder);//
            //MISSING
            case "mortar":
                return new Mortar(holder);
            //Ghost Sprite
            case "inferno":
                return new Inferno(holder);
            //MISSING
            case "doublegun":
                return new DoubleGun(holder);
            case "fastfood":
                return new FastFood(holder);
            //?
            case "salicycle":
                return new Salicycle(holder);
            //?
            case "jackolantern":
                return new JackOLantern(holder);
            default:
                return new Gun(holder);
        }
    }
    public static String[] getItemTypes() {
        return weapons;
    }
}
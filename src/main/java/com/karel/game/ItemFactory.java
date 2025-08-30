public class ItemFactory
{
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
                return new PetMole(holder);
            case "fireworks":
                return new Fireworks(holder);
            case "teslacoil":
                return new TeslaCoil(holder);
            case "soup":
                return new Soup(holder);
            case "lovestrike":
                return new Lovestrike(holder);
            case "drone":
                return new DroneRemote(holder);
            case "farmer":
                return new Farmer(holder);
            case "crystalgun":
                return new CrystalGun(holder);
            case "grenade":
                return new GrenadeLauncher(holder);
            case "snakecharmer":
                //return new SnakeCharmer(holder);
            case "blowgun":
                return new Blowgun(holder);
            case "hearth":
                return new Hearth(holder);
            case "mountainclimber":
                //return new MountainClimber(holder);
            default:
                return new Gun(holder);
        }
    }
}
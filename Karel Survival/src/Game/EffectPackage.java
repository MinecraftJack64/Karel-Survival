package Game;
/**
 * Write a description of class EffectPackage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EffectPackage  
{
    // instance variables - replace the example below with your own
    private int damage;
    private Effect[] effects;
    public EffectPackage(int dmg, Effect... effects){
        damage = dmg;
        this.effects = effects;
    }
}

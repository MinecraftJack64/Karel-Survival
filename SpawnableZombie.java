/**
 * Write a description of class SpawnableZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpawnableZombie extends Zombie  
{
    public boolean wasInOriginalWave(){
        return false;
    }
    public boolean willNotify(){
        return false;
    }
}

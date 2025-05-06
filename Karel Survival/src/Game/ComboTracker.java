package Game;
/**
 * Shared by attacks to track potential combos by communicating a number
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComboTracker  
{
    private int value;
    public ComboTracker(){
        this(0);
    }
    public ComboTracker(int val){
        value = val;
    }
    public void set(int val){
        value = val;
    }
    public int get(){
        return value;
    }
    public int change(int val){
        value+=val;
        return value;
    }
}

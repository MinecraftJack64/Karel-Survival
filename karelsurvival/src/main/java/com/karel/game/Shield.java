package com.karel.game;
final class ShieldID{
    final Object key;
    final String purpose;
    boolean keyimportance = true;//consider key when comparing?
    public ShieldID(Object source, String purpose){
        this.key = source;
        this.purpose = purpose;
    }
    public ShieldID(Object source, boolean keyimp, String purpose){
        this.key = source;
        this.purpose = purpose;
        this.keyimportance = keyimp;
    }
    public ShieldID(Object source){
        this(source, "");
    }
    public ShieldID(String purpose){
        this(null, purpose);
    }
    public ShieldID(){
        this("");
    }
    public String getPurpose(){
        return purpose;
    }
    public Object getKey(){
        return key;
    }
    public boolean equals(Object o){
        if(!(o instanceof ShieldID)){
            return false;
        }
        ShieldID other = (ShieldID)o;
        return purpose.equals(other.getPurpose())&&(!keyimportance||((key==null&&other.getKey()==null)||key.equals(other.getKey())));
    }
}
/**
 * Write a description of class Shield here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shield  
{
    GridEntity myGE;
    ShieldID myID;
    public Shield(ShieldID id){
        myID = id;
    }
    public int processDamage(int dmg, GridObject source){
        return dmg;
    }
    public boolean damage(int amt, GridObject s){return false;}//return true if shield breaks
    public void tick(){}
    public void remove(){
        getHolder().removeShield(getID());
    }
    public int getHealth(){
        return 100;
    }
    public boolean canBreak(){
        return false;
    }
    public boolean canStack(){
        return false;
    }
    public GridEntity getHolder(){
        return myGE;
    }
    public ShieldID getID(){
        return myID;
    }
    public void setHolder(GridEntity g){
        myGE = g;
    }
    public void applyTo(GridEntity g){
        setHolder(g);
    }
}



package com.karel.game.shields;

public class ShieldID{
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
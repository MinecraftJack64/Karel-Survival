package com.karel.game.weapons;

public class EffectID{
    final Object key;
    final String purpose;
    boolean keyimportance = true;//consider key when comparing?
    public EffectID(Object source, String purpose){
        this.key = source;
        this.purpose = purpose;
    }
    public EffectID(Object source, boolean keyimp, String purpose){
        this.key = source;
        this.purpose = purpose;
        this.keyimportance = keyimp;
    }
    public EffectID(Object source){
        this(source, "");
    }
    public EffectID(String purpose){
        this(null, purpose);
    }
    public EffectID(){
        this("");
    }
    public String getPurpose(){
        return purpose;
    }
    public Object getKey(){
        return key;
    }
    public boolean equals(Object o){
        if(!(o instanceof EffectID)){
            return false;
        }
        EffectID other = (EffectID)o;
        return purpose.equals(other.getPurpose())&&(!keyimportance||((key==null&&other.getKey()==null)||key.equals(other.getKey())));
    }
}
package com.karel.game;

public interface EventListener {
    /*
     * Return true if to remove
     */
    public boolean on(String event, GridObject source);//TODO: Add data
}

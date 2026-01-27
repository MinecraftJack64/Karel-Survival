package com.karel.game.ui;

import com.karel.game.EventListener;
import com.karel.game.Game;
import com.karel.game.GridObject;
import com.karel.game.ui.text.Counter;
//import com.raylib.Raylib;

public class CurrencyDisplay extends UI implements EventListener{
    private Counter currencyFrags;
    public void create(){
        currencyFrags = new Counter("Fragments: ", 20);
        getWorld().addObject(currencyFrags, getWorld().gridXToRealX(getWorld().gridwidth-2), 1);
        currencyFrags.setValue(Game.playerData.getNumFrags());

        Game.playerData.setEventListener(this);
    }
    @Override
    public boolean on(String event, GridObject source) {
        currencyFrags.setTarget(Game.playerData.getNumFrags());
        return false;
    }
}

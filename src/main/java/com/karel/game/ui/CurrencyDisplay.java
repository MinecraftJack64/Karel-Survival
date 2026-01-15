package com.karel.game.ui;

import com.karel.game.Game;
import com.karel.game.ui.text.Counter;
//import com.raylib.Raylib;

public class CurrencyDisplay extends UI{
    private Counter currencyFrags;
    public void create(){
        currencyFrags = new Counter("Fragments: ", 20);
        getWorld().addObject(currencyFrags, getWorld().gridXToRealX(getWorld().gridwidth-2), 1);
        currencyFrags.setValue(Game.playerData.getNumFrags());
    }
}

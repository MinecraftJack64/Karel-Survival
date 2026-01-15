package com.karel.game.ui;

import com.karel.game.ui.buttons.Button;
import com.karel.game.ui.sliders.Slider;
import com.raylib.Raylib;

import java.util.ArrayList;

import com.karel.game.Game;
import com.karel.game.Greenfoot;
import com.karel.game.World;
import com.karel.game.gridobjects.Tinkerer;
import com.karel.game.gridobjects.collectibles.TinkerWeaponFrag;
import com.karel.game.gridobjects.collectibles.WeaponFrag;

public class TinkerUI extends UI{
    Button backButton, tinkerButton;
    Slider fragNumPicker;
    ArrayList<WeaponFrag> frags = new ArrayList<>();
    Tinkerer tk;
    public TinkerUI() {
        backButton = new Button(50, 50, "Back", Raylib.GREEN){
            public void click() {
                Game.goToMenu();
            }
        };
        tinkerButton = new Button(250, 250, "!! TINKER !!", Raylib.GREEN){
            public void click(){
                tinker();
            }
        };
        if(Game.playerData.getNumFrags()>=100)fragNumPicker = new Slider(50*18, Game.playerData.getNumFrags()/10*10, 100, 100, 10, false);
        resetBG();
    }
    public void create(){
        getWorld().addToGrid(backButton, 1, 1);
        getWorld().addToGrid(tinkerButton, 11.5, 7.5);
        getWorld().addToGrid(fragNumPicker, 11.5, 14);
        tk = new Tinkerer();
        getWorld().addToGrid(tk, 11.5, 7.5);
        for(int i = 0; i < Game.playerData.getNumFrags(); i++){
            TinkerWeaponFrag wf = new TinkerWeaponFrag();
            wf.setTinkerer(tk);
            getWorld().addObject(wf, Greenfoot.getRandomNumber(getWorld().gridwidth*World.gridSize), Greenfoot.getRandomNumber(getWorld().gridheight*World.gridSize));
            frags.add(wf);
        }
    }
    public void tinker(){
        int nf = Game.playerData.getNumFrags(), uv = (int)fragNumPicker.getValue();
        if(nf < uv){
            return;//TODO
        }
        for(int i = 0; i < uv; i++){
            frags.get(0).setActive(true);
            frags.remove(0);
        }
        backButton.setActive(false);
        tk.expect(uv);
        //do your stuff
        backButton.setActive(true);//for now
        Game.playerData.setNumFrags(nf-uv);
    }
}

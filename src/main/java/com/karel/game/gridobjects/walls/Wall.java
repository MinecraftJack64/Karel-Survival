package com.karel.game.gridobjects.walls;

import com.karel.game.GridObject;

public class Wall extends GridObject{
    //NOTE: All walls are the same overall type, maybe except special walls
    @Override
    public String getObjectID(){
        return "wall";
    }
}

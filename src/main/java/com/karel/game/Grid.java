package com.karel.game;
 

import java.util.*;
/**
 * Write a description of class World here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Grid<T>
{
    public ArrayList<ArrayList<ArrayList<T>>> grid;
    private int xsize, ysize;
    public LocationOld origin;
    public Grid(){
        grid = new ArrayList<ArrayList<ArrayList<T>>>();
        origin = new LocationOld(0, 0);
        xsize = 1;
        ysize = 1;
        grid.add(new ArrayList<ArrayList<T>>());
        grid.get(0).add(new ArrayList<T>());
    }
    private void extend(int x, int y){
        xsize+=Math.abs(x);
        ysize+=Math.abs(y);
        if(y>0){//If there is y to add
            for(int j = 0; j < grid.size(); j++){
                for(int i = 0; i < y; i++){
                    grid.get(j).add(new ArrayList<T>());
                }
            }
        }else if(y<0){
            for(int j = 0; j < grid.size(); j++){
                for(int i = 0; i < -y; i++){
                    grid.get(j).add(0, new ArrayList<T>());
                }
            }
            origin.translate(0, -y);
        }
        if(x>0){//If there is x to add
            for(int i = 0; i < x; i++){
                ArrayList<ArrayList<T>> tmp = new ArrayList<ArrayList<T>>();
                grid.add(tmp);
                for(int j = 0; j < ysize; j++){
                    tmp.add(new ArrayList<T>());
                }
            }
        }
        else if(x<0){
            for(int i = 0; i < -x; i++){
                ArrayList<ArrayList<T>> tmp = new ArrayList<ArrayList<T>>();
                grid.add(0, tmp);
                for(int j = 0; j < ysize; j++){
                    tmp.add(new ArrayList<T>());
                }
            }
            origin.translate(-x, 0);
        }
    }
    public void extendto(Zone loc){
        int xdiff = 0;int xdiff2 = 0;
        if(loc.tip2.x+origin.x+1>grid.size())
            xdiff2 = loc.tip2.x+origin.x+1-grid.size();
        if(loc.tip1.x+origin.x<0)
            xdiff = loc.tip1.x+origin.x;
        int ydiff = 0;int ydiff2 = 0;
        if(loc.tip2.y+origin.y+1>grid.get(0).size())
            ydiff2 = loc.tip2.y+origin.y+1-grid.get(0).size();
        if(loc.tip1.y+origin.y<0)
            ydiff = loc.tip1.y+origin.y;
        if(xdiff!=0||ydiff!=0){
            extend(xdiff, ydiff);
        }
        if(xdiff2!=0||ydiff2!=0){
            extend(xdiff2, ydiff2);
        }
    }
    public void extendto(int x, int y){
        int xdiff = 0;
        if(x+origin.x+1>grid.size())
            xdiff = x+origin.x+1-grid.size();
        else if(x+origin.x<0)
            xdiff = x+origin.x;
        else
            xdiff = 0;
        int ydiff = 0;
        if(y+origin.y+1>grid.get(0).size())
            ydiff = y+origin.y+1-grid.get(0).size();
        else if(y+origin.y<0)
            ydiff = y+origin.y;
        else
            ydiff = 0;
        if(xdiff!=0||ydiff!=0){
            System.out.println("World extended to "+x+" "+y);
            extend(xdiff, ydiff);
        }
    }
    public void extendto(LocationOld loc){
        extendto(loc.x, loc.y);
    }
    public void place(T obj, int x, int y){
        extendto(x, y);
        grid.get(x+origin.x).get(y+origin.y).add(obj);
    }
    public ArrayList<T> get(int x, int y){
        return grid.get(x+origin.x).get(y+origin.y);
    }
    public ArrayList<T> get(LocationOld loc){
        return get(loc.x, loc.y);
    }
    public void remove(T obj, int x, int y){
        ArrayList<T> c = grid.get(x+origin.x).get(y+origin.y);
        c.remove(obj);
    }
    public void place(T obj, LocationOld loc){
        place(obj, loc.x, loc.y);
    }
    public boolean isEmpty(int x, int y){
        return get(x, y).size()==0;
    }
    public boolean isEmpty(LocationOld loc){
        return isEmpty(loc.x, loc.y);
    }
    public void place(T obj, Zone loc){
        extendto(loc);
        for(int x = loc.tip1.x; x < loc.tip2.x; x++){
            for(int y = loc.tip1.y; y < loc.tip2.y; y++){
                place(obj, x, y);
            }
        }
    }
    public void remove(T obj, Zone loc){
        for(int x = loc.tip1.x; x < loc.tip2.x; x++){
            for(int y = loc.tip1.y; y < loc.tip2.y; y++){
                remove(obj, x, y);
            }
        }
    }
    public void remove(T obj, LocationOld loc){
        remove(obj, loc.x, loc.y);
    }
    public void print(){
        for(int x = grid.get(0).size()-1; x >= 0; x--){
            for(int y = 0; y < grid.size(); y++){
                if(x==origin.x&&y==origin.y){
                    System.out.print('x');
                }else
                    System.out.print(grid.get(y).get(x).size());
                    /*for(int o = 0; o < grid.get(y).get(x).size(); o++){
                        System.out.print(grid.get(y).get(x).get(o));
                    }*/
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

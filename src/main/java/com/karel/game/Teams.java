package com.karel.game;
import java.util.ArrayList;
import java.util.HashMap;

class Team{
    private ArrayList<String> aggressions = new ArrayList<String>();
    private ArrayList<String> allies = new ArrayList<String>();
    private ArrayList<GridObject> members = new ArrayList<GridObject>();
    private ArrayList<GridEntity> entitymembers = new ArrayList<GridEntity>();
    private String id;
    public Team(String id){
        this.id = id;
    }
    public ArrayList<String> getAggro(){
        return aggressions;
    }
    public ArrayList<String> getAllies(){
        return allies;
    }
    public ArrayList<GridObject> getMembers(){
        return members;
    }
    public ArrayList<GridEntity> getEntityMembers(){
        return entitymembers;
    }
    public String toString(){
        return id+"\nMembers: "+arrToString(members)+"\nAllied teams: "+arrToString(allies)+"\nAggressive towards: "+arrToString(aggressions);
    }
    public String arrToString(ArrayList<?> a){
        String ans = "";
        for(Object o: a){
            ans+=o;
            ans+=", ";
        }
        return a.size()==0?ans:ans.substring(0, ans.length()-2);
    }
}
/**
 * Write a description of class Teams here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teams  
{
    //Store team aggressions and allies
    //HashMap<String, ArrayList<String>> allies = new HashMap<String, ArrayList<String>>();
    //HashMap<String, ArrayList<String>> aggressions = new HashMap<String, ArrayList<String>>();
    HashMap<String, Team> teams = new HashMap<String, Team>();
    public ArrayList<String> getAggressions(String team){
        if(teams.containsKey(team)){
            return teams.get(team).getAggro();
        }
        return new ArrayList<String>();
    }
    public ArrayList<String> getAllies(String team){
        if(teams.containsKey(team)){
            return teams.get(team).getAllies();
        }
        return new ArrayList<String>();
    }
    public void setAlly(String team1, String team2, boolean mutual){//mutual?
        if(teams.containsKey(team1)){
            if(!teams.get(team1).getAllies().contains(team2))
                teams.get(team1).getAllies().add(team2);
        }
        if(mutual){
            setAlly(team2, team1, false);
        }
    }
    public void setAggro(String team1, String team2, boolean mutual){
        if(teams.containsKey(team1)){
            if(!teams.get(team1).getAggro().contains(team2))
                teams.get(team1).getAggro().add(team2);
        }
        if(mutual){
            setAggro(team2, team1, false);
        }
    }
    //Store members of teams
    public ArrayList<GridObject> getMembers(String team){
        if(teams.containsKey(team)){
            return teams.get(team).getMembers();
        }
        return new ArrayList<GridObject>();
    }
    public ArrayList<GridEntity> getEntityMembers(String team){
        if(teams.containsKey(team)){
            return teams.get(team).getEntityMembers();
        }
        return new ArrayList<GridEntity>();
    }
    public void addTeam(String id){
        teams.put(id, new Team(id));
    }
    public ArrayList<String> getAllTeams(){
        return new ArrayList<String>(teams.keySet());
    }
    public void joinTeam(GridObject g, String team){
        if(!teams.containsKey(team)){
            addTeam(team);
        }
        if(getMembers(team).contains(g)){
            return;
        }
        getMembers(team).add(g);
        if(g instanceof GridEntity){
            getEntityMembers(team).add((GridEntity)g);
        }
    }
    public void leaveTeam(GridObject g, String team){
        if(!teams.containsKey(team)){
            addTeam(team);
        }
        getMembers(team).remove(g);
        if(g instanceof GridEntity){
            getEntityMembers(team).remove((GridEntity)g);
        }
    }
    //Store list of teams
    public String toString(){
        String ans = "";
        for(Team t: teams.values()){
            ans+=t+"\n\n";
        }
        return ans;
    }
}

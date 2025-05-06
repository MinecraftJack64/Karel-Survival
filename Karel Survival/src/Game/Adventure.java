package Game;
/**
 * Write a description of class Adventure here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Adventure extends GameMode
{
    private Player player;
    private Teams teams;
    public AdventureSpawner spawner;
    private String status;
    private static int highscore;
    private static int level = 0;
    /**
     * Constructor for objects of class Adventure
     */
    public Adventure()
    {
    }
    public void tick(){
        spawner.checkSpawn();
        if(player.isDead()||spawner.levelComplete()){
            gameOver();
        }
    }
    public void startGame(){
        teams = new Teams();
        teams.addTeam("player");
        teams.addTeam("zombie");
        teams.addTeam("lootbox");
        teams.setAggro("player", "zombie", true);
        teams.setAggro("player", "lootbox", false);
        teams.setAlly("zombie", "zombie", false);
        teams.setAlly("player", "player", false);
        Player rocket = new Player();
        getWorld().addToGrid(rocket, 12, 8);
        player = rocket;
        spawner = new AdventureSpawner(level);
        SupplyCrate test = new SupplyCrate();
        getWorld().addObject(test, 500.0, 500.0);
        status = "running";
        resetScore();
        System.out.println(teams);
    }
    public Teams getTeams(){
        return teams;
    }
    public Spawner getSpawner(){
        return spawner;
    }
    public Player getPlayer(){
        return player;
    }
    public String getStatus(){
        return status;
    }
    public int getHighScore(){
        return 0;
    }
    public void gameOver() 
    {
        if(spawner.levelComplete()){
            status = "win";
            gameUI().gameOver(level+1, true);
            level++;
            if(level>=Levels.getNumLevels()){
                level = 0;
            }
        }else{
            gameUI().gameOver(level+1, false);
            status = "lose";
        }
    }
}

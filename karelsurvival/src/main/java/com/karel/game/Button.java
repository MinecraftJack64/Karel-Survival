package com.karel.game;
/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Overlay
{
    private Color barColor = Color.GRAY;
    private int size = 0;
    private int width, height;
    private int stringLength;
    private int state = 0;//-1 - inactive, 0 - normal, 1 - mouse over, 2 - mouse clicked
    private boolean active = true;
    public ButtonPuppet puppet;
    public Button()
    {
        this(80, 40);
    }
    
    public Button(int size, int height)
    {
        this.width = size;
        this.height = height;
        setImage(new GreenfootImage(width, height));
        updateImage();
    }
    public Button(int size, int height, String text, Color c)
    {
        this.width = size;
        this.height = height;
        setImage(new GreenfootImage(width, height));
        puppet = new ButtonPuppet(text,size/5,c);
        updateImage();
    }
    public Button(int size, int height, Color c)
    {
        this(size, height);
        barColor = c;
        updateImage();
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        getWorld().addObject(puppet, getRealX(), getRealY());
    }
    public void notifyWorldRemove(){
        getWorld().removeObject(puppet);
        super.notifyWorldRemove();
    }
    public void setText(String s){
        puppet.setText(s);
    }
    
    public void act() {
        //test if mouse over
        int nstate = state;
        if(active){
            if(nstate==2&&!getWorld().isMouseDown()){
                Sounds.play("click");
                click();
                nstate = 1;
            }
            if(isMouseOver()){
                nstate = 1;
                if(getWorld().isMouseDown()){
                    nstate = 2;
                }
            }else{
                nstate = 0;
            }
        }else{
            nstate = -1;
        }
        if(nstate!=state){
            state = nstate;
            update();
        }
        if(needsupdate){
            updateImage();
        }
        needsupdate = false;
    }
    public boolean isMouseOver(){
        double mxx = getWorld().getMouseX()-getRealX()+width/2, myy = getWorld().getMouseY()-getRealY()+height/2;
        //System.out.println(mxx+" "+myy);
        return (mxx>=0&&mxx<=width)&&(myy>=0&&myy<=height);
    }
    private boolean needsupdate = false;
    public void update(){
        needsupdate = true;
    }
    public void setColor(Color c){
        barColor = c;
        update();
    }
    public void click(){
        //notify observer
        System.out.println("Button clicked");
    }

    /**
     * Make the image
     */
    protected void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        if(state<0){
            image.setColor(barColor.brighter());
        }else if(state>0){
            image.setColor(barColor.darker());
        }else{
            image.setColor(barColor);
        }
        if(state==2){
            image.fillRect(5, 5, width-10, height-10);
        }else{
            image.fillRect(0, 0, width, height);
        }
        //modify puppet
        if(puppet==null){
            return;
        }
        //
    }
    
    public void setRealLocation(double x, double y){
        super.setRealLocation(x, y);
        if(puppet!=null){
            puppet.setRealLocation(x, y);
        }
    }
    public void setVisible(boolean b){
        super.setVisible(b);
        if(puppet!=null){
            puppet.setVisible(b);
        }
    }
    public double getBottom(){
        return size+getRealY();
    }
    public void setActive(boolean s){
        active = s;
    }
}

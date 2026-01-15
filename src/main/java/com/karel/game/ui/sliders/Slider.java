package com.karel.game.ui.sliders;
import com.karel.game.ui.Input;
import com.karel.game.ui.bars.StatusBar;
import com.karel.game.ui.buttons.Button;

public class Slider extends Input{
    private int length, width;
    private int clickOrigin;
    private double max, min, value, interval;
    private double xOrigin;
    private boolean smooth; // If slider clicks into place while sliding
    private Button slide;
    private StatusBar bar;
    public Slider(int length, double max, double value, double interval, boolean smooth){
        this(length, max, 0, value, interval, smooth);
    }
    public Slider(int length, double max, double min, double value, double interval, boolean smooth){
        setLength(length);
        setMinValue(min);
        setMaxValue(max);
        setValue(value);
        setInterval(interval);
        setIsSmooth(smooth);
        width = 10;
        slide = new Button(30, 30){
            public void onPress(){
                super.onPress();
                clickOrigin = getWorld().getMouseX();
                xOrigin = getX();
            }
        };
        slide.setCancelClickOnUnhover(false);
        bar = new StatusBar((int)value, (int)max, length, width);
    }
    public void notifyWorldAdd(){
        super.notifyWorldAdd();
        slide.setWorld(getWorld());
        slide.notifyWorldAdd();
        slide.setLocation(getX()+length*value/max-length/2, getY());
        bar.setWorld(getWorld());
        bar.notifyWorldAdd();
        bar.setLocation(getX(), getY());
    }
    public void notifyWorldRemove(){
        super.notifyWorldRemove();
        slide.notifyWorldRemove();
        bar.notifyWorldRemove();
    }
    public void updateOld(){
        slide.update();
        bar.update();
        super.update();
        if(slide.getState()==2){
            int offset = getWorld().getMouseX()-clickOrigin;
            double beginx = getX()-length/2, endx = getX()+length/2;
            double targx = Math.max(beginx, Math.min(xOrigin+offset, endx));
            if(smooth)slide.setLocation(targx, slide.getY());
            //Determine the value of slider
            double prop = (targx-beginx)/length;
            setValue(prop*max);
            if(interval==0)return;
            double divis = value/interval;
            divis = Math.round(divis);
            setValue(divis*interval);
            if(!smooth)slide.setLocation(value/max*length+beginx, slide.getY());
        }
    }
    public void update() {
        slide.update();
        bar.update();
        super.update();
        if (slide.getState()==2) {
            int offset = getWorld().getMouseX() - clickOrigin;
            double beginx = getX() - length / 2;
            double endx = getX() + length / 2;
            double targx = Math.max(beginx, Math.min(xOrigin + offset, endx));
            if (smooth)
                slide.setLocation(targx, slide.getY());
            double range = max - min;
            if(range<=0){
                setValue(min);
                slide.setLocation(getX(), slide.getY());
                return;
            }
            // position to value
            double prop = (targx - beginx) / length;
            setValue(min + prop * range);
            if (interval == 0)
                return;
            // snap to interval relative to min
            double divis = (value - min) / interval;
            divis = Math.round(divis);
            setValue(min + divis * interval);

            // value to position, snap
            if (!smooth)
                slide.setLocation((value - min) / range * length + beginx, slide.getY());
        }
    }
    public void render(){
        bar.render();
        slide.render();
        super.render();
    }
    public void setLength(int length){
        this.length = length;
    }
    public void setMaxValue(double m){
        max = m;
    }
    public void setMinValue(double m){
        min = m;
    }
    public void setValue(double v){
        value = v;
        if(slide!=null)slide.setText(""+value);
        if(bar!=null)bar.setValue((int)value);
    }
    public double getValue(){
        return value;
    }
    public void setInterval(double i){
        interval = i;
    }
    public void setIsSmooth(boolean b){
        smooth = b;
    }
}

package Client.Model;

import Client.GUI.Constans;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.Serializable;

/**
 * Abstract class to represent single entity
 */
public abstract class Entity extends Image implements Serializable {
    protected boolean isFixed=false; // can it be moved by a hero
    protected boolean isVisible=true; // is it visible
    protected boolean isCrossable=true; // can you pass through it and see over/through it
    protected boolean isAttackable=true;
    protected int x,y;

    public final static int WIDTH = 32;
    public final static int HEIGHT = 32;
    public final static int STARTING_X = 200;
    public final static int STARTING_Y = 300;

    public Entity(String imagePath,int x,int y){
//        super(new Texture("heroes/archer.png"));
        this.x = x;
        this.y = y;
        this.setOrigin(WIDTH/2,HEIGHT/2);
        this.setSize(WIDTH,HEIGHT);
        this.setPosition(x*WIDTH+10, Constans.HEIGHT-(y+1)*HEIGHT-10);
    }


    @Override
    public String toString(){ // na razie, pewnie będzie przysłoniona w każdej klasie dziedziczącej
        return this.getClass().toString();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isCrossable() {
        return isCrossable;
    }

    public boolean isAttackable() {
        return isAttackable;
    }
}

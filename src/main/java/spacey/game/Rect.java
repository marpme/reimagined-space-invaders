package spacey.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class description ...
 * Included in PACKAGE_NAME
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 06. Jul 2017
 */
public class Rect {

    public float x;
    public float y;
    public float width;
    public float height;
    public float velocity;

    public static List<Rect> listOfRect = new CopyOnWriteArrayList<>();

    public Rect(float x, float y, float width, float height, float velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        listOfRect.add(this);
    }

    public synchronized void move(){
        this.y += velocity;
    }

    public void rectDraw(){}

    public synchronized void remove(int index){
        Rect.listOfRect.remove(index);
    }

    public boolean isOutOfWindow(float height){
        return this.y > height;
    }

    public boolean inBounds(float x,float y,float size){
        return this.right() >= x && this.left() <= x + size
                && this.bottom() >= y && this.top() <= y + size;
    }

    public float top(){
        return y - height/2;
    }

    public float bottom(){
        return y + height/2;
    }

    public float left(){
        return x - width/2;
    }

    public float right(){
        return x + width/2;
    }
}

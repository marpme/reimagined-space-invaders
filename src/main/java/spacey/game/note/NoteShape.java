package spacey.game.note;

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
public class NoteShape {

    public volatile static List<NoteShape> listOfNoteShape = new CopyOnWriteArrayList<>();
    private float x;
    private float y;
    private float width;
    private float height;
    private float velocity;

    public NoteShape(float x, float y, float width, float height, float velocity) {
        this.x = x;
        this.y = y - height * 2;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        listOfNoteShape.add(this);
    }

    public void move() {
        this.y += velocity;
    }

    public boolean isOutOfWindow(float height) {
        return this.y > height;
    }

    public boolean inBounds(float x, float y, float width, float height) {
        return this.right() >= x && this.left() <= x + width
                && this.bottom() >= y && this.top() <= y + height;
    }

    private float top() {
        return y;
    }

    private float bottom() {
        return y + height;
    }

    private float left() {
        return x;
    }

    private float right() {
        return x + width;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

package spacey.game;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import processing.core.PShape;

/**
 * A public class which is about storing every needed detail of the spaceman
 * We want to have sorta OOP
 */
public class SpaceMan {

    private FloatProperty floatProperty = new SimpleFloatProperty(0);
    private PShape spacer;
    private float x, y;

    /**
     * CTOR
     *
     * @param spacer a shape with a spacer inside of it
     * @param x      the x cord
     * @param y      the y cord
     */
    public SpaceMan(PShape spacer, float x, float y) {
        this.spacer = spacer;
        this.x = x;
        this.y = y;
    }

    public float getFloatProperty() {
        return floatProperty.get();
    }

    public synchronized void addPoints(float floatProperty) {
        this.floatProperty.set(this.floatProperty.getValue() + floatProperty);
    }

    public PShape getSpacer() {
        return spacer;
    }

    public float getWidth() {
        return spacer.width / 2f;
    }

    public float getHeight() {
        return spacer.height / 2f;
    }

    public float getCenteredX() {
        return x - getWidth() / 2f;
    }

    public float getCenteredY() {
        return y - getHeight() / 2f;
    }

    public void addXVelocity(float vel) {
        this.x += vel;
    }
}

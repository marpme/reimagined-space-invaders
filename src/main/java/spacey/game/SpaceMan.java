package spacey.game;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import processing.core.PShape;

public class SpaceMan {

    private FloatProperty floatProperty = new SimpleFloatProperty(0);
    private PShape spacer;
    private float x, y;

    public SpaceMan(PShape spacer, float x, float y) {
        this.spacer = spacer;
        this.x = x;
        this.y = y;
    }

    public float getFloatProperty() {
        return floatProperty.get();
    }

    public void addPoints(float floatProperty) {
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

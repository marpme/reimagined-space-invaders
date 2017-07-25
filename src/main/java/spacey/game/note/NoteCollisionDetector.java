package spacey.game.note;

import spacey.game.SpaceMan;

import java.util.List;

/**
 * Create each for thread for each Frame, maybe we can reduce somehow the overhead
 * and the creation count logically. Is it needed to have 60 collision detection per second?
 * (Runs smoothly tho)
 */
public class NoteCollisionDetector extends Thread {

    private float x, y, width, height;
    private SpaceMan spaceMan;
    private List<NoteShape> notes;

    public NoteCollisionDetector(float x, float y, float width, float height, List<NoteShape> notes, SpaceMan spaceMan) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spaceMan = spaceMan;
        this.notes = notes;
    }

    /**
     * Thread running method to check through each note we had inside our store
     * which could possibly colliding with out spaceman.
     */
    @Override
    public void run() {
        notes.forEach(shape -> {
            if (shape.inBounds(x, y, width, height)) {
                spaceMan.addPoints(10);
                NoteShape.listOfNoteShape.remove(shape);
            }
        });
    }
}

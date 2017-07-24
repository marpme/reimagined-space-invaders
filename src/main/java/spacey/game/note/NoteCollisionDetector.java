package spacey.game.note;

import spacey.game.SpaceMan;

import java.util.List;

public class NoteCollisionDetector extends Thread {

    private float x, y, width, heigth;
    private SpaceMan spaceMan;
    private List<NoteShape> notes;

    public NoteCollisionDetector(float x, float y, float width, float height, List<NoteShape> notes, SpaceMan spaceMan) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = height;
        this.spaceMan = spaceMan;
        this.notes = notes;
    }

    @Override
    public void run() {
        notes.forEach(shape -> {
            if (shape.inBounds(x, y, width, heigth)) {
                spaceMan.addPoints(10);
                NoteShape.listOfNoteShape.remove(shape);
            }
        });
    }
}

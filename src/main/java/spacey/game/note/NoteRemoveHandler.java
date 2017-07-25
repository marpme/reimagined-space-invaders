package spacey.game.note;

import java.util.Iterator;

public class NoteRemoveHandler extends Thread {

    private float height;

    public NoteRemoveHandler(float height) {
        this.height = height;
    }

    @Override
    public void run() {
        while (true) {
            for (NoteLine aListOfNoteShape : NoteLine.movingNotes) {
                handleNext(aListOfNoteShape);
            }
        }
    }

    private void handleNext(NoteLine shape) {
        if (shape.isOutOfWindow(this.height)) {
            NoteLine.movingNotes.remove(shape);
        }
    }
}

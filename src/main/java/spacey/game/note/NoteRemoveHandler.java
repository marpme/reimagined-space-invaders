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
            Iterator<NoteShape> it = NoteShape.listOfNoteShape.iterator();
            while (it.hasNext()) {
                handleNext(it.next());
            }
        }
    }

    public void handleNext(NoteShape shape) {
        if (shape.isOutOfWindow(this.height)) {
            NoteShape.listOfNoteShape.remove(shape);
        }
    }
}

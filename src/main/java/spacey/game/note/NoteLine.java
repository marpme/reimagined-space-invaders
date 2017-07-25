package spacey.game.note;

import spacey.music.MidiMetaTypes;
import spacey.music.MidiNote;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * Replacement for NoteShape (we want lines instead of musical notes!
 */
public class NoteLine {

    public static AtomicReferenceArray<NoteLine> listOfNoteLines =
            new AtomicReferenceArray<>(MidiNote.range);
    public static CopyOnWriteArrayList<NoteLine> movingNotes =
            new CopyOnWriteArrayList<>();
    private float x;
    private float y;
    private float width = 845 / MidiNote.range;
    private float height = 0;
    private float velocity;
    private boolean isFinalLength = false;

    public NoteLine(float x, float y, float velocity, int key, MidiMetaTypes mmd) {
        this.x = x;
        this.y = y;
        this.velocity = 10f;

        NoteLine current = listOfNoteLines.get(key % MidiNote.range);
        synchronized (NoteLine.class){
            if (current != null && mmd.equals(MidiMetaTypes.NOTE_OFF)) {
                movingNotes.add(current);
                listOfNoteLines.set(key % MidiNote.range, null);
                current.noteHasEnded();
            } else if (current == null && mmd.equals(MidiMetaTypes.NOTE_ON)) {
                listOfNoteLines.set(key % MidiNote.range, this);
            }
        }

    }

    private void noteHasEnded() {
        isFinalLength = true;
    }

    public float getX() {
        return this.x;
    }

    public float getStartY() {
        return this.y + height;
    }

    public float getEndY() {
        return this.y;
    }

    public void moveNote() {
        if(!isFinalLength)
            this.height += velocity;
        else
            this.y += velocity;
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

    public boolean isOutOfWindow(float height) {
        return this.getEndY() > height;
    }

    public boolean inBounds(float x, float y, float width, float height) {
        return this.right() >= x && this.left() <= x + width
                && this.bottom() >= y && this.top() <= y + height;
    }
}

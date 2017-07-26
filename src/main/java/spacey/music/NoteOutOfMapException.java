package spacey.music;

public class NoteOutOfMapException extends Exception {
    public NoteOutOfMapException() {
        super("Note is not in bound for our current range, so don't map it!");
    }
}

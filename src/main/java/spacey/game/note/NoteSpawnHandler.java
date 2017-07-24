package spacey.game.note;


import spacey.music.MidiTickHandler;
import spacey.music.NoteOutOfMapException;

public class NoteSpawnHandler {

    public static void startFactory(MidiTickHandler midiTickHandler) {
        midiTickHandler.addEventListener(midiNote -> {
            try {
                new NoteShape(65 + midiNote.getKeyMapped(), 0, 65, 65, 10f);
            } catch (NoteOutOfMapException e) { /* ignore */ }
        });
    }
}

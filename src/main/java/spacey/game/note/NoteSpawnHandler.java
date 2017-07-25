package spacey.game.note;


import spacey.music.MidiTickHandler;
import spacey.music.NoteOutOfMapException;

public class NoteSpawnHandler {

    public static void startFactory(MidiTickHandler midiTickHandler) {
        midiTickHandler.addEventListener((midiNote, metaType) -> {
            try {
                // new NoteShape(65 + midiNote.getKeyMapped(), 0, 65, 65, 10f, midiNote.getKey());
                float velMapped = mapNumber(midiNote.getVelocity(), 0, 127, 5, 10);
                new NoteLine(65 + midiNote.getKeyMapped(), 0 ,velMapped, midiNote.getKey(), metaType);
            } catch (NoteOutOfMapException e) { /* ignore */ }
        });
    }

    public static float mapNumber(float X, float start1, float end1, float start2, float end2){
        return (X-start1)/(end1-start1) * (end2-start2) + start2;
    }
}

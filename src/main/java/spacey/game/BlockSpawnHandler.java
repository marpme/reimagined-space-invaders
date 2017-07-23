package spacey.game;


import spacey.music.MidiTickHandler;

public class BlockSpawnHandler {

    public static void startFactory(MidiTickHandler midiTickHandler) {
        midiTickHandler.addEventListener(midiNote -> {
            synchronized (Rect.class) {
                new Rect(65 + midiNote.getKeyMapped(), 0, 65, 65, 10f);
            }
        });
    }
}

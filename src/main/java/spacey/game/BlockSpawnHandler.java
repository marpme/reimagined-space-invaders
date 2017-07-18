package spacey.game;


import spacey.music.MidiTickHandler;

public class BlockSpawnHandler {

    private MidiTickHandler midiTickHandler;

    public BlockSpawnHandler(MidiTickHandler midiTickHandler){
        this.midiTickHandler = midiTickHandler;
        this.midiTickHandler.addEventListener(midiNote -> {
            synchronized (Rect.class){
                new Rect(32.5f + midiNote.getKeyMapped(), -10, 65, 10, 5f);
            }
        });
    }
}

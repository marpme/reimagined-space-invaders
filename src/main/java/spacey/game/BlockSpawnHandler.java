package spacey.game;


import spacey.music.MidiTickHandler;

public class BlockSpawnHandler {

    private MidiTickHandler midiTickHandler;

    public BlockSpawnHandler(MidiTickHandler midiTickHandler){
        this.midiTickHandler = midiTickHandler;
        this.midiTickHandler.addEventListener(midiNote -> {
            synchronized (Rect.class){
                new Rect(65 + midiNote.getKeyMapped(), 0, 65, 65, 10f);
            }
        });
    }
}

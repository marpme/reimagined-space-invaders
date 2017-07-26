package spacey.game.note;


import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import spacey.music.MidiTickHandler;
import spacey.music.NoteOutOfMapException;

public class NoteSpawnHandler {

    private static long count = 0;

    public static void startFactory(MidiTickHandler midiTickHandler, int size, OscP5 oscServer, NetAddress remoteLocation) {
        midiTickHandler.addEventListener((midiNote, metaType) -> {
            try {
                count++;
                float velMapped = mapNumber(midiNote.getVelocity(), 0, 127, 5, 10);
                new NoteLine(65 + midiNote.getKeyMapped(), 0 ,velMapped, midiNote.getKey(), metaType);

                double percent = count / (double) size;
                oscServer.send(new OscMessage("/game/finished").add("Percentage finished: " + String.format("%.2f", percent * 100) + "%"), remoteLocation);
            } catch (NoteOutOfMapException e) { /* ignore */ }

        });
    }

    public static float mapNumber(float X, float start1, float end1, float start2, float end2){
        return (X-start1)/(end1-start1) * (end2-start2) + start2;
    }
}

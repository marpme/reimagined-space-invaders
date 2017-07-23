package spacey.music;

import javax.sound.midi.ShortMessage;
import java.util.Arrays;

public class MidiNote implements Comparable<MidiNote> {
    private static String[] notesName = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"};
    private long tick;
    private int channel;
    private int key;
    private int velocity;
    private String note;
    private ShortMessage cmd;

    public MidiNote(long tick, int channel, int key, int velocity, ShortMessage cmd) {
        this.tick = tick;
        this.channel = channel;
        this.key = key;
        this.velocity = velocity;
        this.cmd = cmd;
        this.note = notesName[key % notesName.length];
    }

    public MidiNote(ShortMessage cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "spacey.music.MidiNote{" +
                "tick=" + tick +
                ", channel=" + channel +
                ", key=" + key +
                ", velocity=" + velocity +
                '}';
    }

    public long getTick() {
        return tick;
    }

    public float getKeyMapped() {
        int index = Arrays.binarySearch(notesName, this.note);
        // return 32.5 + (65 * index); centered
        return (float) 65 * index;
    }

    public int getChannel() {
        return channel;
    }

    public int getKey() {
        return key;
    }

    public int getVelocity() {
        return velocity;
    }

    public ShortMessage getCmd() {
        return cmd;
    }

    @Override
    public int compareTo(MidiNote o) {
        return (int) (this.tick - o.getTick());
    }
}

package spacey.music;

import javax.sound.midi.ShortMessage;

public class MidiNote implements Comparable<MidiNote> {
    public final static String[] NOTES_NAME = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"}; // 12 notes
    public static int highestKey, lowestKey, range;
    private long tick;
    private int channel;
    private int key;
    private int velocity;

    public MidiNote(long tick, int channel, int key, int velocity, ShortMessage cmd) {
        this.tick = tick;
        this.channel = channel;
        this.key = key;
        this.velocity = velocity;
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

    public float getKeyMapped() throws NoteOutOfMapException {
        if(key >= lowestKey && key <= highestKey)
            return (float) 845/range * (key % range);
        else
            throw new NoteOutOfMapException();
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


    @Override
    public int compareTo(MidiNote o) {
        return (int) (this.tick - o.getTick());
    }
}

package spacey.music;

import javax.sound.midi.ShortMessage;

public class MidiNote implements Comparable<MidiNote> {
    private static String[] notesName = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"}; // 12 notes
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
        /* 0 - 127
           60 - 72
           117 %  12
        if (key >= 60 && key <= 72)
            this.note = notesName[key % notesName.length];
        */
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

    public float getKeyMapped() throws NoteOutOfMapException {
        if (key >= 60 && key <= 72)
            return (float) 65 * (key % 12);
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

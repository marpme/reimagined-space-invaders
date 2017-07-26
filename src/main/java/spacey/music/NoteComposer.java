package spacey.music;

import javax.sound.midi.*;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

/**
 * Class description ...
 * Included in PACKAGE_NAME
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 13. Jul 2017
 */
public class NoteComposer extends Thread {

    private PriorityQueue<MidiNote> notes = new PriorityQueue<>();
    private float BPM;
    private int PPQ;
    private int minimum = Integer.MAX_VALUE, maximum = Integer.MIN_VALUE;
    private int windowWidth;

    public NoteComposer(Sequence sequence, int width) throws Exception {
        super();
        this.windowWidth = width;
        Arrays.stream(sequence.getTracks()).collect(Collectors.toList()).forEach(track -> {
            IntStream.range(0, track.size()).forEach(eventCount -> {
                MidiMessage message = track.get(eventCount).getMessage();
                if (message instanceof ShortMessage)
                    addShortMessage((ShortMessage) message, track.get(eventCount).getTick());
            });
        });

        registerMetaInformation(sequence);
    }

    public void addShortMessage(ShortMessage sm, long tick) {
        if (isNoteMessage(sm.getCommand())) {
            int key = sm.getData1();
            int velocity = sm.getData2();

            minimum = Math.min(minimum, key);
            maximum = Math.max(maximum, key);

            notes.add(new MidiNote(tick, sm.getChannel(), key, velocity, this.windowWidth));
        }
    }

    public void registerMetaInformation(Sequence sequence)
            throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer seq = MidiSystem.getSequencer();
        seq.open();
        seq.setSequence(sequence);

        MidiNote.highestKey = this.maximum;
        MidiNote.lowestKey = this.minimum;
        MidiNote.range = this.maximum - this.minimum;

        this.BPM = seq.getTempoInBPM();
        this.PPQ = sequence.getResolution();
        seq.close();
    }

    public boolean isNoteMessage(int command) {
        return command == NOTE_ON || command == NOTE_OFF;
    }

    public float getBPM() {
        return this.BPM;
    }

    public int getPPQ() {
        return PPQ;
    }

    public Queue<MidiNote> getNotes() {
        return notes;
    }

}

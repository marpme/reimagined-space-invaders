package spacey.music;

import spacey.interfaces.DataTransferHandler;

import javax.sound.midi.MidiUnavailableException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Class description ...
 * Included in spacey.music
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 14. Jul 2017
 */
public class MidiTickHandler extends TimerTask {

    private DataTransferHandler max;
    private NoteComposer noteComposer;
    private volatile List<MidiTickListener> listeners = new LinkedList<>();
    private ScheduledThreadPoolExecutor scheduler;
    /**
     * Needed for the midi ticking event, do not touch!
     */
    private long tick;

    private Timer timer;

    /**
     * Creates a midi tick handler which times notes perfectly
     * in combination with the note composer which holds the notes for him.
     *
     * @param noteComposer the note composer
     */
    public MidiTickHandler(NoteComposer noteComposer) {
        try {
            this.max = DataTransferHandler.getInstance();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.noteComposer = noteComposer;

        scheduler = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        Long nano = Math.round(ticker(noteComposer.getBPM(), noteComposer.getPPQ()) * 1_000_000L);
        scheduler.scheduleAtFixedRate(this, 0, nano, TimeUnit.NANOSECONDS);
    }

    public void addEventListener(MidiTickListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Each time the tick needs to be handled.
     * Run by the TimerTask, which has been setup.
     * <p>
     * Creates a list of played notes based on the current <b>tick</b>
     * Note composer holds all notes as a priority queue which ensures that
     * the notes are in the correct tick order at any time.
     */
    public void run() {
        if (this.noteComposer.getNotes().size() == 0) {
            scheduler.shutdown();
            return;
        }

        List<MidiNote> notesToPlay = new LinkedList<>();
        while (this.noteComposer.getNotes().peek().getTick() == this.tick) {
            notesToPlay.add(this.noteComposer.getNotes().poll());
        }

        notesToPlay.forEach(mn -> {
            try {

                if (mn.getVelocity() == 0) {
                    max.sendMessage(MetaTypes.NOTE_OFF.getType(), mn.getChannel(), mn.getKey(), mn.getVelocity());
                } else {
                    listeners.forEach(listener -> listener.midiTicked(mn));
                    max.sendMessage(MetaTypes.NOTE_ON.getType(), mn.getChannel(), mn.getKey(), mn.getVelocity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tick++;
    }

    /**
     * Converts a BPM and PPQ (midi convention) into a millisecond timespan
     * Stackoverflow: https://stackoverflow.com/a/2038364/4479618
     *
     * @param BPM the bpm
     * @param PPQ the ppq
     * @return a double value representing the the time between to ticks (in ms).
     */
    public double ticker(float BPM, float PPQ) {
        return 60000 / (BPM * PPQ);
    }
}


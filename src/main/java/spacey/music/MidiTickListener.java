package spacey.music;

/**
 * Class description ...
 * Included in spacey.music
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 14. Jul 2017
 */
public interface MidiTickListener {
    void midiTicked(MidiNote mn, MidiMetaTypes mmt);
}

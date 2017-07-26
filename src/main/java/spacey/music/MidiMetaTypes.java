package spacey.music;

/*
    0x00	Sequence number
    0x20	MIDI channel prefix assignment
    0x01	Text event
    0x2F	End of track
    0x02	Copyright notice
    0x51	Tempo setting
    0x03	Sequence or track name
    0x54	SMPTE offset
    0x04	Instrument name
    0x58	Time signature
    0x05	Lyric text
    0x59	Key signature
    0x06	Marker text
    0x7F	Sequencer specific event
    0x07	Cue point
    0x90    Note on
    0x80    Note off
*/
public enum MidiMetaTypes {

    NOTE_ON(0x90),
    NOTE_OFF(0x80);

    private int type;

    MidiMetaTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

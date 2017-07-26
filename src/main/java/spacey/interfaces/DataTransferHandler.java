package spacey.interfaces;

import javax.sound.midi.*;

/**
 * Class description ...
 * Included in spacey.interfaces
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 13. Jul 2017
 */
public class DataTransferHandler {

    private static final String RECEIVER_NAME = "to Max";
    private static DataTransferHandler instance;
    private Receiver receiver;

    private DataTransferHandler() throws MidiUnavailableException {
        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            final MidiDevice device = MidiSystem.getMidiDevice(info);
            final String deviceInfo = device.getDeviceInfo().getName();
            System.out.println(deviceInfo);
            if (RECEIVER_NAME.equals(deviceInfo)) {
                if (!device.isOpen()) device.open();
                receiver = device.getReceiver();
            }
        }

        /*if (receiver == null) {
            System.err.println("MIDI receiver might not be available at the moment. Please check: " + RECEIVER_NAME);
            System.exit(1);
        }*/

    }

    public static DataTransferHandler getInstance() throws MidiUnavailableException {
        if (instance == null) {
            instance = new DataTransferHandler();
        }
        return instance;
    }

    public void sendMessage(int type, int channel, int key, int velocity) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(type, channel, key, velocity);
        if (receiver != null) receiver.send(message, -1);
    }
}

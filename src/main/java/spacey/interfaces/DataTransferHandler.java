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

    private static DataTransferHandler instance;
    private static final String RECEIVER_NAME = "to Max 1";
    private Receiver receiver;

    private DataTransferHandler() throws MidiUnavailableException {
        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            final MidiDevice device = MidiSystem.getMidiDevice(info);
            final String deviceInfo = device.getDeviceInfo().getName();

            if ("to Max 1".equals(deviceInfo)) {
                if (!device.isOpen()) device.open();
                receiver = device.getReceiver();
            }
        }
    }

    public static DataTransferHandler getInstance() throws MidiUnavailableException{
        if(instance == null){
            instance = new DataTransferHandler();
        }
        return instance;
    }

    public void sendMessage(int type, int channel, int key, int velocity) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(type, channel, key, velocity);
        receiver.send(message, -1);
    }
}

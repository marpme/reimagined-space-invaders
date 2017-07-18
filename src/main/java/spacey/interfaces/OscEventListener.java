package spacey.interfaces;

import spacey.game.SpaceInvaders;
import oscP5.OscMessage;

/**
 * Class description ...
 * Included in PACKAGE_NAME
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 03. Jul 2017
 */
public class OscEventListener extends Thread implements oscP5.OscEventListener {

    private volatile SpaceInvaders si;

    public OscEventListener(SpaceInvaders e) {
        this.si = e;
        this.start();
    }

    @Override
    public void run() { /* another Thread */ }

    @Override
    public void oscEvent(OscMessage theOscMessage) {
        float mess = theOscMessage.get(0).floatValue();
        if(mess == 0 && (theOscMessage.checkAddress("/1/push2")
                || theOscMessage.checkAddress("/1/push1"))) {
            si.velocityX = 0;
            return;
        }

        if(theOscMessage.checkAddress("/1/fader2")) {
            si.speed = mess;
            si.sendUpdate(mess);
        } else if(theOscMessage.checkAddress("/1/push2")) {
            si.velocityX = mess * 2 * (float)Math.log(si.speed);
        } else if(theOscMessage.checkAddress("/1/push1")) {
            si.velocityX = -1 * (mess * 2 * (float)Math.log(si.speed));
        }
    }
}

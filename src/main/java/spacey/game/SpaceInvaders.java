package spacey.game;

import processing.video.Movie;
import spacey.music.MidiTickHandler;
import spacey.music.NoteComposer;
import netP5.*;
import processing.core.*;
import oscP5.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;

/**
 * Class description ...
 * Included in PACKAGE_NAME
 *
 * @author Marvin Piekarek (s0556014)
 * @version 1.0
 * @since 03. Jul 2017
 */
public class SpaceInvaders extends PApplet {

    public OscP5 oscP5;
    public NetAddress myRemoteLocation;

    public float x, y;
    public float velocityX = 0;
    public float speed = 5;

    private static final String DACING_QUEEN = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/ABBA_-_Dancing_Queen.mid";
    private static final String MOZART = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/sym40-1.mid";
    private static final String BEVERLY = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/Movie_Themes_-_Beverly_Hills_Cop_-_Axel's_Theme_by_Harold_Faltermeyer.mid";
    private static final String BACKSTREET = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/Backstreet Boys - I'll Never Break Your Heart.mid";
    private static final String KYOTO = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/Skrillex - Kyoto (ft. Sirah).mid";

    private static final String SPACER = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/spacey/game/spacer.svg";
    private static final String BG = "/Users/marvinpiekarek/Desktop/development/SpaceInvaders/src/main/resources/spacey/game/background.mov";
    private PShape shape;
    private Movie movie;

    public static void main(String... args) {
        PApplet.main("spacey.game.SpaceInvaders");
    }

    @Override
    public void settings() {
        super.settings();
        size(845,400);
    }

    @Override
    public void setup() {
        frameRate(60);
        // oscP5 = new OscP5(this, 9455);
        // oscP5.addListener(new OscEventListener(this));
        // myRemoteLocation = new NetAddress("141.45.206.178", 8455);
        shape = this.loadShape(SPACER);
        shape.disableStyle();
        movie = new Movie(this, BG);
        movie.loop();

        rectMode(CENTER);
        x = .50f * this.width;
        y = .92f * this.height;

        try{
            Sequence sequence = MidiSystem.getSequence(new File(BEVERLY));
            NoteComposer noteComposer = new NoteComposer(sequence);
            MidiTickHandler TickHandler = new MidiTickHandler(noteComposer);
            BlockSpawnHandler spawnHandler = new BlockSpawnHandler(TickHandler);
        } catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void draw() {
        background(0);
        image(movie, 0, 0, this.width, this.height);
        this.x += velocityX;

        fill(94, 232, 215);
        shape(shape, this.x - shape.width/2f, this.y- shape.height/2f, 85.6f/2f, 68.48f/2f);

        //Rect.listOfRect.parallelStream().filter(rect -> rect.isOutOfWindow(this.height)).forEach(Rect::remove);
        for (int i = Rect.listOfRect.size() - 1; i >= 0; i--) {
            if(Rect.listOfRect.get(i).isOutOfWindow(this.height))
                Rect.listOfRect.remove(i);
        }

        Rect.listOfRect.forEach(Rect::move);
        fill(255,255,255);
        Rect.listOfRect.forEach(k -> rect(k.x, k.y, k.width, k.height));
        /*if(Rect.listOfRect.stream()
                .filter(rect -> rect.inBounds(this.x, this.y, 10))
                .count() >= 1) {
            finished = true;
        }*/
        // text("Framerate: " + frameRate, 10, 10);
    }

    // Called every time a new frame is available to read
    void movieEvent(Movie m) {
        m.read();
    }

    public void sendUpdate(float speed) {
        OscMessage k = new OscMessage("/1/speed");
        k.add(speed);
        oscP5.send(k, myRemoteLocation);
    }
}

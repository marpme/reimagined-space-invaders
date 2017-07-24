package spacey.game;

import netP5.NetAddress;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import spacey.game.note.NoteCollisionDetector;
import spacey.game.note.NoteRemoveHandler;
import spacey.game.note.NoteShape;
import spacey.game.note.NoteSpawnHandler;
import spacey.interfaces.OSCListener;
import spacey.music.MidiTickHandler;
import spacey.music.NoteComposer;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SpaceInvaders extends PApplet {

    private static final String DACING_QUEEN = SpaceInvaders.class.getResource("ABBA_-_Dancing_Queen.mid").getPath();
    private static final String MOZART = SpaceInvaders.class.getResource("sym40-1.mid").getPath();
    private static final String BEVERLY = SpaceInvaders.class.getResource("Movie_Themes_-_Beverly_Hills_Cop_-_Axel's_Theme_by_Harold_Faltermeyer.mid").getPath();
    private static final String BACKSTREET = SpaceInvaders.class.getResource("Backstreet_Boys_I'll_Never_Break_Your_Heart.mid").getPath();
    private static final String KYOTO = SpaceInvaders.class.getResource("Skrillex_Kyoto_(ft._Sirah).mid").getPath();
    private static final String SPACER = SpaceInvaders.class.getResource("spacer.svg").getPath();
    private static final String NOTE = SpaceInvaders.class.getResource("musical-note.svg").getPath();
    private static final String BG = SpaceInvaders.class.getResource("bg.jpg").getPath();
    private static List<String> resourceList = new LinkedList<>();

    static {
        resourceList.addAll(Arrays.asList(DACING_QUEEN, MOZART, BEVERLY, BACKSTREET, KYOTO));
    }

    public OscP5 oscP5;
    public NetAddress myRemoteLocation;
    public float velocityX = 0;
    public float speed = 5;
    private SpaceMan spaceMan;
    private PShape musicNote;
    private PImage bg;
    private NoteRemoveHandler removeHandler;

    public static String getNOTE() {
        return NOTE;
    }

    public static void main(String... args) {
        PApplet.main(SpaceInvaders.class);
    }

    public static String getRandomTrack() {
        int len = resourceList.size();
        return resourceList.get((int) (Math.random() * len));
    }

    public static String getSPACER() {
        return SPACER;
    }

    public static String getBG() {
        return BG;
    }

    @Override
    public void settings() {
        super.settings();
        size(845, 500);
    }

    @Override
    public void setup() {
        frameRate(30);
        oscP5 = new OscP5(this, 9455);
        oscP5.addListener(new OSCListener(this));
        myRemoteLocation = new NetAddress("192.168.178.35", 8455);

        PShape spacer = this.loadShape(SpaceInvaders.getSPACER());
        spacer.disableStyle();
        // don't scale down here, will not interfere with the real width and height
        // spacer.scale(.5f);
        spaceMan = new SpaceMan(spacer, .50f * this.width, .92f * this.height);

        musicNote = this.loadShape(SpaceInvaders.getNOTE());
        musicNote.disableStyle();
        musicNote.setFill(255);

        bg = loadImage(getBG());

        try {
            Sequence sequence = MidiSystem.getSequence(new File(SpaceInvaders.getRandomTrack()));
            NoteComposer noteComposer = new NoteComposer(sequence);
            MidiTickHandler TickHandler = new MidiTickHandler(noteComposer);
            NoteSpawnHandler.startFactory(TickHandler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        this.removeHandler = new NoteRemoveHandler(this.height);
        removeHandler.start();
    }

    @Override
    public void draw() {
        background(255);
        image(this.bg, 0, 0, this.width, this.height);
        spaceMan.addXVelocity(velocityX);
        new NoteCollisionDetector(
                spaceMan.getCenteredX(),
                spaceMan.getCenteredY(),
                spaceMan.getWidth(),
                spaceMan.getHeight(),
                NoteShape.listOfNoteShape,
                spaceMan
        ).start();

        fill(94, 232, 215);
        stroke(255);
        shape(
                spaceMan.getSpacer(),
                spaceMan.getCenteredX(),
                spaceMan.getCenteredY(),
                spaceMan.getWidth(),
                spaceMan.getHeight()
        );


        fill(255);
        for (NoteShape next : NoteShape.listOfNoteShape) {
            next.move();
            shape(musicNote, next.getX(), next.getY(), next.getWidth(), next.getHeight());
        }

        textSize(32);
        fill(0, 102, 153);
        text("Points: " + (int) spaceMan.getFloatProperty(), 10, 60);
    }

}

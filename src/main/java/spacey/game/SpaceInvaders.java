package spacey.game;

import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
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

    private OscP5 oscP5;
    private NetAddress myRemoteLocation;
    private float x, y;
    public float velocityX = 0;
    public float speed = 5;
    private PShape spaceMan;
    private PShape musicNote;
    private PImage bg;

    public static String getNOTE() {
        return NOTE;
    }

    public static void main(String... args) {
        PApplet.main(SpaceInvaders.class);
    }

    private static String getRandomTrack() {
        int len = resourceList.size();
        return resourceList.get((int) (Math.random() * len));
    }

    private static String getSPACER() {
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

        spaceMan = this.loadShape(SpaceInvaders.getSPACER());
        spaceMan.disableStyle();

        musicNote = this.loadShape(SpaceInvaders.getNOTE());
        musicNote.disableStyle();
        musicNote.setFill(255);

        bg = loadImage(BG);

        rectMode(CENTER);
        x = .50f * this.width;
        y = .92f * this.height;

        try {
            Sequence sequence = MidiSystem.getSequence(new File(SpaceInvaders.getRandomTrack()));
            NoteComposer noteComposer = new NoteComposer(sequence);
            MidiTickHandler TickHandler = new MidiTickHandler(noteComposer);
            BlockSpawnHandler spawnHandler = new BlockSpawnHandler(TickHandler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void draw() {
        background(255);
        image(this.bg, 0, 0, this.width, this.height);
        this.x += velocityX;

        fill(94, 232, 215);
        shape(spaceMan, this.x - spaceMan.width / 2f, this.y - spaceMan.height / 2f, 85.6f / 2f, 68.48f / 2f);

        for (int i = Rect.listOfRect.size() - 1; i >= 0; i--) {
            if (Rect.listOfRect.get(i).isOutOfWindow(this.height))
                Rect.listOfRect.remove(i);
            else
                break;
        }

        fill(255);
        for (Rect next : Rect.listOfRect) {
            next.move();
            shape(musicNote, next.x, next.y, 50, 50);
        }
    }
}

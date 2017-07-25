package spacey.game;

import netP5.NetAddress;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import spacey.game.note.*;
import spacey.interfaces.OSCListener;
import spacey.music.MidiNote;
import spacey.music.MidiTickHandler;
import spacey.music.NoteComposer;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;

public class SpaceInvaders extends PApplet {


    public OscP5 oscP5;
    public NetAddress myRemoteLocation;
    public float velocityX = 0;
    public float speed = 5;
    private SpaceMan spaceMan;
    private PShape musicNote;
    private PImage bg;
    private NoteRemoveHandler removeHandler;
    private float spacer;


    public static void main(String... args) {
        PApplet.main(SpaceInvaders.class);
    }

    @Override
    public void settings() {
        super.settings();
        size(845, 500);
    }

    @Override
    public void setup() {
        frameRate(30);
        smooth();
        colorMode(HSB, 360, 100, 100);
        oscP5 = new OscP5(this, 9455);
        oscP5.addListener(new OSCListener(this));
        myRemoteLocation = new NetAddress("192.168.178.35", 8455);

        PShape spacer = this.loadShape(ResourcesHandler.SPACER);
        spacer.disableStyle();
        // don't scale down here, will not interfere with the real width and height
        // spacer.scale(.5f);
        spaceMan = new SpaceMan(spacer, .50f * this.width, .92f * this.height);

        musicNote = this.loadShape(ResourcesHandler.NOTE);
        musicNote.disableStyle();
        musicNote.setFill(255);

        bg = loadImage(ResourcesHandler.BACKGROUND);

        try {
            Sequence sequence = MidiSystem.getSequence(new File(ResourcesHandler.getRandomTrack()));
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

        fill(168, 100, 100);
        shape(
                spaceMan.getSpacer(),
                spaceMan.getCenteredX(),
                spaceMan.getCenteredY(),
                spaceMan.getWidth(),
                spaceMan.getHeight()
        );


       strokeWeight(this.width / MidiNote.range);
        strokeCap(ROUND);
        for (int i = 0; i < NoteLine.listOfNoteLines.length(); i++) {
            NoteLine next = NoteLine.listOfNoteLines.get(i);
            if(next == null) continue;
            next.moveNote();
            float mapped = mapNumber(next.getX(), 0, this.width, 1, 360);
            stroke(mapped, 100, 100);
            line(next.getX(), next.getStartY(), next.getX(), next.getEndY());
        }
        noStroke();

        strokeWeight(this.width / MidiNote.range);
        strokeCap(ROUND);
        for(NoteLine next : NoteLine.movingNotes){
            next.moveNote();
            float mapped = mapNumber(next.getX(), 0, this.width, 1, 360);
            stroke(mapped,100,100);
            line(next.getX(), next.getStartY(), next.getX(), next.getEndY());
        }
        noStroke();

        textSize(32);
        fill(0, 102, 153);
        text("Points: " + (int) spaceMan.getFloatProperty(), 10, 60);
    }

    public float mapNumber(float X, float start1, float end1, float start2, float end2){
        return (X-start1)/(end1-start1) * (end2-start2) + start2;
    }

}

package spacey.game;

import netP5.*;
import oscP5.*;
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
import java.net.URL;

/**
 * The main game supported by PApplet a part of the processing framework (the core more or less)
 * Most of the logic behind of this goes to the Processing foundation @ https://processing.org/
 */
public class SpaceInvaders extends PApplet {

    // osc server for handling incoming and out going request
    public OscP5 OscP5Server;


    public float velocityX = 0;
    // Constances for the game, there could be settings for them ?!
    public final float speed = 5;

    private SpaceMan spaceMan;
    private PShape musicNote;
    private PImage bg;
    private NoteRemoveHandler removeHandler;

    public static void main(String[] args) {
        PApplet.main(SpaceInvaders.class);
    }

    /**
     * Overall Window settings, like sizes and background Processing things
     */
    @Override
    public void settings() {
        super.settings();
        size(845, 500);
    }

    /**
     * The main setup section where we create the color schema, the midi track,
     * the connection to the midi output device, the osc server for the osc requests,
     * all shapes that are being used, the midi tick handler and a bit of game logic like
     * the remove handler which removes the Notes after being out of screen!
     */
    @Override
    public void setup() {
        frameRate(30);
        smooth();
        colorMode(HSB, 360, 100, 100);

        URL randomTrack = ResourcesHandler.getRandomTrack();

        OscP5Server = new OscP5(this, 8455, OscP5.UDP);
        OscP5Server.addListener(new OSCListener(this));
        NetAddress myRemoteLocation = new NetAddress("192.168.178.35", 9455);

        String fileName = randomTrack.getFile().substring(randomTrack.getFile().lastIndexOf('/') + 1, randomTrack.getFile().length());
        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));

        OscP5Server.send(new OscMessage("/game/title").add("Music played: " + fileNameWithoutExtn), myRemoteLocation);
        OscP5Server.send(new OscMessage("/game/finished").add("Percentage finished: 0%"), myRemoteLocation);

        PShape spacer = this.loadShape(ResourcesHandler.SPACER);
        spacer.disableStyle();

        spaceMan = new SpaceMan(spacer, .50f * this.width, .92f * this.height);

        musicNote = this.loadShape(ResourcesHandler.NOTE);
        musicNote.disableStyle();
        musicNote.setFill(255);

        bg = loadImage(ResourcesHandler.BACKGROUND);

        try {
            Sequence sequence = MidiSystem.getSequence(new File(randomTrack.getPath()));
            NoteComposer noteComposer = new NoteComposer(sequence, this.width);
            MidiTickHandler TickHandler = new MidiTickHandler(noteComposer);
            NoteSpawnHandler.startFactory(TickHandler, noteComposer.getNotes().size(), OscP5Server, myRemoteLocation);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        this.removeHandler = new NoteRemoveHandler(this.height);
        removeHandler.start();
    }

    /**
     * This will be called ONCE per FRAME (60 times a second <= 60FPS)
     * In our example just 30 ... :) RESOURCES!!!! HOLY CRAP
     */
    @Override
    public void draw() {
        // default black background
        background(0, 0, 0); // <- make sure this ISN'T RGB! It's HSB!
        // loading the bg into a image and draw it
        image(this.bg, 0, 0, this.width, this.height);

        // Adds the velocity to the space man, if there is any!
        spaceMan.addXVelocity(velocityX);

        // Start the collision detection for this frame draw and start the thread.
        // will automatically remove colliding notes
        new NoteCollisionDetector(
                spaceMan.getCenteredX(),
                spaceMan.getCenteredY(),
                spaceMan.getWidth(),
                spaceMan.getHeight(),
                NoteLine.movingNotes,
                spaceMan
        ).start();

        // Insert a filling and the space man shape
        fill(168, 100, 100); // HSB
        shape(
                spaceMan.getSpacer(),
                spaceMan.getCenteredX(),
                spaceMan.getCenteredY(),
                spaceMan.getWidth(),
                spaceMan.getHeight()
        );

        /*
         * Let's draw any note we have! Strokes makes them bigger based on the availability of notes.
         * Cap rounds them so they don't look that odd!
         *
         * THERE IS A HUGH DIFFERENCE IN listOfNoteLines AND movingNotes
         */
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

        // write down the current number of points
        textSize(32);
        fill(0, 102, 153);
        text("Points: " + (int) spaceMan.getFloatProperty(), 10, 60);
    }

    /**
     * Maps numbers from X to Y, to A and B!
     *
     * @param X      the current value which is being mapped
     * @param start1 X
     * @param end1   Y
     * @param start2 A
     * @param end2   B
     * @return the mapped number which you insert.
     */
    public float mapNumber(float X, float start1, float end1, float start2, float end2){
        return (X-start1)/(end1-start1) * (end2-start2) + start2;
    }

}

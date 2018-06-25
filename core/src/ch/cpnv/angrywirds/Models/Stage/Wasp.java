package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ch.cpnv.angrywirds.Activities.Play;
import ch.cpnv.angrywirds.AngryWirds;

import static ch.cpnv.angrywirds.Activities.GameActivity.WORLD_HEIGHT;
import static ch.cpnv.angrywirds.Activities.GameActivity.WORLD_WIDTH;

public final class Wasp extends MovingObject {

    public final static float width = 60;
    public final static float height = 60;
    private static final String spritePath = "wasp.png";
    private static final int AGITATION = 15;

    //public final static String spritePath = "wasp.png";

    public Wasp(Vector2 position, Vector2 speed) {
        super(position, spritePath,width,height,speed);
    }

    @Override
    public void accelerate(float dt) {
        // The wasp only slightly alters its speed at random. It is subject to gravity, but it counters it with its flight
        speed.x += (AngryWirds.alea.nextFloat()-sprite.getX()/ Play.WORLD_WIDTH)*AGITATION; // the closer it is to a border, the higher the chances that acceleration goes the other way
        speed.y += (AngryWirds.alea.nextFloat()-sprite.getY()/ Play.WORLD_HEIGHT)*AGITATION;

//        float coeffx, coeffy;
//        coeffx = this.sprite.getX() / WORLD_WIDTH *2;
//        coeffy = this.sprite.getY() / WORLD_HEIGHT *2;
//        if (speed.x > 2000 || speed.y > 2000){
//            speed.x -= 1000;
//            speed.y -= 1000;
//        }
//        speed.x += ((AngryWirds.alea.nextFloat()*2)-coeffx)*50;
//        speed.y += ((AngryWirds.alea.nextFloat()*2)-coeffy)*50;
    }

}
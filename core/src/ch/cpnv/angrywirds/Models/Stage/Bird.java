package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.angrywirds.Activities.Play;
import ch.cpnv.angrywirds.AngryWirds;

public class Bird extends MovingObject{

    public enum BirdState { init, aim, fly }

    public final static int WIDTH = 60;
    public final static int HEIGHT = 60;
    private static final String spritePath = "bird.png";
    private BirdState state = BirdState.init;


    public BirdState getState() {
        return state;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public void setStateAim(){
        this.state = BirdState.aim;
    }

    public void setStateFly(){
        this.state = BirdState.fly;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public Bird() {
        super(new Vector2(Play.TWEETY_START_X, Play.TWEETY_START_Y), spritePath, WIDTH, HEIGHT,new Vector2(0,0));
    }

    @Override
    public void accelerate(float dt) {
        if (state == BirdState.fly) speed.y -= dt * MovingObject.G;//;
    }

    public void reset() {
        sprite.setX(Play.TWEETY_START_X);
        sprite.setY(Play.TWEETY_START_Y);
        this.freeze();
        this.state = BirdState.init;
    }
}
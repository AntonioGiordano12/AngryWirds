package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public abstract class MovingObject extends PhysicalObject {

    public final static float G = 600.81f;
    protected boolean freeze = true;
    protected Vector2 speed;

    public MovingObject(Vector2 position, String spritePath, float width, float height, Vector2 speed)
    {
    	super(position,spritePath, width,height);
    	this.speed = speed;
	}

	public abstract void accelerate(float dt);

    public final void move(float dt){
        if (!freeze){
        this.sprite.translate(speed.x * dt, speed.y * dt);
        //this.sprite.setPosition(this.sprite.getX() + this.speed.x * dt, this.sprite.getY() + this.speed.y * dt);
        }
    }

    public void freeze(){
        freeze = true;
    }

    public void unfreeze(){
        freeze = false;
    }

    public boolean isFreeze(){
        return freeze;
    }


    @Override
    public String toString(){
    	return getClass().getSimpleName()+" at ("+this.sprite.getX()+","+this.sprite.getY()+") moving at ("+ this.speed.x +","+this.speed.y+")";
    }

}
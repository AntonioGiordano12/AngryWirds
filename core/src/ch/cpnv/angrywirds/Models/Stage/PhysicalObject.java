package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.math.Rectangle;


public class PhysicalObject {

    public Sprite sprite;

    public PhysicalObject(Vector2 position, String spritePath, float width, float height)
    {
        this.sprite = new Sprite(new Texture(spritePath));
        this.sprite.setBounds(position.x,position.y,width,height);
    }

    @Override
    public String toString()
    {
    	return getClass().getSimpleName()+" at ("+this.sprite.getX()+","+this.sprite.getY()+")";
    }

    public Vector2 getDimension()
    {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    public Vector2 getPosition()
    {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setPosition(Vector3 position){
        this.sprite.setPosition(position.x,position.y);
    }

    public Sprite getSprite() { return sprite; }

    public void touch(){

    }

    public Rectangle getRectangle()
    {
        return new Rectangle((int)this.getPosition().x,(int)this.getPosition().y,(int)this.getDimension().x,(int)this.getDimension().y);
    }

    public boolean collidesWith(PhysicalObject o){
        return this.getRectangle().overlaps(o.getRectangle());
    }

    public void draw(Batch batch)
    {
        sprite.draw(batch);
    }

}
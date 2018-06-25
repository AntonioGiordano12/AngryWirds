package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Xavier on 31.05.18.
 */

public class RubberBand extends PhysicalObject {
    private static final String PICNAME = "rubber.png";
    private static final float THICKNESS = 12f;

    public RubberBand(){
        super(new Vector2(0,0),PICNAME,0,0);
    }

    public void between (Vector2 ori, Vector2 dest) {
        Vector2 diff = dest.sub(ori);
        sprite.setBounds(ori.x,ori.y,diff.len(), THICKNESS);
        sprite.setOrigin(0,0);
        sprite.setRotation(diff.angle());
    }

}
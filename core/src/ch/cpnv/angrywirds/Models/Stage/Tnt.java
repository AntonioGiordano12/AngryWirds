package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by antonio.giordano on 03.05.2018.
 */

public class Tnt extends PhysicalObject {
    private int NEGATIVEPOINTS = 25;
    public final static float width = 60;
    public final static float height = 60;
    private static final String spritePath = "tnt.png";



    public Tnt(Vector2 position, int negativePoints) {
        super(position, spritePath,width,height);
        this.NEGATIVEPOINTS = negativePoints;
    }

    public int getNEGATIVEPOINTS(){
        return NEGATIVEPOINTS;
    }


}

package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ch.cpnv.angrywirds.Models.Data.Language;

public class LvlButton { // Similaire à une bubble, affiche comme un boutton à l'écran


    private static final String PICNAME = "button.png";
    private static final int WIDTH = 250;
    private static final int HEIGHT = 100;
    private static final int TEXT_OFFSET_X = 40; // to place the text inside the bubble
    private static final int TEXT_OFFSET_Y = 80;
    private static final int SCORE_OFFSET_X = 300; // to place the text inside the bubble
    private static final int SCORE_OFFSET_Y = 80; // to place the text inside the bubble


    public Sprite sprite;
    private BitmapFont font;
    private String message;
    private int id;
    private String result;
    private int assign;


    public LvlButton(float x, float y, String title, String result, int id, int assignment){
        this.message = title;
        sprite = new Sprite(new Texture(PICNAME));
        sprite.setBounds(x, y, WIDTH,HEIGHT);
        font=new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);
        this.id = id;
        this.result = result;
        this.assign = assignment;
    }

    public void draw(Batch batch)
    {
        sprite.draw(batch);
        font.draw(batch, message,  sprite.getX()+ TEXT_OFFSET_X,sprite.getY()+ TEXT_OFFSET_Y  );
        font.draw(batch, "resultat : " + result ,  sprite.getX()+ SCORE_OFFSET_X,sprite.getY()+SCORE_OFFSET_Y);

    }

    public int getAssign() {
        return assign;
    }

    public int getLvlid() {
        return id;
    }


    public Sprite getSprite() {
        return sprite;
    }

}

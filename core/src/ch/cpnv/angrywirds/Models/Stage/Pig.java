package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import ch.cpnv.angrywirds.Models.Data.Word;

/**
 * Created by antonio.giordano on 03.05.2018.
 */

public final class Pig extends PhysicalObject {

    private Word word;
    public final static float width = 60;
    public final static float height = 60;
    private static final String spritePath = "pig.png";


    public Pig(Vector2 position, Word word) {
        super(position, spritePath,width,height);
        this.word = word;
    }

    @Override
    public void touch(){
        Gdx.app.log("message", "message : " + this.getWord());
    }


    public String getWordValue() {  return word.getValue2(); }

    public void setWord(Word word) { this.word = word; }

    public Word getWord() { return word; }
}

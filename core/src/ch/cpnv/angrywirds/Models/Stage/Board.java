package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ch.cpnv.angrywirds.Activities.GameActivity;
import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Data.Word;

public class Board {
    private static final String PICNAME = "panel.png";
    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 200;
    private static final int BOARD_OFFSET = 50; // from left edge
    private static final int TEXT_OFFSET_X = 40; // to place the text inside the bubble
    private static final int TEXT_OFFSET_Y = 100;

    private Word word;
    private Sprite sprite;
    private BitmapFont font;

    public Board(Word word) {
        this.word = word;
        sprite = new Sprite(new Texture(PICNAME));
        sprite.setBounds(BOARD_OFFSET, GameActivity.WORLD_HEIGHT-BOARD_HEIGHT, BOARD_WIDTH, BOARD_HEIGHT);
        font= new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(2);
    }

    public void draw(Batch batch)
    {
        sprite.draw(batch);
        font.draw(batch, this.word.getValue1(), sprite.getX()+TEXT_OFFSET_X, sprite.getY()+TEXT_OFFSET_Y);
    }

    public int getWordId() {
        return word.getId();
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
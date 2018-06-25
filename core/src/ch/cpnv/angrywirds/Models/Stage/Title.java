package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ch.cpnv.angrywirds.Activities.GameActivity;
import ch.cpnv.angrywirds.Providers.FontProvider;

/**
 * Created by Xavier on 10.06.18.
 */

public final class Title {

    private BitmapFont font;
    private GlyphLayout layout;
    private float x;
    private float y;

    public Title(String message) {
        initBase(message);
        x = (GameActivity.WORLD_WIDTH-layout.width)/2;
        y = (GameActivity.WORLD_HEIGHT+layout.height)/2;
    }

    public Title(String message, float px, float py) {
        initBase(message);
        x = px;
        y = py;
    }

    private void initBase(String message)
    {
        layout = new GlyphLayout();
        font = FontProvider.Title;
        setText(message);
    }

    public void draw(Batch batch)
    {
        font.draw(batch, layout, x, y);
    }

    public void setText (String text)
    {
        layout.setText(font,text);
    }
}
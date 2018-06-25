package ch.cpnv.angrywirds.Providers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Xavier on 10.06.18.
 */

public abstract class FontProvider {

    public static BitmapFont Title;
    public static BitmapFont h1;

    public static void load() {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = "abcdefghijklmnopqrstuvwxyzàéèêëùABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Disko.ttf"));
        parameter.size = 150;
        parameter.color = Color.BLUE;
        Title = generator.generateFont(parameter);

        parameter.size = 80;
        h1 = generator.generateFont(parameter);
    }
}
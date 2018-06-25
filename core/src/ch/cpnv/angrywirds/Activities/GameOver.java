package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Stage.ScoreBoard;
import ch.cpnv.angrywirds.Models.Stage.Title;
import ch.cpnv.angrywirds.Providers.VocProvider;


public class GameOver extends GameActivity {

    private Texture background;
    private Title title;

    public GameOver(int usedId)
    {
        super();
        background = new Texture(Gdx.files.internal("background.png"));
        VocProvider.submitResults(usedId);
        title = new Title("Game Over\n score: "+ ScoreBoard.score + "\n" + VocProvider.finalStatus);


    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
        {
            AngryWirds.gameActivityManager.pop(); // game over
            AngryWirds.gameActivityManager.pop(); // play
            AngryWirds.gameActivityManager.pop(); // select
            AngryWirds.gameActivityManager.pop(); // welcome
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        title.draw(spriteBatch);
        spriteBatch.end();
    }
}
package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Stage.Title;
import ch.cpnv.angrywirds.Providers.VocProvider;


public class Welcome extends GameActivity  {

    private Game game;
    private Stage stage;

    private Texture background;
    private Title title;
    private float splashTime = 2;
    private Sprite wheel1;
    private Sprite wheel2;

    public Welcome()
    {
        super();

        background = new Texture(Gdx.files.internal("background.png"));
        title = new Title("Angry Wirds");
        wheel1 = new Sprite(new Texture("cog.png"));
        wheel2 = new Sprite(new Texture("cog.png"));
        wheel1.setBounds(camera.viewportWidth/2 - 45, camera.viewportHeight/4,100,100);
        wheel2.setBounds(camera.viewportWidth/2 + 45, camera.viewportHeight/4,100,100);
        wheel1.setOrigin(50,50);
        wheel2.setOrigin(50,50);
        wheel2.setRotation(15);
        VocProvider.load();

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        wheel1.rotate(1);
        wheel2.rotate(-1);
        if (splashTime > 0)
            splashTime -= dt;
        else
        if (VocProvider.status == VocProvider.Status.loaded) {
            AngryWirds.gameActivityManager.push(new VocabularyChoice());
        }

//        if (VocProvider.status == VocProvider.Status.ready) {
//            AngryWirds.gameActivityManager.push(new Play());
//        }
    }


    @Override
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        title.draw(spriteBatch);
        wheel1.draw(spriteBatch);
        wheel2.draw(spriteBatch);
        spriteBatch.end();

        // clear the screen ready for next set of images to be drawn
//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // tell our stage to do actions and draw itself
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();

    }

//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void show() {
//        Gdx.app.log("show", "Showed");
//    }








}

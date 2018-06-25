package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameActivity {


    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 900;

    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch = new SpriteBatch();

    protected GameActivity() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0,0,0,0);
    }

    protected abstract void handleInput();

    public abstract void update(float dt);
    public abstract void render();


//        VocProvider.load();
//    babble = new ArrayList<Bubble>();
//    camera = new OrthographicCamera();
//        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
//        camera.update();
//
//    batch = new SpriteBatch();
//        batch.setProjectionMatrix(camera.combined);
//    //img = new Texture("badlogic.jpg");
//
//        Gdx.input.setInputProcessor(this);
//
//    random = new Random();
//
//    scenery = new Scenery();


}


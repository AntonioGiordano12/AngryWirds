package ch.cpnv.angrywirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.util.Random;

import ch.cpnv.angrywirds.Activities.GameActivityManager;
import ch.cpnv.angrywirds.Activities.Welcome;
import ch.cpnv.angrywirds.Providers.FontProvider;


public class AngryWirds extends Game {

    static public GameActivityManager gameActivityManager = new GameActivityManager();
    public static Random alea; // random generator object. Static for app-wide use




    @Override
    public void create () {
        alea = new Random();
        FontProvider.load();
        gameActivityManager.push(new Welcome());
    }

    @Override
    public void render () {
        gameActivityManager.handleInput();
        gameActivityManager.update(Gdx.graphics.getDeltaTime());
        gameActivityManager.render();
    }

    @Override
    public void dispose () {
    }



    /*
    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    @Override
    public void create() {
        gameActivityManager = new GameActivityManager();

        //scenery.addElement(new Tnt(300,20,"tnt.jpg"));

        //tnt = new Sprite(new Texture("tnt.png"));
        //tnt.setBounds(800,110,100,100);

        //wasp = new Sprite(new Texture("wasp.png"));
        //wasp.setBounds(500, 500, 100, 80);
    }

    private void update() {
//        for (Bubble buble : babble
//             ) {
//            buble.ageAway(dt);
//            if (buble.isDead()) babble.remove(buble);
//        }

        //Gdx.app.log("ANGRY", "dt = "+ dt);


        //x += 100*dt;
    }

    @Override
    public void render() {
        update();
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        scenery.draw(batch);
        //wasp.draw(batch);
        batch.end();
    }
    /*
    private void lunch(){
        finalshot = new Vector2((startshot.x - endshot.x)*5, (-(startshot.y - endshot.y))*5);
        Gdx.app.log("final :  ", "final : : " + finalshot);
        bird.unfreeze();
        bird.setSpeed(finalshot);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Gdx.app.log("TOUCH : ", "x : "+x + " - y : " + y + " - pointer : " + pointer + " - button : " + button);

        for (PhysicalObject o : scenery.getAllObjects()){
            Rectangle newrect = new Rectangle((int)o.getPosition().x,(int)o.getPosition().y,(int)o.getDimension().x,(int)o.getDimension().y);
            if (newrect.contains(touchLocation.x,touchLocation.y)){
                if (newrect.contains(touchLocation.x,touchLocation.y)){
                    o.touch();
                }
                    if (o.getClass() == Bird.class){
                    bird.setStateAim();
                }
            }
        }
        startshot = new Vector2(x,y);
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        message = "Tap performed, finger" + Integer.toString(button);
        Gdx.app.log("message : ",message);

        touchPosition.set(new Vector3(x,y,0));
        touchLocation = camera.unproject(touchPosition);

        for (PhysicalObject o : scenery.getAllObjects()){
            Rectangle newrect = new Rectangle((int)o.getPosition().x,(int)o.getPosition().y,(int)o.getDimension().x,(int)o.getDimension().y);
            if (newrect.contains(touchLocation.x,touchLocation.y)){
                Gdx.app.log("hit", "hit : " + o.getClass().getSimpleName());
                o.touch();
            }
        }



        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        message = "Fling performed, velocity:" + Float.toString(velocityX) +
                "," + Float.toString(velocityY);
        Gdx.app.log("message : ",message);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        message = "Pan performed, delta:" + x +
                "," + y;
        Gdx.app.log("message : ",message);
        if (bird.getState() == Bird.BirdState.aim) {
            touchPosition.set(new Vector3(x, y, 0));
            touchLocation = camera.unproject(touchPosition);
            touchLocation.x -= 50;
            touchLocation.y -= 50;

            bird.setPosition(touchLocation);
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("PANSTOP : ", "x : "+x + " - y : " + y + " - pointer : " + pointer + " - button : " + button);
        if (bird.getState() == Bird.BirdState.aim) {

            endshot = new Vector2(x, y);
            lunch();
        }
        return false;
    }
    */

}

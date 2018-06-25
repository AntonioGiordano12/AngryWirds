package ch.cpnv.angrywirds.Activities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Data.Vocabulary;
import ch.cpnv.angrywirds.Models.Stage.Bird;
import ch.cpnv.angrywirds.Models.Stage.Board;
import ch.cpnv.angrywirds.Models.Stage.Bubble;
import ch.cpnv.angrywirds.Models.Stage.PhysicalObject;
import ch.cpnv.angrywirds.Models.Stage.Pig;
import ch.cpnv.angrywirds.Models.Stage.RubberBand;
import ch.cpnv.angrywirds.Models.Stage.Scenery;
import ch.cpnv.angrywirds.Models.Stage.ScoreBoard;
import ch.cpnv.angrywirds.Models.Stage.Tnt;
import ch.cpnv.angrywirds.Models.Stage.Wasp;
import ch.cpnv.angrywirds.Providers.VocProvider;

public class Play extends GameActivity implements InputProcessor {

    public static final int FLOOR_HEIGHT = 120;
    private static final int SLINGSHOT_WIDTH = 75;
    private static final int SLINGSHOT_HEIGHT = 225;
    private static final int SLINGSHOT_OFFSET = 100; // from left edge
    public static final int TWEETY_START_X = SLINGSHOT_OFFSET + (SLINGSHOT_WIDTH - Bird.WIDTH) / 2;
    public static final int TWEETY_START_Y = FLOOR_HEIGHT + SLINGSHOT_HEIGHT - Bird.HEIGHT;

    private static final int BOARD_WIDTH = 300;
    private static final int BOARD_HEIGHT = 200;
    private static final int BOARD_OFFSET = 50; // from left edge
    private static final float ELASTICITY = 6f;
    private final int SCORE_BUMP_SUCCESS = 7;
    private final int SCORE_BUMP_FAIL = 1;
    private final int TNT_PENALTY = 5;


//    public static final int TWEETY_START_X = SLINGSHOT_OFFSET + (SLINGSHOT_WIDTH - Bird.WIDTH) / 2;
//    public static final int TWEETY_START_Y = FLOOR_HEIGHT + SLINGSHOT_HEIGHT - Bird.HEIGHT;

    private Board board;
    private ScoreBoard scoreBoard;
    private Scenery scenery;
    private Bird bird;
    private Wasp wasp;
    private ArrayList<Bubble> babble;

    private Texture background , slingshot1, slingshot2;
    private RubberBand rubberBand1;
    private RubberBand rubberBand2;

    private Queue<Touch> actions;
    private Vocabulary vocabulary;

    private int usedID;
    private int assignID;

    public Play(int id, int assign){
        super();
        assignID = assign;
        usedID = id;
        babble = new ArrayList<Bubble>();
        vocabulary = VocProvider.vocabulary.get(id-1);

        background = new Texture("background.jpg");
        slingshot1 = new Texture("slingshot1.png");

        slingshot2 = new Texture(Gdx.files.internal("slingshot2.png"));

        bird = new Bird();
        wasp = new Wasp(new Vector2(WORLD_WIDTH / 2, WORLD_HEIGHT / 2), new Vector2(20,20));
        wasp.unfreeze();

        rubberBand1 = new RubberBand();
        rubberBand2 = new RubberBand();

        scenery = new Scenery();

        try{
            scenery.addElement(bird);
            scenery.addElement(wasp);
        } catch (Exception e){
            Gdx.app.log("ANGRY", "Error");
        }

        for (int i = 5; i < WORLD_WIDTH / 50; i++) {
            try {
                scenery.addElement(new PhysicalObject(new Vector2(i*50 , FLOOR_HEIGHT), "block.png", 50, 50));

            } catch (Exception e) {
                Gdx.app.log("ANGRY", "Could not add block to scenery");
            }
        }
        for (int i = 0; i < 2; i++) {
            try {
                scenery.addElement(new Tnt(new Vector2(AngryWirds.alea.nextInt(WORLD_WIDTH * 2 / 3) + WORLD_WIDTH / 3, FLOOR_HEIGHT + 50), TNT_PENALTY));
            } catch (Exception e) {
                Gdx.app.log("ANGRY", "Could not add TNT to scenery");
            }
        }
        for (int i = 0; i < 8; i++) {
            try {

                scenery.addElement(new Pig(new Vector2(AngryWirds.alea.nextInt(WORLD_WIDTH * 2 / 3) + WORLD_WIDTH / 3, FLOOR_HEIGHT + 50), vocabulary.pickAWord()));

            } catch (Exception e) {
                Gdx.app.log("ANGRY", "Could not add Pig to scenery");
            }
        }

        board = new Board(scenery.pickAWord()); // Put one word from a pig on the board
        scoreBoard = new ScoreBoard(70,240);

        Gdx.input.setInputProcessor(this);
        actions = new LinkedList<Touch>();

    }

    public void update(float dt){
        //Gdx.app.log("UPDATEPLAY", "adqq");
        // --------- Bird
        bird.accelerate(dt);
        bird.move(dt);
        wasp.accelerate(dt);
        wasp.move(dt);
        PhysicalObject hit = null;
        for(PhysicalObject o : scenery.getAllObjects())
        {
            if(o.collidesWith(bird) && o.getClass() != Bird.class){
                hit = o;
            }
        }


//                Gdx.app.log("touch", "objet : " + p.getWord().getId() + " -- board : " + board.getWordId());

        if (hit != null) {
            String c = hit.getClass().getSimpleName();

            if(hit.getClass() == Wasp.class){
                scoreBoard.scoreChange(-100);
                AngryWirds.gameActivityManager.push(new GameOver(assignID));
            }

            if (c.equals("Tnt")){
                scoreBoard.scoreChange(-((Tnt) hit).getNEGATIVEPOINTS());
            }
            else if (c.equals("Pig"))
            {
                Pig p = (Pig)hit;
                if(p.getWord().getId() == board.getWordId()){ //CORRECT
                    scoreBoard.scoreChange(SCORE_BUMP_SUCCESS);
                    p.setWord(vocabulary.pickAWord());
                    board.setWord(scenery.pickAWord());
                } else {
                    scoreBoard.scoreChange(-SCORE_BUMP_FAIL);
                }
            }
            bird.reset();
        }


        if (bird.getSprite().getX() > WORLD_WIDTH - Bird.WIDTH) bird.reset();

        // --------- Bubbles
        for (int i = babble.size() - 1; i >= 0; i--) { // we go reverse, so that removing items does not affect the rest of the loop
            babble.get(i).ageAway(dt);
            if (babble.get(i).isDead()) babble.remove(i);
        }

        // --------- Rubberbands
        rubberBand1.between(new Vector2(bird.getSprite().getX() + 20, bird.getSprite().getY() + 10), new Vector2(SLINGSHOT_OFFSET + SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT + FLOOR_HEIGHT - 40));
        rubberBand2.between(new Vector2(bird.getSprite().getX() + 20, bird.getSprite().getY() + 10), new Vector2(SLINGSHOT_OFFSET + 15, SLINGSHOT_HEIGHT + FLOOR_HEIGHT - 40));

        // --------- Scoreboard
        scoreBoard.update(dt);
        if (scoreBoard.gameOver())
            AngryWirds.gameActivityManager.push(new GameOver(assignID));
    }

    @Override
    protected void handleInput() {
        Touch action;
        while ((action = actions.poll()) != null) {
            switch (action.type) {
                case down:
                    if (bird.isFreeze() && action.point.x < TWEETY_START_X && action.point.y >= FLOOR_HEIGHT && action.point.y < TWEETY_START_Y) {
                        bird.getSprite().setX(action.point.x);
                        bird.getSprite().setY(action.point.y);
                    }

                    Pig piggy = scenery.pigTouched(action.point.x, action.point.y);
                    if (piggy != null)
                        babble.add(new Bubble(piggy.getPosition().x, piggy.getPosition().y, piggy.getWordValue(), 2));
                    break;
                case up:
                    if (bird.isFreeze() && action.point.x < TWEETY_START_X && action.point.y >= FLOOR_HEIGHT && action.point.y < TWEETY_START_Y) {
                        bird.setSpeed(new Vector2(100 + (TWEETY_START_X - action.point.x) * ELASTICITY, 100 + (TWEETY_START_Y - action.point.y) * ELASTICITY));
                        bird.unfreeze();
                        bird.setStateFly();
                    }
                    break;
                case drag:
                    if (bird.isFreeze() && action.point.x < TWEETY_START_X && action.point.y >= FLOOR_HEIGHT && action.point.y < TWEETY_START_Y) {
                        bird.getSprite().setX(action.point.x);
                        bird.getSprite().setY(action.point.y);
                    }
                    break;
            }
        }
    }


    @Override
    public void render () {



        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        board.draw(spriteBatch);
        scoreBoard.draw(spriteBatch);
        spriteBatch.draw(slingshot1, SLINGSHOT_OFFSET, FLOOR_HEIGHT, SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT);
        if (bird.isFreeze()) // Some things are only displayed while aiming
        {
            for (Bubble b : babble) b.draw(spriteBatch);
            //rubberBand1.draw(spriteBatch);
        }
        //if (bird.isFreeze())
        scenery.draw(spriteBatch);
        spriteBatch.draw(slingshot2, SLINGSHOT_OFFSET, FLOOR_HEIGHT, SLINGSHOT_WIDTH, SLINGSHOT_HEIGHT);
        spriteBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("Keydown : ", "keydown :" + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.log("Keydown : ", "keyup :" + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.log("Keydown : ", "keytyped :" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 pointTouched = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinates
        actions.add(new Touch(pointTouched, Touch.Type.down));
        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        Vector3 pointTouched = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinates
        actions.add(new Touch(pointTouched, Touch.Type.up));
        return false;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 pointTouched = camera.unproject(new Vector3(screenX, screenY, 0)); // Convert from screen coordinates to camera coordinates
        actions.add(new Touch(pointTouched, Touch.Type.drag
        ));
        return false;


    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }




}



        /*
        Gdx.app.log("TOUCHup : ", "x : "+screenX + " - y : " + screenY + " - pointer : " + pointer + " - button : " + button);
        touchup = camera.unproject(new Vector3(screenX,screenY,0));

        for (PhysicalObject o : scenery.getAllObjects()){
            Rectangle newrect = new Rectangle((int)o.getPosition().x,(int)o.getPosition().y,(int)o.getDimension().x,(int)o.getDimension().y);
            if (newrect.contains(touchup.x,touchup.y)){
                Gdx.app.log("hit", "hit : " + o.getClass().getSimpleName());
                o.touch();
            }
        }

        if (bird.getState() == Bird.BirdState.aim)
        {
            Gdx.app.log("lunch at : ", "value : "+new Vector2((touchdown.x-touchup.x),touchdown.y-touchup.y));


            bird.unfreeze();
            bird.setSpeed(new Vector2((touchdown.x-touchup.x)*5,((touchdown.y-touchup.y)*5)));
            bird.setStateFly();
        }



        return false;
        */

  /* Gdx.app.log("TOUCHdragged : ", "x : "+screenX + " - y : " + screenY + " - pointer : " + pointer);
        if (bird.getState() == Bird.BirdState.aim)
        {
            touchPosition.set(camera.unproject(new Vector3(screenX -50, screenY+50, 0)));

            bird.setPosition(touchPosition);
        }
        return false; */

/*
        Gdx.app.log("TOUCHdown : ", "x : "+screenX + " - y : " + screenY + " - pointer : " + pointer + " - button : " + button);
        touchdown = camera.unproject(new Vector3(screenX,screenY,0));

        for (PhysicalObject o : scenery.getAllObjects()){
            Rectangle newrect = new Rectangle((int)o.getPosition().x,(int)o.getPosition().y,(int)o.getDimension().x,(int)o.getDimension().y);
            if (newrect.contains(touchdown.x,touchdown.y)){
                if (o.getClass() == Bird.class){
                    Gdx.app.log("hit", "hit : " + o.getClass().getSimpleName());

                    bird.setStateAim();
                    Gdx.app.log("hit", "birdstate : " + bird.getState());
                }
                if(o.getClass() == Pig.class){
                    Pig piggy = scenery.pigTouched(touchdown.x,touchdown.y);
                    babble.add(new Bubble(o.getPosition().x,o.getPosition().y, piggy.getWord(), 2));
                }
            }
        }
        startshot = new Vector2(touchdown.x,touchdown.y);

        return false;*/


        /*for (PhysicalObject o : scenery.getAllObjects()
                ) {
            if (o.getClass() != Bird.class){
                if(bird.collidesWith(o)) {

                    bird.freeze();
                    Gdx.app.log("Colide", "colide with : " + o.getClass().getSimpleName());
                }}

        }
wasp.accelerate(dt);
        wasp.move(dt);
        if (bird.collidesWith(wasp)) {
            bird.reset();
        }
        if (bird.getSprite().getX() > WORLD_WIDTH - Bird.WIDTH) bird.reset();


        */
// --------- Wasp


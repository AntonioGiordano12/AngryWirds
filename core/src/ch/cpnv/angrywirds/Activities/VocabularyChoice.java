package ch.cpnv.angrywirds.Activities;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Data.Language;
import ch.cpnv.angrywirds.Models.Data.ToDoVocs;
import ch.cpnv.angrywirds.Models.Stage.LvlButton;
import ch.cpnv.angrywirds.Models.Stage.ScoreBoard;
import ch.cpnv.angrywirds.Models.Stage.Title;
import ch.cpnv.angrywirds.Providers.VocProvider;

public class VocabularyChoice extends GameActivity implements InputProcessor {

    private Game game;
    private Stage stage;
    private Queue<Touch> actions;


    private Texture background;
    private Title title;

    private ArrayList<LvlButton> buttons;


    public VocabularyChoice() {
        super();

        background = new Texture(Gdx.files.internal("background.png"));
        buttons = new ArrayList<LvlButton>();
        int y = 150;

        for(ToDoVocs vocs : VocProvider.todo){ // Pour chaque voc que l'on doit faire, on créer un boutton pour ce dernier
            buttons.add(new LvlButton(500,0+y,vocs.title,vocs.result, vocs.getVocId(), vocs.getAssignId()));
            y+=150;
        }

        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        title = new Title("Plus de devoir ! / Erreur");


    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        for (LvlButton button: buttons) {
            button.draw(spriteBatch);
        }
        if (VocProvider.status == VocProvider.status.nocnx) // Si status = nocnx, ça veut dire qu'une erreur de l'api est survenue
        {
            title.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 pointTouched = camera.unproject(new Vector3(screenX, screenY, 0)); // Si on click sur un LvlButton
        for (LvlButton btn : buttons ) {
            if (btn.getSprite().getBoundingRectangle().contains(new Vector2(pointTouched.x,pointTouched.y))) {
                AngryWirds.gameActivityManager.push(new Play(btn.getLvlid(), btn.getAssign())); // On lance le voc en correspondance
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

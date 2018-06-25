package ch.cpnv.angrywirds.Models.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

import ch.cpnv.angrywirds.AngryWirds;
import ch.cpnv.angrywirds.Models.Data.Word;

public class Scenery {

    ArrayList<PhysicalObject> allObjects;
    private ArrayList<Pig> pigs;

    public Scenery(){
        this.allObjects = new ArrayList<PhysicalObject>();
        pigs = new ArrayList<Pig>();
    }

    public ArrayList<PhysicalObject> getAllObjects() {
        return allObjects;
    }

    //public PhysicalObject

    public Pig pigTouched(float x, float y)
    {
        for (PhysicalObject el : allObjects)
            if (el.getClass().equals(Pig.class)) // we only care about Pigs
                if (el.getRectangle().contains(x,y))
                    return (Pig)el; // must cast because el is declared as PhysicalObject
        return null;
    }

    public PhysicalObject collidesWith(PhysicalObject o)
    {
        for (PhysicalObject el : allObjects) {
            if (el.collidesWith(o))
                return el;
        }
        return null;
    }


    public void addElement(PhysicalObject element) throws Exception{
        for (PhysicalObject o : allObjects)
            if (element.collidesWith(o))
                throw new Exception("Can't spawn item");

        allObjects.add(element);

        if (element.getClass().equals(Pig.class))
            pigs.add((Pig)element);
    }

    public Word pickAWord(){
        return pigs.get(AngryWirds.alea.nextInt(pigs.size())).getWord();
    }

    public void draw(Batch batch){
        for (PhysicalObject po: allObjects) {
            po.sprite.draw(batch);
        }

    }
}

/*try {
            boolean error = false;
            for (PhysicalObject po : allObjects) {
                startx = po.sprite.getX();
                starty = po.sprite.getY();
                finishx = po.sprite.getWidth() + startx;
                finishy = po.sprite.getWidth() + starty;
                Gdx.app.log("MSG", "startx = " +startx + " | starty = "+ starty + " | finishx" + finishx + " | finishy" + finishy);
                Gdx.app.log("MSG2", "startx = " +element.sprite.getX() + " | starty = "+ element.sprite.getY() + " | finishx" + finishx + " | finishy" + finishy);

                if (element.sprite.getX() > startx || element.sprite.getX() < finishx || element.sprite.getX() + element.sprite.getWidth() > startx || element.sprite.getX() +element.sprite.getWidth() < finishx) {
                    error = true;
                }
                if (element.sprite.getY() > starty || element.sprite.getY() < finishy){
                    error = true;
                }
            }
            if (error){
                throw new Exception("FAIL");
            }
            else {
                allObjects.add(element);

            }
        }
        catch (Exception e){
            Gdx.app.log("MSG", "1)" +e);
        }*/

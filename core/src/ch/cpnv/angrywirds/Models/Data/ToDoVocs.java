package ch.cpnv.angrywirds.Models.Data;

public class ToDoVocs { // Tous les assignements qu'on a (dans le cas ou plus de voc sont présent que ceux que l'on doit faire)

    public int vocId;
    public String title;
    public int assignId;
    public String result;

    public ToDoVocs(int assign, int vocid, String title, String result){
        this.assignId = assign;
        this.vocId = vocid;
        this.title = title;
        this.result = result;
    }

    public int getVocId() // Retourne l'id du voc utilisé
    {
        return vocId;
    }

    public int getAssignId(){ // Retourne l'assignement id
        return assignId;
    }


}

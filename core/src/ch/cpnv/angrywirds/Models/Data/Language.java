package ch.cpnv.angrywirds.Models.Data;


public class Language {

    String langue;
    int id;

    public Language(int id, String langue){
        this.id = id;
        this.langue = langue;
    }

    public String getLangue()
    {
        return langue;
    }

}

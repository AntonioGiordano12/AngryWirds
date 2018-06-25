package ch.cpnv.angrywirds.Models.Data;

import com.badlogic.gdx.utils.Json;

public class PostAssignmentsDatas {

    public int id;
    public String token;
    public int score;

    public PostAssignmentsDatas(int id, String token, int score){
        this.id = id;
        this.token = token;
        this.score = score;
    }

    public String BuildJson(){ // Build le json comme voulu (pour répondre à l'api)
        String json = "{\"id\":\""+ id+"\",\"token\":\""+ token+"\",\"result\":\""+ score+"\"}";

        return json;
    }
}
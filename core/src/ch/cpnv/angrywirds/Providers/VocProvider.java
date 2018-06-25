package ch.cpnv.angrywirds.Providers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

import ch.cpnv.angrywirds.Activities.Touch;
import ch.cpnv.angrywirds.Models.Data.Language;
import ch.cpnv.angrywirds.Models.Data.PostAssignmentsDatas;
import ch.cpnv.angrywirds.Models.Data.ToDoVocs;
import ch.cpnv.angrywirds.Models.Data.Vocabulary;
import ch.cpnv.angrywirds.Models.Data.Word;
import ch.cpnv.angrywirds.Models.Stage.ScoreBoard;

public class VocProvider {

    private static final String API = "http://voxerver.mycpnv.ch/api/v1/";
    private static final String USER_TOKEN = "*043121600ABFEC3BA5D841C275EF591F71B9AA05";
    public enum Status { unknown, ready, cancelled, nocnx, loaded }
    public static Status status = Status.unknown;
    public static String finalStatus;

    public static ArrayList<Language> languages;
    public static ArrayList<Vocabulary> vocabulary;
    public static ArrayList<Word> vocs;
    public static ArrayList<ToDoVocs> todo;

    static public void preload(){ // On récupère les devoirs qu'on doit faire avant de charger les données
        Net.HttpRequest requestLangues = new Net.HttpRequest(Net.HttpMethods.GET);
        requestLangues.setUrl(API+"assignments/"+USER_TOKEN);

        Gdx.net.sendHttpRequest(requestLangues, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());
                for (JsonValue languageJson : json.iterator())
                {
                    String result;

                    if(languageJson.get("result").isNull()) // Si aucun resultats étaient présent
                    {
                        result = "null";
                    }
                    else
                    {
                        result = languageJson.getString("result"); // Autrement on affiche le précédent résultat
                    }
                    todo.add(new ToDoVocs(languageJson.getInt("assignment_id"), languageJson.getInt("vocabulary_id"), languageJson.getString("title"), result )); // Ajoute a la liste des devoirs
                }
            }

            @Override
            public void failed(Throwable t) {
                status = status.nocnx;
                Gdx.app.log("Failed", t.toString());

            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("Cancelled", "Cancelled");
            }
        });
    }

    static public void load(){
        preload();
        languages = new ArrayList<Language>();
        vocabulary = new ArrayList<Vocabulary>();
        todo = new ArrayList<ToDoVocs>();

        Net.HttpRequest requestLangues = new Net.HttpRequest(Net.HttpMethods.GET);
        requestLangues.setUrl(API+"languages");
        Gdx.net.sendHttpRequest(requestLangues, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());
                for (JsonValue languageJson : json.iterator())
                {
                    languages.add(new Language(languageJson.getInt("lId"),languageJson.getString("lName")));
                }
                //status = Status.loaded;
            }

            @Override
            public void failed(Throwable t) {
                status = status.nocnx;
                Gdx.app.log("Failed", t.toString());
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("Cancelled", "Cancelled");
            }
        });

        Net.HttpRequest requestVoc = new Net.HttpRequest(Net.HttpMethods.GET);
        requestVoc.setUrl(API+"fullvocs");
        Gdx.net.sendHttpRequest(requestVoc, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());

                for (JsonValue voc : json.iterator())
                {
                    for (ToDoVocs td : todo)
                    {
                        if(td.getVocId() == voc.getInt("mId")){
                        Vocabulary newvoc = new Vocabulary(voc.getInt("mId"),voc.getString("mTitle"),voc.getInt("mLang1"),voc.getInt("mLang2"));
                            for (JsonValue word : voc.get("Words").iterator())
                            {

                                newvoc.addWord(new Word(word.getInt("mId"), word.getString("mValue1"), word.getString("mValue2")));

                            }
                            vocabulary.add(newvoc);
                        }
                    }

                }
                status = Status.loaded;
            }

            @Override
            public void failed(Throwable t) {
                status = status.nocnx;
                Gdx.app.log("Failed", t.toString());
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("Cancelled", "Cancelled");
            }
        });
    }

    static public void submitResults (int assignement) {
        Gdx.app.log("AJAXPOST", "Appel ajax demandé");
        HttpRequestBuilder requestSubmitResults = new HttpRequestBuilder();
        PostAssignmentsDatas datas = new PostAssignmentsDatas(assignement, USER_TOKEN, ScoreBoard.score);
        String json = datas.BuildJson(); // Construire le JSON afin de pouvoir l'envoyer
        Gdx.app.log("JSON", json);
        Net.HttpRequest httpRequestSubmitResults = requestSubmitResults.newRequest()
                .method(Net.HttpMethods.POST)
                .header("Content-Type","application/json")
                .header("X-Requested-With","XmlHttpRequest")
                .content(json)
                .url(API+"result")
                .build();
        Gdx.app.log("AJAXPOST", httpRequestSubmitResults.getContent());

        Gdx.net.sendHttpRequest(httpRequestSubmitResults, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) { // Requete OK, resultats sauvegardés
                Gdx.app.log("AJAXPOST", "Soumission des résultats");
                Gdx.app.log("AJAXPOST", httpResponse.getStatus().getStatusCode() + " = status ");
                finalStatus = "Sauvegardé !";
            }

            @Override
            public void failed(Throwable t) {
                status = Status.nocnx;
                finalStatus = "Resultats non sauvegardé !";
                Gdx.app.log("AJAXPOST", "No connection", t);
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                finalStatus = "Cancelled";
                Gdx.app.log("AJAXPOST", "cancelled");
            }
        });
    }

}

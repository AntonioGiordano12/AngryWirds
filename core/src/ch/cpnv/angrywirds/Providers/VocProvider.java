package ch.cpnv.angrywirds.Providers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

import ch.cpnv.angrywirds.Models.Data.Language;
import ch.cpnv.angrywirds.Models.Data.PostAssignmentsDatas;
import ch.cpnv.angrywirds.Models.Data.Vocabulary;
import ch.cpnv.angrywirds.Models.Data.Word;

public class VocProvider {

    private static final String API = "http://voxerver.mycpnv.ch/api/v1/";
    public enum Status { unknown, ready, cancelled, nocnx, loaded }
    public static Status status = Status.unknown;
    private String statut;

    public static ArrayList<Language> languages;
    public static ArrayList<Vocabulary> vocabulary;
    public static ArrayList<Word> vocs;


    static public void load(){
        languages = new ArrayList<Language>();
        vocabulary = new ArrayList<Vocabulary>();

        Net.HttpRequest requestLangues = new Net.HttpRequest(Net.HttpMethods.GET);
        requestLangues.setUrl(API+"languages");
        Gdx.net.sendHttpRequest(requestLangues, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("get langue", httpResponse.getResultAsString());
                JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());
                for (JsonValue languageJson : json.iterator())
                {
                    languages.add(new Language(languageJson.getInt("lId"),languageJson.getString("lName")));
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

        Net.HttpRequest requestVoc = new Net.HttpRequest(Net.HttpMethods.GET);
        requestVoc.setUrl(API+"fullvocs");
        Gdx.net.sendHttpRequest(requestVoc, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());

                for (JsonValue voc : json.iterator())
                {
                    Vocabulary newvoc = new Vocabulary(voc.getInt("mId"),voc.getString("mTitle"),voc.getInt("mLang1"),voc.getInt("mLang2"));
                    for (JsonValue word : voc.get("Words").iterator())
                    {
                        newvoc.addWord(new Word(word.getInt("mId"), word.getString("mValue1"), word.getString("mValue2")));
                    }
                    vocabulary.add(newvoc);
                }
                status = Status.ready;
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

    static public void submitResults (int assignement, String token, int score) {
        Gdx.app.log("AJAXPOST", "Appel ajax demandé");
        HttpRequestBuilder requestSubmitResults = new HttpRequestBuilder();
        PostAssignmentsDatas datas = new PostAssignmentsDatas(assignement, token, score);
        Net.HttpRequest httpRequestSubmitResults = requestSubmitResults.newRequest()
                .method(Net.HttpMethods.POST)
                .jsonContent(datas)
                .url(API+"result")
                .build();
        Gdx.app.log("AJAXPOST", httpRequestSubmitResults.getContent());
        Gdx.net.sendHttpRequest(httpRequestSubmitResults, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("AJAXPOST", "Soumission des résultats");
                Gdx.app.log("AJAXPOST", httpResponse.getStatus().getStatusCode() + " = status ");
            }

            @Override
            public void failed(Throwable t) {
                status = Status.nocnx;
                Gdx.app.log("AJAXPOST", "No connection", t);
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("AJAXPOST", "cancelled");
            }
        });
    }

    static public void SelectVoc(Language l){
        if (l.getLangue() == "Français"){
            status = Status.ready;
        }
        if (l.getLangue() == "Anglais"){

            Net.HttpRequest requestVoc = new Net.HttpRequest(Net.HttpMethods.GET);
            requestVoc.setUrl(API+"fullvocs");
            Gdx.net.sendHttpRequest(requestVoc, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    JsonValue json = new JsonReader().parse(httpResponse.getResultAsString());

                    for (JsonValue voc : json.iterator())
                    {
                        Vocabulary newvoc = new Vocabulary(voc.getInt("mId"),voc.getString("mTitle"),voc.getInt("mLang1"),voc.getInt("mLang2"));
                        for (JsonValue word : voc.get("Words").iterator())
                        {
                            newvoc.addWord(new Word(word.getInt("mId"), word.getString("mValue2"), word.getString("mValue1")));
                        }
                        vocabulary.add(newvoc);
                    }
                    status = Status.ready;
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
            status = Status.ready;
        }
    }

    static public void getVocs(int voc) {
        vocs = new ArrayList<Word>();
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("http://voxerver.mycpnv.ch/api/v1/voc/" +voc);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonReader json = new JsonReader();
                JsonValue base = json.parse(httpResponse.getResultAsString());
                JsonValue voc = base.get("mWords");
                for (JsonValue word : voc.iterator()) {
                    vocs.add(new Word(word.getInt("mId"), word.getString("mValue2"), word.getString("mValue1")));
                }
                status = Status.ready;
            }

            @Override
            public void failed(Throwable t) {
                status = Status.nocnx;
                Gdx.app.log("ANGRY", "No connection", t);
            }

            @Override
            public void cancelled() {
                status = Status.cancelled;
                Gdx.app.log("ANGRY", "cancelled");
            }
        });
    }

}

package ch.cpnv.angrywirds.Models.Data;

import java.util.ArrayList;

import ch.cpnv.angrywirds.AngryWirds;

public class Vocabulary {
    int id;
    String vocName;
    int langprof;
    int langeleve;
    ArrayList<Word> words;

    public Vocabulary(int id, String vocName, int langprof, int langeleve) {
        this.id = id;
        this.vocName = vocName;
        this.langprof = langprof;
        this.langeleve = langeleve;
        this.words = new ArrayList<Word>();
    }

    public Word pickAWord() {
        return words.get(AngryWirds.alea.nextInt(words.size()));
   }

    public void addWord(Word w) {
        words.add(w);
    }

}

package com.company;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //finding stopwords and bag of words
        String filePath = "C:\\Users\\Apple\\IdeaProjects\\NLPLabSecondProject\\inputFile.txt";
        Spliter spliter = new Spliter();
        StopWords stopWords = new StopWords();
        BagOfWords bagOfWords = new BagOfWords();
        Corpus.FileCleaner();
        List<String> allSentences = new ArrayList<>();
        try{
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                line = line.trim();
                String lines[] = spliter.lineSplitter(line);
                for (String singleLine : lines) {
                    allSentences.add(singleLine);
                    if (!singleLine.isEmpty() && singleLine != "") {
                        stopWords.stopWordsFinder(singleLine);
                        String singleWords [] = spliter.sentenceSplitter(singleLine);
                        bagOfWords.allWordsListCreator(singleWords);
                    }
                }
            }
            bagOfWords.wordCounter(allSentences);

        }catch(Exception e) {
            e.printStackTrace();
        }


        //Wordnet: Results in a network of meaningfully related words and concepts
        //To see the related words and concepts for each input, you should go through breakpoints.
        try {
            JWNL.initialize(ClassLoader.getSystemResourceAsStream("properties.xml"));
            final net.didion.jwnl.dictionary.Dictionary dictionary = net.didion.jwnl.dictionary.Dictionary.getInstance();
            //POS pos = POS.ADJECTIVE;
            String lemma = "bad";
            String lemma1 = "water";
            final IndexWord indexWord = dictionary.lookupIndexWord(POS.ADJECTIVE, lemma);
            final Synset[] senses = indexWord.getSenses();
            final IndexWord indexWord1 = dictionary.lookupIndexWord(POS.NOUN, lemma1);
            final Synset[] senses1 = indexWord1.getSenses();
            final IndexWord indexWord2 = dictionary.lookupIndexWord(POS.VERB, "run");
            final Synset[] senses2 = indexWord2.getSenses();
        }catch (Exception e){
            String s = e.getMessage();
        }
    }
}

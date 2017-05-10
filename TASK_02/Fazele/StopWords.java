package com.company;

/**
 * Created by Apple on 5/10/2017.
 */
public class StopWords {

    public void stopWordsFinder(String singleLine) {
        String newLine = "";
        Corpus.writeInFile(singleLine);
        Spliter spliter = new Spliter();
        String singleWords [] = spliter.sentenceSplitter(singleLine);
        for(String s : singleWords) {
            if (!Corpus.getStopWordsList().containsKey(s) && !Corpus.pSmallA.matcher(s).find() &&
                    !Corpus.pCapitalA.matcher(s).find()) {
                newLine = newLine + " " + s;
            }

        }
        newLine = newLine.trim();
        Corpus.writeInFile(newLine);
        Corpus.writeInFile("\n");
    }

}

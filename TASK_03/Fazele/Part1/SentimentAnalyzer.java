package com.company;

/**
 * Created by Apple on 5/17/2017.
 */
public class SentimentAnalyzer {
    float numberOfPositiveWords = 0;
    float numberOfNegativeWords = 0;
    float numberOfAllWords = 0;
    float positivityIndicator = 0;
    float negativityIndicator = 0;

    public void positive_negativeCounter(String singleWords[]){
        numberOfAllWords += singleWords.length;
        for (String s: singleWords){
            if (Corpus.getPositiveWords().containsKey(s)){
                numberOfPositiveWords +=1;
            }
            else if(Corpus.getNegativeWords().containsKey(s)){
                numberOfNegativeWords +=1;
            }
        }
    }

    public float positive_negativeIndicator(){
        positivityIndicator = numberOfPositiveWords / numberOfAllWords;
        negativityIndicator = numberOfNegativeWords / numberOfAllWords;
        if (positivityIndicator > negativityIndicator)
            return positivityIndicator;
        else
            return negativityIndicator;
    }


}

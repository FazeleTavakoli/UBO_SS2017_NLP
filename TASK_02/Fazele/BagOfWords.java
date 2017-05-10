package com.company;

import java.util.*;




public class BagOfWords {
    private List<String> allWords = new ArrayList<>();


    public Map<String,Map<String,Integer>> wordCounter(List<String> allSentences){
        Map<String,Map<String,Integer>> BOWHistogram = new HashMap<>();
        Spliter spliter = new Spliter();
        //String singleWord[] =spliter.sentenceSplitter(sentence);
        for (int i=0; i<allSentences.size(); i++){
            Map<String,Integer> numberOfWords_singleSentence_Map = new HashMap<>();
            String words [] = spliter.sentenceSplitter(allSentences.get(i));
            for (String s1: allWords) {
                if(Arrays.asList(words).contains(s1)) {
                    while(Arrays.asList(words).contains(s1)) {
                        if (numberOfWords_singleSentence_Map.containsKey(s1)) {
                            int newNumber = numberOfWords_singleSentence_Map.get(s1) + 1;
                            numberOfWords_singleSentence_Map.replace(s1, newNumber);
                        } else {
                            numberOfWords_singleSentence_Map.put(s1, 1);
                        }
                        int index = Arrays.asList(words).indexOf(s1);
                        words[index] = " ";

                    }
                }
                else {
                    numberOfWords_singleSentence_Map.put(s1,0);
                }
            }
            String BOWKey = "Sentence" + " " + i;
            BOWHistogram.put(BOWKey, numberOfWords_singleSentence_Map);
        }
        //Corpus.writeInFile(BOWHistogram.toString());
        Corpus.writeHashMap(BOWHistogram);
        return BOWHistogram;
    }

    public void allWordsListCreator(String singleWords[]){
        for (String s: singleWords){
            if (!allWords.contains(s)){
                allWords.add(s);
            }
        }
    }

}

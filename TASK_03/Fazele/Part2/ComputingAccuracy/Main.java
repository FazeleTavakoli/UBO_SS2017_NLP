package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // producing finalResult.txt
        /*Corpus.FileCleaner();
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/apple/Fafa Java Projects/AccuracyFinderForSentimentalAnalyzer/primaryResult.txt");
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                line = line.trim();
                if (line.contains("sentence 0 overall sentiment rating is")){
                    Corpus.writeInFile(line);
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //checking the accuracy
        Spliter spliter = new Spliter();
        List<String> producedSentiments = new ArrayList<>();
        List<String> originalSentiments = new ArrayList<>();
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/apple/Fafa Java Projects/AccuracyFinderForSentimentalAnalyzer/finalResult.txt");
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                line = line.trim();
                String[] singleWords = spliter.sentenceSplitter(line);
                producedSentiments.add(singleWords[singleWords.length - 1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/apple/Fafa Java Projects/AccuracyFinderForSentimentalAnalyzer/storage.txt");
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                line = line.trim();
                String [] singleWords = spliter.sentenceSplitter(line);
                originalSentiments.add(singleWords[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        double matched = 0;
        double accuracy = 0;
        for (int i=0; i <originalSentiments.size(); i++){
            String pSentiment = producedSentiments.get(i);
            String oSentiment = originalSentiments.get(i);
            if (pSentiment.equals("Negative")) {
                if (oSentiment.equals("0"))
                    matched++;
            }
            else if (pSentiment.equals("Positive")) {
                if (oSentiment.equals("1"))
                    matched++;
            }
            else if (pSentiment.equals("Neutral")) {
                matched++;
            }
        }
        accuracy = matched / originalSentiments.size();
        System.out.println("Accuracy = " + accuracy );
    }
}

package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        //String filePath0 = "C:\\Users\\Apple\\IdeaProjects\\NLPLabThirdProject\\trainingData.txt";
        //String filePath1 = "C:\\Users\\Apple\\IdeaProjects\\NLPLabThirdProject\\testData.txt";
        float finalResult [] = new float[2];
        for (int i=0; i<2; i++) {
            Spliter spliter = new Spliter();
            SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
            Corpus.FileCleaner();
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("C:\\Users\\Apple\\IdeaProjects\\NLPLabThirdProject\\inputFile"+i+".txt");
                br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("[\uFEFF]", "");
                    line = line.trim();
                    String lines[] = spliter.lineSplitter(line);
                    for (String singleLine : lines) {
                        if (!singleLine.isEmpty() && singleLine != "") {
                            String singleWords[] = spliter.sentenceSplitter(singleLine);
                            sentimentAnalyzer.positive_negativeCounter(singleWords);

                        }
                    }
                }
                finalResult[i] = sentimentAnalyzer.positive_negativeIndicator();
                System.out.println("final result for the file "+i+"is: "+  finalResult[i]);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        float accuracy = finalResult[0]/finalResult[1];
        System.out.println("accuracy is: "+ accuracy);

    }
}

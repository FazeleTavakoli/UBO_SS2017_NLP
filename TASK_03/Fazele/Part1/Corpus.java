package com.company;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;


public class Corpus  {

    private static File file = new File("C:\\Users\\Apple\\IdeaProjects\\NLPLabSecondProject\\StopWords&BOWOutput.txt");
    public static Map<String,String> stopWordsList = new HashMap<>();
    public static Map<String,String> positiveWordsList  = new HashMap<String,String>();
    public static Map<String,String> negativeWordsList  = new HashMap<String,String>();
    private static final String REGEXSMALLA = "^a$";
    public static final Pattern pSmallA = Pattern.compile(REGEXSMALLA);
    private static final String REGEXCAPITALA = "^A$";
    public static final Pattern pCapitalA = Pattern.compile(REGEXCAPITALA);


    public static void writeInFile(String fileInput) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(fileInput);
            bw.write("\r\n");
            System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }

        }
    }

    public static void writeHashMap(Map<String, Map<String,Integer>> termFreqHash) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            for(String s: termFreqHash.keySet()) {
                bw.write(s);
                bw.write("\r\n");
                bw.write(termFreqHash.get(s).toString());
                bw.write("\r\n");
            }
            System.out.println("File written Successfully");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter" + ex);
            }

        }
    }

    public static Map getStopWordsList(){
        if (stopWordsList.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("C:\\Users\\Apple\\IdeaProjects\\NLPLabSecondProject\\stopwordsList.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    stopWordsList.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return stopWordsList;

    }

    public static Map getPositiveWords(){
        if (positiveWordsList.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("C:\\Users\\Apple\\IdeaProjects\\NLPLabThirdProject\\positiveWords.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("[\uFEFF]", "");
                    positiveWordsList.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return positiveWordsList;

    }

    public static Map getNegativeWords(){
        if (negativeWordsList.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("C:\\Users\\Apple\\IdeaProjects\\NLPLabThirdProject\\negativeWords.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("[\uFEFF]", "");
                    negativeWordsList.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return negativeWordsList;

    }


    public static void FileCleaner(){
        // empty the current content
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


}


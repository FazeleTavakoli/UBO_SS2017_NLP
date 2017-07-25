package com.company;

import com.company.Corpus;
import com.company.Graph;
import com.company.SingletoneKCore;
import com.company.Spliter;

import java.io.*;
import java.util.*;

public class KeyWordExtractorMain {
    public static void main(String[] args) {
        String filePath = "/Users/fazeletavakoli/Desktop/KeywordInput_NLPLab/art_and_culture-20893614.txt";
        int windowSize = 4;
        Spliter spliter = new Spliter();
        //List<String> stemmedTermList = new ArrayList<>();
        ArrayList finalTermType = new ArrayList();
        String stemmedTerm = "";
        Graph graph = new Graph();
        String splittedArray[];
        List<Map<String, Map<String, Integer>>> kcoreNodesList = new ArrayList<>();
        List<List<Integer>> densityList = new ArrayList<>();
        //List<Integer> kcoreNodesSize = new ArrayList<>();
        boolean isTrue1 = false; //for checking if program enters while loop for second or ... time
        boolean isTrue2 = false; //for storing just KCores of the main graph
        List<Double> kCoreDensity = new ArrayList<>();
        //Map<String,Map<String,Integer>> weightedGraph = new HashMap<>();
        //Graph graph_temp;

        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\uFEFF]", "");
                //line = line.replaceAll("[&nbsp;]+", "");
                line = line.trim();
                splittedArray = spliter.lineSplitter(line);
                for (int i = 0; i < splittedArray.length; i++) {
                    
                    splittedArray[i] = splittedArray[i].trim();
                    String sentence = splittedArray[i];
                    if (!Corpus.pSingleSpace.matcher(sentence).find() && !Corpus.pEmpty.matcher(sentence).find()) {
                        //graph.setStemmedTermList(sentence);
                        graph.setStemmedTermList_English(sentence);
                    
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        SingletoneKCore singletoneKCore = new SingletoneKCore();
        Map<String, Map<String, Integer>> kcoreNodesMap = new HashMap<>();
        List<String> visited = new ArrayList<>();

        interviews.graphs.Graph InterviewsGraph = new interviews.graphs.Graph(graph.getStemmedTermList_English().size());
        interviews.graphs.KCore InterviewsKCore = new interviews.graphs.KCore(InterviewsGraph);
        graph.graphCreator();
        InterviewsGraph = graph.G1();
        InterviewsKCore = singletoneKCore.KC(graph);
        //weightedGraph = new HashMap<>(graph.getWeightedAdjGraph());
        List<String> stemmedTermList = new ArrayList<>(graph.getStemmedTermList_English());
        /*graph_temp = new Graph(graph).InternalGraph();
        try {
            graph_temp = (Graph)graph.clone();
        }catch (Exception e){
            e.printStackTrace();
        }*/


        List<String> stemmedTermList_temp = new ArrayList<>(stemmedTermList);
        //kcoreNodesList = Arrays.asList(new Map[stemmedTermList.size()]);   // fixed-size list
        for (String s : stemmedTermList) {
            if (!s.equals("NA")) {
                kcoreNodesMap = new HashMap<>();
                visited = new ArrayList<>();
                //singletoneKCore = new SingletoneKCore();
                kcoreNodesList.add(graph.KCoreNodesExtractor(singletoneKCore, s, s, kcoreNodesMap, visited));
                //kcoreNodesList.set(stemmedTermList.indexOf(s),graph.KCoreNodesExtractor(s,s, kcoreNodesMap, visited));

            }
        }


        //SingletoneKCore.KC(graph);

        List<Map<String, Map<String, Integer>>> kcoreNodesList1;
        List<Map<String, Map<String, Integer>>> kcoreNodesList2; //contains all kcores that are obtained in a specific step of for loop
        List<Map<String, Map<String, Integer>>> kcoreNodesList_firstTransition = new ArrayList<>();
        LinkedHashMap<Map<String, Map<String, Integer>>, Integer> sortedkcoreNodes_coreNumber_firstTransition = new LinkedHashMap<>();
        Map<String,Map<String,Integer>> allKCoreNodesNLevel; //contains all kcores with a specific (highest) core-number
        while (!graph.getWeightedAdjGraph().isEmpty() || graph.getWeightedAdjGraph().size() != 0) {
            if (isTrue1) {
                kcoreNodesList = new ArrayList<>();
                stemmedTermList = new ArrayList<>(graph.getStemmedTermList_English());
                for (int i = 0; i < stemmedTermList.size(); i++) {
                    //if (!s.equals("NA")) {
                    if (!stemmedTermList.get(i).equals("NA")) {
                        //singletoneKCore = new SingletoneKCore();
                        kcoreNodesMap = new HashMap<>();
                        visited = new ArrayList<>();
                        if (stemmedTermList.get(i).equals("NA")) {
                            int a = 0;
                        }
                        //singletoneKCore.setKC(graph);
                        kcoreNodesList.add(graph.KCoreNodesExtractor(singletoneKCore, stemmedTermList.get(i), stemmedTermList.get(i), kcoreNodesMap, visited));
                    }
                }
            }
            //do {
            Map<Map<String, Map<String, Integer>>, Integer> kcoreNodes_coreNumber = new HashMap<>();
            int i1 = 0;
            for (Map<String, Map<String, Integer>> m : kcoreNodesList) {

                try {
                    while (singletoneKCore.KC(graph).core().length > i1 && singletoneKCore.KC(graph).core(i1) == 0) {
                        i1++;
                    }
                    if(singletoneKCore.KC(graph).core().length > i1)
                        kcoreNodes_coreNumber.put(m, singletoneKCore.KC(graph).core(i1));

                    i1++;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print(m);
                }
            }

            LinkedHashMap<Map<String, Map<String, Integer>>, Integer> sortedkcoreNodes_coreNumber = new LinkedHashMap<>(graph.sortByValues(kcoreNodes_coreNumber));
            kcoreNodesList1 = new ArrayList<>();
            kcoreNodesList2 = new ArrayList<>();


            for (Map<String, Map<String, Integer>> m : sortedkcoreNodes_coreNumber.keySet()) {
                //if (!kcoreNodesList1.contains(m)) {
                //kcoreNodesList1.add(m);

                kcoreNodesList2.add(m);
                //}
            }

            if(!isTrue2) {
                //kcoreNodesList_firstTransition = new ArrayList<>(kcoreNodesList2);
                sortedkcoreNodes_coreNumber_firstTransition = new LinkedHashMap<>(sortedkcoreNodes_coreNumber);
                isTrue2 = true;
            }
            //computing density of n-leveles of k-cores
            kcoreNodesList2.size();
            int maxCoreNumber = sortedkcoreNodes_coreNumber.get(kcoreNodesList2.get(0));

            //try {
            allKCoreNodesNLevel = new HashMap<>();
            while (sortedkcoreNodes_coreNumber.get(kcoreNodesList2.get(0)) == maxCoreNumber) {

                allKCoreNodesNLevel.putAll(kcoreNodesList2.get(0));
                //kCoreDensity.add(graph.getDensity(kcoreNodesList2.get(0)));
                graph.nodesRemover(kcoreNodesList2.get(0));
                kcoreNodesList2.remove(kcoreNodesList2.get(0));
                if (kcoreNodesList2.isEmpty()) {
                    break;
                }
            }
            kCoreDensity.add(graph.getDensity(allKCoreNodesNLevel));
            isTrue1 = true;
            /*}catch (Exception e){
                graph.getWeightedAdjGraph();
                int m = 1;
            }*/
            //} while (!kcoreNodesList1.isEmpty());
        }
        LinkedHashMap<Integer,Double> level_KcoreDensity = new LinkedHashMap<>();
        for (int i = 0; i< kCoreDensity.size(); i++){
            level_KcoreDensity.put(i+1,kCoreDensity.get(i));
        }
        System.out.println(kCoreDensity);
        int elbow = graph.computeElbow(level_KcoreDensity);
        System.out.println("The elbow is:"+ elbow);

        //obtaining the KBest-core of main graph as keywords
        List<String> keywordsList = new ArrayList<>();
        int level = 0; //level number in the main graph
        double matchedKeywords = 0; //number of Matched Keywords
        double totalKeywords = 0; //number of total keywords
        List <String> removedkeyWords = new ArrayList<>(); //these keywords should be removed form the corresponding hashmap
        for (Map<String, Map<String, Integer>> map : sortedkcoreNodes_coreNumber_firstTransition.keySet()) {
            //newly added
            if(level == elbow) {     /// change into level = elbow
                totalKeywords = map.size();
                for (String s : map.keySet()) {
                    keywordsList.add(s);
                    System.out.println(s);

                }
                //checking accuracy
                String filePath_test = "/Users/fazeletavakoli/Desktop/KeywordInput_NLPLab/art_and_culture-20893614_test.txt";
                try {
                    String line = "";
                    BufferedReader br = null;
                    InputStream inputStream = new FileInputStream(filePath_test);
                    br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    while ((line = br.readLine()) != null) {
                        for (String s : map.keySet()){
                            if (line.contains(s)){
                                matchedKeywords++;
                                removedkeyWords.add(s);
                            }
                        }
                        for (String s: removedkeyWords){
                            map.remove(s);
                        }
                    }
                }catch (Exception e){

                }

                double precision = matchedKeywords/totalKeywords;
                System.out.println("Precision is:" + precision);

                break;
            }
            else{
                level++ ;
            }
            /*if (sortedkcoreNodes_coreNumber_firstTransition.get(map) == elbow) {
                for (String s : map.keySet()) {
                    keywordsList.add(s);
                    System.out.println(s);
                }
            }
            else {

            }*/




        }

    }
}

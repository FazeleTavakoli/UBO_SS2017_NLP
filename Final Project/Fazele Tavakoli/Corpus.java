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

    private static final String REGEXY = ".*ی$";
    public static final Pattern pY = Pattern.compile(REGEXY);
    private static final String REGEXGAN = ".*گان$";
    public static final Pattern pGan = Pattern.compile(REGEXGAN);
    private static final String REGEXAN = ".*ان$";
    public static final Pattern pAn = Pattern.compile(REGEXAN);
    private static final String REGEXAT = ".*ات$";
    public static final Pattern pAt = Pattern.compile(REGEXAT);
    private static final String REGEXYN = ".*ین$";
    public static final Pattern pYn = Pattern.compile(REGEXYN);
    private static final String REGEXOUN = ".*ون$";
    public static final Pattern pOun = Pattern.compile(REGEXOUN);
    private static final String REGEXHA = ".*ها$";
    public static final Pattern pHa = Pattern.compile(REGEXHA);
    private static final String REGEXENDHALFS = ".*\\u200c$";
    public static final Pattern pEndHalfS = Pattern.compile(REGEXENDHALFS);
    private static final String REGEXK = ".*ک$";
    public static final Pattern pK = Pattern.compile(REGEXK);
    private static final String REGEXSHENASE_M = ".*م$";
    public static final Pattern pShenaseM = Pattern.compile(REGEXSHENASE_M);
    private static final String REGEXSHENASE_T = ".*ت$";
    public static final Pattern pShenaseT = Pattern.compile(REGEXSHENASE_T);
    private static final String REGEXSHENASE_SH = ".*ش$";
    public static final Pattern pShenaseSh = Pattern.compile(REGEXSHENASE_SH);
    private static final String REGEXSHENASE_MAN = ".*مان$";
    public static final Pattern pShenaseMan = Pattern.compile(REGEXSHENASE_MAN);
    private static final String REGEXSHENASE_TAN = ".*تان$";
    public static final Pattern pShenaseTan = Pattern.compile(REGEXSHENASE_TAN);
    private static final String REGEXSHENASE_SHAN = ".*شان$";
    public static final Pattern pShenaseShan = Pattern.compile(REGEXSHENASE_SHAN);
    private static final String REGEXTARIN = ".*ترین$";
    public static final Pattern pTarin = Pattern.compile(REGEXTARIN);
    private static final String REGEXTAR = ".*تر$";
    public static final Pattern pTar = Pattern.compile(REGEXTAR);
    private static final String REGEXSEVERALHALFS = "\\u200c\\u200c+";
    public static final Pattern pSeveralHalfS = Pattern.compile(REGEXSEVERALHALFS);
    private static final String REGEXSINGLEHALFS = "\u200c";
    public static final Pattern pSingleHalfS = Pattern.compile(REGEXSINGLEHALFS);
    private static final String REGEXSEVERALNULL = "\\u0000\\u0000+";
    public static final Pattern pSeveralNull = Pattern.compile(REGEXSEVERALNULL);
    private static final String REGEXUJUST200B = "\u200b+";
    public static final Pattern pJustU200b = Pattern.compile(REGEXUJUST200B);
    private static final String REGEXSeVerALSPACE = "\\s+";
    public static final Pattern pSeveralSpace = Pattern.compile(REGEXSeVerALSPACE);
    private static final String REGEXJUSTU200C = "\u200c+";
    public static final Pattern pJustU200c = Pattern.compile(REGEXJUSTU200C);
    private static final String REGEXJUSTU200F = "\u200F+";
    public static final Pattern pJustU200f = Pattern.compile(REGEXJUSTU200F);
    private static final String REGEXSINGLESPACE = "^\\s$";
    public static final Pattern pSingleSpace = Pattern.compile(REGEXSINGLESPACE);
    private static final String REGEXEMPTY = "^$";
    public static final Pattern pEmpty = Pattern.compile(REGEXEMPTY);
    private static final String REGEXUFFFD = "\ufffd+";
    public static final Pattern pUfffd = Pattern.compile(REGEXUFFFD);
    private static final String REGEXJUSTU200A = "\u200a";
    public static final Pattern pU200a = Pattern.compile(REGEXJUSTU200A);



    public static Map<String,String> pluralAt  = new HashMap<String,String>();
    public static Map<String,String> pluralAn = new HashMap<String,String>();
    public static Map<String,String> pluralGan = new HashMap<String,String>();
    public static Map<String,String> pluralUnIn = new HashMap<String,String>();
    public static Map<String,String> pureNoun = new HashMap<String,String>();
    public static Map<String,String> nounPrepSharingTar = new HashMap<String,String>();
    public static Map<String,String> pastV = new HashMap<String,String>();
    public static Map<String,String> presentV = new HashMap<String,String>();
    public static Map<String,String> preposition = new HashMap<String,String>();
    public static Map<String,String> unknown = new HashMap<String,String>();
    public static Map<String,String> halfSpace = new HashMap<String,String>();
    public static Map<String,String> halfSpaceVerbs = new HashMap<String,String>();
    public static Map<String,String> compoundVerb = new HashMap<String,String>();
    public static Map<String,String> noun_prepositionIntersect = new HashMap<>();
    public static Map<String,String> prep_nounIntersec = new HashMap<>();
    public static Map <String,String> modifedPluralAn = new HashMap<String,String>();
    //for english keyword extractor version
    public static Map<String,String> keywordsList_original = new HashMap<>(); //contains original(human assigned) keywords to measure accuracy

    private static File file = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/txtOutput.txt");
    private static File file1 = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/Noun11.txt");
    private static File file2 = new File("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Input/serajiReplacedOutput.txt");
    private List<Integer> repeatedPhrasesIndexes = new ArrayList<Integer>(); //contains indexes of repeated phrases
    private String finalResult = ""; //static is removed
    private int printedCounter = 0; //newly added
    //private static List<String> removedNouns = new ArrayList<String>();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static ReentrantLock reentrantLock2 = new ReentrantLock();

    public static Map getPureNoun(){
        if (pureNoun.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun11.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    pureNoun.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pureNoun;

    }

    public static Map getMapAt (){
        String line1 = "";
        String line2 = "";
        if (pluralAt.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/AtPNouns.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    if (line.contains("$")) {
                        int index = line.indexOf('$');
                        if (line != null) {
                            line1 = line.substring(0, index);
                            line2 = line.substring(index + 1);
                            //writeInFile(line,file1);
                        }
                    }
                    pluralAt.put(line1, line2);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}

        }

        return pluralAt;
    }

    public static Map getMapAn (){
        if (pluralAn.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pNounAn.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralAn.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralAn;
    }

    public static Map getMapGan (){
        if (pluralGan.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pNounGan.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralGan.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralGan;
    }

    public static Map getMapUnIn (){
        if (pluralUnIn.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/unInPNouns.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pluralUnIn.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return pluralUnIn;
    }

    public static Map getMapNounPrepSharingTar(){
        if (nounPrepSharingTar.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/NounPrepSharingTar.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    nounPrepSharingTar.put(line, line);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return nounPrepSharingTar;
    }




    public static Map getMapPastV(){
        if (pastV.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/pastV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    pastV.put(line, line);
                inputStream.close();
            }catch (IOException e ){
                e.printStackTrace();
            }
        }
        return pastV;
    }

    public static Map getMapPresentV(){
        if(presentV.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/presentV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    presentV.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return presentV;
    }

    public static Map getMapPreposition(){
        if(preposition.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/prepV.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    preposition.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return preposition;
    }

    public static Map getMapUnknown(){
        if(unknown.isEmpty()){
            try{
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/unknownWords.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    unknown.put(line,line);
                inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return unknown;
    }

    public static Map getMapHalfSpace(){
        if (halfSpace.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun11.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null)
                    if (line.contains("\u200c")){
                        halfSpace.put(line, line);
                    }
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return halfSpace;
    }



    public static Map getMapHalfSpaceVerbs(){
        if (halfSpaceVerbs.isEmpty()){
            try {
                String singleWord = "";
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/infinitive.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    String splittedLine[] = line.split("@");
                    singleWord = splittedLine[4];
                    singleWord = singleWord.trim();
                    if (singleWord.contains(" ")) {
                        singleWord = singleWord.replaceAll(" ", "\u200c");
                        halfSpaceVerbs.put(singleWord, singleWord);
                    }
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return halfSpaceVerbs;
    }

    public static Map getMapCompoundVerb (){
        if (compoundVerb.isEmpty()){
            try {
                String pastRoot = "";
                String presentRoot = "";
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/infinitive.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    String splittedLine[] = line.split("@");
                    presentRoot = splittedLine[6];
                    pastRoot = splittedLine[5];
                    presentRoot = presentRoot.trim();
                    pastRoot = pastRoot.trim();
                    if (presentRoot.contains(" "))
                        presentRoot = presentRoot.replaceAll(" ","\u200c");
                    if (pastRoot.contains(" "))
                        pastRoot = pastRoot.replaceAll(" ","\u200c");
                    compoundVerb.put(pastRoot,pastRoot);
                    compoundVerb.put(presentRoot,presentRoot);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return compoundVerb;
    }



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



    public  void writeInString (String newWord){
        finalResult = finalResult + " " + newWord;

    }

    public  String getFinalString (){
        return finalResult;
    }

    public  void removeWhiteSpace(){
        finalResult = finalResult.replaceAll("( )+"," ");
        finalResult = finalResult.trim();
    }

    public static void writeOtherNounFile(){
        try {
            String line = "";
            BufferedReader br = null;
            InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun1.txt");
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '\u200c')
                    line = line.substring(1,line.length());
                else if (line.charAt(line.length()-1) == '\u200c')
                    line = line.substring(0,line.length()-1);
                BufferedWriter bw = null;
                try {
                    FileWriter fw = new FileWriter(file1, true);
                    bw = new BufferedWriter(fw);
                    bw.write(line);
                    bw.write("\r\n");

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
        } catch (IOException e) {
            e.printStackTrace();}
    }




    

    public static Map getNoun_PrepIntersect (){
        if (noun_prepositionIntersect.isEmpty())
            noun_prepositionIntersect = new HashMap<>(getIntersect(getPureNoun(),getMapPreposition()));
        return noun_prepositionIntersect;
    }

    public static Map getPrep_NounIntersect (){
        if (prep_nounIntersec.isEmpty())
            prep_nounIntersec = new HashMap<>(getIntersect(getMapPreposition() , getPureNoun()));
        return prep_nounIntersec;
    }




    public void setPhrasesIndexes(int i){
        repeatedPhrasesIndexes.add(i);
    }

    public List getPhrasesIndexes(){
        return repeatedPhrasesIndexes;
    }

    public static Map<String,String> getModifiedMapAn() {

        if (modifedPluralAn.isEmpty()) {
            modifedPluralAn = new HashMap<String,String>(getMapAn());
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/Irsa/mainInput/Noun&PrepIntersect.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    modifedPluralAn.remove(line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return modifedPluralAn;
    }


    public static void setHash_Null(){
        pureNoun = null;
        pluralAn = null;
        pluralAt = null;
        pluralGan = null;
        pluralUnIn = null;
        pastV = null;
        presentV = null;
        preposition = null;
        unknown = null;
        halfSpace = null;
        halfSpaceVerbs = null;
        compoundVerb = null;
        noun_prepositionIntersect = null;
        prep_nounIntersec = null;

    }


    public static Map getOriginalKeywords(){
        if (keywordsList_original.isEmpty()) {
            try {
                String line = "";
                BufferedReader br = null;
                InputStream inputStream = new FileInputStream("/Users/fazeletavakoli/Desktop/KeywordInput_NLPLab/art_and_culture-20893614_test.txt");
                br = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = br.readLine()) != null) {
                    keywordsList_original.put(line, line);
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();}
        }
        return keywordsList_original;

    }

}


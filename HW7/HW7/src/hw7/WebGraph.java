package hw7;

import java.io.*;
import java.util.*;

public class WebGraph {
    public static final int MAX_PAGES = 40;
    private ArrayList<WebPage> pages;
    private ArrayList<ArrayList<Integer>> edges = new ArrayList<>(MAX_PAGES);

    public WebGraph() {

    }

    public WebGraph(File pagesFile, File linksFile){

    }

    public void setPages(ArrayList<WebPage> pages) {
        this.pages = pages;
    }

    public void setEdges(ArrayList<ArrayList<Integer>> edges) {
        this.edges = edges;
    }

    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException{
        File pagesFileFile = new File(pagesFile);
        File linksFileFile = new File(linksFile);
        if(pagesFileFile.exists() == false || linksFileFile.exists() == false){
            throw new IllegalArgumentException();
        }else{
            WebGraph webGraph = new WebGraph();

            return webGraph;
        }
    }


    public static WebGraph[] objectInputStream (String pagesFile, String linksFile)
            throws IOException, ClassNotFoundException {
        FileInputStream pagesFileStream  = new FileInputStream(pagesFile);
        FileInputStream linksFileStream = new FileInputStream(linksFile);
        ObjectInputStream pagesInputStream = new ObjectInputStream(pagesFileStream);
        ObjectInputStream linksInputStream = new ObjectInputStream(linksFileStream);
        WebGraph webGraph = (WebGraph) pagesInputStream.readObject();
        WebGraph webGraph1 = (WebGraph) linksInputStream.readObject();
        WebGraph[] result = new WebGraph[2];
        result[0] = webGraph;
        result[1] = webGraph1;
        Scanner pagesScanner = new Scanner(pagesInputStream);
        Scanner linksScanner = new Scanner(linksInputStream);
        while (pagesScanner.nextLine()!= null){
            String pagesLine = pagesScanner.nextLine().trim();
            WebPage lineWebpage = new WebPage();
            ArrayList<Integer> indexOfSpace = WebGraph.indexOfSpaces(pagesLine);
            String lineUrl = pagesLine.substring(0,indexOfSpace.get(1));
            lineWebpage.setUrl(lineUrl);
            ArrayList<String> lineKeyWords = new ArrayList<>();
            int numOfSpace = indexOfSpace.size();
            if (numOfSpace == 1){

            }
            for (int i = 1; (i + 1)< numOfSpace; i++){
                String oneOfKeyword = pagesLine.substring(indexOfSpace.get(i), indexOfSpace.get(i+1)).trim();

            }
        }
        return result;
    }


    /**
     * Self defined static method to find the indexes of spaces in a string.
     * @param s the input string.
     * @return the indexes in the form of ArrayList.
     */
    public static ArrayList<Integer> indexOfSpaces(String s){
        ArrayList<Integer> arrayListOfSpace = new ArrayList<>();
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' '){
                arrayListOfSpace.add(i);
            }
        }
        return arrayListOfSpace;
    }


    /**
     * the personalized method to determine a string (can be a whole string or a substring of other strings).
     * @param s the input string.
     * @return the boolean value, if true, then contains at least one space, else it does not contain any.
     */
    public static boolean stringContainsSpace(String s){
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' ') return true;
        }
        return false;
    }
}

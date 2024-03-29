

/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Email: yichen.li.1@stonybrook.edu
 *  Programming assignment number: HW6
 *  Course: CSE214
 *  Recitation: R02
 *      TAs: Yu Xiang (Naxy) Dong, Ryan Chen
 */


package hw7;

import java.io.*;
import java.util.*;

public class WebGraph {
    /**
     * Expected data fields.
     */
    public static final int MAX_PAGES = 40;
    private ArrayList<WebPage> pages = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> edges = new ArrayList<>(MAX_PAGES);


    /**
     * The default constructor. A brand new WebGraph contains nothing will be created if this method is been
     * called.
     */
    public WebGraph() {

    }


    /**
     * Two setters for the data fields.
     */
    public void setPages(ArrayList<WebPage> pages) {
        this.pages = pages;
    }

    public void setEdges(ArrayList<ArrayList<Integer>> edges) {
        this.edges = edges;
    }


    /**
     * Two getters of the data fields in this class.
     */
    public ArrayList<ArrayList<Integer>> getEdges() {
        return this.edges;
    }

    public ArrayList<WebPage> getPages() {
        return this.pages;
    }

    /**
     * Constructs a WebGraph object using the indicated files as the source for pages and edges.
     * @param pagesFile String of the relative path to the file containing the page information.
     * @param linksFile String of the relative path to the file containing the link information.
     * @Pre-conditions:
     *      Both parameters reference text files which exist.
     *      The files follow proper format as outlined in the "Reading Graph from File" section below.
     * @return The WebGraph constructed from the text files.
     * @throws IllegalArgumentException Thrown if either of the files does not reference a valid text file,
     *                                  or if the files are not formatted correctly.
     * @throws IOException the IOException.
     * @throws ClassNotFoundException if the class is not found, this might happen from the experience of the
     *                                previous homework assignment.
     * @throws FileNotFoundException if the file does not exist, this might happen from the experience of the
     *      *                                previous homework assignment.
     * @throws SourceDestinationSameException self-defined exception.
     */
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws
            FileNotFoundException, IllegalArgumentException, IOException, ClassNotFoundException,
            SourceDestinationSameException {
        File pagesFileFile = new File(pagesFile);
        File linksFileFile = new File(linksFile);
        if(!pagesFileFile.exists() || !linksFileFile.exists()){
            throw new FileNotFoundException();
        }else{
            WebGraph webGraph = new WebGraph();
            webGraph.objectInputStream(pagesFile, linksFile);
            return webGraph;
        }
    }


    /**
     * The method intended to use 2 files to construct the data fields in this class called WebGraph.
     * @param pagesFile the pages file.
     * @param linksFile the links file.
     * @return The constructed WebGraph.
     * @throws IOException,ClassNotFoundException,IllegalArgumentException 3 possible exceptions
     * which are expected might will happen here.
     * @throws SourceDestinationSameException self-defined exception.
     */
    public void objectInputStream (String pagesFile, String linksFile)
            throws IOException, ClassNotFoundException, IllegalArgumentException, SourceDestinationSameException {
        FileInputStream pagesFileStream  = new FileInputStream(pagesFile);
        FileInputStream linksFileStream = new FileInputStream(linksFile);
        InputStreamReader pagesInputStream = new InputStreamReader(pagesFileStream);
        InputStreamReader linksInputStream = new InputStreamReader(linksFileStream);
       // WebGraph webGraph = (WebGraph) pagesInputStream.readObject();
       // WebGraph webGraph = new WebGraph();
        Scanner pagesScanner = new Scanner(pagesInputStream);
        Scanner linksScanner = new Scanner(linksInputStream);
        ArrayList<WebPage> webPageArrayList = new ArrayList<>();
        while (pagesScanner.hasNextLine()){
            String pagesLine = pagesScanner.nextLine().trim();
            if (!WebGraph.stringContainsSpace(pagesLine)){
                WebPage lineWebpage = new WebPage();
                lineWebpage.setUrl(pagesLine);
            }else{
                webPageArrayList.add(WebGraph.scannerToWebpage(pagesLine));
            }
        }
        this.setPages(webPageArrayList);

        ArrayList<ArrayList<Integer>> edgesExisting = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++){
            ArrayList<Integer> newLineEdges = new ArrayList<>();
            for (int j = 0; j < this.pages.size(); j++){
                newLineEdges.add(0);
            }
            edgesExisting.add(newLineEdges);
        }
        this.edges = edgesExisting;

        while (linksScanner.hasNextLine()){
            String linksLine = linksScanner.nextLine().trim();
            String[] linksLineArray = linksLine.split(" ");
            int sourceIndex = this.searchForPage(linksLineArray[0]);
            int destIndex = this.searchForPage(linksLineArray[1]);
                // webPageArrayList = WebGraph.scannerToWebpage(pagesScanner);
                ArrayList<Integer> newLineEdges=
                        WebGraph.scannerToEdges(this.edges.get(sourceIndex), sourceIndex,destIndex);
               // this.edges.set(sourceIndex,newLineEdges);
        }
//        ArrayList<ArrayList<Integer>> edgesExisting = this.scannerToEdges(linksScanner);
//        this.setEdges(edgesExisting);
        this.updatePageRanks();
        //return webGraph;
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


    /**
     * One of the two scanner method in this class, intended to face " pages.txt ", or similar files.
     * @param pagesLine the scanner which will be imported.
     * @return the webpages
     */
    public static WebPage scannerToWebpage(String pagesLine){
        WebPage lineWebpage = new WebPage();
        ArrayList<Integer> indexOfSpace = WebGraph.indexOfSpaces(pagesLine);
        String lineUrl = pagesLine.substring(0,indexOfSpace.get(0));
        String keywordsString = pagesLine.substring(indexOfSpace.get(0)+1);
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(keywordsString.split(" ")));
        lineWebpage.setUrl(lineUrl);
        lineWebpage.setKeywords(keywords);
        return lineWebpage;
    }


    /**
     * One of two scanners in this class, designed for file called "links.txt" or similar files
     * @param older the imported older arraylist of integers.
     * @param destLocation location of destination.
     * @param sourceLocation location of the source.
     * @return the arraylist of the edges.
     * @throws IllegalArgumentException will be thrown if unexpected values appeared.
     * @throws SourceDestinationSameException self-defined exception.
     */
    public static ArrayList<Integer> scannerToEdges(ArrayList<Integer> older,int sourceLocation, int destLocation)
            throws IllegalArgumentException, SourceDestinationSameException {
        if (sourceLocation == destLocation) throw new SourceDestinationSameException();
            ArrayList<Integer> resultOfEdges = older;
            resultOfEdges.set(destLocation,1);
        return resultOfEdges;
    }


    /**
     * Searching for the url in the data field.
     * @param inputSearching the url intended to find in the arraylist.
     * @return return the index of the url.
     */
    public Integer searchForPage(String inputSearching){
        for (int i = 0; i < this.pages.size(); i++){
            String urlFromPagesArrayList = this.pages.get(i).getUrl();
            if (WebGraph.stringEqual(urlFromPagesArrayList, inputSearching)){
                return i;
            }
        }
        return -99999;
    }


    /**
     * Personalized string equal method, intended to be a part of another method for search a url in the existing
     * array list in the data field, and the imported string of url.
     * @param s1 the first string.
     * @param s2 the second string.
     * @return boolean value, if they are same, true. Either false.
     */
    public static boolean stringEqual(String s1, String s2){
        if (s1.length() != s2.length()) return false;
        int length = s1.length();
        for (int i = 0; i < length; i++){
            if (s1.charAt(i)!= s2.charAt(i)) return false;
        }
        return true;
    }


    /**
     * The personalized method in order to update the existing arraylist of integer. We can update a new edge without
     * changing the original ones.
     * @param arrayListOfEdge the existing arraylist of Integer,
     * @param newEdgeIndex the index of the position of new edge.
     * @return return the updated ArrayList of Integers.
     */
    public static ArrayList<Integer> setArrayListOfEdge(ArrayList<Integer> arrayListOfEdge, int newEdgeIndex){
        ArrayList<Integer> newerArrayListOfEdge = arrayListOfEdge;
        for (int i = 0; i < arrayListOfEdge.size(); i++){
            if (i == newEdgeIndex){
                newerArrayListOfEdge.set(i, 1);
            }
        }
        return newerArrayListOfEdge;
    }


    /**
     * Adds a page to the WebGraph.
     * Preconditions:
     * url is unique and does not exist as the URL of a WebPage already in the graph.
     * url and keywords are not null.
     * Post-conditions:
     * The page has been added to pages at index 'i' and links has been logically extended to include
     * the new row and column indexed by 'i'.
     * @param url The URL of the webpage (must not already exist in the WebGraph).
     * @param keywords The keywords associated with the WebPage.
     * @throws IllegalArgumentException
     * If url is not unique and already exists in the graph, or if either argument is null.
     */
    public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException{
        WebPage newPage = new WebPage();
        newPage.setKeywords(keywords);
        newPage.setUrl(url);
        newPage.setIndex(this.pages.size());
        this.extendTheCurrentEdgesAndPages(newPage);
        this.updatePageRanks();
    }


    public void addPage(WebPage page) throws IllegalArgumentException{
        this.extendTheCurrentEdgesAndPages(page);
        this.updatePageRanks();
    }


    /**
     * This method is a sub-method of addPage. This is intended to extend the webPage and extend
     * the edges(2d ArrayList).
     * @param webPage the new webPage.
     */
    public void extendTheCurrentEdgesAndPages(WebPage webPage){
        this.pages.add(webPage);
        for (int i = 0; i < this.edges.size(); i++){
            this.edges.get(i).add(0);
        }
        this.edges.add(new ArrayList<Integer>(this.edges.size()+1));
        this.updatePageRanks();
    }


    /**
     * Brief:
     * Adds a link from the WebPage with the URL indicated by source
     * to the WebPage with the URL indicated by destination.
     * @param source the URL of the page which contains the hyperlink to destination.
     * @param destination the URL of the page which the hyperlink points to.
     * @preconditions Both parameters reference WebPages which exist in the graph.
     * @throws IllegalArgumentException  If either of the URLs are null or could not be found in pages.
     * @throws SourceDestinationSameException self-defined exception.
     */
    public void addLink(String source, String destination) throws
            IllegalArgumentException, SourceDestinationSameException {
        int locationOfS1 = this.searchForPage(source);
        int locationOfS2 = this.searchForPage(destination);
        if (WebGraph.stringEqual(source,destination)) throw new SourceDestinationSameException();
        if (locationOfS2 < 0 || locationOfS1 < 0) throw new IllegalArgumentException();
        ArrayList<Integer> arrayListOfEdge = this.edges.get(locationOfS1);
        arrayListOfEdge.set(locationOfS2,1);
        this.edges.set(locationOfS1,arrayListOfEdge);
        this.updatePageRanks();
    }


    /**
     * @Brief Removes the WebPage from the graph with the given URL.
     * @param url The URL of the page to remove from the graph.
     * @PostConditions The WebPage with the indicated URL has been removed from the graph,
     * and it's corresponding row and column has been removed from the adjacency matrix.
     * All pages that has an index greater than the index that was removed should decrease their index value by 1.
     * If url is null or could not be found in pages, the method ignores the input and does nothing.
     * @throws IllegalArgumentException will be thrown if the url imported is not valid.
     */
    public void removePage(String url) throws IllegalArgumentException{
        int indexOfUrl = this.searchForPage(url);
        if (indexOfUrl < 0) throw new IllegalArgumentException();
        for (int i = 0; i < this.edges.size(); i++){
            ArrayList<Integer> tempArrayList = this.edges.get(i);
            tempArrayList.remove(indexOfUrl);
            this.edges.set(i, tempArrayList);
        }
        this.edges.remove(indexOfUrl);
        this.pages.remove(indexOfUrl);
        this.updatePageRanks();
    }


    /**
     * Removes the link from WebPage with the URL indicated by source to the WebPage
     * with the URL indicated by destination.
     * @param source The URL of the WebPage to remove the link.
     * @param destination The URL of the link to be removed.
     */
    public void removeLink(String source, String destination){
        if (this.searchForPage(source)< 0 || this.searchForPage(destination)<0){
            // does nothing if either of the URLs could not be found.
            return;
        }
        int locationOfSource = this.searchForPage(source);
        int locationOfDestination = this.searchForPage(destination);
        ArrayList<Integer> sourceRow = this.edges.get(locationOfSource);
        sourceRow.set(locationOfDestination, 0);
        this.edges.set(locationOfSource, sourceRow);
        this.updatePageRanks();
    }


    /**
     * @Briefs: Calculates and assigns the PageRank for every page in the WebGraph.
     * Note: This operation should be performed after ANY alteration of the graph structure.
     */
    public void updatePageRanks() {
        for(int i = 0;i < pages.size();i++) {
            int rank = 0;
            for (int j = 0;j<pages.size();j++){
                if(edges.get(j).get(i) == 1) rank++;
            }
            pages.get(i).setRank(rank);
        }
    }


    /**
     * Prints the WebGraph in tabular form.
     */
    public void printTable(){
        ArrayList<String> printOutTable = new ArrayList<>();
        printOutTable.add("Index     URL               PageRank  Links               Keywords");
        printOutTable.add("-------------------------------------------------------------------------" +
                "-----------------------------");
        for (int i = 0; i < pages.size(); i++){
            String keywords = "";
            for (int j = 0; j < this.pages.get(i).getKeywords().size(); j++){
                keywords += this.pages.get(i).getKeywords().get(j);
                keywords += ",";
            }
            String oneOfPagePrint = "  " + i + "  | " + this.pages.get(i).getUrl();
            oneOfPagePrint += "    |    " + this.pages.get(i).getRank() + "    | ";
            String linksString = "";
            for (int j = 0; j < this.edges.get(i).size(); j++){
                if (this.edges.get(i).get(j) == 1) {
                    linksString += j + ", ";
                }
            }
            oneOfPagePrint += linksString;
            oneOfPagePrint +=  "    | " + keywords;
            printOutTable.add(oneOfPagePrint);
        }
        for (int i = 0; i < printOutTable.size(); i++){
            System.out.println(printOutTable.get(i));
        }
    }


    /**
     * Personalized searching method, by input a specific keyword.
     * @param searchingStringInput the intended searching.
     * @return return the arraylist of webpages which contains the keyword.
     */
    public ArrayList<WebPage> searchByKeyword(String searchingStringInput){
        ArrayList<WebPage> webPageArrayList = new ArrayList<>();
        for (int i = 0; i < this.pages.size(); i++){
            ArrayList<String> keyWordsArrayList = this.pages.get(i).getKeywords();
            if (WebGraph.containKeywords(keyWordsArrayList, searchingStringInput)){
                webPageArrayList.add(this.pages.get(i));
            }
        }
        return webPageArrayList;
    }


    /**
     * Personalized method, to find if the string arraylist contains the intended keyword.
     * @param stringArrayList arraylist
     * @param s searching string
     * @return boolean value.
     */
    public static boolean containKeywords (ArrayList<String> stringArrayList, String s){
        for (int i = 0; i < stringArrayList.size(); i++){
            if (WebGraph.stringEqual(stringArrayList.get(i), s)){
                return true;
            }
        }
        return false;
    }
}


/**
 * Exception will be thrown if the new link has the same destination and source. Because according to the
 * given sample, there is not such link which can direct a page to itself.
 */
class SourceDestinationSameException extends Exception{
    String s = "SourceDestinationSameException";
}


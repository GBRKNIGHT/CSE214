package hw7;

import java.io.*;
import java.util.*;

public class WebGraph {
    /**
     * Expected data fields.
     */
    public static final int MAX_PAGES = 40;
    private ArrayList<WebPage> pages;
    private ArrayList<ArrayList<Integer>> edges = new ArrayList<>(MAX_PAGES);


    /**
     * The default constructor. A brand new WebGraph contains nothing will be created if this method is been
     * called.
     */
    public WebGraph() {

    }

    /**
     * Another constructor with given parameters.
     * @param pagesFile pages file
     * @param linksFile links file
     */
    public WebGraph(File pagesFile, File linksFile){

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
     */
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws
            FileNotFoundException,IllegalArgumentException, IOException, ClassNotFoundException {
        File pagesFileFile = new File(pagesFile);
        File linksFileFile = new File(linksFile);
        if(!pagesFileFile.exists() || !linksFileFile.exists()){
            throw new FileNotFoundException();
        }else{
            WebGraph webGraph = new WebGraph();
            webGraph = webGraph.objectInputStream(pagesFile, linksFile);
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
     */
    public WebGraph objectInputStream (String pagesFile, String linksFile)
            throws IOException, ClassNotFoundException, IllegalArgumentException {
        FileInputStream pagesFileStream  = new FileInputStream(pagesFile);
        FileInputStream linksFileStream = new FileInputStream(linksFile);
        ObjectInputStream pagesInputStream = new ObjectInputStream(pagesFileStream);
        ObjectInputStream linksInputStream = new ObjectInputStream(linksFileStream);
        WebGraph webGraph = (WebGraph) pagesInputStream.readObject();
        Scanner pagesScanner = new Scanner(pagesInputStream);
        Scanner linksScanner = new Scanner(linksInputStream);
        ArrayList<WebPage> webPageArrayList = new ArrayList<>();
        while (pagesScanner.nextLine()!= null){
            String pagesLine = pagesScanner.nextLine().trim();
            if (!WebGraph.stringContainsSpace(pagesLine)){
                WebPage lineWebpage = new WebPage();
                lineWebpage.setUrl(pagesLine);
            }else{
                webPageArrayList = WebGraph.scannerToWebpage(pagesScanner);
            }
        }
        this.setPages(webPageArrayList);
        ArrayList<ArrayList<Integer>> edgesExisting = webGraph.scannerToEdges(linksScanner);
        webGraph.setEdges(edgesExisting);
        return webGraph;
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
     * @param stdin the scanner which will be imported.
     * @return the webpages
     */
    public static ArrayList<WebPage> scannerToWebpage(Scanner stdin){
        ArrayList<WebPage> result = new ArrayList<>();
        while (stdin.nextLine()!= null){
            String pagesLine = stdin.nextLine().trim();
            if (!WebGraph.stringContainsSpace(pagesLine)){
                WebPage lineWebpage = new WebPage();
                lineWebpage.setUrl(pagesLine);
                result.add(lineWebpage);
            }
            else{
                WebPage lineWebpage = new WebPage();
                ArrayList<Integer> indexOfSpace = WebGraph.indexOfSpaces(pagesLine);
                String lineUrl = pagesLine.substring(0,indexOfSpace.get(0));
                lineWebpage.setUrl(lineUrl);
                ArrayList<String> lineKeyWords = new ArrayList<>();
                int numOfSpace = indexOfSpace.size();
                int lineLength = pagesLine.length();
                if (numOfSpace == 1){
                    lineKeyWords.add(pagesLine.substring(indexOfSpace.get(0),lineLength));
                    lineWebpage.setKeywords(lineKeyWords);
                }else{
                    for (int i = 1; (i + 1) <= numOfSpace; i++){
                        String oneOfKeyword = pagesLine.substring(indexOfSpace.get(i), indexOfSpace.get(i+1)).trim();
                        lineKeyWords.add(oneOfKeyword);
                    }
                    String lastKeyword = pagesLine.substring(indexOfSpace.get(indexOfSpace.size()), lineLength);
                    lineKeyWords.add(lastKeyword);
                    lineWebpage.setKeywords(lineKeyWords);
                }
            }
        }
        return result;
    }


    /**
     * One of two scanners in this class, designed for file called "links.txt" or similar files
     * @param stdin the imported scanner for file called "links.txt" or similar files.
     * @return the 2d arraylist of the edges.
     * @throws IllegalArgumentException will be thrown if unexpected values appeared.
     */
    public ArrayList<ArrayList<Integer>> scannerToEdges(Scanner stdin) throws IllegalArgumentException{
        int size = 0;
        ArrayList<ArrayList<Integer>> resultOfEdges = new ArrayList<>();
        for (int i = 0; i < this.pages.size(); i++){
            resultOfEdges.set(i, new ArrayList<Integer>(size));
        }
        while (stdin.nextLine()!= null){
            size++;
            String pagesLine = stdin.nextLine().trim();
            if (!WebGraph.stringContainsSpace(pagesLine)){
                System.out.println("This line does not include any spaces!");
                throw new IllegalArgumentException();
            }
            ArrayList<Integer> indexOfSpaces = WebGraph.indexOfSpaces(pagesLine);
            if (indexOfSpaces.size() >= 2){
                System.out.println("This line contains more than one spaces!");
                throw new IllegalArgumentException();
            }
            String s1 = pagesLine.substring(0, pagesLine.indexOf(' '));
            String s2 = pagesLine.substring(pagesLine.indexOf(' ')+1);
            this.addLink(s1,s2);
        }
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
    }


    /**
     * Brief:
     * Adds a link from the WebPage with the URL indicated by source
     * to the WebPage with the URL indicated by destination.
     * @param source the URL of the page which contains the hyperlink to destination.
     * @param destination the URL of the page which the hyperlink points to.
     * @preconditions Both parameters reference WebPages which exist in the graph.
     * @throws IllegalArgumentException  If either of the URLs are null or could not be found in pages.
     */
    public void addLink(String source, String destination) throws IllegalArgumentException{
        int locationOfS1 = this.searchForPage(source);
        int locationOfS2 = this.searchForPage(destination);
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
            String oneOfPagePrint = "  " + i + "  | " + this.pages.get(i).getUrl();
            oneOfPagePrint += "    |    " + this.pages.get(i).getRank() + "    | ";
            String linksString = "";
            for (int j = 0; j < this.edges.get(i).size(); j++){
                if (this.edges.get(i).get(j) == 1) {
                    linksString += j + ", ";
                }
            }
            oneOfPagePrint += linksString;
            printOutTable.add(oneOfPagePrint);
        }
    }
}


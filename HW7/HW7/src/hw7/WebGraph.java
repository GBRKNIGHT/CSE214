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

    
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException{
        File pagesFileFile = new File(pagesFile);
        File linksFileFile = new File(linksFile);
        WebGraph webGraph = new WebGraph(pagesFileFile,linksFileFile);
        return webGraph;
    }
}

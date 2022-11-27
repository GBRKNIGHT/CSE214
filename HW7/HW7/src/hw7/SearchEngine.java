

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private WebGraph web = new WebGraph();
    private File pagesFile, linksFile;

    public WebGraph getWeb() {
        return this.web;
    }

    public void setWeb(WebGraph web) {
        this.web = web;
    }

    public SearchEngine(){

    }

    public void setLinksFile(File linksFile) {
        this.linksFile = linksFile;
    }

    public void setPagesFile(File pagesFile) {
        this.pagesFile = pagesFile;
    }

    public File getLinksFile() {
        return this.linksFile;
    }

    public File getPagesFile() {
        return this.pagesFile;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        boolean existingGraph = false;
        SearchEngine userSearchEngine = new SearchEngine();
        while (true){
            try {
                if (!existingGraph){
                    System.out.println("Loading WebGraph data...");
                    userSearchEngine = new SearchEngine();
                    File pagesFile = new File(PAGES_FILE);
                    File linksFile = new File(LINKS_FILE);
                    if(!pagesFile.exists() || !linksFile.exists()){
                        throw new FileNotFoundException();
                    }
                    WebGraph webGraph = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
                    userSearchEngine.setWeb(webGraph);
                    existingGraph = true;
                    System.out.println("Success!");
                }else {
                    System.out.println("(AP) - Add a new page to the graph.\n" +
                            "(RP) - Remove a page from the graph.\n" +
                            "(AL) - Add a link between pages in the graph.\n" +
                            "(RL) - Remove a link between pages in the graph.\n" +
                            "      ==(P) - Print the graph.\n" +
                            "      ==(I) Sort based on index (ASC)\n" +
                            "      ==(U) Sort based on URL (ASC)\n" +
                            "(R) Sort based on rank (DSC)\n" +
                            "(S) - Search for pages with a keyword.\n" +
                            "(Q) - Quit.");
                    System.out.println("Please select an option: ");
                    String userOption = stdin.nextLine().trim().toUpperCase();
                    switch (userOption) {
                        case "AP":
                            System.out.println("Enter a URL: ");
                            String userURL = stdin.nextLine().trim();
                            System.out.println("Enter keywords (space-separated): ");
                            String userKeywords = stdin.nextLine().trim();
                            ArrayList<String> userKeywordsArrayList = WebPage.stringToArrayList(userKeywords);
                            WebPage userNewWebPage = new WebPage();
                            userNewWebPage.setUrl(userURL);
                            userNewWebPage.setKeywords(userKeywordsArrayList);
                            userSearchEngine.web.addPage(userNewWebPage);
                            break;
                        case "RP":
                            System.out.println("Enter a URL:  ");
                            String userURLRemoving = stdin.nextLine().trim();
                            userSearchEngine.web.removePage(userURLRemoving);
                            System.out.println(userURLRemoving + " has been removed from the graph!");
                            break;
                        case "AL":
                            System.out.println("Enter a source URL: ");
                            String userSourceURL = stdin.nextLine().trim();
                            if(userSearchEngine.web.searchForPage(userSourceURL) < 0) {
                                throw new FileNotFoundException();
                            }
                            System.out.println("Enter a destination URL: ");
                            String userDestinationURL = stdin.nextLine().trim();
                            if(userSearchEngine.web.searchForPage(userDestinationURL) < 0) {
                                throw new FileNotFoundException();
                            }
                            userSearchEngine.web.addLink(userSourceURL,userDestinationURL);
                            break;
                        case "RL":
                            System.out.println("Enter a source URL: ");
                            String userSourceURLRL = stdin.nextLine().trim();
                            if(userSearchEngine.web.searchForPage(userSourceURLRL) < 0) {
                                throw new FileNotFoundException();
                            }
                            System.out.println("Enter a source URL: ");
                            String userDestinationURLRL = stdin.nextLine().trim();
                            if(userSearchEngine.web.searchForPage(userDestinationURLRL) < 0) {
                                throw new FileNotFoundException();
                            }
                            if (WebGraph.stringEqual(userSourceURLRL,userDestinationURLRL)){
                                throw new SourceDestinationSameException();
                            }
                            userSearchEngine.web.removeLink(userSourceURLRL,userDestinationURLRL);
                            System.out.println("Link removed from " + userSourceURLRL + " to " +
                                    userDestinationURLRL + "!");
                            break;
                        case "P":
                            System.out.println(" This is the sub-menu. \n" +
                                    "   (I) Sort based on index (ASC)\n" +
                                    "    (U) Sort based on URL (ASC)\n" +
                                    "    (R) Sort based on rank (DSC)");
                            System.out.println("Please select an option: ");
                            String userSubMenu = stdin.nextLine().trim().toUpperCase();
                            switch (userSubMenu){
                                case "I":
                                    Collections.sort(userSearchEngine.web.getPages(), new IndexComparator());
                                    userSearchEngine.web.printTable();
                                    break;
                                case "U":
                                    Collections.sort(userSearchEngine.web.getPages(), new URLComparator());
                                    userSearchEngine.web.printTable();
                                    break;
                                case "R":
                                    Collections.sort(userSearchEngine.web.getPages(), new RankComparator());
                                    userSearchEngine.web.printTable();
                                    break;
                                default:
                                    throw new OptionNotInMenuException();
                            }
                            break;
                        case "I","U","R":
                            System.out.println("This is an option in the sub-menu. " +
                                    "Please do not call it directly.");
                            break;
                        case "S":
                            System.out.println("Search keyword: ");
                            String userKeywordSearch = stdin.nextLine().trim();
                            break;
                        case "Q":
                            System.out.println("Goodbye.");
                            System.exit(0);
                        default:
                            throw new OptionNotInMenuException();
                    }
                }
            } catch (IllegalArgumentException IAE){
                System.out.println("Illegal arguments!");
            } catch (OptionNotInMenuException e) {
                System.out.println("There is no such option in the menu! Please retry. ");
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SourceDestinationSameException e) {
                System.out.println("Same destination and source!");
            }

        }

    }
}

/**
 * Exception class if the unexpected menu options appeared.
 */
class OptionNotInMenuException extends Exception{

}

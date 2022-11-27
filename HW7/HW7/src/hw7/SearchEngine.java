package hw7;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
public class SearchEngine {
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private WebGraph web;

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        try{
            System.out.println("Loading WebGraph data...");
            File pagesFile = new File(PAGES_FILE);
            File linksFile = new File(LINKS_FILE);
            if(!pagesFile.exists() || !linksFile.exists()){
                throw new FileNotFoundException();
            }
            System.out.println("Success!");
        }catch (IllegalArgumentException IAE){
            System.out.println("Illegal arguments!");
        }catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        while (true){
            try {
                System.out.println("(AP) - Add a new page to the graph.\n" +
                        "(RP) - Remove a page from the graph.\n" +
                        "(AL) - Add a link between pages in the graph.\n" +
                        "(RL) - Remove a link between pages in the graph.\n" +
                        "(P) - Print the graph.\n" +
                        "(I) Sort based on index (ASC)\n" +
                        "(U) Sort based on URL (ASC)\n" +
                        "(R) Sort based on rank (DSC)\n" +
                        "(S) - Search for pages with a keyword.\n" +
                        "(Q) - Quit.");
                System.out.println("Please select an option: ");
                String userOption = stdin.nextLine().trim().toUpperCase();
                switch (userOption){
                    case "AP":
                        System.out.println("Enter a URL: ");
                        String userURL = stdin.nextLine().trim();
                        System.out.println("Enter keywords (space-separated): ");
                        
                        break;
                    case "RP":
                        break;
                    case "AL":
                        break;
                    case "RL":
                        break;
                    case "P":
                        break;
                    case "I":
                        break;
                    case "U":
                        break;
                    case "R":
                        break;
                    case "S":
                        break;
                    case "Q":
                        System.out.println("Goodbye.");
                        System.exit(0);
                    default:
                        throw new OptionNotInMenuException();
                }
            }catch (IllegalArgumentException IAE){
                System.out.println("Illegal arguments!");
            } catch (OptionNotInMenuException e) {
                System.out.println("There is no such option in the menu! Please retry. ");
            }

        }

    }
}

/**
 * Exception class if the unexpected menu options appeared.
 */
class OptionNotInMenuException extends Exception{

}

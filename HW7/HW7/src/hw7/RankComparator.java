

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
import java.util.Comparator;

public class RankComparator implements Comparator{
    public int compare(Object webPage1O, Object webPage2O) {
        WebPage webPage1 = (WebPage) webPage1O;
        WebPage webPage2 = (WebPage) webPage2O;
        if (webPage1.getRank() == webPage2.getRank()) return 0;
        else if (webPage1.getRank() > webPage2.getRank()) return 1;
        else return -1;
    }
}

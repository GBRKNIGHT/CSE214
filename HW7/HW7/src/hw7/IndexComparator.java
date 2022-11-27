

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

public class IndexComparator extends Comparator {
    public int compareTo(WebPage webPage1, WebPage webPage2) {
        if (webPage1.getIndex() == webPage2.getIndex()) return 0;
        else if (webPage1.getIndex() > webPage2.getIndex()) return 1;
        else return -1;
    }
}

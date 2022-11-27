package hw7;
import java.util.Comparator;

public class URLComparator implements Comparator {
    public int compare(Object webPage1O, Object webPage2O) {
        WebPage webPage1 = (WebPage) webPage1O;
        WebPage webPage2 = (WebPage) webPage2O;
        int value = webPage1.getUrl().compareTo(webPage2.getUrl());
        if (value < 0) return -1;
        else if (value > 0) return 1;
        else return 0;
    }
}

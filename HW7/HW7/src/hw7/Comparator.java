package hw7;

import org.jetbrains.annotations.NotNull;

import java.lang.Comparable;
public class Comparator implements Comparable{
    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }


    public int compareTo(Object webPage1O, Object webPage2O) {
        WebPage webPage1 = (WebPage) webPage1O;
        WebPage webPage2 = (WebPage) webPage2O;
        if (webPage1.getIndex() == webPage2.getIndex()) return 0;
        else if (webPage1.getIndex() > webPage2.getIndex()) return 1;
        else return -1;
    }
}

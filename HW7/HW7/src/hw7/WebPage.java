package hw7;

import java.util.*;

public class WebPage {
    /**
     * Requested data fields.
     */
    private String url;
    private int index;
    private int rank;
    private ArrayList<String> keywords;

    /**
     * Default constructor
     */
    public WebPage() {
    }


    /**
     * Getters and setters for private data fields.
     */
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public ArrayList<String> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    /**
     * A brief toString method.
     * @return the wanted string.
     */
    public String toString(){
        String result = "" + this.rank+"     " + "| google.com             |***|  ";
        for (int i = 0; i < this.keywords.size(); i++){
            result += this.keywords.get(i);
        }
        return result;
    }
}

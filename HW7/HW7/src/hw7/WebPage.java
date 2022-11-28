

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
        this.url = "";
        this.keywords = new ArrayList<>();
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
        String result = "" + this.rank+"     " + "| " + this.url     +"       |";
        for (int i = 0; i < this.keywords.size(); i++){
            result += this.keywords.get(i);
        }
        return result;
    }


    /**
     * Personalized static method, intended to split a string into an arraylist. Using a space.
     * @param s the input string, intended to be keywords.
     * @return the arraylist of keywords.
     */
    public static ArrayList<String> stringToArrayList(String s){
        String[] resultArray = s.split(" ", 999);
        ArrayList<String> resultArrayList = new ArrayList<>(Arrays.asList(resultArray));
        return resultArrayList;
    }
}

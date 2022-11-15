
import java.io.Serializable;
import java.util.*;
import big.data.*;


/**
 * <a href=" http://tinyurl.com/nbf5g2h ">...</a> - Ebay Auction Data
 * <a href=" http://tinyurl.com/p7vub89 ">...</a> - Yahoo Auction Data
 */

public class AuctionTable implements Serializable {
    public HashMap<String, Auction> stringAuctionHashMap;
    public static ArrayList<String> idNumArrayList = new ArrayList<>();
    public AuctionTable(){
        stringAuctionHashMap = new HashMap<>();
    }

    public static AuctionTable buildFromURL(String URL) throws IllegalArgumentException{
        DataSource ds = DataSource.connect(URL).load();
        if (!URL.contains("http://") && !URL.contains("https://")){
            throw new IllegalArgumentException("This is not an eligible URL!");
        }else{
            /**Below are notes to denote how to briefly extract information what we need from the given sample urls.
             * I copied from the HW6.html instructions.
             *
             * listing/seller_info/seller_name
             * listing/auction_info/current_bid
             * listing/auction_info/time_left
             * listing/auction_info/id_num
             * listing/auction_info/high_bidder/bidder_name
             * // the following should be combined to get the information of the item
             *      listing/item_info/memory
             *      listing/item_info/hard_drive
             *      listing/item_info/cpu
             *
             *      following the instructions and recommendations, using this method to generate a String array would
             *      be a good choice, it is a blackbox method for me, I just need to replace the empty Strings with
             *      "N/A".
             */
            String[] sellerNameList = ds.fetchStringArray("listing/seller_info/seller_name");
            String[] currentBidList = ds.fetchStringArray("listing/auction_info/current_bid");
            String[] timeLeftList = ds.fetchStringArray("listing/auction_info/time_left");
            String[] idNumList = ds.fetchStringArray("listing/auction_info/id_num");
            String[] bidderNameList = ds.fetchStringArray("listing/auction_info/high_bidder/bidder_name");
            String[] memoryList = ds.fetchStringArray("listing/item_info/memory");
            String[] hardDriveList = ds.fetchStringArray("listing/item_info/hard_drive");
            String[] cpuList = ds.fetchStringArray("listing/item_info/cpu");
            String[] itemList = AuctionTable.mergeThreeStringArrays(cpuList,memoryList,hardDriveList);
            AuctionTable.replaceEmptyStringToNA(sellerNameList);
            AuctionTable.replaceEmptyStringToNA(currentBidList);
            AuctionTable.replaceEmptyStringToNA(timeLeftList);
            AuctionTable.replaceEmptyStringToNA(idNumList);
            AuctionTable.replaceEmptyStringToNA(bidderNameList);
            AuctionTable.replaceEmptyStringToNA(memoryList);
            AuctionTable.replaceEmptyStringToNA(hardDriveList);
            AuctionTable.replaceEmptyStringToNA(cpuList);
            AuctionTable.replaceEmptyStringToNA(itemList);
            AuctionTable auctionTable = new AuctionTable();
            AuctionTable.deleteStringArrayEmptySpace(timeLeftList);
            for (int i = 0; i < idNumList.length; i++){
                String currentBid = currentBidList[i].substring(1);
                currentBid = currentBid.replaceAll(",", "");
                currentBid = currentBid.replaceAll("$", "");
                double doubleCurrentBid = Double.parseDouble(currentBid);
                String timeString = timeLeftList[i];
                int timeGoes = 0;
                if (timeString.contains("day") && !timeString.contains("hour")){
                    int indexOfDays = timeString.indexOf("day") - 1;
                    String days = timeString.substring(0, indexOfDays);
                    days = AuctionTable.removeNonNumbers(days);
                    int numOfDays = Integer.parseInt(days);
                    timeGoes+= 24 * numOfDays;
                } else if (timeString.contains("day")){
                    int indexOfDays = timeString.indexOf("day") - 1;
                    String days = timeString.substring(0, indexOfDays);
                    int numOfDays = Integer.parseInt(days);
                    timeGoes+= 24 * numOfDays;
                    int indexOfHours = timeString.indexOf("hour") - 1;
                    String hours = "";
                    hours += timeString.substring(indexOfHours - 2, indexOfHours);
                    hours = AuctionTable.removeNonNumbers(hours);
                    int numOfHours = Integer.parseInt(hours);
                    timeGoes += numOfHours;
                } else if (timeString.contains("hour")){
                    int indexOfComma = timeString.indexOf(",");
                    String hours = "";
                    hours += timeString.substring(indexOfComma+1, indexOfComma+2);
                    hours = AuctionTable.removeNonNumbers(hours);
                    int numOfHours = Integer.parseInt(hours);
                    timeGoes += numOfHours;
                }
                else{
                    throw new IllegalArgumentException();
                }
                AuctionTable.deleteStringArrayEmptySpace(sellerNameList);
                AuctionTable.deleteStringArrayEmptySpace(bidderNameList);
                AuctionTable.deleteStringArrayEmptySpace(idNumList);
                Auction auction = new Auction(timeGoes, doubleCurrentBid, idNumList[i], sellerNameList[i],
                        bidderNameList[i], itemList[i]);
                auctionTable.putAuction(idNumList[i], auction);
            }
            return auctionTable;
        }
    }


    /**
     * This self-defined static method is intended to replace empty strings with "N/A".
     * @param stringArray input is the extracted string array from the urls.
     */
    public static void replaceEmptyStringToNA (String[] stringArray){
        for (int i = 0; i < stringArray.length; i++){
            if (stringArray[i].equals("")){
                stringArray[i] = "N/A";
            }
        }
    }


    /**
     * find the min length of 3 String arrays. Intended to prevent to existence of an item without necessary
     * components.
     * stringArray1 means cpu, 2 means memory, 3 means hard drive.
     * @param stringArray1
     * @param stringArray2
     * @param stringArray3
     * @return
     */
    public static int tripleStringArrayMinLength (String[] stringArray1, String[] stringArray2,
                                                  String[] stringArray3){
        int length1 = stringArray1.length;
        int length2 = stringArray2.length;
        int length3 = stringArray3.length;
        int[] lengths = new int[3];
        lengths[0] = length1;
        lengths[1] = length2;
        lengths[2] = length3;
        return Arrays.stream(lengths).min().getAsInt();
    }


    /**
     * Intended to merge 3 list, stringArray1 means cpu, 2 means memory, 3 means hard drive.
     * @param stringArray1
     * @param stringArray2
     * @param stringArray3
     * @return
     */
    public static String[] mergeThreeStringArrays(String[] stringArray1, String[] stringArray2,
                                                  String[] stringArray3){
        int minLength = AuctionTable.tripleStringArrayMinLength(stringArray1,stringArray2,stringArray3);
        String[] itemList = new String[minLength];
        for (int i = 0; i < minLength; i++) {
            itemList[i] = stringArray1[i];
            itemList[i] += " - ";
            itemList[i] += stringArray2[i];
            itemList[i] += " - ";
            itemList[i] = stringArray3[i];
        }
        return itemList;
    }


    /**
     * Self-defined method, intended to remove all spaces and line changes in a string array.
     * @param stringArray
     */
    public static void deleteStringArrayEmptySpace(String[] stringArray){
        for (int i = 0; i < stringArray.length; i++){
            stringArray[i].replaceAll(" ", "");
            stringArray[i].replaceAll("\n", "");
        }
    }


    /**
     *
     * @param auctionID
     * @param auction
     * @throws IllegalArgumentException
     */
    public void putAuction(String auctionID, Auction auction) throws IllegalArgumentException{
        if (this.stringAuctionHashMap == null){
            this.stringAuctionHashMap.put(auctionID, auction);
            AuctionTable.idNumArrayList.add(auctionID);
        }
        else if (this.stringAuctionHashMap.containsKey(auctionID)){
            throw new IllegalArgumentException("This ID already exist!");
        }else{
            this.stringAuctionHashMap.put(auctionID, auction);
            AuctionTable.idNumArrayList.add(auctionID);
        }
    }


    public Auction getAuction(String auctionID){
        return this.stringAuctionHashMap.get(auctionID);
    }


    /**
     * Simulates the passing of time.
     * Decrease the timeRemaining of all Auction objects by the amount specified.
     * The value cannot go below 0.
     * @param numHours the hours intended to decrease.
     * @throws IllegalArgumentException will be thrown when numHours < 0.
     */
    public void letTimePass(int numHours) throws IllegalArgumentException{
        if (numHours >= 0) {
            Set<String> keySets = this.stringAuctionHashMap.keySet();
            for (String keySet: keySets){
                this.stringAuctionHashMap.get(keySet).decrementTimeRemaining(numHours);
            }
        }else{
            throw new IllegalArgumentException("Time cannot go backward!");
        }
    }


    /**
     * Iterates over all Auction objects in the table and removes them if they are expired (timeRemaining == 0).
     * @throws NullPointerException
     */
    public void removeExpiredAuctions() throws NullPointerException{
        try{
            for (int i = 0; i < idNumArrayList.size(); i++){
                if (getAuction(AuctionTable.idNumArrayList.get(i)).getTimeRemaining() <= 0){
                    this.stringAuctionHashMap.remove(AuctionTable.idNumArrayList.get(i));
                    idNumArrayList.remove(i);
                }
            }
        }catch (NullPointerException NPE){
            System.out.println("Null pointer exception happened.");
        }
    }


    /**
     * Prints the AuctionTable in tabular form.
     */
    public void printTable(){
        for (int i = 0; i < idNumArrayList.size(); i++){
            if (stringAuctionHashMap.get(idNumArrayList.get(i))!= null){
                System.out.println(stringAuctionHashMap.get(idNumArrayList.get(i)));
            }
        }
    }

    public void removeAuctionFromTable(String auctionID){
        //511601118
        for (int i = 0; i < stringAuctionHashMap.size()+1 ; i++){
            if (AuctionTable.idNumArrayList.get(i).equals(auctionID)){
                this.stringAuctionHashMap.remove(auctionID);
            }
        }
        for (int i = 0; i < idNumArrayList.size(); i++){
            if (idNumArrayList.get(i).equals(auctionID)){
                idNumArrayList.remove(i);
            }
        }
    }



    /**
     * This method is intended to delete non-numeric characters in a string.
     * @param s input string
     * @return new string contains only numeric character.
     */
    public static String removeNonNumbers(String s){
        String result = "";
        for (int i = 0; i < s.length(); i++){
            if (Character.isDigit(s.charAt(i))){
                result += s.charAt(i);
            }
        }
        return result;
    }

    public HashMap<String, Auction> getStringAuctionHashMap() {
        return this.stringAuctionHashMap;
    }

    /**
     * extra method in order to make the IDNumArrayList to follow the hashmap.
     */
    public void setIdNumArrayList() {
        for (String key : stringAuctionHashMap.keySet()){
            idNumArrayList.add(key);
        }
    }
}

class AuctionNotFoundException extends Exception{
    public static void main(String[] args) {
        System.out.println("There is not such auction in the current table!");
    }
}

class LowBidException extends Exception{
    public static void main(String[] args) {
        System.out.println("Your bid cannot be equal or lower than the current bid of this item!");
    }
}
import java.io.Serializable;

public class Auction implements Serializable {
    private int timeRemaining;
    private double currentBid;
    private String auctionID, sellerName, buyerName,itemInfo;


    public Auction(){}// default constructor


    /**
     * Constructor with all specified parameters.
     * @param timeRemaining
     * @param currentBid
     * @param auctionID
     * @param sellerName
     * @param buyerName
     * @param itemInfo
     */
    public Auction (int timeRemaining, double currentBid, String auctionID, String sellerName, String buyerName,
                    String itemInfo){
        this.timeRemaining = timeRemaining;
        this.currentBid = currentBid;
        this.auctionID = auctionID;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.itemInfo = itemInfo;
    }

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    public double getCurrentBid() {
        return this.currentBid;
    }

    public String getAuctionID() {
        return this.auctionID;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public String getItemInfo() {
        return this.itemInfo;
    }


    /**
     * This method is intended to decrease the time remaining currently. 
     * @param time the requested time decreased.
     */
    public void decrementTimeRemaining(int time){
        if (time >= this.timeRemaining){
            this.timeRemaining = 0;
        }else{
            int currentTimeRemaining = this.getTimeRemaining();
            this.timeRemaining = (currentTimeRemaining - time);
        }
    }


    /** Brief:
     * Makes a new bid on this auction. If bidAmt is larger than currentBid,
     * then the value of currentBid is replaced by bidAmt and buyerName is is replaced by bidderName.*
     * Preconditions:
     * The auction is not closed (i.e. timeRemaining > 0).*
     * Postconditions:
     * currentBid Reflects the largest bid placed on this object.
     * If the auction is closed, throw a ClosedAuctionException.*
     * Throws:
     * ClosedAuctionException: Thrown if the auction is closed and no more bids can be placed
     * (i.e. timeRemaining == 0).
     */
    public void newBid(String bidderName, double bidAmt) throws ClosedAuctionException{
        if (bidAmt > this.getCurrentBid() && this.getTimeRemaining() > 0){
            this.buyerName = bidderName;
            this.currentBid = bidAmt;
        }
    }


    /**
     * returns string of data members in tabular form.
     */

    public String toString(){
        String result = " " + this.getAuctionID() + " | $" + this.getCurrentBid() + " | " + this.getBuyerName()
                + " | " + this.timeRemaining +" hours | " + this.getItemInfo();
        return result;
    }
}


/**
 * This is self-defined exception following the given instructions.
 */
class ClosedAuctionException extends Exception{

}

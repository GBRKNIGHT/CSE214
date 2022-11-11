public class Auction {
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

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public double getCurrentBid() {
        return this.currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public String getAuctionID() {
        return this.auctionID;
    }

    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSellerName() {
        return this.sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getItemInfo() {
        return this.itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }


    /**
     * This method is intended to decrease the time remaining currently. 
     * @param time the requested time decreased.
     */
    public void decrementTimeRemaining(int time){
        if (time >= this.timeRemaining){
            this.setTimeRemaining(0);
        }else{
            int currentTimeRemaining = this.getTimeRemaining();
            this.setTimeRemaining(currentTimeRemaining - time);
        }
    }
}

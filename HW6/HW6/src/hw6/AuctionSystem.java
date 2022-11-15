
import java.io.*;
import java.util.*;



/**
 * <a href=" http://tinyurl.com/nbf5g2h ">...</a> - Ebay Auction Data
 * <a href=" http://tinyurl.com/p7vub89 ">...</a> - Yahoo Auction Data
 */


public class AuctionSystem implements Serializable {

    private static AuctionTable auctionTable;
    private static String userName;


    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        System.out.println("Starting...");
        try{
            File newFile = new File("auctions.obj");
            if (!newFile.exists()){
                System.out.println(" No previous auction table detected.\n" +
                        "Creating new table...  ");
                auctionTable = new AuctionTable();
            }else{
                System.out.println("Loading previous Auction Table...");
                AuctionSystem.objectInputStream();
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        System.out.println("Please select a username: ");
        String userInputUsername = stdin.nextLine().trim();
        AuctionSystem.userName = userInputUsername;
        while (true){
                try {
                    System.out.println(
                            "(D) - Import Data from URL\n" +
                                    "(A) - Create a New Auction\n" +
                                    "(B) - Bid on an Item\n" +
                                    "(I) - Get Info on Auction\n" +
                                    "(P) - Print All Auctions\n" +
                                    "(R) - Remove Expired Auctions\n" +
                                    "(T) - Let Time Pass\n" +
                                    "(Q) - Quit\n" +
                                    "Please select an option:"
                    );
                    String userOption = stdin.nextLine().trim().toUpperCase();
                    switch (userOption) {
                        case "D":
                            System.out.println("Please enter a URL:");
                            String userURL = stdin.nextLine().trim();
                            System.out.println("Loading...");
                            AuctionSystem.auctionTable = AuctionTable.buildFromURL(userURL);
                            System.out.println("Auction data loaded successfully!");
                            break;
                        case "A":
                            System.out.println("Creating new Auction as " + userName);
                            System.out.println("Please enter an Auction ID:");
                            String userAuctionID = stdin.nextLine().trim();
                            System.out.println("Please enter an Auction time (hours):");
                            String userHours = stdin.nextLine().trim();
                            int userHoursInt = Integer.parseInt(userHours);
                            System.out.println("Please enter some Item Info:");
                            String userItemInfo = stdin.nextLine().trim();
                            Auction userAuction = new Auction(userHoursInt, 0, userAuctionID,
                                    userName, null, userItemInfo);
                            AuctionSystem.auctionTable.putAuction(userAuctionID, userAuction);
                            System.out.println("Auction " + userAuctionID + " inserted into table.");
                            break;
                        case "B":
                            System.out.println("Please enter an Auction ID:");
                            String userBidAuctionID = stdin.nextLine().trim();
                            Auction userBidAuction = AuctionSystem.auctionTable.getAuction(userBidAuctionID);
                            if (userBidAuction == null) throw new AuctionNotFoundException();
                            else {
                                if (userBidAuction.getTimeRemaining() <= 0) {
                                    System.out.println("Auction " + userBidAuctionID + " is CLOSED");
                                    double currentBid = userBidAuction.getCurrentBid();
                                    if (currentBid == 0) {
                                        System.out.println("    Current Bid: " + "NONE" + " .");
                                    } else {
                                        System.out.println("    Current Bid: " + currentBid + " .");
                                    }
                                    System.out.println("\n You can no longer bid on this item.");
                                    break;
                                }
                                System.out.println("Auction " + userBidAuctionID + " is OPEN");
                                double currentBid = userBidAuction.getCurrentBid();
                                if (currentBid == 0) {
                                    System.out.println("    Current Bid: " + "NONE" + " .");
                                } else {
                                    System.out.println("    Current Bid: " + currentBid + " .");
                                }
                                System.out.println("What would you like to bid?: ");
                                String userBidAmountStr = stdin.nextLine().trim();
                                double userBidAmount = Double.parseDouble(userBidAmountStr);
                                if (userBidAmount <= currentBid) {
                                    System.out.println("You cannot enter an amount equal or lower than the current" +
                                            " bid price.");
                                    break;
                                } else {
                                    AuctionSystem.auctionTable.removeAuctionFromTable(userBidAuctionID);
                                    Auction userNewBid = new Auction(userBidAuction.getTimeRemaining(), userBidAmount,
                                            userBidAuctionID, userBidAuction.getSellerName(), userInputUsername,
                                            userBidAuction.getItemInfo());
                                    String newID = userBidAuctionID;
                                    AuctionSystem.auctionTable.putAuction(newID, userNewBid);
                                    System.out.println("Bid accepted.");
                                }
                            }
                            break;
                        case "I":
                            System.out.println("Please enter an Auction ID:");
                            String userIDWantInfo = stdin.nextLine().trim();
                            Auction userWantedAuction = AuctionSystem.auctionTable.getAuction(userIDWantInfo);
                            if (userWantedAuction == null) throw new AuctionNotFoundException();
                            System.out.println("Auction " + userIDWantInfo + ":");
                            System.out.println("    Seller: " + userWantedAuction.getSellerName());
                            System.out.println("    Buyer: " + userWantedAuction.getBuyerName());
                            System.out.println("    Time: " + userWantedAuction.getTimeRemaining() + " hours.");
                            System.out.println("    Info: " + userWantedAuction.getItemInfo());
                            break;
                        case "P":
                            System.out.println(" Auction ID |      Bid   |        Seller         |   " +
                                    "       Buyer          |    Time   |  Item Info" +
                                    "\n===========================================" +
                                    "===============================================" +
                                    "================================");
                            AuctionSystem.auctionTable.printTable();
                            break;
                        case "R":
                            System.out.println("Removing expired auctions...");
                            for (int i = 0; i < AuctionTable.idNumArrayList.size() + 99; i++) {
                                AuctionSystem.auctionTable.removeExpiredAuctions();
                            }
                            System.out.println("All expired auctions removed.");
                            break;
                        case "T":
                            System.out.println("How many hours should pass: ");
                            String userWantedTimePassStr = stdin.nextLine().trim();
                            double userWantedTimePassDouble = Double.parseDouble(userWantedTimePassStr);
                            int userWantedTimePass = AuctionSystem.roundDoubleToInt(userWantedTimePassDouble);
                            System.out.println("Time passing...");
                            AuctionSystem.auctionTable.letTimePass(userWantedTimePass);
                            System.out.println("Auction times updated.");
                            break;
                        case "Q":
                            System.out.println("Writing Auction Table to file... ");
                            AuctionSystem.objectOutputStream();
                            System.out.println("Done!\n" +
                                    "\n" +
                                    "Goodbye.");
                            System.exit(0);
                        default:
                            System.out.println(" No entered option in the menu! ");
                            break;
                    }
                }catch (IllegalArgumentException IAE){
                    System.out.println("Illegal arguments found!");
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                } catch (IOException e) {
                    System.out.println("IOException emerged!");
                } catch (AuctionNotFoundException e) {

                }
            }

        }


    /**
     * self-defined method to round a double to int.
     * @param userDouble
     * @return
     */
    public static int roundDoubleToInt(double userDouble){
        int userDoubleFloor = (int) userDouble;
        if (userDouble - userDoubleFloor < 0.5){
            return userDoubleFloor;
        }else{
            return userDoubleFloor + 1;
        }
    }


    /**
     * In order to generate the saved data and export it to the directory of the java project.
     * @throws IOException all IOExceptions will be thrown.
     */
    public static void objectOutputStream () throws IOException {
        try{
            FileOutputStream file = new FileOutputStream("auctions.obj");
            ObjectOutputStream outputStream = new ObjectOutputStream(file);
            AuctionTable auctions = AuctionSystem.auctionTable;
            outputStream.writeObject(auctions);
        }catch (IOException IOE){
            System.out.println(" IOException emerged! ");
        }
    }


    /**
     * In order to import the previously saved data.
     * @throws IOException all IOExceptions will be thrown.
     * @throws ClassNotFoundException will be thrown if file not found.
     */
    public static void objectInputStream () throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream("auctions.obj");
        ObjectInputStream inputStream = new ObjectInputStream(file);
        AuctionTable auctions = (AuctionTable) inputStream.readObject();
        AuctionSystem.auctionTable = auctions;
        AuctionSystem.auctionTable.setIdNumArrayList();
    }
}


import java.io.*;
import java.util.*;

import big.data.*;


public class AuctionSystem implements Serializable {

    private static AuctionTable auctionTable;
    private static String userName;


    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Starting...");
        File newFile = new File("auctions.obj");
        try {
            if (!newFile.exists()){
                System.out.println(" No previous auction table detected.\n" +
                        "Creating new table...  ");
                auctionTable = new AuctionTable();
            }else{
                FileInputStream file = new FileInputStream("auction.obj");
                ObjectInputStream inStream = new ObjectInputStream(file);
                System.out.println("Loading previous Auction Table...");
                auctionTable = (AuctionTable) inStream.readObject();
            }
            System.out.println("Please select a username: ");
            String userInputUsername = stdin.nextLine().trim();
            AuctionSystem.userName = userInputUsername;
            while (true){
                System.out.println("(D) - Import Data from URL\n" +
                        "(A) - Create a New Auction\n" +
                        "(B) - Bid on an Item\n" +
                        "(I) - Get Info on Auction\n" +
                        "(P) - Print All Auctions\n" +
                        "(R) - Remove Expired Auctions\n" +
                        "(T) - Let Time Pass\n" +
                        "(Q) - Quit\n" +
                        "Please select an option:");
                String userOption = stdin.nextLine().trim().toUpperCase();
                switch (userOption){
                    case "D":
                        break;
                    case "A":
                        break;
                    case "B":
                        break;
                    case "I":
                        break;
                    case "P":
                        auctionTable.printTable();
                        break;
                    case "R":
                        break;
                    case "T":
                        break;
                    case "Q":
                        System.exit(0);
                }


            }

        }catch (IllegalArgumentException IAE){

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

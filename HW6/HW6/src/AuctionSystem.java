
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

/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

import java.util.Scanner;

public class TrainManager extends TrainLinkedList{
    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        TrainLinkedList newTrainLinkedList = new TrainLinkedList();
        /**
         * Return to the selection if the selected option has been completed or broke because error.
         */
        while (true){
            /**
             * Print the menu.
             */
            System.out.println("\n(F) Cursor Forward\n" +
                    "(B) Cursor Backward\n" +
                    "(I) Insert Car After Cursor\n" +
                    "(R) Remove Car At Cursor\n" +
                    "(L) Set Product Load\n" +
                    "(S) Search For Product\n" +
                    "(T) Display Train\n" +
                    "(M) Display Manifest\n" +
                    "(D) Remove Dangerous Cars\n" +
                    "(Q) Quit\n");

            System.out.println("Enter a selection:");
            String userSelection = stdin.nextLine().trim().toUpperCase();
            switch (userSelection){
                case "R":
                    if (newTrainLinkedList.size() == 0){
                        System.out.println("The train is currently attached to no car, please create a new" +
                                "car at first.");
                        break;
                    }
                    else{
                        newTrainLinkedList.removeCursor();
                        break;
                    }
                /**
                 * This selection is intended to push the cursor forward, to the next train car.
                 * If: current size = 0, ask user to create car.
                 * Else if: the cursor car is the last car(next car is null)
                 * Else: Other situations
                 */
                case "F":
                    if (newTrainLinkedList.size() == 0){
                        System.out.println("The train is currently attached to no car, please create a new" +
                                "car at first.");
                        break;
                    }
                    if (newTrainLinkedList.getCursorReference().getNextTCN() == null){
                        System.out.println("No next car, cannot move cursor forward.");
                        break;
                    }else{
                        newTrainLinkedList.cursorForward();
                        System.out.println("The cursor has been moved forward.");
                        break;
                    }

                /**
                 * This selection is intended to pull the cursor backward, to the previous train car.
                 * If: current size = 0, ask user to create car.
                 * Else if: the cursor car is the first car(the previous car is null).
                 * Else: other situations.
                 */
                case "B":
                    if (newTrainLinkedList.size() == 0){
                        System.out.println("The train is currently attached to no car, please create a new" +
                                "car at first.");
                        break;
                    }
                    if (newTrainLinkedList.getCursorReference().getPreviousTCN() == null){
                        System.out.println("No previous car, cannot move cursor backward.");
                        break;
                    }else{
                        newTrainLinkedList.cursorBackward();
                        System.out.println("The cursor has been moved backward.");
                        break;
                    }


                /**
                 * This selection is intended to add a new car.
                 * Throw errors if the user did not enter the numbers correctly.
                 */
                case "I":
                    System.out.println("Enter car length in meters: ");
                    String newCarLengthStr = stdin.nextLine().trim();
                    try{
                        Double.parseDouble(newCarLengthStr);
                    }catch (Exception NumberFormatException){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    double newCarLength = Double.parseDouble(newCarLengthStr);
                    if (newCarLength <= 0){
                        System.out.println("Please enter the number correctly!");
                    }
                    System.out.println("Enter car weight in tons: ");
                    String newCarWeightStr = stdin.nextLine().trim();
                    try{
                        Double.parseDouble(newCarWeightStr);
                    }catch (Exception NumberFormatException){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    double newCarWeight = Double.parseDouble(newCarWeightStr);
                    if (newCarWeight <= 0){
                        System.out.println("Please enter the number correctly!");
                    }
                    ProductLoad newLoad = new ProductLoad();
                    TrainCar newTrainCar = new TrainCar(newCarLength,newCarWeight,newLoad);
                    newTrainLinkedList.insertAfterCursor(newTrainCar);
                    System.out.println("New train car with "+ newCarLength +" meters and "
                            + newCarWeight+ " tons inserted into train.");
                    break;


                /**
                 * This selection is intended to load products to the cursor car.
                 * If there is no car currently, return error message to user.
                 * If there is already something in the selected car, tell the user choose another car.
                 * Throw errors if the user did not enter the numbers correctly.
                 */
                case "L":
                    if (newTrainLinkedList.size() == 0){
                        System.out.println("The train is currently attached to no car, please create a new " +
                                "car at first.");
                        break;
                    }
                    if (!newTrainLinkedList.getCursorReference().getThisCar().
                            getLoadReference().getProductName().equals("Empty")){
                        System.out.println("There is already has "+ newTrainLinkedList.getCursorReference().
                                getThisCar().getLoadReference().getProductName()
                                + " in this car, please select another car.");
                        break;
                    }
                    System.out.println("Enter produce name: ");
                    String newProductName = stdin.nextLine();
                    System.out.println("Enter product weight in tons:");
                    String newProductWeightStr = stdin.nextLine();
                    try{
                        Double.parseDouble(newProductWeightStr);
                    }catch (Exception NumberFormatException){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    double newProductWeight = Double.parseDouble(newProductWeightStr);
                    if (newProductWeight < 0){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    System.out.println("Enter product value in dollars:");
                    String newProductValueStr = stdin.nextLine();
                    try{
                        Double.parseDouble(newProductValueStr);
                    }catch (Exception NumberFormatException){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    double newProductValue = Double.parseDouble(newProductValueStr);
                    if (newProductValue < 0){
                        System.out.println("Please enter the number correctly!");
                        break;
                    }
                    System.out.println("Enter is product dangerous? (y/n):");
                    boolean newProductDangerous;
                    String newProductDangerousStr = stdin.nextLine().trim().toLowerCase();
                    if(newProductDangerousStr.equals("y")){
                        newProductDangerous = true;
                    }
                    else if(newProductDangerousStr.equals("n")){
                        newProductDangerous = false;
                    }else {
                        System.out.println("Please enter y or n !!!");
                        break;
                    }
                    ProductLoad newProductLoad = new ProductLoad(newProductName, newProductWeight, newProductValue,
                            newProductDangerous);
                    newTrainLinkedList.load(newProductLoad);
                    System.out.println(newProductWeight+" tons of "+ newProductName+" added to the current car.");
                    break;

                /**
                 * This selection is using the toString method to print out all train cars in this train,
                 * and what inside the train car.
                 */
                case "M":
                    if (newTrainLinkedList.getHeadReference().getThisCar() == null){
                        System.out.println("The train is currently attached to no car, please create a new " +
                                "car at first.");
                        break;
                    }
                    String printedTrain = newTrainLinkedList.toString();
                    System.out.println(printedTrain);
                    break;

                /**
                 * This selection is intended to calculate the total length, weight, value, size and dangerous or not.
                 */
                case "T":
                    String result = "";
                    String dangerous;
                    if (newTrainLinkedList.isDangerous()){
                        dangerous = "DANGEROUS";
                    }else{
                        dangerous = "Not Dangerous";
                    }
                    result += "  ============================================================" +
                            "=========================================\n";
                    result = String.format("||                     Train: %d cars, %.2f meters, %.2f tons, $%.2f dollars, %s                   ||\n",
                             newTrainLinkedList.size(), newTrainLinkedList.getLength(), newTrainLinkedList.getWeight(),
                            newTrainLinkedList.getValue(), dangerous
                    );
//                    result += "||                     Train:"+ newTrainLinkedList.size() + " cars, "
//                            + newTrainLinkedList.getLength()+ " meters," + newTrainLinkedList.getWeight() + " tons, $"
//                             + newTrainLinkedList.getValue() + " dollars,"+ dangerous+ "                   ||\n";
                    result += "  ============================================================" +
                            "=========================================\n";
                    System.out.println(result);
                    break;

                /**
                 * This selection is intended to delete all dangerous train cars,
                 * and to ensure there is no error about the cursor, this selection will also automatically
                 * move the cursor to the first non-dangerous train car.
                 */
                case "D":
                    if (newTrainLinkedList.size() <= 0){
                        System.out.println("The train is currently attached to no car, please create a new " +
                                "car at first.");
                        break;
                    }
                    newTrainLinkedList.removeDangerousCars();
                    System.out.println("All dangerous cars have been removed!\n " +
                            "The cursor has been moved to the head train car!");
                    break;

                /**
                 * This selection is intended to search a specific product on the train.
                 */
                case "S":
                    System.out.println("Enter product name: ");
                    String userInput = stdin.nextLine().trim().toUpperCase();
                    /**
                     * If the current train has no cars, break and return to the main menu.
                     */
                    if (newTrainLinkedList.size() <= 0){
                        System.out.println("The train is currently attached to no car, please create a new " +
                                "car at first.");
                    }else{
                        newTrainLinkedList.findProduct(userInput);
                        if (!newTrainLinkedList.isFound()){
                            break;
                        }
                        String output = "Load: \n";
                        output+= String.format("%13s %17s %13s %10s\n", "Name","Weight (t)", "Value ($)", "Dangerous");
                        output+="===============================================================" +
                                "==========\n";/**/
                        String dangerousSearching;
                        /**
                         * Change true/false to Yes/No
                         */
                        if (newTrainLinkedList.isSearchingProductDangerous()){
                            dangerousSearching = "Yes";
                        }else {
                            dangerousSearching = "No";
                        }
                        output += String.format("%13s %17s %13s %10s\n", userInput,
                                newTrainLinkedList.getSearchingProductWeight()
                                , newTrainLinkedList.getSearchingProductValue(),dangerousSearching);
                        System.out.println("The following products were found on " +
                                newTrainLinkedList.getSearchingProductCount() + " cars: \n");
                        System.out.println(output);

                    }
                    newTrainLinkedList.resetSearch();
                    break;

                /**
                 * This selection is intended to quit the program.
                 */
                case "Q":
                    System.out.println("Program terminating successfully...");
                    System.exit(0);
                    break;

                /**
                 * The default option if the user entered the wrong selection which is not entailed in the main menu.
                 */
                default:
                    System.out.println("There is no such selection in the menu, please select again.");
                    break;
            }
            }
        }
    }
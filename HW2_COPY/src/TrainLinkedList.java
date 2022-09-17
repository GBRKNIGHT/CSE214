/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

public class TrainLinkedList {
    /**
     * The TrainlinkedList class implements an abstract data type for a Double-Linked
     * List of train cars supporting some common operations on such lists,
     * as well as a few others.
     */

    /**
     * Initialize the head, cursor and tail.
     */
    private TrainCarNode headReference = new TrainCarNode();
    private TrainCarNode tailReference = new TrainCarNode();
    private TrainCarNode cursorReference = new TrainCarNode();
    /**
     * Extra data fields to store the current size, length, value, weight, dangerous or not, num of dangerous
     * train cars
     */
    private int currentSize;
    private double currentLength ;
    private double currentValue;
    private double currentWeight;
    private boolean dangerous;
    private int dangerousCar;
    /**
     * Extra data fields to store the total weight, value and dangerous of searching product.
     */
    private double searchingProductWeight;
    private double searchingProductValue;
    private boolean searchingProductDangerous;
    private int searchingProductCount=0;
    private boolean isFound = false;

    /**
     * Brief:
     * Constructs an instance of the TrainLinkedList with no TrainCar objects in it.
     * Postconditions:
     * This TrainLinkedList has been initialized to an empty linked list.
     * head, tail, and cursor are all set to null.
     */
    public TrainLinkedList(){} // default constructor


    /**
     * Brief:
     * Returns a reference to the TrainCar at the node currently referenced by the cursor.
     * Preconditions:
     * The list is not empty (cursor is not null).
     * Returns:
     * The reference to the TrainCar at the node currently referenced by the cursor.
     * @return this car
     */
    public TrainCar getCursorData(){
        return cursorReference.getThisCar();
    }

    /**
     * Brief:
     * Places car in the node currently referenced by the cursor.
     * Preconditions:
     * The list is not empty (cursor is not null).
     * Postconditions:
     * The cursor node now contains a reference to car as its data.
     * @param car the pre-defined car
     */
    public void setCursorData(TrainCar car){
        cursorReference.setThisCar(car);
    }

    /**
     * Brief:
     * Moves the cursor to point at the next TrainCarNode.
     * Preconditions:
     * The list is not empty (cursor is not null) and cursor does not currently reference the tail of the list.
     * Postconditions:
     * The cursor has been advanced to the next TrainCarNode, or has remained at the tail of the list.
     */
    public void cursorForward(){
        /*
        The comments below are testing codes, please ignore.
         */
//        System.out.println("You are currently forwarding to "
//                + this.cursorReference.getNextTCN().getThisCar().getLength());
        TrainCarNode temp = this.cursorReference.getNextTCN();
        this.setCursorReference(temp);
    }

    /**
     * Brief:
     * Moves the cursor to point at the previous TrainCarNode.
     * Preconditions:
     * The list is not empty (cursor is not null) and the cursor does not currently reference the head of the list.
     * Postconditions:
     * The cursor has been moved back to the previous TrainCarNode, or has remained at the head of the list.
     */
    public void cursorBackward(){
        /*
        The comments below are testing codes, please ignore.
         */
//        System.out.println("You are currently back-warding to " +
//                this.cursorReference.getPreviousTCN().getThisCar().getLength());
        TrainCarNode temp = this.cursorReference.getPreviousTCN();
        this.setCursorReference(temp);
    }

    /**
     * Brief:
     * Inserts a car into the train after the cursor position.
     * Parameters:
     * newCar - the new TrainCar to be inserted into the train.
     * Preconditions:
     * This TrainCar object has been instantiated
     * Postconditions:
     * The new TrainCar has been inserted into the train after the position of the cursor.
     * All TrainCar objects previously on the train are still on the train, and their order has been preserved.
     * The cursor now points to the inserted car.
     * Throws:
     * IllegalArgumentException - Indicates that newCar is null.
     * @param newCar the pre-defined new car
     */
    public void insertAfterCursor(TrainCar newCar){
        if (this.size() == 0){
            /**
             * If there is currently no car, means we are going to insert the very first car.
             */
            this.currentSize++;
            this.cursorReference = new TrainCarNode(null, null, newCar);
            this.headReference = cursorReference;
            this.tailReference = cursorReference;
            this.currentLength += newCar.getLength();
            if (newCar.getLoadReference() == null){
                currentValue += 0;
                currentWeight += newCar.getWeight();
            }else{
                currentValue += newCar.getLoadReference().getValue();
                currentWeight += newCar.getTotalWeight();
                /**
                 *This statement is mean to control the num of dangerous car, and turn the boolean var to true.
                 */
                if (newCar.getLoadReference().isDangerous()){
                    this.dangerous = true;
                    this.dangerousCar++;
                }
//                System.out.println("The very first new car " + newCar.getLength() + " has been connected.");
//                testCode above
            }
        }else{
            /**
             * If it is not the first car...
             */
            this.currentSize++;
            if (cursorReference.getNextTCN() == null){
                /**
                 * If the cursor train car is the last car...
                 */
                TrainCarNode newTCN = new TrainCarNode(cursorReference, null, newCar);
                cursorReference.setNextTCN(newTCN);
                this.tailReference = newTCN;
                this.cursorForward();
                currentLength += newCar.getLength();
                currentWeight += newCar.getWeight();
                if (newCar.getLoadReference().isDangerous()){
                    this.dangerous = true;
                    this.dangerousCar++;
                }
                System.out.println("The tail new car " + newCar.getLength() + " has been connected.");
            }else{
                /**
                 * If the cursor train car is not the last car...
                 * (Since we are just inserting a new car to the current cursor car, we don't have to write another
                 * case if the cursor car is the first car.)
                 */
                TrainCarNode newTCN = new TrainCarNode(cursorReference, cursorReference.getNextTCN(), newCar);
                cursorReference.setNextTCN(newTCN);
                this.cursorForward();
                this.cursorForward();
                cursorReference.setPreviousTCN(newTCN);
                this.cursorBackward();
                currentLength += newCar.getLength();
                currentWeight += newCar.getWeight();
                if (newCar.getLoadReference().isDangerous()){
                    /**
                     *This statement is mean to control the num of dangerous car, and turn the boolean var to true.
                     */
                    this.dangerous = true;
                    this.dangerousCar++;
                }
                System.out.println("The middle new car " + newCar.getLength() + " has been connected.");
            }

        }
    }

    /**
     * Brief:
     * Removes the TrainCarNode referenced by the cursor and returns the TrainCar contained within the node.
     * Preconditions:
     * The cursor is not null.
     * Postconditions:
     * The TrainCarNode referenced by the cursor has been removed from the train.
     * The cursor now references the next node, or the previous node if no next node exists.
     * @return the removed car.
     */
    public TrainCar removeCursor(){
        /*
        The case of empty train has been written at other places.
         */
        TrainCar removedCar = cursorReference.getThisCar().clone();
        /*
        If there is only one car (it should equal to the head car and tail car simultaneously),
        and it is going to be removed, we just need to simply initialize all parameters.
         */
        if (cursorReference.getNextTCN() == null && cursorReference.getPreviousTCN() == null){
            currentSize = 0;
            currentValue = 0;
            currentLength = 0;
            currentWeight = 0;
            dangerousCar = 0;
            dangerous = false;
            headReference = null;
            tailReference = null;
            System.out.println("The only one car has been removed!");
        }
        /*
        Situation if the tail car going to be removed.
         */
        else if(this.cursorReference.getNextTCN() == null){
            System.out.println("You are currently deleting " + this.cursorReference.getThisCar().getLength());
            this.cursorBackward();
            tailReference = this.cursorReference;
            this.cursorReference.setNextTCN(null);
            currentSize--;
            currentLength = currentLength - removedCar.getLength();
            currentValue = currentValue - removedCar.getLoadReference().getValue();
            currentWeight -= removedCar.getTotalWeight();
            /**
             * Count the numbers of dangerous cars, if it is the only one dangerous car, flip the boolean var to false.
             */
            if (removedCar.getLoadReference().isDangerous()){
                this.dangerousCar--;
                if (this.dangerousCar == 0){
                    this.dangerous = false;
                }
            }
        }
        /*
        Situation if the head car going to be removed.
         */
        else if(this.cursorReference.getPreviousTCN() == null){
            System.out.println("You are currently deleting " + this.cursorReference.getThisCar().getLength());
            this.cursorForward();
            this.headReference = cursorReference;
            this.cursorReference.setPreviousTCN(null);
            currentSize--;
            currentLength = currentLength - removedCar.getLength();
            currentValue = currentValue - removedCar.getLoadReference().getValue();
            currentWeight -= removedCar.getTotalWeight();
            /**
             * Count the numbers of dangerous cars, if it is the only one dangerous car, flip the boolean var to false.
             */
            if (removedCar.getLoadReference().isDangerous()){
                this.dangerousCar--;
                if (this.dangerousCar == 0){
                    this.dangerous = false;
                }
            }
        }
        /*
        Situation if neither the head car nor the tail car going to be removed.
         */
        else{
            System.out.println("You are currently deleting " + this.cursorReference.getThisCar().getLength());
            TrainCarNode removed = this.getCursorReference();
            this.cursorBackward();
            cursorReference.setNextTCN(removed.getNextTCN());
            this.cursorForward();
            cursorReference.setPreviousTCN(removed.getPreviousTCN());
            currentSize--;
            currentLength = currentLength - removedCar.getLength();
            currentValue = currentValue - removedCar.getLoadReference().getValue();
            currentWeight -= removedCar.getTotalWeight();
            /**
             * Count the numbers of dangerous cars, if it is the only one dangerous car, flip the boolean var to false.
             */
            if (removedCar.getLoadReference().isDangerous()){
                this.dangerousCar--;
                if (this.dangerousCar == 0){
                    this.dangerous = false;
                }
            }
        }
        return removedCar;
    }

    /**
     * Brief:
     * Determines the number of TrainCar objects currently on the train.
     * Returns:
     * The number of TrainCar objects on this train.
     * Notes:
     * This function should complete in O(1) time.
     * @return current size
     */
    public int size(){
        return this.currentSize;
    }

    /**
     * Brief:
     * Returns the total length of the train in meters.
     * Returns:
     * The sum of the lengths of each TrainCar in the train.
     * Notes:
     * This function should complete in O(1) time.
     */
    public double getLength(){
        return this.currentLength;
    }

    /**
     * Brief:
     * Returns the total value of product carried by the train.
     * Returns:
     * The sum of the values of each TrainCar in the train.
     * Notes:
     * This function should complete in O(1) time.
     */
    public double getValue(){
        return this.currentValue;
    }

    /**
     * Brief:
     * Returns the total weight in tons of the train.
     * Note that the weight of the train is the sum of the weights of each empty TrainCar,
     * plus the weight of the ProductLoad carried by that car.
     * Returns:
     * The sum of the weight of each TrainCar plus the sum of the ProductLoad carried by that car.
     * Notes:
     * This function should complete in O(1) time.
     */
    public double getWeight(){
        return this.currentWeight;
    }

    /**
     * Brief:
     * Whether or not there is a dangerous product on one of the TrainCar objects on the train.
     * Returns:
     * Returns true if the train contains at least one TrainCar carrying a dangerous ProductLoad, false otherwise.
     * Notes:
     * This function should complete in O(1) time.
     * @return Whether the train is dangerous.
     */
    public boolean isDangerous(){
        return this.dangerous;
    }

    /**
     * Brief:
     * Searches the list for all ProductLoad objects with the indicated name and sums together their weight and value
     * (Also keeps track of whether the product is dangerous or not),
     * then prints a single ProductLoad record to the console.
     * Parameters:
     * name - the name of the ProductLoad to find on the train.
     * Notes:
     * This method should search the entire train for the indicated ProductLoad,
     * and should not stop after the first match.
     * Simply use the boolean value of isDangerous for the first match found.
     * @param name
     */
    public void findProduct(String name){
        TrainCarNode pointer = headReference;
        TrainCarNode[] foundProduct = new TrainCarNode[this.currentSize];
        int i = 0;
        /*
        Use an array to store the found product with the same name.
         */
        while(pointer!=null){
            if (pointer.getThisCar().getLoadReference().getProductName().toUpperCase().equals(name)){
                this.isFound = true;
                this.searchingProductCount++;
                this.searchingProductValue += pointer.getThisCar().getLoadReference().getValue();
                this.searchingProductWeight += pointer.getThisCar().getLoadReference().getWeight();
                this.searchingProductDangerous = pointer.getThisCar().getLoadReference().isDangerous();
                foundProduct[i] = pointer;
                i++;
            }
//            else{
//                System.out.println("There is currently no such product on the train!");
//                this.searchingProductValue = 0;
//                this.searchingProductWeight = 0;
//                this.searchingProductDangerous = false;
//                break;
//            }
            pointer = pointer.getNextTCN();
        }
        if (!isFound){
            System.out.println("There is currently no such product on the train!");
            this.searchingProductValue = 0;
            this.searchingProductWeight = 0;
            this.searchingProductDangerous = false;
        }
//        for (int j = 0; j < foundProduct.length; j++){
////            System.out.println("We should print the founded products here.");
//        }
    }

    /**
     * Brief:
     * Prints a neatly formatted table of the car number, car length, car weight, load name,
     * load weight, load value, and load dangerousness for all of the car on the train.
     * Notes:
     * There should be a record for each TrainCar printed to the console,
     * numbered from 1 to n. For each car, print the data of the car,
     * followed by the ProductLoad data if the car is not empty.
     * If the car is empty, print "Empty" for name, 0 for weight and value,
     * and "NO" for dangerousness (see sample I/O for example).
     */
    public void printManifest(){
        System.out.println(this.toString());
    }

    /**
     * Brief:
     * Removes all dangerous cars from the train, maintaining the order of the cars in the train.
     * Postconditions:
     * All dangerous cars have been removed from this train.
     * The order of all non-dangerous cars must be maintained upon the completion of this method.
     * Notes:
     * All the dangerous cars may be discarded after calling this method.
     */
    public void removeDangerousCars(){
        /*
        Try to catch the error if there is no car in this train.
         */
        try{
            boolean b = headReference.equals(null) || tailReference.equals(null);
        }catch (Exception NullPointerException){
            System.out.println("==============================!!!!!!!There is no cars currently" +
                    "!!!!!!!===============================");
        }
        this.setCursorReference(this.getHeadReference());
        while (cursorReference!= null){
            if (cursorReference.getThisCar().getLoadReference().isDangerous()){
                /**
                 * If this dangerous car is the only one car.
                 */
                if (cursorReference.getPreviousTCN() == null && cursorReference.getNextTCN() == null){
                    this.removeCursor();
                    break;
                }
                this.removeCursor();
            }else{
                if (cursorReference.getPreviousTCN() == null && cursorReference.getNextTCN() == null){
                    break;
                }
                this.cursorForward();
            }
        }
        this.setCursorReference(headReference);
    }

    public String toString(){
        /*
        Make the cursor back to the head of the train.
         */
        TrainCarNode temp_cursorReference = this.headReference;
        if (temp_cursorReference== null)
        {System.out.println("==============================!!!!!!!There is no cars currently" +
                    "!!!!!!!===============================\n");
            return "";
        }
        /*
        Formatted print the head part of the form.
         */
        String result = String.format("%s %40s\n", "CAR:", "| LOAD:");
        result+= String.format("%s %13s %17s %3s %13s %17s %13s %10s\n", "Num","Length (m)", "Weight (t)" ,
                "|", "Name","Weight (t)", "Value ($)", "Dangerous");
        result+="===============================================================" +
                "======================================\n";
        /*
        Try to catch the error if the train has no cars.
         */
        int i = 0;
        /**
         *Add the information of each car to the String, until it reach the end of train.
         */
        while (temp_cursorReference!= null){
            /**
             *Print and arrow before the Num, if the current cursor is equal to the temp cursor,
             * (The "equals" method is defined in the class "TrainCarNode")
             */
            if (temp_cursorReference.equals(this.cursorReference)){
                String dangerous;
                /*
                Change the true/false to Yes/No.
                 */
                if (temp_cursorReference.getThisCar().getLoadReference().isDangerous()){
                    dangerous = "Yes";
                }else{
                    dangerous = "No";
                }
                result += String.format("%s %13s %17s %3s %13s %17s %13s %10s\n",
                        "->"+(i+1),temp_cursorReference.getThisCar().getLength(),
                        temp_cursorReference.getThisCar().getWeight() ,
                        "|", temp_cursorReference.getThisCar().getLoadReference().getProductName(),
                        temp_cursorReference.getThisCar().getLoadReference().getWeight(),
                        temp_cursorReference.getThisCar().getLoadReference().getValue(),
                        dangerous);
                temp_cursorReference = temp_cursorReference.getNextTCN();
                i++;
            }
            /**
             * Print two spaces before the Num, if the current cursor is not equal to the temp cursor,
             * (The "equals" method is defined in the class "TrainCarNode")
             */
            else{
                String dangerous;
                /*
                Change the true/false to Yes/No.
                 */
                if (temp_cursorReference.getThisCar().getLoadReference().isDangerous()){
                    dangerous = "Yes";
                }else{
                    dangerous = "No";
                }
                result += String.format("%s %13s %17s %3s %13s %17s %13s %10s\n",
                        "  "+(i+1),temp_cursorReference.getThisCar().getLength(),
                        temp_cursorReference.getThisCar().getWeight() ,
                        "|", temp_cursorReference.getThisCar().getLoadReference().getProductName(),
                        temp_cursorReference.getThisCar().getLoadReference().getWeight(),
                        temp_cursorReference.getThisCar().getLoadReference().getValue(), dangerous);
                temp_cursorReference = temp_cursorReference.getNextTCN();
                i++;
            }
            //System.out.println(temp_cursorReference.getThisCar().getLength());
        }
        return result;
    }

    /**
     * getters and setters for the head, cursors, and tail.
     * @return
     */
    public TrainCarNode getHeadReference(){
        return headReference;
    }

    public TrainCarNode getTailReference(){
        return tailReference;
    }

    public TrainCarNode getCursorReference(){
        return cursorReference;
    }

    public void setHeadReference(TrainCarNode headReference) {
        this.headReference = headReference;
    }

    public void setTailReference(TrainCarNode tailReference) {
        this.tailReference = tailReference;
    }

    public void setCursorReference(TrainCarNode cursorReference) {
        this.cursorReference = cursorReference;
    }

    /**
     * Method to load a constructed product to the cursored car.
     * @param product pre-defined product
     */
    public void load(ProductLoad product){
        this.getCursorReference().getThisCar().setLoadReference(product);
        this.currentWeight += product.getWeight();
        this.currentValue += product.getValue();
        if (product.isDangerous()){
            dangerousCar++;
            this.dangerous = true;
        }
    }

    /**
     * Getters for the searching product.
     * @return
     */
    public double getSearchingProductValue() {
        return searchingProductValue;
    }

    public double getSearchingProductWeight() {
        return searchingProductWeight;
    }

    public boolean isSearchingProductDangerous() {
        return searchingProductDangerous;
    }

    public int getSearchingProductCount() {
        return searchingProductCount;
    }

    /**
     * Return a boolean value to indicate the product searching is found or not.
     * @return
     */
    public boolean isFound() {
        return isFound;
    }

    /**
     * Established method in order to reset the search,
     * to prevent the second search repeat the same content as the first one.
     */
    public void resetSearch(){
        this.searchingProductCount = 0;
        this.isFound = false;
        this.searchingProductDangerous = false;
        this.searchingProductValue = 0;
        this.searchingProductWeight = 0;
    }

}



/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

import java.io.*;
import java.util.*;

public class PythonTracer {
    public static final int SPACE_COUNT = 4;

    /**
     * Main method which can allow user to perform manipulations.
     * @throws Exception all exceptions will be thrown.
     */
    public static void main(String[] args) throws Exception {
        while (true){
            Scanner stdin = new Scanner(System.in);
            System.out.println("Please enter a file name (or 'quit' to quit):");
            String userInputName = stdin.nextLine().trim();
            if (userInputName.equals("quit")){
                System.out.println("Program terminating successfully...");
                System.exit(0);
            }
            try {
                Complexity finalComplexity = traceFile(userInputName);
                System.out.println("Overall complexity of : "+  userInputName +" " + finalComplexity.toString());
            }catch (FileNotFoundException e){
                System.out.println("File not found!");
            }
        }
    }

    /**
     * Method
     * @param filename the user defined filename.
     * @return return the complexity of the user defined python file.
     * @throws Exception input mismatch
     */
    public static Complexity traceFile(String filename) throws Exception {
        BlockStack userStack = new BlockStack();
        try {
            /*
            We need to detect whether the file does exist, and does it have lines.
             */
            File newPyFile = new File(filename);
            Scanner reader = new Scanner(newPyFile);
            if (!newPyFile.canRead()){
                System.out.println("Cannot read file.");
                System.exit(0);
            }
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (!data.isEmpty() && !data.trim().isEmpty() && data.trim().charAt(0) != '#') {
                    int indents;
                    for (indents = 0; indents < data.length(); indents++) {
                        /*
                        Count the numbers of spaces of each line.
                         */
                        if (data.charAt(indents) == ' ') {
                            indents++;
                        } else {
                            break;
                        }
                    }
                    indents /= SPACE_COUNT;
//                    System.out.println(indents);
//                    System.out.println(userStack.size());
                    System.out.println();
                    while (indents < userStack.size()) {
                        if (indents == 0) {
                            /*
                            If the indents is 0, which can means that we have exited all blocks, and it is not a
                            comment.
                             */
                            Complexity sub = userStack.peek().getHighestSubComplexity();
                            Complexity block_com = userStack.peek().getBlockComplexity();
                            Complexity totalComplexity = new Complexity(sub.getN_Power() + block_com.getN_Power(),
                                    sub.getLog_power()+ block_com.getLog_power());
                            return totalComplexity;

                        } else {
                            CodeBlock oldTop = userStack.pop();
                            CodeBlock newTop = userStack.peek();
                            Complexity sub = oldTop.getHighestSubComplexity();
                            Complexity block_com = oldTop.getBlockComplexity();
                            Complexity totalComplexity = new Complexity(sub.getN_Power() + block_com.getN_Power(),
                                    sub.getLog_power()+ block_com.getLog_power());
                            if (totalComplexity.getN_Power() > newTop.getHighestSubComplexity().getN_Power()) {
                                newTop.setHighestSubComplexity(totalComplexity);
//                                System.out.println("Current top complexity" + newTop.getBlockComplexity());
                            } else if (totalComplexity.getN_Power() == newTop.getHighestSubComplexity().getN_Power()){
                                if (totalComplexity.getLog_power() > newTop.getHighestSubComplexity().getLog_power()) {
                                    newTop.setHighestSubComplexity(totalComplexity);
//                                    System.out.println("Current top complexity" + newTop.getBlockComplexity());
                                }
                            }
                        }
                        System.out.println("This indent " + indents);
                        System.out.println("This stack " + userStack.size());
                        //break;
                    }
                    String dataTrim = data.trim();
                    String[] dataTrimArray = dataTrim.split(" ");
                    boolean containKeyWord = false;
//                    boolean keyWordAtFirst = false;
                    for (int i = 0; i < 1; i++) {
                        for (int j = 0; j < CodeBlock.BLOCK_TYPES.length; j++) {
                            if (dataTrimArray[i].equals(CodeBlock.BLOCK_TYPES[j])) {
                                containKeyWord = true;
//                                if (i == 0){
//                                    keyWordAtFirst = true;
//                                }
                                break;
                            }
                        }
                    }

                    /*
                    We need to make sure the current size is not 0, and the loop variable is not empty.
                     */
                    if (userStack.size()>0 && userStack.peek().getLoopVariable() != null)
                    {
                        if (dataTrimArray[0].equals(userStack.peek().getLoopVariable())) {
                            /*
                            Situation if the complexity is a log.
                            This will only be reached when the loop is a while loop.
                             */
                            if (((dataTrimArray[1].equals("/=")) || (dataTrimArray[1].equals("*=")))) {
                                CodeBlock updatingCodeBlock = userStack.peek();
                                updatingCodeBlock.getBlockComplexity().
                                        setLog_power(updatingCodeBlock.getBlockComplexity().getLog_power() + 1);
                            /*
                            Situation is the complexity is a n_power.
                            This will only be reached when the loop is a while loop.
                             */
                            } else if (((dataTrimArray[1].equals("+=")) || (dataTrimArray[1].equals("-=")))) {
                                CodeBlock updatingCodeBlock = userStack.pop();
                                Complexity updatingComplexity = new Complexity
                                        (updatingCodeBlock.getBlockComplexity().getN_Power() + 1,
                                                updatingCodeBlock.getBlockComplexity().getLog_power());
                                updatingCodeBlock.setBlockComplexity(updatingComplexity);
                                userStack.push(updatingCodeBlock);
                            }
                        }
                    }
                    /*
                    If this line contains a keyword.
                     */
                    if (containKeyWord) {
//                        System.out.println(dataTrimArray[0]);
                        if (dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[1])) { // for
                            Complexity forComplexity = new Complexity(0, 0);
                            CodeBlock forCodeBlock = new CodeBlock();
                            forCodeBlock.setName("for");
                            for (int i = 0; i < dataTrimArray.length; i++) {
                                if (dataTrimArray[i].equals("N:")) {
                                    forComplexity.setN_Power(1);
                                    break;
                                } else if (dataTrimArray[i].equals("log_N:")) {
                                    forComplexity.setLog_power(1);
                                    break;
                                }
                            }
                            forCodeBlock.setBlockComplexity(forComplexity);
                            Complexity highestSubComplexity = new Complexity(0,0);
                            forCodeBlock.setHighestSubComplexity(highestSubComplexity);
                            forCodeBlock.setName("forCodeBlock");
                            userStack.push(forCodeBlock);
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'for' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() != 0) {
                                printout += userStack.peek().getBlockComplexity().toString() + "    highest sub-complexity:";
                                printout+= userStack.peek().getHighestSubComplexity().toString();
                            }
                            System.out.println(printout);
                        } else if (dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[2])) { //while
                            Complexity whileComplexity = new Complexity(0, 0);
                            CodeBlock whileCodeBlock = new CodeBlock();
                            whileCodeBlock.setName("while");
                            whileCodeBlock.setName(dataTrimArray[0]);
                            whileCodeBlock.setBlockComplexity(whileComplexity);
                            Complexity hisub = new Complexity(0,0);
                            whileCodeBlock.setHighestSubComplexity(hisub);
                            whileCodeBlock.setLoopVariable(dataTrimArray[1]);
                            userStack.push(whileCodeBlock);
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'while' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() != 0) {
                                printout += userStack.peek().getBlockComplexity().toString() + "    highest sub-complexity:";
                                printout+= userStack.peek().getHighestSubComplexity().toString();
                            }
                            System.out.println(printout);
                        } else if(dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[0])){
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'def' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() == 0) {
                                Complexity newComplexity = new Complexity(0,0);
                                printout += newComplexity.toString() + "    highest sub-complexity:";
                                printout += newComplexity.toString();
                            }
                            System.out.println(printout);
                        }else if (dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[3])){
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'if' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() != 0) {
                                printout += userStack.peek().getBlockComplexity().toString() + "    highest sub-complexity:";
                                printout+= userStack.peek().getHighestSubComplexity().toString();
                            }
                            System.out.println(printout);
                        }else if (dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[5])){
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'elif' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() != 0) {
                                printout += userStack.peek().getBlockComplexity().toString() + "    highest sub-complexity: ";
                                printout+= userStack.peek().getHighestSubComplexity().toString();
                            }
                            System.out.println(printout);
                        }else if (dataTrimArray[0].equals(CodeBlock.BLOCK_TYPES[4])){
                            String printout = "";
                            String blockWeAre = "";
                            printout+= "Entering block: ";
                            if (userStack.size() == 0){
                                blockWeAre += "1";
                            }else if (userStack.size() == 1){
                                blockWeAre+= "1.";
                                blockWeAre += userStack.size();
                            }else if (userStack.size() == 2){
                                blockWeAre+= "1.1.";
                                blockWeAre += userStack.size();
                            }else{
                                blockWeAre+= "1.1.1.";
                                blockWeAre+= userStack.size();
                            }
                            printout+= blockWeAre;
                            printout += " 'else' :";
                            printout += "\n";
                            printout+= "BLOCK " + blockWeAre + ":";
                            printout += "block complexity: ";
                            if (userStack.size() != 0) {
                                printout += userStack.peek().getBlockComplexity().toString() + "    highest sub-complexity:";
                                printout+= userStack.peek().getHighestSubComplexity().toString();
                            }
                            System.out.println(printout);
                        }
                        else {
                            Complexity otherComplexity = new Complexity();
                            CodeBlock otherCodeBlock = new CodeBlock();
                            otherCodeBlock.setName("otherCodeBlock");
                            Complexity highestSubComplexity = new Complexity(0,0);
                            otherCodeBlock.setHighestSubComplexity(highestSubComplexity);
                            otherCodeBlock.setBlockComplexity(otherComplexity);
                            userStack.push(otherCodeBlock);
                        }
                    }
                    /*
                    The else if part which will reach when it is in a while block is skipped because the while
                    has been deal in the previous part.
                     */

                    /*
                    Else, skip the line.
                     */
                    else{
                        continue;
                    }
                }
            }
            while (userStack.size()>1){
                CodeBlock oldTop = userStack.pop();
                Complexity oldTopComplexity = oldTop.getBlockComplexity();
                if (oldTopComplexity.getN_Power()> userStack.peek().getBlockComplexity().getN_Power()){
                    userStack.peek().setBlockComplexity(oldTopComplexity);
                }else if  (oldTopComplexity.getLog_power()> userStack.peek().getBlockComplexity().getLog_power()){
                    userStack.peek().setBlockComplexity(oldTopComplexity);
                }
            }
        } catch (InputMismatchException ime) {
            System.out.println("Input Mismatch!");
        }
        Complexity sub_1 = userStack.peek().getHighestSubComplexity();
        Complexity block_com_1 = userStack.peek().getBlockComplexity();
        Complexity totalComplexity = new Complexity(sub_1.getN_Power() + block_com_1.getN_Power(),
                sub_1.getLog_power()+ block_com_1.getLog_power());
        return totalComplexity;
    }

    /**
     * Method is written to simply read and print a python file.
     *
     * @param fileName user file
     * @throws FileNotFoundException file not found
     */
    public static void readAndPrint(String fileName) throws Exception {
        try {
            File newPyFile = new File(fileName);
            Scanner reader = new Scanner(newPyFile);
            System.out.println('\u0022');
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
        } catch (java.io.FileNotFoundException x) {
            System.out.println("File not found!");
        }
    }
}

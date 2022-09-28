import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class PythonTracer {
    public static final int SPACE_COUNT = 4;
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Input file name:");
        String userInputName = stdin.nextLine().trim();
    }

    public static Complexity traceFile(String filename) throws Exception {
        BlockStack userStack = new BlockStack();
        try {
            File newPyFile = new File(filename);
            Scanner reader = new Scanner(newPyFile);
            newPyFile.exists();
            newPyFile.canRead();
            while (reader.hasNextLine()){
                String data = reader.nextLine();
                if (!data.isEmpty() && data.trim().charAt(0)!='#'){
                    int indents;
                    for (indents = 0; indents < data.length(); indents++){
                        /*
                        Count the numbers of spaces of each line.
                         */
                        if (data.charAt(indents) == ' '){
                            indents++;
                        }else{
                            break;
                        }
                    }
                    while(indents < userStack.size()){
                        if (indents == 0){
                            newPyFile.delete();
                            return (Complexity) userStack.get(userStack.size());
                        }else{
                            CodeBlock oldTop = (CodeBlock) userStack.pop();
                            Complexity oldTopComplexity = oldTop.getBlockComplexity();
                            CodeBlock newTop = (CodeBlock) userStack.pop();
                            if (oldTopComplexity.getN_Power() > newTop.getBlockComplexity().getN_Power()){
                                newTop.setBlockComplexity(oldTopComplexity);
                            }
                            userStack.push(newTop);
                        }
                    }

                }
            }
        }catch (Exception e){
            throw new Exception();
        }
        return new Complexity();
    }

}

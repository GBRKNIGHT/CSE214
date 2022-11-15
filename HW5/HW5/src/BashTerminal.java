
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

import java.util.Scanner;
public class BashTerminal{
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Starting bash terminal.");
        DirectoryNode root = new DirectoryNode();
        root.setName("root");
        DirectoryTree tree = new DirectoryTree();
        tree.setRoot(root);
        tree.setRoot(root);
        tree.setCursor(root);

            while (true){try{
                String menuOption = stdin.nextLine().trim();
                String possibleCdPath = "";
                String possibleMkdirName = "";
                String possibleTouchName = "";
                String possibleFindName = "";
                String possibleMoveName = "";


                /*
                 If the input is a "cd ..".
                 */
                if (menuOption.charAt(0) == 'c' && menuOption.charAt(1) == 'd' &&
                        menuOption.charAt(2) == ' ' && menuOption.charAt(3) == '.' && menuOption.charAt(4) == '.'){
                    menuOption = "cd ..";
                }

                /*
                If the input is a "cd"
                 */

                else if (menuOption.charAt(0) == 'c' && menuOption.charAt(1) == 'd'){
                    for (int i = 3; i < menuOption.length(); i++){
                        possibleCdPath += menuOption.charAt(i);
                    }
                    menuOption = "regularCd";
                }



                /*
                If the input is a "mkdir"
                 */
                else if (menuOption.charAt(0) == 'm' && menuOption.charAt(1) == 'k' && menuOption.charAt(2) == 'd'
                        && menuOption.charAt(3) == 'i' && menuOption.charAt(4) == 'r' && menuOption.charAt(5) == ' '){
                    for (int i = 6; i < menuOption.length(); i++){
                        possibleMkdirName += menuOption.charAt(i);
                    }
                    menuOption = "mkdir";
                }

                /*
                If the input is a "find".
                 */
                else if (menuOption.charAt(0) == 'f' && menuOption.charAt(1) == 'i' && menuOption.charAt(2) == 'n'
                    && menuOption.charAt(3) == 'd' && menuOption.charAt(4) == ' '){
                    for (int i = 5; i < menuOption.length(); i++){
                        possibleFindName += menuOption.charAt(i);
                    }
                    menuOption = "find";
            }
                /*
                If the input is a "touch"
                 */
                else if (menuOption.charAt(0) == 't' && menuOption.charAt(1) == 'o' && menuOption.charAt(2) == 'u'
                        && menuOption.charAt(3) == 'c' && menuOption.charAt(4) == 'h' && menuOption.charAt(5) == ' '){
                    for (int i = 6; i < menuOption.length(); i++){
                        possibleTouchName += menuOption.charAt(i);
                    }
                    menuOption = "touch";
                }

                    /*
                    Switch as menu.
                     */
                    switch (menuOption){
                        case "pwd":
                            String printPwd = tree.presentWorkingDirectory();
                            System.out.println(printPwd);
                            break;
                        case "ls":
                            System.out.println(tree.listDirectory());
                            break;
                        case "ls -R":
                            tree.printDirectoryTree();
                            break;
                        case "cd ..":
                            tree.backToParent();
                            System.out.println("The cursor has been set to its parent successfully.");
                            break;
                        case "regularCd":
                            tree.changeDirectory(possibleCdPath);
                            break;
                        case "mkdir":
                            tree.makeDirectory(possibleMkdirName);
                            break;
                        case "touch":
                            tree.makeFile(possibleTouchName);
                            break;
                        case "exit":
                            System.out.println("Program terminating normally.");
                            System.exit(0);
                            break;
                        case "find":
                            tree.findNode(possibleFindName);
                            break;
                        default:
                            System.out.println("Unrecognized command");
                            break;
                    }
            }catch (IllegalArgumentException IAE ){
                System.out.println("Invalid input!");
            }
            catch (NotADirectoryException IADE){
                System.out.println("This path point to a file or directory not exist currently.");
            }
            catch (FullDirectoryException FDE){
                System.out.println("This node is full, cannot add more children.");
            }
            catch (ArrayIndexOutOfBoundsException AIOOBE){
                System.out.println(" Invalid input! ");
            }
            catch (StringIndexOutOfBoundsException BIOOBE){
                System.out.println(" Invalid input! ");
            }
            catch (NullPointerException NPE){
                System.out.println("null pointer");
            }
        }
    }
}

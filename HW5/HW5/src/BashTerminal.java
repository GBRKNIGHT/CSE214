import java.util.Scanner;
public class BashTerminal{
    public static void main(String[] args) throws NotADirectoryException, FullDirectoryException,
            IllegalArgumentException {
        Scanner stdin = new Scanner(System.in);
        System.out.println("Starting bash terminal.");
        DirectoryNode root = new DirectoryNode();
        root.setName("root");
        DirectoryTree tree = new DirectoryTree();
        tree.setRoot(root);
        String possibleCdPath = "";
        String possibleMkdirName = "";
        String possibleTouchName = "";
        tree.setRoot(root);
        tree.setCursor(root);


        while (true){
            String menuOption = stdin.nextLine().trim();
                    /*
        If the input is a "cd"
         */
            if (menuOption.charAt(0) == 'c' && menuOption.charAt(1) == 'd' &&
                    menuOption.charAt(2) == ' '){
                for (int i = 3; i < menuOption.length(); i++){
                    possibleCdPath += menuOption.charAt(i);
                }
                menuOption = "regularCd";
            }

        /*
        If the input is a "mkdir"
         */
            if (menuOption.charAt(0) == 'm' && menuOption.charAt(1) == 'k' && menuOption.charAt(2) == 'd'
                    && menuOption.charAt(3) == 'i' && menuOption.charAt(4) == 'r' && menuOption.charAt(5) == ' '){
                for (int i = 6; i < menuOption.length(); i++){
                    possibleMkdirName += menuOption.charAt(i);
                }
                menuOption = "mkdir";
            }

        /*
        If the input is a "touch"
         */
            if (menuOption.charAt(0) == 't' && menuOption.charAt(1) == 'o' && menuOption.charAt(2) == 'u'
                    && menuOption.charAt(3) == 'c' && menuOption.charAt(4) == 'h' && menuOption.charAt(5) == ' '){
                for (int i = 6; i < menuOption.length(); i++){
                    possibleTouchName += menuOption.charAt(i);
                }
                menuOption = "touch";
            }
            switch (menuOption){
                case "pwd":
                    String printPwd = tree.presentWorkingDirectory();
                    System.out.println(printPwd);
                    break;
                case "ls":
                    tree.printDirectoryTree();
                    break;
                case "ls -R":
                    tree.setCursor(tree.getRoot());
                    tree.printDirectoryTree();
                    break;
                case "regularCd":
                    tree.changeDirectory(possibleCdPath);
                    possibleCdPath = "";
                    break;
                case "mkdir":
                    tree.makeDirectory(possibleMkdirName);
                    possibleMkdirName = "";
                    break;
                case "touch":
                    tree.makeFile(possibleTouchName);
                    possibleTouchName = "";
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }
}

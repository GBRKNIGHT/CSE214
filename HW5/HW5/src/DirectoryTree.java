
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

public class DirectoryTree {
    /**
     * Requested data fields, include root and cursor.
     */
    private DirectoryNode root, cursor;
    private DirectoryNode[] recursiveFindArray = new DirectoryNode[10];

    public DirectoryNode getCursor() {
        return this.cursor;
    }

    public DirectoryNode getRoot() {
        return this.root;
    }

    public void setCursor(DirectoryNode cursor) {
        this.cursor = cursor;
    }

    public void setRoot(DirectoryNode root) {
        this.root = root;
    }

    /**
     * Default constructor.
     * The root variable is created after performing this.
     */
    public DirectoryTree(){
        DirectoryNode root =  new DirectoryNode();
        root.setName("root");
        this.setRoot(root);
        this.setCursor(root);
    }


    /**
     * Moves the cursor to the root node of the tree.
     */
    public void resetCursor(){
        this.cursor = this.root;
    }

    /**
     * Moves the cursor to the directory with the name indicated by name.
     * The directory can be split by "/".
     * @param name 'name' references a valid directory ('name' cannot reference a file).
     * @throws NotADirectoryException  Thrown if the node with the indicated name is a file,
     *                                 as files cannot be selected by the cursor, or cannot be found.
     */
    public void changeDirectory(String name) throws NotADirectoryException{
        name = name.trim();
        String[] nameSplit = name.split("/",-1);
        for (int i = 0; i < nameSplit.length && i < cursor.getChildren().length; i++){
            if (name.equals("root")) {
                resetCursor();
                System.out.println("The cursor has been set to root successfully.");
            }
            else if (name.equals("/")){
                resetCursor();
            }
            else if (isThisNameCursorChild(name) != -999){
                int indexOfMatchedChild = isThisNameCursorChild(name);
                cursor = cursor.getChild(indexOfMatchedChild);
            }
            else if(isThisNameCursorChild(nameSplit[i+1]) != -999){
                cursor = cursor.getChild(i);
                break;
            }
            else{
                this.resetCursor();
                System.out.println("Directory cannot be found!");
                throw new NotADirectoryException();
            }
        }
        if (this.cursor.getIsFile()){
            this.resetCursor();
            System.out.println("The node with the indicated name is a file!");
            throw new NotADirectoryException();
        }
    }


    /**
     * Returns a String containing the path of directory names from the root node of the tree to the cursor,
     * with each name separated by a forward slash "/".
     *
     * Find the directory from below to above, until it reach the root, root's parent must be null.
     * @return the directory in String.
     */
    public String presentWorkingDirectory() throws NotADirectoryException{
        String directory = "" + this.cursor.getName();
        DirectoryNode current = DirectoryNode.cloneNode(cursor);
        while(current.getParent()!= null){
            current = current.getParent();
            directory = current.getName() + "/" + directory;
        }
        return directory;
    }


    /**
     * Returns a String containing a space-separated list of names of all the child directories or files of the cursor.
     * @return
     */
    public String listDirectory(){
        boolean allNull = true;
        String children = "";
        for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++){
            if (cursor.getChild(i) != null){
                allNull = false;
                children += " ";
                children += cursor.getChild(i).getName();
            }
            else{
                break;
            }
        }
        if (allNull){
            return "No children in this cursor!";
        }else{
            return children;
        }
    }


    /**
     * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
     */
    public void printDirectoryTree(){
        recursive_print(0, cursor);
    }

    public void recursive_print(int indent, DirectoryNode current){
        String printLine = "";
        for (int i = 0; i<indent; i++){
            printLine += "    ";
        }
        if (current.getIsFile()){
            printLine += ("-  " + current.getName());
            System.out.println(printLine);
        }else{
            printLine += ( "|-  " + current.getName());
            System.out.println(printLine);
            indent += 1;
            for (int i = 0; current.getChild(i)!= null; i++){
                recursive_print(indent, current.getChild(i));
            }

        }
    }

    /**
     * Creates a directory with the indicated name and adds it to the children of the cursor node.
     * Remember that children of a node are added in left-to-right order.
     * @param name the given name
     * @throws IllegalArgumentException if include illegal arguments.
     * @throws FullDirectoryException if the cursor is currently full.
     * @throws NotADirectoryException if it is a file.
     */
    public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException,
            NotADirectoryException {
        for (int i = 0; i < name.length(); i++){
            if (name.charAt(i) == '/' || name.charAt(i) == ' '){
                System.out.println("Illegal input!");
                throw new IllegalArgumentException();
            }
        }
        DirectoryNode newNode = new DirectoryNode();
        newNode.setName(name);
        newNode.setIsFile(false); // Because a directory cannot be a file.
        if (cursor.isFull()){
            System.out.println("This cursor is current full. ");
            throw new FullDirectoryException();
        }else{
            newNode.setParent(cursor);
            cursor.addChild(newNode);
        }
    }


    /**
     * Creates a file with the indicated name and adds it to the children of the cursor node.
     * Remember that children of a node are added in left-to-right order.
     * @param name the given name
     * @throws IllegalArgumentException if include illegal arguments.
     * @throws FullDirectoryException if the cursor is currently full.
     * @throws NotADirectoryException if it is a file.
     */
    public void makeFile(String name) throws FullDirectoryException,
            IllegalArgumentException, NotADirectoryException {
        for (int i = 0; i < name.length(); i++){
            if (name.charAt(i) == '/' || name.charAt(i) == ' '){
                System.out.println("Illegal input!");
                throw new IllegalArgumentException();
            }
        }
        DirectoryNode newNode = new DirectoryNode();
        newNode.setName(name);
        newNode.setIsFile(true);
        if (cursor.isFull()){
            System.out.println("This cursor is current full. ");
            throw new FullDirectoryException();
        }else{
            newNode.setParent(cursor);
            cursor.addChild(newNode);
        }
    }


    /**
     * Extra find node method, intend to find the node in a specific path.
     * @param findingName input file or directory name.
     * @return The path of given match of the given name.
     * @throws NotADirectoryException
     */
    public void findNode(String findingName) throws NotADirectoryException{
        recursiveFind(findingName, this.root);
    }

    public void recursiveFind(String inputName, DirectoryNode node){
        if (node == null) return;
        else{
            if (stringEqual(node.getName(), inputName)){
                System.out.println(DirectoryNode.presentWorkingDirectory(node));
                //return;
            }

            if (node.getIsFile()) return;
            for (int i = 0; i < DirectoryNode.MAX_CHILDREN; i++){
                recursiveFind(inputName,node.getChild(i));
            }
        }
    }




    /**
     * Self defined equal method for 2 strings.
     * @param string1
     * @param string2
     * @return
     */
    public static boolean stringEqual(String string1, String string2){
        if (string1.length() != string2.length()) return false;
        else{
            for (int i = 0; i < string1.length(); i++){
                if (string1.charAt(i) != string2.charAt(i)){
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * find the name is cursor's child or not.
     * @param possibleChild possible name.
     * @return
     */
    public int isThisNameCursorChild(String possibleChild){
        for (int i = 0; i < cursor.getChildren().length; i++){
            if (cursor.getChild(i)!= null && stringEqual(cursor.getChild(i).getName(),possibleChild)){
                return i;
            }else if(cursor.getChild(i) == null){
                return -999;
            }
        }
        return -999;
    }


    /**
     * Method to pin cursor back to its parent.
     */
    public void backToParent(){
        if (this.cursor.getParent()!= null) {
            this.setCursor(cursor.getParent());
        }
    }

}

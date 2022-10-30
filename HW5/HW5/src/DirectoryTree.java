import java.util.logging.Level;

public class DirectoryTree {
    /**
     * Requested data fields, include root and cursor.
     */
    private DirectoryNode root, cursor;


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
        this.resetCursor();

        for (int i = 0; i < nameSplit.length; i++){
            if (name.equals("root")) {
                cursor = root;
            } else if((cursor.getChildren()[i] != null)&& (cursor.getChildren()[i].getName().equals(nameSplit[i]))){
                cursor = cursor.getChild(i);
            } else{
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
    public String presentWorkingDirectory(){
        String directory = "" + this.cursor.getName();
        while(this.cursor.getParent()!= null){
            this.setCursor(this.cursor.getParent());
            directory = this.cursor.getName() + "/" + directory;
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
        String printLine = "";
        String spaces = "";
        if (cursor.getIsFile()){
            printLine += (spaces + "-  " + cursor.getName());
            System.out.print(printLine);
        }else{
            printLine += (spaces +"|-  " + cursor.getName());
            System.out.print(printLine);
            printLine = "";
            spaces += "  ";
            String cursorListDirectory = this.listDirectory().trim();
            String[] cursorListDirectoryArray = cursorListDirectory.split(" ", DirectoryNode.MAX_CHILDREN);
            for (int i = 0; i < cursorListDirectoryArray.length; i++){
                System.out.println(spaces);
                printDirectoryTree();
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

    public String findNode(String findingName){
        this.resetCursor();
        if (cursor.getName().equals(findingName)){;
            return this.presentWorkingDirectory();
        }else{
            for (int i = 0; cursor.getChild(i)!= null; i++){
                if (cursor.getChild(i).getName().equals(findingName)){
                    cursor = cursor.getChild(i);
                    return this.presentWorkingDirectory();
                }
                else{
                    return findNode(findingName);
                }
            }
        }
        return null;
    }

}

public class DirectoryNode {
    /**
     * Given data fields.
     */
    private String name;
    /**
     * Because in order to earn the extra credit, we store 10 children as an array, instead of 10 different segments in
     * data fields. Actually, left, middle, right are just left here, not actually used.
     */
    private DirectoryNode left, middle, right;


    /**
     * customized data field, to store 10 children.
     */
    private DirectoryNode[] children = new DirectoryNode[10];

    public static final int MAX_CHILDREN = 10;
    private boolean isFile = false;

    /**
     * For convenience, we use another data parameter, which is called "parent", allow us to search the total tree
     * from down to up.
     */
    private DirectoryNode parent;


    /**
     * Getters and setters for all parameters.
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DirectoryNode getChild(int child){
        return this.children[child];
    }


    public DirectoryNode getLeft() {
        return this.left;
    }


    public DirectoryNode getMiddle() {
        return this.middle;
    }


    public DirectoryNode getRight() {
        return this.right;
    }


    /**
     * getter and setter for the extra parameter.
     * @param parent
     */
    public void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public DirectoryNode getParent() {
        return this.parent;
    }

    public DirectoryNode[] getChildren() {
        return this.children;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public boolean getIsFile() {
        return this.isFile;
    }

    /**
     * Default constructor
     * The left, middle and right are just nominal here.
     */
    public DirectoryNode(){
        this.left = children[0];
        this.middle = children[1];
        this.right = children[2];
    }


    /**
     * Customized constructor.
     * @param name its name
     * @param children self defined array for extra credit
     * @param parent self defined parameter for parent
     * @param isFile determine it is a file or not.
     * The left, middle and right are just nominal here.
     */
    public DirectoryNode (String name, DirectoryNode[] children, DirectoryNode parent, boolean isFile){
        this.children = children;
        this.parent = parent;
        this.name = name;
        this.isFile = isFile;
        this.left = children[0];
        this.middle = children[1];
        this.right = children[2];
    }


    /**
     * This method meant to add a children to this cursor.
     * @param newChild new children intended to add to this cursor.
     * @throws FullDirectoryException exception thrown if left, middle and right are not null currently.
     * @throws NotADirectoryException exception if this cursor is a file.
     */
    public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException{
        if (this.isFull()){
            System.out.println("This cursor directory is currently full!");
            throw new FullDirectoryException();
        }else{
            for (int i = 0; i < MAX_CHILDREN; i++){
                if(this.getChild(i) == null){
                    newChild.setParent(this);
                    this.getChildren()[i] = newChild;
                    break;
                }
            }
        }
    }


    /**
     * Extra isFull method, to detect whether 3 children of this node is already filled with something.
     * @return true if not full, false otherwise.
     */
    public boolean isFull() throws NotADirectoryException{
        boolean isFull = true;
        if (this.isFile){
            throw new NotADirectoryException();
        }
        for (int i = 0; i < MAX_CHILDREN; i++){
            if (this.getChild(i) == null){
                isFull = false;
                break;
            }
        }
        return isFull;
    }


    /**
     * Detect if this node is leaf or not. A file must be a leaf.
     * @return
     */
    public boolean isLeaf(){
        boolean result = true;
        if (this.isFile) {
            return result;
        }
        for (int i = 0; i < MAX_CHILDREN; i++){
            if (this.getChild(i) != null){
                result = false;
                return result;
            }
        }
        return result;
    }

}


/**
 * 2 exceptions defined in the addChild method.
 */
class FullDirectoryException extends Exception{

}
class NotADirectoryException extends Exception{

}

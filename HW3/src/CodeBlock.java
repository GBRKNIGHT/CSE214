public class CodeBlock {
    public static final String[] BLOCK_TYPES = {"def","for","while","if","else","elif"};
    public static final int DEF = 0, FOR = 1, WHILE = 2, IF = 3, ELIF = 4, ELSE = 5;


    private Complexity blockComplexity;
    private Complexity highestSubComplexity;

    private String name;
    private String loopVariable;

    public Complexity getBlockComplexity() {
        return blockComplexity;
    }
    public void setBlockComplexity(Complexity blockComplexity) {
        this.blockComplexity = blockComplexity;
    }

    public Complexity getHighestSubComplexity() {
        return highestSubComplexity;
    }
    public void setHighestSubComplexity(Complexity highestSubComplexity) {
        this.highestSubComplexity = highestSubComplexity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLoopVariable() {
        return loopVariable;
    }
    public void setLoopVariable(String loopVariable) {
        this.loopVariable = loopVariable;
    }

    public CodeBlock(String name, String loopVariable, Complexity blockComplexity, Complexity highestSubComplexity){
        this.name = name;
        this.loopVariable = loopVariable;
        this.blockComplexity = blockComplexity;
        this.highestSubComplexity = highestSubComplexity;
    }
}

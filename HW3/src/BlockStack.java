
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

import java.util.Stack;


public class BlockStack{
    Stack<CodeBlock> stack;
    Complexity overallHighestComplexity = new Complexity(0,0);
    public BlockStack()
    {
        this.stack = new Stack<>();
    }


    /**
     * Personalized push method, and trying to find the whether it is the currently highest complexity in the
     * stack or not.
     * @param cb
     */
    public void push (CodeBlock cb){
        if (this.isEmpty()){
            this.stack.push(cb);
            setOverallHighestComplexity(cb.getBlockComplexity());
        }else{
            this.stack.push(cb);
        }
    }

    public CodeBlock peek(){
        return this.stack.peek();
    }

    public CodeBlock pop() {
        return this.stack.pop();
    }

    public int size(){
        return stack.size();
    }

    public boolean isEmpty(){
        return this.size() == 0;
    }

    public void setOverallHighestComplexity(Complexity overallHighestComplexity){
        this.overallHighestComplexity = overallHighestComplexity;
    }

    public Complexity getOverallHighestComplexity() {
        return overallHighestComplexity;
    }

}

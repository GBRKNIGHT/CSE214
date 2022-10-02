
/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */


public class Complexity {
    private int n_Power;
    private int log_power;

    /**
     * Getters and setters inside the Complexity class.
     * @return
     */
    public int getN_Power() {
        return n_Power;
    }

    public void setN_Power(int n_Power) {
        this.n_Power = n_Power;
    }

    public int getLog_power() {
        return log_power;
    }

    public void setLog_power(int log_power) {
        this.log_power = log_power;
    }

    /*
    Default constructor
     */
    public Complexity(){
    }

    /*
    Constructor which has parameters
     */
    public Complexity (int n_Power, int log_power){
        this.n_Power = n_Power;
        this.log_power = log_power;
    }

    /*
    ToString method which can return the printed contents
     */
    @Override
    public String toString() {
        return "O(n^" + + n_Power +
                "*log(n)^" + log_power +")";
    }


}

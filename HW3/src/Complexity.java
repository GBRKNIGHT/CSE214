public class Complexity {
    private int n_Power;
    private int log_power;

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

    public Complexity (int n_Power, int log_power){
        this.n_Power = n_Power;
        this.log_power = log_power;
    }


    @Override
    public String toString() {
        return "O(^" + + n_Power +
                "*log(n)^" + log_power +")";
    }
}

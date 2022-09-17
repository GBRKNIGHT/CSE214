/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

public class ProductLoad {
    private String productName;
    private double weight;
    private double value;
    private boolean dangerous;


    /*
    The getters and setters of the data fields
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }

    public String getProductName() {
        return productName;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public ProductLoad(){
        this.productName = "Empty";
        this.value = 0;
        this.weight = 0;
        this.dangerous = false;
    } // default constructors, intend to create an empty car

    /*
    The constructor contain essential parameters
     */
    public ProductLoad(String productName, double weight, double value, boolean dangerous){
        this.productName = productName;
        this.value = value;
        this.weight = weight;
        this.dangerous = dangerous;
    }


}

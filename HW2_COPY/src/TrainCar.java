/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

public class TrainCar {
    private double length,weight;
    private ProductLoad loadReference;
    private double value;
    private double totalWeight;

    public double getLength() {
        return this.length;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getTotalWeight(){
        return this.weight + this.loadReference.getWeight();
    }

    public ProductLoad getLoadReference() {
        return this.loadReference;
    }

    public void setLoadReference(ProductLoad loadReference) {
        this.loadReference.setWeight(loadReference.getWeight());
        this.loadReference.setValue(loadReference.getValue());
        this.loadReference.setDangerous(loadReference.isDangerous());
        this.loadReference.setProductName(loadReference.getProductName());
    }

    public TrainCar(){} // default constructor

    /*
    The constructor for given parameters
     */
    public TrainCar(double length, double weight, ProductLoad loadReference){
        this.length = length;
        this.weight = weight;
        this.loadReference = loadReference;
    }

    public static boolean isEmpty(TrainCar x){
        if (x == null){
            return true;
        }else{
            return false;
        }
    }

    public TrainCar clone(){
        TrainCar clonedCar = new TrainCar(this.length,this.weight,this.loadReference);
        return clonedCar;
    }


    public void setTotalWeight(ProductLoad product) {
        this.totalWeight = this.weight + product.getWeight();
    }

    public void setValue(ProductLoad product) {
        this.value = this.value + product.getValue();
    }
}

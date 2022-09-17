/**
 *  Name: Yichen Li
 *  SBU ID: 112946979
 *  Recitation: R02
 */

public class TrainCarNode {
    /**
     * Data fields for the previous, next and thisCar.
     */
    private TrainCarNode previousTCN;
    private TrainCarNode nextTCN;
    private TrainCar thisCar;


    public TrainCar getThisCar() {
        return thisCar;
    }

    public void setThisCar(TrainCar thisCar) {
        this.thisCar = thisCar;
    }

    public TrainCarNode getNextTCN() {
        return nextTCN;
    }

    public void setNextTCN(TrainCarNode nextTCN) {
        this.nextTCN = nextTCN;
    }

    public TrainCarNode getPreviousTCN() {
        return previousTCN;
    }

    public void setPreviousTCN(TrainCarNode previousTCN) {
        this.previousTCN = previousTCN;
    }

    public TrainCarNode (){} // default constructor

    /*
    The constructor contains essential parameters.
     */
    public TrainCarNode(TrainCarNode previousTCN, TrainCarNode nextTCN, TrainCar thisCar){
        this.previousTCN = previousTCN;
        this.nextTCN = nextTCN;
        this.thisCar = thisCar;
    }

    /**
     * Methods to compare two TrainCarNodes.
     * @param obj Abstract data type
     * @return boolean
     */
    public boolean equals(Object obj){
        boolean result = true;
        if (!(obj instanceof TrainCarNode)){
            //Error because the referred object is not a Train Car Node
            result = false;
        }else{
            TrainCarNode temp = (TrainCarNode) obj;
            //Error because Train Car Nodes are not same
            if ((!temp.getThisCar().getLoadReference().getProductName().equals(this.thisCar.getLoadReference().getProductName())
            )||(temp.getThisCar().getLoadReference().getWeight()!= this.thisCar.getLoadReference().getWeight())||
                    temp.getThisCar().getLoadReference().getValue()!= this.thisCar.getLoadReference().getValue()||
            temp.getThisCar().getLength()!= this.thisCar.getLength()||
            temp.getThisCar().getWeight()!= this.thisCar.getWeight()){
                result = false;
            }
        }
        return (result);
    }


}

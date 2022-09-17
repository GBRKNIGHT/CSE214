public class TestTrainCar {
    public static void main(String[] args) {
        ProductLoad milk = new ProductLoad("Milk", 18,19,false);
        TrainCar car1 = new TrainCar();
        TrainCar car2 = new TrainCar(14,14,milk);
        TrainLinkedList x = new TrainLinkedList();
        x.printManifest();
    }
}

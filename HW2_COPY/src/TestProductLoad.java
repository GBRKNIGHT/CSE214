import java.util.Scanner;
public class TestProductLoad {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        ProductLoad water = new ProductLoad();
        ProductLoad milk = new ProductLoad("Milk", 10,10, false);
        ProductLoad gasoline = new ProductLoad("Gas", 20,20,true);
        milk.setProductName("2% milk");
//        System.out.println(milk.getProductName());
        System.out.println("Please enter the product name");
        String productName = stdin.nextLine();
        System.out.println("Please enter the weight.");
        String weightString = stdin.nextLine();
        double weight = Double.parseDouble(weightString);
        System.out.println("Please enter the value.");
        String valueString = stdin.nextLine();
        double value = Double.parseDouble(valueString);
        System.out.println("Is this dangerous?");
        String dangerousString = stdin.nextLine();
        boolean dangerous = Boolean.valueOf(dangerousString);
        ProductLoad userProduct = new ProductLoad(productName,weight,value,dangerous);
        System.out.println(userProduct.getValue());
    }
}

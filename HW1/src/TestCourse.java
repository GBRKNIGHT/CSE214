/**
 * THIS IS A SELF-TEST FILE, in order to simplify the testing procedures.
 * Please ignore and use PlannerManager.java to perform testing.
 * //Name: Yichen Li
 * //SBU ID: 112946979
 * //Recitation: R02
 */



import java.util.Scanner;
public class TestCourse extends Course{
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        Course a = new Course();
        Course b = new Course("AMS210", "AMS", 210, (byte) 1, "Alan Tucker");
        Course bClone = (Course) b.clone();
        String x = "testCase";
        System.out.println(bClone.equals(x));

    }
}

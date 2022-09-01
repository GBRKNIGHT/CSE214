/**
 * THIS IS A SELF-TEST FILE, in order to simplify the testing procedures.
 * Please ignore and use PlannerManager.java to perform testing.
 * //Name: Yichen Li
 * //SBU ID: 112946979
 * //Recitation: R02
 */

public class TestPlanner {

    public static void main(String[] args) {
        try {
            Course[] testCases = new Course[50];
            Course cse214 = new Course("Data Structures","CSE",
                    214, (byte)1,"Ahmad Esmaili" );
            Course ams210 = new Course("Linear Algebra", "AMS",
                    210,(byte)2,"Alan Tucker");
            Course mus119 = new Course("Elements of Music", "MUS",
                    119,(byte)2,"Taylor Ackley");
            Course phy131 = new Course("Classical Physics I", "PHY",131,
                    (byte)1, "Thomas Hemmick");
            Course cse220 = new Course("System Fundamentals", "CSE",
                    220,(byte)1,"Kevin McDonnell");
            Course cse214Clone = (Course) cse214.clone();
            testCases[0] = cse214;
            testCases[1] = ams210;
            testCases[2] = cse220;
            Planner testAll = new Planner(testCases);
            System.out.println(testAll.exists(cse214Clone));
            testAll.addCourse(mus119, 1);
            testAll.addCourse(phy131,3);
            testAll.printAllCourses();
        }
        catch (FullPlannerException fpe){
            System.out.println(" There is no more room in the Planner to record an additional Course.");
        }
        catch (IllegalArgumentException iae){
            System.out.println(" Position is not within the valid range.");
        }
    }
}

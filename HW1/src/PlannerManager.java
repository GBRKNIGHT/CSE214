    /**Name: Yichen Li
     * //SBU ID: 112946979
     * //Recitation: R02
     */


import java.util.Scanner;


public class PlannerManager {
    public static void main(String[] args) throws FullPlannerException, IllegalArgumentException {
        /**
         * Below are written testCases, if you want to test without typing, please consider to use this, if so, please change
         * line 49 (new Planner() to testAll).
         */
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
        testCases[0] = cse214;
        testCases[1] = ams210;
        testCases[2] = cse220;
        testCases[3] = mus119;
        testCases[4] = phy131;;
        Planner testAll = new Planner(testCases);
        Planner backup = new Planner();

        /**
        Menu, only will show once as requested.
         */
        System.out.println("(A) Add Course\n" +
                "(G) Get Course\n" +
                "(R) Remove Course\n" +
                "(P) Print Courses in Planner\n" +
                "(F) Filter by Department Code\n" +
                "(L) Look For Course\n" +
                "(S) Size\n" +
                "(B) Backup\n" +
                "(PB) Print Courses in Backup\n" +
                "(RB) Revert to Backup\n" +
                "(Q) Quit");
        Planner userPlanner = new Planner();   // import pre-written testcases
        Scanner stdin = new Scanner(System.in).useDelimiter("\n");


        /**
         * The loop will end if and only if forced stop or the user entered "Q".
         */
        while (true){
            System.out.println("Enter a selection:");
            String selection = stdin.nextLine();
            selection = selection.toUpperCase(); // change the input to uppercase
            System.out.println(selection);


            switch (selection){
                /**
                 * A - Add Course <Name> <Code> <Section> <Instructor> <Position>
                 * Adds a new course into the list.
                 */
                case "A":
                    System.out.println("Enter course name: ");
                    String name = stdin.nextLine();
                    System.out.println("Enter department: ");
                    String department = stdin.nextLine();
                    department= department.toUpperCase();
                    System.out.println("Enter course code: ");
                    int courseCode = Integer.valueOf(stdin.nextLine());
                    System.out.println("Enter course section: ");
                    byte section = (byte)(int)Integer.valueOf(stdin.nextLine()); //cast to byte
                    System.out.println("Enter instructor: ");
                    String instructor = stdin.nextLine();
                    System.out.println("Enter position: ");
                    int position = Integer.valueOf(stdin.nextInt());
                    /**
                     * Use for out-of-range error handling.
                     */
                    try{
                        if (position < 1 || position > 50){
                            throw new IllegalArgumentException();
                        }
                    }catch (IllegalArgumentException i ){
                        System.out.println("IllegalArgumentException\n" +
                                "Indicates that position is not within the valid range.");
                        break;
                    }
                    Course newCourse = new Course(name,department,courseCode,section,instructor);
                    userPlanner.addCourse(newCourse,position);
                    System.out.println(newCourse.getDepartment() + " " + newCourse.getCode() + ".0" +
                            newCourse.getSection() + " successfully added to planner.");
                    break;

                /**
                 * G - Get Course <Position>
                 * Displays information of a Course at the given position.
                 */
                case "G":
                    System.out.print("Enter position: ");
                    int positionG = stdin.nextInt();
                    /**
                     * Use for out-of-range error handling.
                     */
                    try{
                        if (positionG < 1 || positionG > 50){
                            throw new IllegalArgumentException();
                        }
                    }catch (IllegalArgumentException i ){
                        System.out.println("IllegalArgumentException\n" +
                                "Indicates that position is not within the valid range.");
                        break;
                    }
                    if (positionG > userPlanner.size()){
                        System.out.println("The entered position is currently no course.");
                        break;
                    }else{
                        positionG--;
                        String result = String.format("%3s %-25s %-10s %-4s %-7s %-10s \n", "No.",
                                "Course Name","Department","Code","Section","Instructor");
                        result+= "-------------------------------------------------------------------------\n";
                        result+= userPlanner.courseToString(positionG);
                        System.out.println(result);
                        break;
                    }


                /** R - Remove Course <Position>
                 * Removes the Course at the given position.
                 */
                case "R": //- Remove Course <Position>. Removes the Course at the given position.
                    System.out.print("Enter position: ");
                    int positionR = stdin.nextInt();

                    /**
                     * Use for out-of-range error handling.
                     */
                    try{
                        if (positionR < 1 || positionR > 50){
                            throw new IllegalArgumentException();
                        }
                    }catch (IllegalArgumentException i ){
                        System.out.println("IllegalArgumentException\n" +
                                "Indicates that position is not within the valid range.");
                        break;
                    }
                    if (positionR > userPlanner.size()){
                        System.out.println("The entered position is currently no course.");
                        break;
                    }else{
                        System.out.println(userPlanner.getCourse(positionR).getDepartment() + " "
                                + userPlanner.getCourse(positionR).getCode() + ".0" +
                                userPlanner.getCourse(positionR).getSection() +
                                " has been successfully removed from the planner.");
                        userPlanner.removeCourse(positionR);
                    }
                    break;


                /** P - Print Courses in Planner
                 * Displays all courses in the list.
                 */
                case "P":
                    userPlanner.printAllCourses();
                    break;


                /** F - Filter By Department Code <Code>
                 * Displays courses that match the given department code.
                 */
                case "F" : //- Filter By Department Code <Code>
                    // Display courses that match the given department code.
                    System.out.print("Enter department code:");
                    String departmentF = stdin.next();
                    departmentF = departmentF.toUpperCase();
                    System.out.println(departmentF);
                    Planner.filter(userPlanner,departmentF);
                    break;


                /** L - Look For Course <Name> <Code> <Section> <Instructor>
                 *  Determines whether the course with the given attributes is in the list.
                 */
                case "L":
                    System.out.println("Enter course name: ");
                    String nameL = stdin.nextLine();
                    System.out.println("Enter department: ");
                    String departmentL = stdin.nextLine();
                    departmentL = departmentL.toUpperCase();
                    System.out.println("Enter course code: ");
                    int courseCodeL = Integer.valueOf(stdin.nextLine());
                    System.out.println("Enter course section: ");
                    byte sectionL = (byte)(int)Integer.valueOf(stdin.nextLine()); //cast to byte
                    System.out.println("Enter instructor: ");
                    String instructorL = stdin.nextLine();
                    Course userLookUp = new Course(nameL, departmentL, courseCodeL, sectionL, instructorL);
                    if (!userPlanner.exists(userLookUp)){
                        System.out.println(userLookUp.getDepartment() + " " + userLookUp.getCode() + ".0" +
                                userLookUp.getSection() + " is currently not in the planner.");
                    }else {
                        int positionL = 0;
                        for (int i = 0; i < userPlanner.size(); i++) {
                            if (!userLookUp.equals(userPlanner.getCourse(i + 1))) {
                                positionL++;
                            }else {System.out.println(userLookUp.getDepartment() + " " + userLookUp.getCode() + ".0" +
                                        userLookUp.getSection() + " is found in the planner at position " +
                                        (positionL + 1));
                                break;
                            }
                        }
                    }


                /**
                 * S - Size
                 * Determines the number of courses in the Planner.
                 */
                case "S":
                    System.out.println("There are "+ userPlanner.size() + " courses in the planner.");
                    break;

                /**
                 * B - Backup
                 * Creates a copy of the given Planner.
                 * Changes to the copy will not affect the original and vice versa.
                 */
                case "B":
                    backup = (Planner) userPlanner.clone();
                    System.out.println("Created a backup of the current planner.");


                /**
                 * PB - Print Courses in Backup
                 * Displays all the courses from the backed-up list.
                 */
                case "PB":
                    if (backup.getCourse(1) == null){
                        System.out.println("The backup is not exist currently. \nPlease make a clone the " +
                                "current planner at first.");
                    }else{
                        backup.printAllCourses();
                        break;
                    }


                /**
                 * RB - Revert to Backup
                 * Reverts the current Planner to the backed up copy.
                 */
                case "RB":
                    userPlanner = backup;
                    break;


                /**
                 * Q - Quit
                 * Terminates the program.
                 */
                case "Q":
                    System.out.println("Program terminating successfully...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("There is no such choice in the menu");
                    break;
            }
        }
    }
}

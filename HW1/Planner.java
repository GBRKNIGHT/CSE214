/**Name: Yichen Li
 * //SBU ID: 112946979
 * //Recitation: R02
 */


public class Planner extends Course{
    public final int MAX_COURSES = 50; //read-only max courses
    private Course[] list = new Course[MAX_COURSES];



    /**
     * Constructs an instance of the Planner with no Course objects in it (default).
     */
    public Planner(){
    }


    /**
     * Constructor for pre-defined testcases
     * @param testCase
     */
    public Planner(Course[] testCase){
        for (int i = 0; i < testCase.length; i++){
            if (testCase[i] == null){
                break;
            }
            this.list[i] = new Course(testCase[i].getName(), testCase[i].getDepartment(), testCase[i].getCode()
            ,testCase[i].getSection(),testCase[i].getInstructor());
        }
    }


    /**
     * Determines the number of courses currently in the list.
     * Preconditions:
     * This Planner has been instantiated.
     * @return
     * The number of Courses in this Planner.
     */
    public int size(){
        int n = 0;
        for (int i = 0; i < MAX_COURSES; i++){
            if (this.list[i] == null){
                break;
            }
            n++;
        }
        return n ;
    }


    /**
     * Add a Course given a new Course and its position
     * Parameters:
     * @param newCourse - the new course to add to the list
     * @param position - the position (preference) of this course on the list
     * Preconditions:
     * This Course object has been instantiated and 1 ≤ position ≤ items_currently_in_list + 1.
     *                 The number of Course objects in this Planner is less than MAX_COURSES.
     * Postconditions:
     * The new Course is now listed in the correct preference on the list.
     *  All Courses that were originally greater than or equal to position are moved back one position.
     * Throws:
     * @exception  IllegalArgumentException
     * Indicates that position is not within the valid range.
     * @exception  FullPlannerException
     * Indicates that there is no more room in the Planner to record an additional Course.
     */
    public void addCourse(Course newCourse, int position) throws IllegalArgumentException, FullPlannerException{
        position = position-1;
        if (position > MAX_COURSES){
            throw new FullPlannerException();
        }else{
            if (position < 0 || position > this.size()){
                throw new IllegalArgumentException();
               // System.out.println("IllegalArgumentException\n Indicates that position is not within the valid range.");
            }else{
                for (int i = MAX_COURSES-1 ; i > position; i--){
                    list[i] = list[i-1];
                }
                list[position] = newCourse;
            }
        }
    }


    /**
     * Works just like public void addCourse (Course newCourse, int position), except adds to the end of the list.
     * @param newCourse
     * @throws FullPlannerException
     * @throws IllegalArgumentException
     */
    public void addCourse(Course newCourse) throws FullPlannerException, IllegalArgumentException{
        this.addCourse(newCourse, size() );
    }


    /**
     * Remove a Course given the position
     Preconditions:This Planner has been instantiated and 1 ≤ position ≤ items_currently_in_list.
     Postconditions:The Course at the desired position has been removed.
     All Courses that were originally greater than or equal to position are moved backward one position.
     * @Throws IllegalArgumentException
     * @param position - the position in the Planner where the Course will be removed from.
     */
    public void removeCourse(int position) throws IllegalArgumentException {
        if (position < 1 || position > this.size()+1){
            throw new IllegalArgumentException();
        }
        for (int i = position-1; i < MAX_COURSES-1; i++){
            if (list[i+1] == null) {
                list[i] = null;
                break;
            }
            Course nextCourse = list[i+1];
            String name = nextCourse.getName();
            String department = nextCourse.getDepartment();
            int code = nextCourse.getCode();
            byte section = nextCourse.getSection();
            String instructor = nextCourse.getInstructor();
            this.list[i] = new Course(name,department,code,section,instructor);
        }
    }


    /**
     * @param position - position of the Course to retrieve.
     * @return  The Course at the specified position in this Planner object.
     * Preconditions:
     * The Planner object has been instantiated and 1 ≤ position ≤ items_currenyly_in_list.
     * @throws IllegalArgumentException
     */
    public Course getCourse(int position) throws IllegalArgumentException {
        if (position < 1 || position > this.size()+1){
            throw new IllegalArgumentException();
        }
        return list[position-1];
    }


    /**
     * Prints all Courses that are within the specified department.
     * @param planner - the list of courses to search in
     * @param department - the 3 letter department code for a Course
     * Preconditions:
     * This Planner object has been instantiated.
     * Postconditions:
     * Displays a neatly formatted table of each course filtered from the Planner.
     * Keep the preference numbers the same.
     */
    public static void filter(Planner planner, String department){
        System.out.printf("%3s %-25s %-10s %-4s %-7s %-10s \n", "No.",
                "Course Name","Department","Code","Section","Instructor");
        System.out.println("------------------------------------------------------------------");
        for (int i = 0; i < planner.MAX_COURSES; i++){
            if (planner.list[i] == null){
                System.out.println("The requested Courses has been printed above.");
                break; //print until last course
            }
            if(!planner.list[i].getDepartment().equals(department)){
                continue;//skip if the department is not same
            }else{
                System.out.printf("%3d %-25s %-10s %-4d %02d      %-10s \n",
                        i+1, planner.list[i].getName(), planner.list[i].getDepartment(),
                        planner.list[i].getCode(), planner.list[i].getSection(), planner.list[i].getInstructor());
            }
        }
    }


    /**
     * Checks whether a certain Course is already in the list.
     * Preconditions: This Planner and Course has both been instantiated.
     * @param course -the Course we are looking for
     * @return True if the Planner contains this Course, false otherwise.
     */
    public boolean exists(Course course){
        for (int i = 0; i < MAX_COURSES; i++){
            if (course.equals(this.list[i])){
                return true;
                //change the result to true if the course is found in the planner
            }else if(this.list[i] == null){
                break;
            }
        }
        return false;
    }


    /**
     * Creates a copy of this Planner. Subsequent changes to the copy will not affect the original and vice versa.
     * Preconditions This Planner object has been instantiated.
     * @return A copy (backup) of this Planner object.
     */
    public Object clone(){
        Course[] cloneCourseSchedule = new Course[MAX_COURSES];
        for (int i = 0; i < MAX_COURSES; i++){
            if (this.list[i] == null) break;
            cloneCourseSchedule[i] = this.list[i];
        }
        Object clonePlanner = new Planner(cloneCourseSchedule);
        return (Planner)clonePlanner;
    }


    /**
     * Simply use the toString method below to print the String representation of the current planner.
     */
    public void printAllCourses(){
        String result = this.toString();
        System.out.println(result);
    }


    /**
     * Gets the String representation of this Planner object, which is a neatly formatted table of each Course
     * in the Planner on its own line with its position number as shown in the sample output.
     * @return The String representation of this Planner object.
     */
    public String toString(){
        String result = String.format("%3s %-25s %-10s %-4s %-7s %-10s \n", "No.",
                "Course Name","Department","Code","Section","Instructor");
        result+= "----------------------------------------------------------------------\n";
        for (int i = 0; i < this.size(); i++){
            result += String.format("%3d %-25s %-10s %-4d %02d      %-10s \n",
                    i+1, this.list[i].getName(), this.list[i].getDepartment(),
                    this.list[i].getCode(), this.list[i].getSection(), this.list[i].getInstructor());
        }
        return result;
    }


    public String courseToString(int inputPosition){
        String result = "";
        result += String.format("%3d %-25s %-10s %-4d %02d      %-10s \n",
                inputPosition+1, this.list[inputPosition].getName(), this.list[inputPosition].getDepartment(),
                this.list[inputPosition].getCode(), this.list[inputPosition].getSection(),
                this.list[inputPosition].getInstructor());
        return result;
    }
}


class FullPlannerException extends Exception {
    public static void main(String[] args){
        System.out.println("FullPlannerExpcetion\n" +
                "Indicates that there is no more room in the Planner to record an additional Course.");
    }
}


class IllegalArgumentException extends Exception {
    public static void main(String[] args){
        System.out.println("IllegalArgumentException\n" +
                "Indicates that position is not within the valid range.");
    }
}

/**Name: Yichen Li
 * //SBU ID: 112946979
 * //Recitation: R02
 */


public class Course {
    /**
     * private data fields
     */
    private String name;
    private String department;
    private int code;
    private byte section;
    private String instructor;



    int printTime = 0;


    public Course(){
        //default constructor
    }


    /**
     * constructor for complete information
     */
    public Course(String name, String department, int code, byte section, String instructor){
        this.name = name;
        this.department = department;
        this.code = code;
        this.section = section;
        this.instructor = instructor;
    }

    /**
     getter/setter methods for each variable
     @return
     */

    public String getName(){
        return this.name;
    }


    public void setName(String name){
        this.name = name;
    }


    public String getDepartment(){
        return this.department;
    }


    public void setDepartment(String department){
        this.department = department;
    }


    public int getCode(){
        return this.code;
    }


    public void setCode(int code){
        this.code = code;
    }


    public byte getSection(){
        return this.section;
    }


    public void setSection(byte section){
        this.section = section;
    }


    public String getInstructor(){
        return this.instructor;
    }


    public void setInstructor(String instructor){
        this.instructor = instructor;
    }


    /**
     the return value is a copy of this Course.
     Subsequent changes to the copy will not affect the original and vice versa.
     Note that the return value must be typecasted to a Course before it can be used.
     */
    public Object clone(){
        Object cloneCourse = new Course(this.name, this.department, this.code, this.section, this.instructor);
        return (Course) cloneCourse;
    }


    /**
     *
     * @param obj
     * a return value of true indicates that obj refers to a Course object with the same attributes as this Course.
     * Otherwise, the return value is false.
     * @return
     */
    public boolean equals(Object obj){
        boolean result = true;
        if (!(obj instanceof Course)){
            //Error because the referred object is not a course
            result = false;
        }else{
            Course temp = (Course) obj;
            //Error because the classes are not same
            if (!temp.getName().equalsIgnoreCase(this.getName())||
                    !temp.getDepartment().equalsIgnoreCase(this.getDepartment())||
                    temp.getCode() != this.getCode()|| temp.getSection() != this.getSection()||
                    !temp.getInstructor().equalsIgnoreCase(this.getInstructor())){
                result = false;
            }
        }
        return (result);
    }

    /**
     *this method can print a specific class
     */
    public void printCourse(){
        printTime++;
        System.out.printf("%3d %-25s %-10s %-4d %02d      %-10s\n",
                this.printTime, this.getName(), this.getDepartment(),
                this.getCode(), this.getSection(), this.getInstructor());
    }
}





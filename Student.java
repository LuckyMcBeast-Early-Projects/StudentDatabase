
import java.util.ArrayList;
import java.util.Random;

public class Student {

    private ArrayList<String> courses; //Course Enrolled
    private String id;//Student ID
    private int balance;//Tuition Balance
    private String name;//Student's Name
    public String[] courseList = {"History 101", "Mathematics 101", //List of Available Courses
            "English 101", "Chemistry 101", "Computer Science 101"};

    //Constructor
    public Student(String studentName, String gradeLevel) {
        name = studentName;
        courses = new ArrayList<String>();
        id = generateID(gradeLevel);
    }

    //ID Generation
    public String generateID(String gradeLevel) {
        String newID = "";
        do {
            newID = createID(gradeLevel);
        } while (!isIDUnique(newID, StudentDatabase.students) //Insures ID is Unique within database
                || Integer.parseInt(newID) > 99999); //Prevents number from going over 5 digits
        return newID;
    }

    public String createID(String gradeLevel) {
        Random rand = new Random();
        int suffix = rand.nextInt(999) + 100;
        return (gradeLevel + suffix);
    }

    public boolean isIDUnique(String id, ArrayList<Student> students) {
        for (Student person : students) {
            if (person.getId().equals(id))
                return false;
        }
        return true;
    }

    //Courses
    public void addCourse(int index) {
        try {
            this.courses.add(this.courseList[index]);
            this.balance = this.balance + 600;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Out of Bounds, Choose Course from List");
        }
    }

    public boolean isEnrolledInCourse(String course) {
        for (String c : this.courses) {
            if (course.equals(c)) {
                return true;
            }
        }
        return false;
    }

    //Getters and Setters

    public ArrayList<String> getCourses() {
        return courses;
    }

    public String getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }
}

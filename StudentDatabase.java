import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author M. Cullen McClellan
 *
 * Written in Java 11. I don't believe there are any issues related to the minor differences.
 * Runs fine and meets requirements. Allows the overpayment of balances. I left this because
 * I thought it was more of feature than a bug.  It allows to students to pay for their courses
 * ahead of time. I used a lot of Strings in this assignments, primarily because they seem to be
 * easier on the Scanner utility and are prone to less errors. I wrote my out println and print
 * methods, because I believe typing System.out all the time is crazy. I didn't include any
 * traditional for statements. I didn't have the need for them, but I definitely know how to use
 * them. I had a lot of fun with this project.
 */
public class StudentDatabase {

    public static ArrayList<Student> students = new ArrayList<>();//Data Storage Vessel (Chose ArrayList because Mutable)
    public static Scanner scan = new Scanner(System.in); //User Input

    //Execution
    public static void main(String[] args) {
        switchMenu();
    }

    //Menu Text
    public static void menu() {
        println("");
        println("Welcome the Cullen University Student Database \n" +
                "\n" +
                "++++++Options++++++\n" +
                "1 : Add Students to Database\n" +
                "2 : Student Status\n" +
                "3 : View Balance/Pay Tuition\n" +
                "4 : Enroll in Courses\n" +
                "5 : View All Students\n" +
                "6 : Exit");
    }

    //User Menu
    public static void switchMenu() {
        menu();
        do {
            switch (scan.nextLine().replaceAll(" ", "")) {
                case "1":
                    addStudentsToDatabase();
                    menu();
                    break;
                case "2":
                    showStudentStatus();
                    menu();
                    break;
                case "3":
                    viewAndPayBalance();
                    menu();
                    break;
                case "4":
                    enrollInCourses();
                    menu();
                    break;
                case "5":
                    viewAllStudents();
                    menu();
                    break;
                case "6":
                    System.exit(0);
                    break;
                default:
                    println("Invalid Input");
                    break;
            }
        } while (!scan.hasNext("6"));
    }

    //Adding Students to Database
    public static void addStudentsToDatabase() {
        try {
            print("How many student would you like to add? ");
            int numberOfStudents = Integer.parseInt(scan.nextLine());
            while (numberOfStudents > 0) {
                addStudent();
                numberOfStudents--;
            }
        } catch (NumberFormatException ex) {
            println("Invalid Input: Not an Integer.");
        }
    }

    public static void addStudent() {
        print("Provide Student Name: ");
        String name = scan.nextLine();
        boolean valid = false;
        String gradeYear = "";
        do {
            print("Provide Student Grade Number \n" +
                    "[Freshman : 13, Sophomore : 14, Junior : 15, Senior : 16]: ");
            gradeYear = scan.nextLine();
            if (gradeYear.equals("13") || gradeYear.equals("14")
                    || gradeYear.equals("15") || gradeYear.equals("16"))
                valid = true;
            else {
                println("Invalid Grade.");
            }
        } while (!valid);
        Student newStu = new Student(name, gradeYear);
        students.add(newStu);
        println("Student added. New StudentID: " + newStu.getId());
    }

    //Course Enrollment
    public static void enrollInCourses() {
        try {
            Student needsCourse = students.get(findStudentByID(userInputID()));
            print("How many courses would you like to add? ");
            int numberOfCourses = Integer.parseInt(scan.nextLine());
            while (numberOfCourses > 0) {
                println("Choose from the following courses:\n" +
                        "0 : History 101 \n" +
                        "1 : Mathematics 101 \n" +
                        "2 : English 101 \n" +
                        "3 : Chemistry 101\n" +
                        "4 : Computer Science 101 ");
                int courseIndex = Integer.parseInt(scan.nextLine());
                if (needsCourse.isEnrolledInCourse(needsCourse.courseList[courseIndex])) {
                    println("Cannot enroll in the same course more than once." +
                            "\nEnter \"n\" to Exit or Enter to continue.");
                    if ((scan.nextLine()).toLowerCase().equals("n")) {
                        break;
                    } else {
                        continue;
                    }
                } else {
                    needsCourse.addCourse(courseIndex);
                    numberOfCourses--;
                }
            }
        } catch (Exception ex) {
            println("Input Error Occurred.\n" +
                    "If courses were added before the error, they were recorded.");
        }
    }

    //Student Statuses
    public static void studentStatus(Student st) {
        println("Name: " + st.getName());
        println("StudentID: " + st.getId());
        println("Courses: " + st.getCourses().toString());
        println("Balance: $" + st.getBalance());
    }

    public static void showStudentStatus() {
        try {
            studentStatus(students.get(findStudentByID(userInputID())));
        } catch (IndexOutOfBoundsException ex) {
            println("ID Not Found. View All Students to Retrieve Correct ID.");
        }
    }

    public static void viewAllStudents() {
        for (Student stu : students) {
            studentStatus(stu);
            println("");
        }
    }

    //View and Pay Tuition Balances
    public static void viewAndPayBalance() {
        try {
            Student needsBalance = students.get(findStudentByID(userInputID()));
            println("Tuition Balance: $" + needsBalance.getBalance());
            print("Pay Tuition? (y/n) ");
            if ((scan.nextLine()).toLowerCase().equals("y")) {
                try {
                    print("Payment Amount: ");
                    int payment = Integer.parseInt(scan.nextLine());
                    needsBalance.setBalance(needsBalance.getBalance() - payment);
                    println("New Balance: " + needsBalance.getBalance());
                } catch (Exception ex) {
                    println("Please only provide integers. Letters and Decimals not accepted as payment.");
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            println("ID Not Found. View All Students to Retrieve Correct ID.");
        }
    }

    //ID Matching
    public static int findStudentByID(String id) {
        for (Student stu : students) {
            if (stu.getId().equals(id))
                return students.indexOf(stu);
        }
        return -1;
    }

    public static String userInputID() {
        print("Enter Student ID number: ");
        return scan.nextLine();
    }

    //Print Simplifier
    public static void println(String toBePrinted) {
        System.out.println(toBePrinted);
    }

    public static void print(String toBePrinted) {
        System.out.print(toBePrinted);
    }


}

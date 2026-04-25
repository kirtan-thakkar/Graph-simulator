
import java.util.*;

class Student {

    int marks;
    String course_name = "";
    int credits = 0;
    String s_name = "";
    int s_id = 0;

    Student(int marks, String course_name, int credits, String s_name, int s_id) {
        this.marks = marks;
        this.course_name = course_name;
        this.credits = credits;
        this.s_name = s_name;
        this.s_id = s_id;
    }

    void display() {
        System.out.println("Student details: Name = " + s_name + ", Course = " + course_name + ", Credits = " + credits + ", Marks = " + marks);
    }

}

public class Course {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        Student s1 = new Student(98, "dmgt", 5, "thakkar kirtan", 23);
        Student s2 = new Student(87, "DBMS", 4, "Rahul Patel", 21);
        Student s3 = new Student(92, "OS", 6, "Priya Shah", 22);
        Student s4 = new Student(76, "CN", 3, "Amit Mehta", 20);
        Student s5 = new Student(89, "Java", 5, "Neha Joshi", 24);

        //addinng that in to the arraylist
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);

        for(Student s : students){
            s.display();
        }
    }   
}

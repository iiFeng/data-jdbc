import service.Student;
import service.StudentService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        StudentService studentService = new StudentService();
        List<Student> list = studentService.getStudents();
        System.out.println("user list");
        list.forEach(System.out::println);

        System.out.println("insert student");
        System.out.println(studentService.insertStudent("ifeng",false,6,99));

        System.out.println("update user name by id");
        Student student = studentService.getStudentById(4);
        student.setName("apple");
        System.out.println(studentService.updateStudent(student));

        System.out.println("delete student by name");
        studentService.deleteStudentByName("ifeng");

        List<Student> listStudent = studentService.getStudents();
        System.out.println("user listStudent");
        listStudent.forEach(System.out::println);
    }
}

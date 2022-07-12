package Controller;

import Model.Student;
import Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCrudController {
    public Student searchStudent(String studentId) throws SQLException, ClassNotFoundException {
        Student student = new Student();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM student WHERE student_id = ?", studentId);

        while(resultSet.next()){
            student.setStudentId(resultSet.getString(1));
            student.setStudentName(resultSet.getString(2));
            student.setEmail(resultSet.getString(3));
            student.setContact(resultSet.getString(4));
            student.setAddress(resultSet.getString(5));
            student.setNic(resultSet.getString(6));
        }
        return student;
    }
}

package Controller;

import Model.Student;
import Util.CrudUtil;

import javax.management.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public static String generateNewId() throws SQLException, ClassNotFoundException {
            ResultSet set = CrudUtil.executeQuery("SELECT * FROM student ORDER BY student_id DESC LIMIT 1");
            int a=0;
            String newVersion;
            if (set.next()){
                String version = set.getString(1);
                int i = (Integer.parseInt(version.substring(1, version.length()))+1);

                if (i==100) {
                    newVersion = "S0" +  i;
                }else {
                    newVersion = "S00" + i;
                }
                return newVersion;

            }else{
                return "S001";
            }
    }
}

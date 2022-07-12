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

    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT student_id FROM student ORDER BY student_id DESC");


        String newUserId = "";
        int integer = 0;

        String lastUserId = resultSet.getString(1);
        String[] split = lastUserId.split("[A-z]");
        if(split.length>0){
            integer = Integer.valueOf(split[2]);
            ++integer;
        }


        if(resultSet.next()){
            if (integer>=100) {
                newUserId = "S00-" + String.valueOf(integer) ;
            }else if(integer>=10){
                newUserId = "S00-0" + String.valueOf(integer);
            }else{
                newUserId = "S00-00" + String.valueOf(integer);
            }
            return newUserId;

        }else{
            return "S00-001";
        }
    }
}

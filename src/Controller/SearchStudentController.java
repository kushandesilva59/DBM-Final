package Controller;

import Model.Student;
import Util.CrudUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchStudentController {
    public AnchorPane searchContext;
    public TextField txtSearch;
    public TextField txtNic;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtId;
    public TextField txtContact;
    public TextField txtAddress;

    public void backOnaction(ActionEvent event) throws IOException {
       Stage stage = (Stage) searchContext.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/StudentForm.fxml"))));
    }

    public void searcOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM student WHERE student_id = ?", txtSearch.getText());
        if(resultSet.next()){
            txtName.setText(resultSet.getString("student_name"));
            txtAddress.setText(resultSet.getString("address"));
            txtContact.setText(resultSet.getString("contact"));
            txtEmail.setText(resultSet.getString("email"));
            txtId.setText(resultSet.getString("student_id"));
            txtNic.setText(resultSet.getString("nic"));
        }


    }
}

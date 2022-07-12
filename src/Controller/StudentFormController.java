package Controller;

import Model.Student;
import Util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane studentFormContext;
    public TableView<Student> tblStudents;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colMail;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colNIC;
    public Button btnSave;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtNic;
    public TextField txtSearch;

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory("studentName"));
        colMail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory("nic"));

        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setDetails(newValue));

        loadStudents();
    }

    private void setDetails(Student student) {
        txtName.setText(student.getStudentName());
        txtEmail.setText(student.getEmail());
        txtAddress.setText(student.getAddress());
        txtContact.setText(student.getContact());
        txtNic.setText(student.getNic());
    }

    private void loadStudents() throws SQLException, ClassNotFoundException {
        ObservableList <Student>studentList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM student");

        while(resultSet.next()){
           studentList.add(new Student(
                   resultSet.getString(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getString(4),
                   resultSet.getString(5),
                   resultSet.getString(6)
           ));
        }
        tblStudents.setItems(studentList);
    }

    public void saveOnAction(ActionEvent event) {

    }

    public void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure ?").showAndWait();
        if(buttonType.get().equals(ButtonType.OK)){
            Student student = tblStudents.getSelectionModel().getSelectedItem();
            tblStudents.getItems().remove(student);
            tblStudents.refresh();
            CrudUtil.executeUpdate("DELETE FROM student WHERE student_id = ?",student.getStudentId());
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted!..").show();
        }
    }

    public void searchOnAction(ActionEvent event) {

    }

    public void newOnAction(ActionEvent event) {

    }
}

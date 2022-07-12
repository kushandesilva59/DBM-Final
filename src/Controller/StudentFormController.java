package Controller;

import Model.Student;
import Util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    public Button btnNew;
    public Button btnDelete;

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory("studentName"));
        colMail.setCellValueFactory(new PropertyValueFactory("email"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colNIC.setCellValueFactory(new PropertyValueFactory("nic"));

        btnDelete.setDisable(true);
        btnSave.setDisable(true);
        btnNew.setDisable(true);

        tblStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                setDetails(newValue);
            }
        }


        );

        loadStudents();
    }

    private void setDetails(Student student) {
        btnNew.setDisable(false);
        btnSave.setText("Update");
        btnDelete.setDisable(false);
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

    public void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(btnSave.getText().equals("Save")){
            Student student = new Student();
            student.setStudentId(StudentCrudController.generateNewId());
            student.setStudentName(txtName.getText());
            student.setAddress(txtAddress.getText());
            student.setEmail(txtEmail.getText());
            student.setContact(txtContact.getText());
            student.setNic(txtNic.getText());

            boolean isSaved = CrudUtil.executeUpdate("INSERT INTO student VALUES (?,?,?,?,?,?)", StudentCrudController.generateNewId(), txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText());

            if(isSaved){
                tblStudents.getItems().add(student);
                tblStudents.refresh();
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!..").show();

            }else{
                new Alert(Alert.AlertType.WARNING,"Something went wrong!..").show();
            }
        }else{
            Student student = tblStudents.getSelectionModel().getSelectedItem();
            boolean isUpdated = CrudUtil.executeUpdate("UPDATE student SET student_name = ? ,email = ?,contact = ?,address = ?,nic = ? WHERE student_id = ?", txtName.getText(), txtEmail.getText(), txtContact.getText(), txtAddress.getText(), txtNic.getText(), student.getStudentId());
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!..").show();
                tblStudents.getItems().clear();
                loadStudents();
            }else {
                new Alert(Alert.AlertType.WARNING,"something went wrong!..").show();
            }
        }

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

    public void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList <Student>student = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM student WHERE student_id = ?", txtSearch.getText());

        while (resultSet.next()){
           student.add(new Student(resultSet.getString(1),     resultSet.getString(2),
                   resultSet.getString(3),
                   resultSet.getString(4),
                   resultSet.getString(5),resultSet.getString(6)));
        }

        tblStudents.setItems(student);
    }

    public void newOnAction(ActionEvent event) {
        txtName.clear();
        txtEmail.clear();
        txtNic.clear();
        txtContact.clear();
        txtAddress.clear();
    btnSave.setText("Save");

    }

    public void keyReleasedOnAction(KeyEvent keyEvent) {
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
    }
}

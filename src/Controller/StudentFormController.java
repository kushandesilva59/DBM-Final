package Controller;

import Model.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    public void saveOnAction(ActionEvent event) {

    }

    public void deleteOnAction(ActionEvent event) {

    }
}

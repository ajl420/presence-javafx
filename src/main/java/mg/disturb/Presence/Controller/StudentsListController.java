package mg.disturb.Presence.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import mg.disturb.Presence.CustomComponent.StudentCellFactory;
import mg.disturb.Presence.Dialog.StudentForm;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.StudentService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentsListController extends AbstractDefaultScreenController implements Initializable {
    @FXML
    private ListView studentListView;
    @FXML
    private JFXTextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentListView.setCellFactory(new StudentCellFactory());
        List<Student> students = StudentService.findAll();
        ObservableList<Student> obsStudentList = FXCollections.observableArrayList(students);
        studentListView.setItems(obsStudentList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch(newValue);
        });
    }

    public void onNewStudent(){
        StudentForm studentForm = new StudentForm();
        studentForm.showAndWait().ifPresent(buttonType->{
            ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
            if(
                    Objects.equals(ButtonBar.ButtonData.FINISH,
                            buttonData)
            ){
                Student student = (Student) contextLoader.load(StudentForm.getClResultKey());
                studentListView.getItems().add(student);
            }
        });
    }

    public void onSearch(String query){
        List<Student> students = StudentService.search(query);
        ObservableList<Student> obsStudentList = FXCollections.observableArrayList(students);
        studentListView.setItems(obsStudentList);
    }
}

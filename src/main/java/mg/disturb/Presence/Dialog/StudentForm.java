package mg.disturb.Presence.Dialog;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.StudentService;
import mg.disturb.Presence.Utils.TextTransformer;
import mg.disturb.Presence.Utils.Validation;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentForm extends AbstractCustomDialog implements Initializable {
    @FXML
    private JFXTextField numInscript;
    @FXML
    private JFXTextField fstNameStud;
    @FXML
    private JFXTextField nameStud;

    @FXML
    protected Label dialogHeadTitle;
    @FXML
    private Label errorMess;
    private String defaultId;
    private Student student;
    public StudentForm(){
        this.fxmlFileName = "student-form.fxml";
        this.defaultId = "";
        loadFxml();
    }
    public static String getClFormKey(){
        return "STUDENT_CL_FORM_KEY";
    }

    public static String getClResultKey(){
        return "STUDENT_CL_RESULT_KEY";
    }
    @Override
    public String _getClResultKey() {
        return getClResultKey();
    }

    @Override
    public String _getClFormKey() {
        return getClFormKey();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefautValue();

        setOnCloseRequest(event -> {
            ButtonType buttonType = (ButtonType) getResult();
            if (buttonType != null){
                if(
                        Objects.equals(ButtonBar.ButtonData.FINISH,
                                buttonType.getButtonData())
                ){
                    if(hasSomeNullValue()){
                        event.consume();
                        errorMess.setText("Tous les champs sont obligatoires");
                    } else if(!isValidNumInscript()){
                        event.consume();
                        errorMess.setText("Le numero d'inscription est invalide");
                    } else if (isTakenNumInscript()) {
                        event.consume();
                        errorMess.setText("Le numero d'inscription est deja prise");
                    } else if (!isValidName()) {
                        event.consume();
                        errorMess.setText("Entrer un nom invalide");
                    } else if (!isValidFstName()) {
                        event.consume();
                        errorMess.setText("Entrer un prenom invalide");
                    } else {
                        onConfirm();
                    }
                }
            }
        });
    }

    private void setDefautValue(){
        student = (Student) cl.load(_getClFormKey());
        cl.clear(_getClFormKey());
        if(student != null){
            defaultId = student.getNumInscri();
            dialogHeadTitle.setText("Modifier un etudiant");
            numInscript.setText(student.getNumInscri());
            numInscript.setEditable(false);
            nameStud.setText(student.getNameStud());
            fstNameStud.setText(student.getFstNameStud());
        }
    }

    private void onConfirm(){
        if(student == null){
            createStudent();
        } else {
            uptadeStudent();
        }
        cl.save(_getClResultKey(),student);
    }

    private void uptadeStudent() {
        student.setNumInscri(numInscript.getText());
        student.setNameStud(nameStud.getText().toUpperCase());
        student.setFstNameStud(
                TextTransformer.capitalize(fstNameStud.getText())
        );
        StudentService.update(student);
    }

    private void createStudent(){
        student = StudentService.create(
                numInscript.getText(),
                nameStud.getText().toUpperCase(),
                TextTransformer.capitalize(fstNameStud.getText())
        );
    }

    private boolean isValidNumInscript(){
        return Validation.isValidNumInscript(numInscript.getText());
    }
    private boolean isTakenNumInscript(){
        return (
                !defaultId.equals(numInscript.getText()) &&
                StudentService.isExist(numInscript.getText())
        );
    }

    private boolean isValidName(){
        return Validation.isValidName(nameStud.getText());
    }

    private boolean isValidFstName(){
        return Validation.isValidName(fstNameStud.getText());
    }
    private boolean hasSomeNullValue(){
        return (
                numInscript.getText().equals("") ||
                fstNameStud.getText().equals("") ||
                nameStud.getText().equals("")
        );
    }
}

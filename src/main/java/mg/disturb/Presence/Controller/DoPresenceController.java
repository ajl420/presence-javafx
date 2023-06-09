package mg.disturb.Presence.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import mg.disturb.Presence.CustomComponent.Icons;
import mg.disturb.Presence.CustomComponent.StudentAbsentCellFactory;
import mg.disturb.Presence.CustomComponent.StudentPresentCellFactory;
import mg.disturb.Presence.Dialog.EventSingleDayForms;
import mg.disturb.Presence.Dialog.RemotePresenceDialog;
import mg.disturb.Presence.Dialog.StudentForm;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.EventMSingleDay;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Service.PresenceService;
import mg.disturb.Presence.Service.StudentService;
import mg.disturb.Presence.Utils.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class DoPresenceController extends AbstractDefaultScreenController implements Initializable {
    public ListView<Student> absStudentListView;
    public ListView<Student> presentStudentListView;
    public JFXTextField searchField;

    public Label eventName;
    public Label betweenTime;
    public Label dateOfPresence;
    public Tab absTab;
    public TabPane tabPane;
    public JFXButton remoteBtn;
    public ImageView remoteIcon;
    private Presence currentPresence;
    private EventM currentEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPresenceTab();
        initAbsentStudentList();
        initPresentStudentList();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            onSearch(newValue);
        });
    }

    private void onSearch(String query) {
        List<Student> students1 = StudentService.search(query);
        List<Student> students2 = new ArrayList<>(students1);
        Collections.copy(students2, students1);
        List<Student> absStudents = PresenceService.absentStudentFrom(currentPresence,students1);
        List<Student> presentStudents = PresenceService.presentStudentFrom(currentPresence,students2);
        ObservableList<Student> obsAbsStudents = FXCollections.observableArrayList(absStudents);
        ObservableList<Student> obsPresentStudents = FXCollections.observableArrayList(presentStudents);
        absStudentListView.setItems(obsAbsStudents);
        presentStudentListView.setItems(obsPresentStudents);
    }

    private void initPresenceTab(){
        currentPresence = (Presence) contextLoader.load();
        currentEvent = currentPresence.getEvent();
        eventName.setText(currentEvent.getEventName());
        String timePresent =
                currentEvent.getBeginTime().toString().replace(":00$","") + "   ~   " +
                        currentEvent.getEndTime().toString().replace(":00$","");
        betweenTime.setText(timePresent);
        dateOfPresence.setText(DateUtils.toFrString(DateUtils.getCurrentDate()));
    }

    private void initAbsentStudentList(){
        absStudentListView.setCellFactory(
                new StudentAbsentCellFactory(currentPresence, presentStudentListView)
        );
        List<Student> absStudents = PresenceService.absentStudentFrom(currentPresence);
        ObservableList<Student> obsAbsStudents = FXCollections.observableArrayList(absStudents);
        absStudentListView.setItems(obsAbsStudents);
    }

    private void initPresentStudentList(){
        presentStudentListView.setCellFactory(
                new StudentPresentCellFactory(currentPresence, absStudentListView)
        );
        List<Student> presentStudents = PresenceService.presentStudentFrom(currentPresence);
        ObservableList<Student> obsPresentStudents = FXCollections.observableArrayList(presentStudents);
        presentStudentListView.setItems(obsPresentStudents);
    }

    public void showAbsTab(ActionEvent actionEvent) {
        Tab secondTab = tabPane.getTabs().get(1);
        tabPane.getSelectionModel().select(secondTab);
    }

    public void remotePresence(ActionEvent actionEvent) {
        Icons.load("remote_on");
        RemotePresenceDialog remotePresenceDialog = new RemotePresenceDialog();
        remotePresenceDialog.showAndWait().ifPresent(buttonType->{
            ButtonBar.ButtonData buttonData = ((ButtonType)buttonType).getButtonData();
            if(
                    Objects.equals(ButtonBar.ButtonData.FINISH,
                            buttonData)
            ){
                showAbsTab(actionEvent);
                remoteBtn.setDisable(true);
                remoteIcon.setImage(Icons.load("remote_on"));
                Thread thread = new Thread(()->{
                    Socket socket = (Socket) contextLoader.load(RemotePresenceDialog.getClResultKey());
                    try {
                        onRemoteAccepted(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                thread.start();
            }
        });
    }

    private void onRemoteAccepted(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String numInscript = "";
        int attempt = 5;
        do {
            numInscript = reader.readLine();
            String finalNumInscript = numInscript;
            if(finalNumInscript == null){
                attempt--;
            }
            System.out.println(finalNumInscript);
            List<Student> filtredStudent = absStudentListView
                    .getItems()
                    .stream()
                    .filter(student1 -> student1.getNumInscri().equals(finalNumInscript))
                    .toList();
            System.out.println(filtredStudent);
            if(filtredStudent.size() == 1){
                Student student = filtredStudent.get(0);
                PresenceService.setToPresent(currentPresence,student);
                Platform.runLater(()->{
                    absStudentListView.getItems().removeIf(
                            student1 -> student1.getNumInscri().equals(finalNumInscript)
                    );
                    presentStudentListView.getItems().add(student);
                });
            } else {
                System.out.println("Cette eleve n'existe pas");
            }
        } while (attempt > 0);
        System.out.println("Tapaka pory oooooo!");
        Platform.runLater(()->{
            remoteBtn.setDisable(false);
            remoteIcon.setImage(Icons.load("remote_off"));
        });
    }
}


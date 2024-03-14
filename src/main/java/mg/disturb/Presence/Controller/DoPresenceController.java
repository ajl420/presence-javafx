package mg.disturb.Presence.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.net.httpserver.HttpServer;
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
import mg.disturb.Presence.Dialog.RemotePresenceDialog;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Navigation.Navigator;
import mg.disturb.Presence.Service.PresenceService;
import mg.disturb.Presence.Service.StudentService;
import mg.disturb.Presence.Utils.DateUtils;
import java.io.*;
import java.net.URL;
import java.util.*;

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
    public ImageView remoteIcon2;
    public JFXButton remoteBtn2;
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
                currentEvent.getBeginTime().toString().replaceAll(":00$","") + "   ~   " +
                        currentEvent.getEndTime().toString().replaceAll(":00$","");
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
                remoteBtn2.setDisable(true);
                remoteIcon.setImage(Icons.load("remote_on"));
                remoteIcon2.setImage(Icons.load("remote_on"));

                Thread thread = new Thread(()->{
                    HttpServer httpServer = (HttpServer) contextLoader.load(RemotePresenceDialog.getClResultKey());
                    try {
                        onRemoteAccepted(httpServer);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                thread.start();
            }
        });
    }

    private void onRemoteAccepted(HttpServer httpServer) throws IOException {
         httpServer.createContext("/connection-test", httpExchange -> {
            String method = httpExchange.getRequestMethod();
            if(method.equals("GET")){
                OutputStream outStream = httpExchange.getResponseBody();
                StringBuilder sb = new StringBuilder("Hello from server");
                String resStr = sb.toString();
                httpExchange.sendResponseHeaders(200,resStr.length());
                outStream.write(resStr.getBytes());
                outStream.flush();;
                outStream.close();
            }
        });

         httpServer.createContext("/set-to-present",httpExchange -> {
             String method = httpExchange.getRequestMethod();
             if (method.equals("POST")){
                 InputStream inStream = httpExchange.getRequestBody();
                 OutputStream outStream = httpExchange.getResponseBody();
                 Scanner sc = new Scanner(inStream);
                 String data = sc.nextLine();
                 String[] datas = data.split("&");
                 String numInscript = datas[0].replace("numInscript=","");
                 String resStr = "";

                 List<Student> filtredStudent = absStudentListView
                         .getItems()
                         .stream()
                         .filter(student1 -> student1.getNumInscri().equals(numInscript))
                         .toList();
                 if(filtredStudent.size() == 1){
                     Student student = filtredStudent.get(0);
                     PresenceService.setToPresent(currentPresence,student);
                     Platform.runLater(()->{
                         presentStudentListView.getItems().add(student);
                         absStudentListView.getItems().removeIf(
                                 student1 -> student1.getNumInscri().equals(numInscript)
                         );
                     });

                     resStr = student.getFstNameStud() +" est maintenant present";
                 } else {
                     Student findedStudent = StudentService.findById(numInscript);
                     if(findedStudent != null){
                         resStr = "Cette eleve est deja present";
                     } else {
                         resStr = "Cette eleve n'existe pas";
                     }
                 }

                 httpExchange.sendResponseHeaders(200,resStr.length());
                 outStream.write(resStr.getBytes());
                 outStream.flush();;
                 outStream.close();
             }
         });

        httpServer.start();
    }

    public void printThisPresence(ActionEvent actionEvent) throws IOException {
        Navigation.getNavigator().navigateTo("presence-sheet","Fiche de Presence",currentPresence);
    }
}


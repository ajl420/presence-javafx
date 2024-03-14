package mg.disturb.Presence.Controller;

import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import mg.disturb.Presence.Model.EventM;
import mg.disturb.Presence.Model.Presence;
import mg.disturb.Presence.Model.Student;
import mg.disturb.Presence.Model.StudentPresence;
import mg.disturb.Presence.Navigation.Navigation;
import mg.disturb.Presence.Service.StudentService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PresenceSheet extends AbstractDefaultScreenController implements Initializable {
    public TableColumn numInscrip;
    public TableColumn nameStud;
    public TableColumn fstNameStud;
    public TableColumn isPresent;
    public TableView tabStudent;

    private Presence currentPresence;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPresence = (Presence) Navigation.getContextLoader().load();
        numInscrip.setCellValueFactory(new PropertyValueFactory<>("numInscri"));
        nameStud.setCellValueFactory(new PropertyValueFactory<>("nameStud"));
        fstNameStud.setCellValueFactory(new PropertyValueFactory<>("fstNameStud"));
        isPresent.setCellValueFactory(new PropertyValueFactory<>("hisPresence"));

        List<Student> studentList = StudentService.findAll();
        List<StudentPresence> studentPresenceList = studentList
                .stream()
                .map(student -> new StudentPresence(student,currentPresence))
                .toList();
        ObservableList<StudentPresence> tableContent = FXCollections.observableArrayList(studentPresenceList);
        tabStudent.setItems(tableContent);
        tabStudent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public void printThis(ActionEvent actionEvent) throws IOException {
        EventM eventM = currentPresence.getEvent();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fiche de presence");
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Fichier PDF","*.pdf ")
        );
        fileChooser.setInitialFileName(
                "presence-"+eventM.getBeginTime()+"~"+eventM.getEndTime()+"-"+
                currentPresence.getPresenceDate()+".pdf"
        );
        File finaleFile = fileChooser.showSaveDialog(Navigation.getConfig().getStage());
        if(finaleFile != null){
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document,page);
            WritableImage image = tabStudent.snapshot(null,null);
            File tempFile = File.createTempFile("temp-save",".png");
            ImageIO.write(SwingFXUtils.fromFXImage(image,null),"png",tempFile);

            contentStream.drawImage(
                    PDImageXObject.createFromFile(tempFile.getAbsolutePath(), document),
                    26, (float) image.getHeight()
            );

            contentStream.close();
            document.save(finaleFile);
            document.close();

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Votre fichier a ete enregistre avec success"
            );
            alert.show();
        }
    }

    @Override
    public void forward() throws IOException {
        Navigation.getNavigator().navigateTo(
                "do-presence",
                "Presence",
                currentPresence
        );
    }
}

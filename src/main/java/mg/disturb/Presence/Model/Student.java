package mg.disturb.Presence.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "STUDENT")
public class Student implements Serializable {
    @Id
    @Column(name = "num_inscri")
    private String numInscri;
    @Column(name = "name_stud")
    private String nameStud;
    @Column(name = "fstname_stud")
    private String fstNameStud;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private Collection<Presence> presences = new ArrayList<Presence>();

    public Student(){
    }

    public void setPresences(Collection<Presence> presences) {
        this.presences = presences;
    }

    public void setFstNameStud(String fstNameStud) {
        this.fstNameStud = fstNameStud;
    }

    public void setNameStud(String nameStud) {
        this.nameStud = nameStud;
    }

    public void setNumInscri(String numInscri) {
        this.numInscri = numInscri;
    }

    public String getNumInscri() {
        return numInscri;
    }

    public String getFstNameStud() {
        return fstNameStud;
    }

    public String getNameStud() {
        return nameStud;
    }

    public Collection<Presence> getPresences() {
        return presences;
    }

    public void addPresence(Presence presence){
        getPresences().add(presence);
    }

    public void removePresence(Presence presence){
        getPresences().removeIf(presence1 -> presence1.getPresenceId() == presence.getPresenceId());
    }

    public boolean isPresentOn(Presence presence){
        return getPresences()
                .stream()
                .anyMatch(presence1 -> presence1.getPresenceId() == presence.getPresenceId());
    }

    @Override
    public String toString() {
        return "Student{" + "\n" +
                "\tnumInscri='" + numInscri + "'\n" +
                "\tnameStud='" + nameStud + "'\n" +
                "\tfstNameStud='" + fstNameStud + "'\n" +
                "\tpresences=" + presences.size() + "\n" +
                '}';
    }
}

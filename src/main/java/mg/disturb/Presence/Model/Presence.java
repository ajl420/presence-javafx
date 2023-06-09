package mg.disturb.Presence.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "PRESENCE")
public class Presence implements Serializable {
    @Id
    @Column(name = "presence_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String presenceId;

    @Column(name = "presence_date")
    @Temporal(TemporalType.DATE)
    private Date presenceDate;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private Collection<Student> students = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE})
    private EventM event;

    public Date getPresenceDate() {
        return presenceDate;
    }

    public String getPresenceId() {
        return presenceId;
    }
    public EventM getEvent() {
        return event;
    }

    public Collection<Student> getStudents() {
        return students;
    }
    public void setEvent(EventM event) {
        this.event = event;
        event.getPresences().add(this);
    }
    public void setPresenceDate(Date presenceDate) {
        this.presenceDate = presenceDate;
    }

    public void addStudent(Student student){
        if(!student.isPresentOn(this)){
            getStudents().add(student);
            student.addPresence(this);
        }
    }
    public void removeStudent(Student student){
        getStudents().removeIf((student1) -> student1.getNumInscri() == student.getNumInscri());
        student.removePresence(this);
    }

    @Override
    public String toString() {
        return "Presence{" + "'\n" +
                "\tpresenceId='" + presenceId + "'\n" +
                "\tpresenceDate=" + presenceDate + "\n" +
                "\tstudents=" + students.size() + "'\n" +
                "\tevent='" + event.getEventName() + "'\n" +
                "\teventId='" + event.getEventId() + "'\n" +
                "\teventBeginTime='" + event.getBeginTime() + "'\n" +

                '}';
    }
}


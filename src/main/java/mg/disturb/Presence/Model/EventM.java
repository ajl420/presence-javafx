package mg.disturb.Presence.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "EVENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EventM implements Serializable {
    @Id
    @Column(name = "event_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String eventId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "begin_time")
    @Temporal(TemporalType.TIME)
    private Time beginTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Time endTime;

//    @OneToMany(cascade = {CascadeType.REMOVE})
//    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
//    private Collection<Presence> presences = new ArrayList<>();
    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public Time getBeginTime() {
        return beginTime;
    }

    public Time getEndTime() {
        return endTime;
    }


    public void setBeginTime(Time beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

//    public Collection<Presence> getPresences() {
//        return presences;
//    }

    @Override
    public String toString() {
        return "EventM{\n" +
                "\teventId='" + eventId + "'\n" +
                "\teventName='" + eventName + "'\n" +
                "\tbeginTime=" + beginTime + "\n" +
                "\tendTime=" + endTime + "\n" +
//                "\tpresences=" + presences.size() + "\n" +
                '}';
    }
}

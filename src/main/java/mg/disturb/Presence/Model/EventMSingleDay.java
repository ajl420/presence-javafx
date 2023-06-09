package mg.disturb.Presence.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class EventMSingleDay extends EventM {
    @Column(name = "event_date")
    @Temporal(TemporalType.DATE)
    private Date eventDate;

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}

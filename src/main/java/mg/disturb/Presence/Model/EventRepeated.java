package mg.disturb.Presence.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class EventRepeated extends Event{
    @Column(name = "repeated_day_id")
    int repeatedDayId;

    public int getRepeatedDayId() {
        return repeatedDayId;
    }

    public void setRepeatedDayId(int repeatedDayId) {
        this.repeatedDayId = repeatedDayId;
    }
}

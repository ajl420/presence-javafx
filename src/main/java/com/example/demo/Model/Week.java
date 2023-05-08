package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "WEEK")
public class Week implements Serializable {
    @Id
    @Column(name = "day_id")
    private int dayId;

    @Column(name = "day_label")
    private String dayLabel;
    @Column(name = "day_name")
    private String dayName;

    @ManyToMany
    private Collection<Event> events = new ArrayList<Event>();

    public Collection<Event> getEvents() {
        return events;
    }

    public String getDayLabel() {
        return dayLabel;
    }

    public int getDayId() {
        return dayId;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public void setDayLabel(String dayLabel) {
        this.dayLabel = dayLabel;
    }

    public void setDayId(int dayId) {
        this.dayId = dayId;
    }
}

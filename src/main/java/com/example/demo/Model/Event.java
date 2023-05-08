package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "EVENT")
public class Event implements Serializable {
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

    @Column(name = "event_date")
    @Temporal(TemporalType.DATE)
    private Date eventDate;
    @ManyToMany
    private Collection<Week> repeated = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.MERGE})
    private Collection<Presence> presences = new ArrayList<>();
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

    public Collection<Week> getRepeated() {
        return repeated;
    }

    public Date getEventDate() {
        return eventDate;
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

    public void setRepeated(Set<Week> repeated) {
        this.repeated = repeated;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setRepeated(Collection<Week> repeated) {
        this.repeated = repeated;
    }

    public void setPresences(Collection<Presence> presences) {
        this.presences = presences;
    }
}

package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;
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

    @Column(name = "is_present")
    private boolean isPresent;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Student student;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Event event;

    public Date getPresenceDate() {
        return presenceDate;
    }

    public String getPresenceId() {
        return presenceId;
    }
    public Event getEvent() {
        return event;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setPresenceDate(Date presenceDate) {
        this.presenceDate = presenceDate;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}


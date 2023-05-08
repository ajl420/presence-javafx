package com.example.demo.Model;

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

    @OneToMany(cascade = {CascadeType.MERGE})
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
}

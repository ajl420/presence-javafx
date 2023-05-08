package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
public class EventRepeated extends Event{
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Week> repeated = new ArrayList<>();

    public Collection<Week> getRepeated() {
        return repeated;
    }
    public void setRepeated(ArrayList<Week> repeated) {
        this.repeated = repeated;
    }

}

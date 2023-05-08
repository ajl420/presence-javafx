package com.example.demo.DAO;

import com.example.demo.Model.Student;
import jakarta.persistence.EntityManager;

import java.util.List;

public class StudentDAO extends AbstractDAOWithModel<Student,String> {
    public StudentDAO(){
        this.modelClass = Student.class;
    }

    public List<Student> searchByName(String critere){
        EntityManager em = getEntityManager();
        return (List<Student>) em.createQuery("SELECT s from Student s WHERE s.nameStud LIKE :name")
                .setParameter("name","%"+critere+"%")
                .getResultList();
    }
}

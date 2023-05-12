package mg.disturb.Presence.DAO;

import mg.disturb.Presence.Model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class StudentDAO extends AbstractDAOWithModel<Student,String> {
    public StudentDAO(){
        this.modelClass = Student.class;
    }

    public List<Student> search(String critere){
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Student> q = cb.createQuery(Student.class);
        Root<Student> root = q.from(Student.class);
        q.select(root);
        q.where(
                cb.or(
                        cb.like(root.get("nameStud"),critere),
                        cb.like(root.get("fstNameStud"),critere),
                        cb.like(root.get("numInscri"),critere)
                )
        );

        return getEntityManager().createQuery(q).getResultList();
    }
}

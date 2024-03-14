package mg.disturb.Presence.Model;

public class StudentPresence extends Student{
    private String hisPresence;

    public StudentPresence(){};
    public StudentPresence(Student s,Presence p) {
        super();
        this.setNumInscri(s.getNumInscri());
        this.setNameStud(s.getNameStud());
        this.setFstNameStud(s.getFstNameStud());
        this.hisPresence = s.isPresentOn(p)? "Present(e)" : "Absent(e)";
    }

    public String getHisPresence(){ return hisPresence; };
}

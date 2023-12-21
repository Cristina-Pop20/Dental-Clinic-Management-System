package domain;
import domain.Patient;

import java.io.Serializable;

public class Appointment implements Serializable,Identifiable<Integer>{
    private Integer id;
    private Patient patient;
    private String date;
    private String time;
    public Appointment(Integer id,Patient patient,String date,String time){
        this.id=id;
        this.patient=patient;
        this.date=date;
        this.time=time;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patient=" + patient +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

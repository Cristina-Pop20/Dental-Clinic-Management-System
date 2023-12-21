package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements  Serializable ,  Identifiable<Integer> {
    private Integer id;
    private String name;
    private int age;

    public Patient(Integer id,String name,int age){
        this.id=id;
        this.name=name;
        this.age=age;
    }
    @Override
    public Integer getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String anotherName){
        this.name=anotherName;
    }
    public void setAge(int anotherAge){
        this.age=anotherAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return age == patient.age && Objects.equals(name, patient.name);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

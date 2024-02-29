package repository;

import domain.Appointment;
import domain.Patient;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class AppointmentRepoBinaryFile extends FileRepo<Integer, Appointment>   {
    public AppointmentRepoBinaryFile(String filename) {
        super(filename);
        //this.readFromFile();
    }

    @Override
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            super.elements = (Map<Integer, Appointment>) ois.readObject();
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filename));
            oos.writeObject(this.elements);
            oos.close();
        }
        catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}

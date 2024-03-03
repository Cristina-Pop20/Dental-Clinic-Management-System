package repository;

import domain.Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class PatientRepoBinaryFile extends FileRepo<Integer, Patient> {
    public PatientRepoBinaryFile(String filename) {
        super(filename);
        //  this.readFromFile();
    }

    @Override
    public void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            super.elements = (Map<Integer, Patient>) ois.readObject();
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
            oos.writeObject(super.elements);
            oos.close();
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}

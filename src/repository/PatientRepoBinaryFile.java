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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            super.elements = (Map<Integer, Patient>) ois.readObject();
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
//    public void readFromFile() {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.filename));
//            try {
//                Object obj = ois.readObject();
//                if (!(obj instanceof Map)) {
//                    throw new RuntimeException("Invalid data format in the file");
//                }
//
//                super.elements.putAll((Map)obj);
//            }
//            catch (Throwable var5) {
//                try {
//                    ois.close();
//                } catch (Throwable var4) {
//                    var5.addSuppressed(var4);
//                }
//                throw var5;
//            }
//        }catch (ClassNotFoundException | IOException var6) {
//            throw new RuntimeException(var6);
//        }
//    }

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

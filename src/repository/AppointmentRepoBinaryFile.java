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
//    public void readFromFile() {
//        File file = new File(this.filename);
//        if (!file.exists()) {
//            Map<Integer, Appointment> emptyMap = new HashMap();
//
//            try {
//                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.filename));
//
//                try {
//                    oos.writeObject(emptyMap);
//                }
//                catch (Throwable var11) {
//                    try {
//                        oos.close();
//                    }
//                    catch (Throwable var6) {
//                        var11.addSuppressed(var6);
//                    }
//
//                    throw var11;
//                }
//
//                oos.close();
//            }
//            catch (IOException var12) {
//                throw new RuntimeException(var12);
//            }
//        }
//
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.filename));
//
//            try {
//                this.elements = (Map)ois.readObject();
//            }
//            catch (Throwable var8) {
//                try {
//                    ois.close();
//                }
//                catch (Throwable var7) {
//                    var8.addSuppressed(var7);
//                }
//
//                throw var8;
//            }
//
//            ois.close();
//        }
//        catch (IOException e) {
//            System.err.println("File not found: " + e.getMessage());
//        }
//        catch (ClassNotFoundException var10) {
//            throw new RuntimeException(var10);
//        }
//    }

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
